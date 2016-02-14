/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dequeproblem;

/**
 *
 * @author matt
 */
public class DequeProblem {

    /**
    * @param candidates addends to test
    * @param targetSum sum to seek
    * @param answerSize optional size for solution array
    * @return the array of addends or in the case of no solution, an empty array of type int
    **/
    
    private static final int DEFAULT_SOLUTION_SIZE = 2;
    
    public static int[] findAddendsThatSum(int[] candidates, int targetSum) {
        int startIndex = 0;
        int numberOfAddends= DEFAULT_SOLUTION_SIZE;
        int endIndex = startIndex + numberOfAddends;
        int maxIndex = candidates.length;
        if (endIndex > maxIndex || numberOfAddends < 1) {
            return new int[]{};
        } 

        while (getSum(candidates, startIndex, endIndex) != targetSum) {
            if (endIndex < maxIndex) {
                endIndex++;
                startIndex++;
            } else if (startIndex < (maxIndex - numberOfAddends)) {
                startIndex++;
                endIndex = startIndex + numberOfAddends;
            } else {
                return new int[]{};
            }
        }
        return slice(candidates, startIndex, endIndex);
    }

    /**
     * 
     * @param candidates the source array
     * @param start the starting index from which to begin the slice
     * @param end the ending index at which to end the slice
     * @return a slice of the candidates array from start to end
     */
    private static int[] slice(int[] candidates, int start, int end) {
        int[] subArray = new int[end - start];
        int ii = 0;
        for (int jj = start; jj < end; jj++) {
            subArray[ii++] = candidates[jj];
        }
        return subArray;
    }

    /**
     * 
     * @param candidates the source array
     * @param start first index of addend in sum
     * @param end last index of addend in sum
     * @return summation of addends from start to end
     */
    
    private static int getSum(int[] candidates, int start, int end) {
        int sum = 0;
        for (int ii = start; ii < end; ii++) {
            sum += candidates[ii];
        }
        return sum;
    }
}
