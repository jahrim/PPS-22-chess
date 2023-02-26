% w_move(+int, +int, -int, -int): finds all the possible positions to the west of the specified position (X1, Y1)
%						          (not fully relational)
w_move(X1, Y1, X2, Y1) :- X2 is X1 - 1; w_move(X1 - 1, Y1, X2, Y1).