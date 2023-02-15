nw_move(FS, RS, FD, RD) :- nw_move(FS, RS, 1, FD, RD) . 

nw_move(FS, RS, X, FD, RD) :- FD is FS - X, RD is RS + X .

nw_move(FS, RS, X, FD, RD) :- X1 is X + 1, nw_move(FS, RS, X1, FD, RD) .