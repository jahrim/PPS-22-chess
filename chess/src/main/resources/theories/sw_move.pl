sw_move(Xs, Ys, X, Y) :- sw_move(Xs, Ys, 1, X, Y) .

sw_move(Xs, Ys, N, X, Y) :- X is Xs - N, Y is Ys - N .

sw_move(Xs, Ys, N, X, Y) :- N1 is N + 1, sw_move(Xs, Ys, N1, X, Y) .
