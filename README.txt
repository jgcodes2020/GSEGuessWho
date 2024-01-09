GSE Guess Who (WIP)
======================
Current code can be run by copy-pasting the contents of the source folder into Eclipse.
Only supports the question asking stuff as of now. We'll un-scuff it next year.

- Jacky G, Winston Z, Chapman Y; Dec. 22, 2023

NOTE 1
----------
Let X represent whether P1 is answering (equivalently, P2 is asking) (1 if P1 is answering, 0 if not)
Let Y represent the last answer (1 for yes, 0 for no).
Let Q represent whether the winner is P1 (1 for P1 win, 0 for P2 win).

We draw a truth table:
X | Y | Q
--+---+--
0 | 0 | 0
0 | 1 | 1
1 | 0 | 1
1 | 1 | 0

The truth table shows that Q = X XOR Y. This is why we use ^ (the XOR operator).