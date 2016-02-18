/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import subsetsum.SubsetSum;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author matt
 */
public class SubsetSumTest {

    public SubsetSumTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void getAddendsOfTargetSum() {
        int[] input = {1, 2, 3, 7, 10};
        int targetSum = 17;
        int[] answer = SubsetSum.findPair(input, targetSum);
        assertTrue(SubsetSum.sumOfElementsEquals(answer, targetSum));
    }

    @Test
    public void noAnswerReturnsEmpty() {
        int[] input = {4, 6, 8, 9};
        int targetSum = 3;
        int[] answer = SubsetSum.findPair(input, targetSum);
        assertTrue(SubsetSum.sumOfElementsEquals(answer, 0));
    }

    @Test
    public void smallInputArrayReturnsEmptyArray() {
        int[] input = {1};
        int targetSum = 100;
        int[] answer = SubsetSum.findPair(input, targetSum);
        assertTrue(SubsetSum.sumOfElementsEquals(answer, 0));
    }

    @Test
    public void findsPairForAllNegativeInputs() {
        int[] input = {-2, -4, -6};
        int targetSum = -6;
        int[] answer = SubsetSum.findPair(input, targetSum);
        assertTrue(SubsetSum.sumOfElementsEquals(answer, targetSum));
    }

    @Test
    public void findsPairForAllNegativeInputsWhenInputOrderSwapped() {
        int[] input = {-2, -6, -4};
        int targetSum = -6;
        int[] answer = SubsetSum.findPair(input, targetSum);
        assertTrue(SubsetSum.sumOfElementsEquals(answer, targetSum));
    }

    //tests below for n inputs
    @Test
    public void noAnswerReturnsEmptyForAnySubset() {
        int[] input = {4, 6, 8, 9};
        int targetSum = 3;
        int[] answer = SubsetSum.findFastestSolution(input, targetSum);
        assertTrue(SubsetSum.sumOfElementsEquals(answer, 0));
    }

    @Test
    public void smallInputArrayReturnsEmptyArrayForAnySubset() {
        int[] input = {1};
        int targetSum = 100;
        int[] answer = SubsetSum.findFastestSolution(input, targetSum);
        assertTrue(SubsetSum.sumOfElementsEquals(answer, 0));
    }

    @Test
    public void findsAnyAddendsThatSumToTarget() {
        int[] input = {1, 2, 3, 4, 5, 7};
        int targetSum = 10;
        int[] answer = SubsetSum.findFastestSolution(input, targetSum);
        assertTrue(SubsetSum.sumOfElementsEquals(answer, targetSum));
    }

    @Test
    public void findsOneAddendThatEqualsTarget() {
        int[] input = {2, 4, 6, 25};
        int targetSum = 25;
        int[] answer = SubsetSum.findFastestSolution(input, targetSum);
        assertTrue(SubsetSum.sumOfElementsEquals(answer, targetSum));
    }

    @Test
    public void handlesNegativeNumbers() {
        int[] input = {2, 4, -6, 24, 34};
        int targetSum = 0;
        int[] answer = SubsetSum.findFastestSolution(input, targetSum);
        assertTrue(SubsetSum.sumOfElementsEquals(answer, targetSum));
    }

    @Test
    public void handlesMoreNegativeNumbers() {
        int[] input = {-6, -4, -2};
        int targetSum = -6;
        int[] answer = SubsetSum.findFastestSolution(input, targetSum);
        assertTrue(SubsetSum.sumOfElementsEquals(answer, targetSum));
    }

    @Test
    public void handlesManyMoreNegativeNumbers() {
        int numberOfInts = 100;
        int[] input = getRandomInts(-500, -100, numberOfInts);
        int targetSum = pickRandomSum(input);
        int[] answer = SubsetSum.findFastestSolution(input, targetSum);
        assertTrue(SubsetSum.sumOfElementsEquals(answer, targetSum));
    }

    @Test
    public void handlesUnsortedInputs() {
        int[] input = {1, 7, 5, 10, 15};
        int targetSum = 21;
        int[] answer = SubsetSum.findFastestSolution(input, targetSum);
        assertTrue(SubsetSum.sumOfElementsEquals(answer, targetSum));
    }

    @Test
    public void handlesLargerSetsWithoutUsingJavaSet() {
        int numberOfInts = 10;
        int[] input = getRandomInts(-500, -100, numberOfInts);
        int targetSum = pickRandomSum(input);
        int[] answer = SubsetSum.findSolutionWithoutTryingPairsFirst(input, targetSum);
        assertTrue(SubsetSum.sumOfElementsEquals(answer, targetSum));
    }

    @Test
    public void findsAPairForVeryLargeSets() {
        int numberOfInts = 1000;
        int[] input = getRandomInts(-5000, 5000, numberOfInts);
        int targetSum = pickRandomSum(input);

        int[] answer = SubsetSum.findPair(input, targetSum);
        assertTrue(SubsetSum.sumOfElementsEquals(answer, targetSum));
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
