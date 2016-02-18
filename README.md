The Java class `SubsetSum` accepts two inputs:

```
int[] input, int sum
```

The class provides three public methods:

1) Returns two addends that total sum, or an empty int[] if no solution is found:

 ```
int [] findPair(int[] input, int sum)
```

and

2) Returns an int[] containing addends that total sum, or an empty int[] if no solution is found

```
int [] findSolutionWithoutTryingPairsFirst(int[] input, int sum)
```

3) Returns an int[] containing addends that total sum, or an empty int[] if no solution is found.  Tries `findPair` first, as it is usually fast.

```
int [] findFastestSolution(int[] input, int sum)
```


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

Design decisions:

1. The class uses a static method since there is no need to store state or create multiple instances.

2. Duplicate entries in the input set are filtered out.

3. I experimented with Streams and found they slowed things down considerably in certain places, so I used primitives and loops in those spots. 

4. The public API returns the subset with the elements sorted from smallest to largest.