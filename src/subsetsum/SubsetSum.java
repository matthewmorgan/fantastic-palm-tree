package subsetsum;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/**
 *
 * @author matt
 */
public class SubsetSum {

    private static int[] columnIndex;
    private static int[] rowIndex;
    private static boolean[][] matrix;

    /**
     *
     * @param input array of ints that we will search for subsets. positive,
     * negative, or zero, need not be sorted. Duplicate entries are filtered.
     * @param sum target sum for which we want to find an applicable subset. Can
     * be positive, negative, or zero.
     * @return an array of ints containing the addends that sum to the target.
     * Only one solution is returned. Returns an empty int array if no subset of
     * one or more addends in the set sum to the target.
     */
    public static int[] findFastestSolution(int[] input, int sum) {

        int[] pairSolution = SubsetSum.findPair(input, sum);
        if (SubsetSum.sumOfElementsEquals(pairSolution, 0)) {
            return findSolutionWithoutTryingPairsFirst(input, sum);
        } else {
            return pairSolution;
        }
    }

    public static int[] findSolutionWithoutTryingPairsFirst(int[] input, int sum) {
        if (input == null || input.length < 1) {
            return new int[]{};
        }
        int[] set = Arrays.copyOf(input, input.length);
        Arrays.sort(set);
        boolean stopOnFirst;
        columnIndex = generateColumnIndex(set, sum);
        rowIndex = generateRowIndex(set);
        matrix = createMatrix();
        populateFirstRow();
        populateRemainingRows(stopOnFirst = true, sum);
        return getASolutionArray(sum, rowIndex.length - 1);
    }

    /**
     *
     * @param set array of ints that we will search for subsets. positive,
     * negative, or zero, need not be sorted. Duplicate entries are filtered.
     * @param sum target sum for which we want to find an applicable subset. Can
     * be positive, negative, or zero.
     * @return an array of two ints containing the addends that sum to the
     * target, or an empty int array if no pair of addends in the set sum to the
     * target.
     */
    public static int[] findPair(int[] set, int sum) {
        if (set == null || set.length < 1) {
            return new int[]{};
        }
        Set<Integer> copy = new HashSet<>(set.length);
        for (int i : set) {
            copy.add(i);
        }

        for (Integer i : copy) {
            if (copy.contains(sum - i)) {
                return new int[]{i.intValue(), sum - i};
            }
        }
        return new int[]{};
    }

    private static Set<int[]> getAllSolutions(int sum) {
        int[] solutionRows = getAllSolutionStartingRows(sum);
        int solutionIndex;
        Set<int[]> solutions = new HashSet<>();
        //loop through rows, modifying matrix after getting each solution
        while (solutionRows.length > 0) {
            solutionIndex = solutionRows[0];
            solutions.add(getASolutionArray(sum, solutionIndex));
            matrix = getSplicedArray(matrix, solutionIndex);
            rowIndex = getSplicedArray(rowIndex, solutionIndex);
            solutionRows = getSplicedArray(solutionRows, solutionIndex);
            for (int ii = 0; ii < solutionRows.length; ii++) {
                solutionRows[ii]--;
            }
        }
        return solutions;
    }

    private static int[] getASolutionArray(int sum, int startingRow) {

        int remaining = sum;
        int cc = getColumnForSum(sum);
        int rr = startingRow;
        int[] solutionSet = new int[rr + 1];
        int idx = 0;
        while (rr >= 0 && cc > 0) {
            if (matrix[rr][cc]) {
                if (rr == 0 || !matrix[rr - 1][cc]) {
                    solutionSet[idx++] = rowIndex[rr];
                    remaining -= rowIndex[rr];
                    cc = getColumnForSum(remaining);
                }
            }
            rr--;
        }

        return Arrays.copyOfRange(solutionSet, 0, idx);
    }

    private static int[] generateColumnIndex(int[] set, int sum) {
        int lowerBound = getLowestSum(set);
        int upperBound = getHighestSum(set);
        int[] colIdx = new int[(upperBound - lowerBound) + 1];
        int jj = 0;
        for (int ii = lowerBound; ii <= upperBound; ii++) {
            colIdx[jj++] = ii;
        }
        return colIdx;
    }

    private static int getHighestSum(int[] set) {
        int sum = 0;
        for (int i : set) {
            if (i > 0) {
                sum += i;
            }
        }
        return sum;
    }

    private static int getLowestSum(int[] set) {
        int sum = 0;
        for (int i : set) {
            if (i < 0) {
                sum += i;
            }
        }
        return sum;
    }
    private static int[] generateRowIndex(int[] set) {
        int[] idx = Arrays.copyOf(set, set.length);
        return Arrays.stream(idx)
                .mapToObj(i -> i)
                .sorted((a, b) -> b - a)
                .mapToInt(i -> i)
                .toArray();
    }

