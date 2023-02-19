% e_move(+int, +int, -int, -int): finds all the possible positions to the east of the specified position (X1, Y1)
%						          (not fully relational)
e_move(X1, Y1, X2, Y1) :- X2 is X1 - 1; e_move(X1 - 1, Y1, X2, Y1).