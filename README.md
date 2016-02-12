The Java class DequeProblem accepts up to three inputs:

```
int[] input, int sum, <int numberOfAddends>
```

If numberOfAddends is not provided, a default of 2 will be assigned.

These were the instructions and example inputs/outputs provided:

```
1. Give an array of numbers, return 2 numbers that has sum of a given number, or empty array if no valid answer, for example

int[] findNumbers(int[] input, int sum)

input = {1, 2, 3, 7, 10}, sum = 17, result: {7, 10}
input = {4, 6, 8, 9}, sum = 3, result: {}

2. Instead of 2 numbers, return n numbers that has sum of a given number, e.g.

input = {1, 2, 3, 4, 5, 7}, sum = 10, result: {1, 2, 3, 4}
input = {2, 4, 6, 25}, sum = 25, result: {25}
input = {2, 4, -6, 24, 34}, sum = 0, result: {2, 4, -6}

In the case of multiple solutions, only need to return the first one found.
```

Assumptions:

1. If a solution exists, the addends will be N (numberOfAddends) sequential elements of the input array.

2. Inputs will be of the correct type and not null.

3. The first two inputs will always be provided, the third one is optional.

Design decisions:

1. The class uses a static method since there is no need to store state or create multiple instances.

2. I could have used overloaded signatures instead of varargs.  Either would have worked for this exercise.