    private static boolean[][] createMatrix() {
        return new boolean[rowIndex.length][columnIndex.length];
    }

    private static void populateFirstRow() {
        int firstAddend = rowIndex[0];
        for (int ii = 0; ii < columnIndex.length; ii++) {
            matrix[0][ii] = (columnIndex[ii] == firstAddend);
        }
    }

    private static void populateRemainingRows(boolean stopOnFirst, int sum) {
        for (int rr = 1; rr < rowIndex.length; rr++) {
            int addend = rowIndex[rr];
            for (int cc = 0; cc < columnIndex.length; cc++) {
                if (columnIndex[cc] == addend || matrix[rr - 1][cc]) {
                    matrix[rr][cc] = true;
                    if (stopOnFirst && (columnIndex[cc] == sum && columnIndex[cc] > 0)) {
                        stopAndSplice(rr, cc);
                        return;
                    }
                } else {
                    int subsetSum = columnIndex[cc] - addend;
                    int subsetSumIndex = getColumnForSum(subsetSum);
                    if (subsetSumIndex >= 0 && subsetSumIndex < columnIndex.length) {
                        matrix[rr][cc] = matrix[rr - 1][subsetSumIndex];
                    }
                }
            }
        }
    }

    private static void stopAndSplice(int row, int col) {
        boolean[][] newMatrix = new boolean[row + 1][col + 1];
        int[] newRowIndex = new int[row + 1];
        int[] newColumnIndex = new int[col + 1];

        for (int ii = 0; ii <= row; ii++) {
            newRowIndex[ii] = rowIndex[ii];
            for (int jj = 0; jj <= col; jj++) {
                newMatrix[ii][jj] = matrix[ii][jj];
            }
        }
        for (int jj = 0; jj <= col; jj++) {
            newColumnIndex[jj] = columnIndex[jj];
        }

        rowIndex = newRowIndex;
        columnIndex = newColumnIndex;
        matrix = newMatrix;
    }

    private static int getColumnForSum(int sum) {
        for (int ii = 0; ii < columnIndex.length; ii++) {
            if (columnIndex[ii] == sum) {
                return ii;
            }
        }
        return -1;
    }

    private static boolean solutionExists(int sum) {
        int sumColumn = getColumnForSum(sum);
        if (sumColumn < 0) {
            return false;
        }
        for (boolean[] row : matrix) {
            if (row[sumColumn]) {
                return true;
            }
        }
        return false;
    }

    private static int[] getAllSolutionStartingRows(int sum) {
        int sumColumn = getColumnForSum(sum);
        if (sumColumn < 0) {
            return new int[]{};
        }
        int[] solutionRows = new int[matrix.length];
        int ii = 0;
        for (int rr = 0; rr < matrix.length; rr++) {
            if (matrix[rr][sumColumn]) {
                solutionRows[ii] = rr;
            }
        }
        return Arrays.copyOfRange(solutionRows, 0, ii);
    }

    private static boolean[][] getSplicedArray(boolean[][] oldArray, int row) {
        boolean[][] newArray = new boolean[oldArray.length - 1][oldArray[0].length];
        int oldIndex = oldArray.length - 1;
        int newIndex = newArray.length - 1;
        while (oldIndex > 0) {
            if (oldIndex != row) {
                newArray[newIndex--] = oldArray[oldIndex--];
            } else {
                oldIndex--;
            }
        }
        return newArray;
    }

    private static int[] getSplicedArray(int[] oldArray, int row) {
        int[] newArray = new int[oldArray.length - 1];
        int oldIndex = oldArray.length - 1;
        int newIndex = newArray.length - 1;
        while (oldIndex > 0) {
            if (oldIndex != row) {
                newArray[newIndex--] = oldArray[oldIndex--];
            } else {
                oldIndex--;
            }
        }
        return newArray;
    }

    public static boolean sumOfElementsEquals(int[] result, int sum) {
        int resultSum = 0;
        for (int i : result) {
            resultSum += i;
        }
        return resultSum == sum;
    }



    private static int[] getRandomInts(int lowerBound, int upperBound, int numberOfInts) {
        Set<Integer> randomInts = new HashSet<>(numberOfInts);

        int diff = upperBound - lowerBound;
        Random ran = new Random();
        while (randomInts.size() < numberOfInts) {
            int val = ran.nextInt(diff);
            randomInts.add(lowerBound + val);
        }
        return randomInts.stream().mapToInt(i -> i).toArray();
    }

    private static int pickRandomSum(int[] set) {
        Random ran = new Random();
        int numberOfInts = set.length;
        int idx1 = ran.nextInt(numberOfInts - 1);
        int idx2 = idx1;
        while (idx2 == idx1) {
            idx2 = ran.nextInt(numberOfInts - 1);
        }
        return set[idx1] + set[idx2];
    }
}
