white_pawn_capture(F,R,FD,RD) :- RD is R + 1, pawn_capture_file(F,R,FD,RD) .

pawn_capture_file(F,R,FD,RD) :- FD is F + 1 .
pawn_capture_file(F,R,FD,RD) :- FD is F - 1 .