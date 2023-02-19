% n_move(+int, +int, -int, -int): finds all the possible positions to the north of the specified position (X1, Y1)
%						          (not fully relational)
n_move(X1, Y1, X1, Y2) :- Y2 is Y1 + 1; n_move(X1, Y1 + 1, X1, Y2).