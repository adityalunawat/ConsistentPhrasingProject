Overview of the solution:
Prerequisites : Java 1.8 should be installed
Intellij should be installed

How to run?
1) Import the project via Intellij
2) Compile the class ConsistentPhrasing
3) It reads input from input.txt and writes to output.txt. The path can be changed in the class.
4) Read output from output.txt
...
1. What can you say about the complexity of your code?
   O(n) for scanning the complete input.
   O(m x n x m) for computing the similar lines with a single word un-common.
   m -> No. of rows in the file
   n -> No. of words in the row.
   
   ...
2. How will your algorithm scale?
   1) If we know that only words at pre-defined indexes are changing, for ex: here mainly the 1st and the last words were changing,
   then we don't need to scan over the complete input.
   
    2) We can remove a single word at a time in the complete and match the complete string with all the other rows. 

3. If you had two weeks to do this task, what would you have done differently? What would be
   better?
   I would go for more clarity and then decide the solution.
   I would create new string rows by eliminating a word in each iteration and then see the result.