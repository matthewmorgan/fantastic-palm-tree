/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

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
        int[] expected = {7, 10};
        int[] input = {1, 2, 3, 7, 10};
        int targetSum = 17;
        int[] answer = SubsetSum.findPair(input, targetSum);
        assertTrue(compareElements(answer, expected));
    }

    @Test
    public void noAnswerReturnsEmpty() {
        int[] expected = {};
        int[] input = {4, 6, 8, 9};
        int targetSum = 3;
        assertTrue(compareElements(SubsetSum.findPair(input, targetSum), expected));
    }

    @Test
    public void smallInputArrayReturnsEmptyArray() {
        int[] expected = {};
        int[] input = {1};
        int targetSum = 100;
        assertTrue(compareElements(SubsetSum.findPair(input, targetSum), expected));
    }

    @Test
    public void findsPairForAllNegativeInputs() {
        int[] expected = {-4, -2};
        int[] input = {-2, -4, -6};
        int targetSum = -6;
        assertTrue(compareElements(SubsetSum.findPair(input, targetSum), expected));
    }

    @Test
    public void findsPairForAllNegativeInputsWhenInputOrderSwapped() {
        int[] expected = {-4, -2};
        int[] input = {-2, -6, -4};
        int targetSum = -6;
        assertTrue(compareElements(SubsetSum.findPair(input, targetSum), expected));
    }

    //tests below for n inputs
    @Test
    public void noAnswerReturnsEmptyForAnySubset() {
        int[] expected = {};
        int[] input = {4, 6, 8, 9};
        int targetSum = 3;
        assertTrue(compareElements(SubsetSum.findAnySolution(input, targetSum), expected));
    }

    @Test
    public void smallInputArrayReturnsEmptyArrayForAnySubset() {
        int[] expected = {};
        int[] input = {1};
        int targetSum = 100;
        assertTrue(compareElements(SubsetSum.findAnySolution(input, targetSum), expected));
    }

    @Test
    public void findsNAddendsThatSumToTarget() {
        int[] expected = {3, 7};
        int[] input = {1, 2, 3, 4, 5, 7};
        int targetSum = 10;
        assertTrue(compareElements(SubsetSum.findAnySolution(input, targetSum), expected));
    }

    @Test
    public void findsOneAddendThatEqualsTarget() {
        int[] expected = {25};
        int[] input = {2, 4, 6, 25};
        int targetSum = 25;
        assertTrue(compareElements(SubsetSum.findAnySolution(input, targetSum), expected));
    }

    @Test
    public void handlesNegativeNumbers() {
        int[] expected = {-6, 2, 4};
        int[] input = {2, 4, -6, 24, 34};
        int targetSum = 0;
        assertTrue(compareElements(SubsetSum.findAnySolution(input, targetSum), expected));
    }

    @Test
    public void handlesManyNegativeNumbers() {
        int[] expected = {-4, -2};
        int[] input = {-6, -4, -2};
        int targetSum = -6;
        assertTrue(compareElements(SubsetSum.findAnySolution(input, targetSum), expected));
    }

    @Test
    public void handlesUnsortedInputs() {
        int[] input = {1, 7, 5, 10, 15};
        int targetSum = 21;
        int[] expected = {1, 5, 15};
        assertTrue(compareElements(SubsetSum.findAnySolution(input, targetSum), expected));
    }

    private boolean compareElements(int[] one, int[] two) {
        if (one.length != two.length) {
            return false;
        }

        for (int ii = 0; ii < one.length; ii++) {
            if (one[ii] != two[ii]) {
                return false;
            }
        }
        return true;
    }
}
