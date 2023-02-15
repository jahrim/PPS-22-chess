south_east(Xs, Ys, X, Y) :- south_east(Xs, Ys, 1, X, Y) .

south_east(Xs, Ys, N, X, Y) :- X is Xs + N, Y is Ys - N .

south_east(Xs, Ys, N, X, Y) :- N1 is N + 1, south_east(Xs, Ys, N1, X, Y) .
