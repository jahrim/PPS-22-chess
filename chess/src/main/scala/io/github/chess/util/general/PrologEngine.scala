/*
 * MIT License
 * Copyright (c) 2023 Cesario Jahrim Gabriele & Derevyanchenko Maxim & Felice Mirko & Kentpayeva Madina
 *
 * Full license description available at: https://github.com/jahrim/PPS-22-chess/blob/master/LICENSE
 */
package io.github.chess.util.general

import alice.tuprolog.*

trait PrologEngine:

  def solveWithSuccess(goal: Term): Boolean

  def solveOneAndGetTerm(goal: Term, term: String): Term

  def solveOneAndGetTerms(goal: Term, terms: String*): Map[String, Term]

  def solveAll(goal: Term, terms: String*): LazyList[Map[String, Term]]

  def solveAll(goal: Term, term: String): LazyList[Term]

object PrologEngine:

  given Conversion[String, Term] = Term.createTerm(_)

  given Conversion[Seq[_], Term] = _.mkString("[", ",", "]")

  given Conversion[String, Theory] = fileName =>
    Theory.parseLazilyWithStandardOperators(getClass.getResourceAsStream(fileName))

  def apply(theories: Theory*): PrologEngine = PrologEngineImpl(theories*)

  private case class PrologEngineImpl(theories: Theory*) extends PrologEngine:

    private val engine: Term => LazyList[SolveInfo] =
      val prolog = Prolog()
      theories.foreach(prolog.addTheory)
      goal =>
        new Iterable[SolveInfo] {

          override def iterator: Iterator[SolveInfo] = new Iterator[SolveInfo]:
            var solution: Option[SolveInfo] = Some(prolog.solve(goal))

            override def hasNext: Boolean = solution match
              case Some(value) => value.isSuccess || value.hasOpenAlternatives
              case None        => false

            override def next(): SolveInfo =
              val sol = solution match
                case Some(value) => value
                case None        => throw Exception()
              solution = if (sol.hasOpenAlternatives) Some(prolog.solveNext()) else None
              sol
        }.to(LazyList)

    override def solveWithSuccess(goal: Term): Boolean =
      engine(goal).map(_.isSuccess).headOption.contains(true)

    override def solveOneAndGetTerm(goal: Term, term: String): Term =
      engine(goal).headOption.map(extractTerm(_, term)) match
        case Some(value) => value
        case None        => "no."

    override def solveOneAndGetTerms(goal: Term, terms: String*): Map[String, Term] =
      engine(goal).headOption match
        case Some(value) => terms.map(term => (term, extractTerm(value, term))).toMap
        case None        => Map.empty

    override def solveAll(goal: Term, terms: String*): LazyList[Map[String, Term]] =
      engine(goal).map(solution =>
        (for (term <- terms)
          yield (term, extractTerm(solution, term))).toMap
      )

    override def solveAll(goal: Term, term: String): LazyList[Term] =
      engine(goal).map(extractTerm(_, term))

    private def extractTerm(solveInfo: SolveInfo, s: String): Term =
      solveInfo.getTerm(s)
