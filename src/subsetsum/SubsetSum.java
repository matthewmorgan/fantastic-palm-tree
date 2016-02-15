package subsetsum;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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
     * @param set array of ints that we will search for subsets. positive,
     * negative, or zero, need not be sorted. Duplicate entries are filtered.
     * @param sum target sum for which we want to find an applicable subset. Can
     * be positive, negative, or zero.
     * @return an array of ints containing the addends that sum to the target.
     * Only one solution is returned. Returns an empty int array if no subset of
     * one or more addends in the set sum to the target.
     */
    public static int[] findAnySolution(int[] set, int sum) {
        if (set == null || set.length < 1) {
            return new int[]{};
        }

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

        boolean stopOnFirst;
        columnIndex = generateColumnIndex(set, sum);
        rowIndex = generateRowIndex(set);
        matrix = createMatrix();

        populateFirstRow();
        populateRemainingRows(stopOnFirst = false, sum);
        return SubsetSum.getAllSolutions(sum).stream()
                .filter(solution -> solution.length == 2)
                .findAny().orElse(new int[]{});
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
            for (int ii=0; ii<solutionRows.length;ii++){
                solutionRows[ii]--;
            }
        }
        return solutions;
    }

    private static int[] getASolutionArray(int sum, int startingRow) {
        Set<Integer> solutionSet = new HashSet<>();
        int remaining = sum;
        int cc = getColumnForSum(sum);
        int rr = startingRow;

        while (rr >= 0 && cc > 0) {
            if (matrix[rr][cc]) {
                if (rr == 0 || !matrix[rr - 1][cc]) {
                    solutionSet.add(rowIndex[rr]);
                    remaining -= rowIndex[rr];
                    cc = getColumnForSum(remaining);
                }
            }
            rr--;
        }

        return solutionSet.stream()
                .sorted((a, b) -> a - b)
                .mapToInt(i -> i)
                .toArray();
    }

    private static int[] generateColumnIndex(int[] set, int sum) {
        Map<Boolean, Set<Integer>> sumsHeader = Arrays.stream(set)
                .mapToObj(i -> i)
                .sorted()
                .collect(Collectors.partitioningBy(el -> el < 0, Collectors.toSet()));
        Integer lowerBound = sumsHeader.get(true)
                .stream()
                .reduce(0, Integer::sum);
        Integer upperBound = sumsHeader.get(false)
                .stream()
                .reduce(0, Integer::sum);
        return IntStream.rangeClosed(lowerBound, upperBound)
                .toArray();
    }

    private static int[] generateRowIndex(int[] set) {
        return Arrays.stream(set)
                .mapToObj(i -> i)
                .sorted((a,b) -> b-a)
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
                    if (columnIndex[cc] == sum && columnIndex[cc] > 0) {
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
        return Arrays.stream(matrix)
                .anyMatch(row -> row[sumColumn]);
    }

    private static int[] getAllSolutionStartingRows(int sum) {
        int sumColumn = getColumnForSum(sum);
        if (sumColumn < 0) {
            return new int[]{};
        }
        Set<Integer> solutionRows = new HashSet<>();
        for (int rr = 0; rr < matrix.length; rr++) {
            if (matrix[rr][sumColumn]) {
                solutionRows.add(rr);
            }
        }
        return solutionRows.stream()
                .mapToInt(i -> i)
                .toArray();
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
}
