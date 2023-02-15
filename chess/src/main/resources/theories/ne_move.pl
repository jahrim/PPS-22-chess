ne_move(FS, RS, FD, RD) :- ne_move(FS, RS, 1, FD, RD) . 

ne_move(FS, RS, X, FD, RD) :- FD is FS + X, RD is RS + X .

ne_move(FS, RS, X, FD, RD) :- X1 is X + 1, ne_move(FS, RS, X1, FD, RD) .