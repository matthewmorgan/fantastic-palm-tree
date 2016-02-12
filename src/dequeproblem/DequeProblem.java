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

    public static int[] findAddendsThatSum(int[] candidates, int targetSum, int... answerSize) {
        int startIndex = 0;
        int numberOfAddends = answerSize.length != 0 ? answerSize[0] : 2;
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
        return getSubarray(candidates, startIndex, endIndex);
    }

    private static int[] getSubarray(int[] candidates, int start, int end) {
        int[] subArray = new int[end - start];
        int ii = 0;
        for (int jj = start; jj < end; jj++) {
            subArray[ii++] = candidates[jj];
        }
        return subArray;
    }

    private static int getSum(int[] candidates, int start, int end) {
        int sum = 0;
        for (int ii = start; ii < end; ii++) {
            sum += candidates[ii];
        }
        return sum;
    }
}
