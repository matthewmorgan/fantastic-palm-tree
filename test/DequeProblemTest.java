/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import dequeproblem.DequeProblem;
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
public class DequeProblemTest {

    public DequeProblemTest() {
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
        int[] answer = DequeProblem.findAddendsThatSum(input, targetSum);
        assertTrue(compareElements(answer, expected));
    }

    @Test
    public void noAnswerReturnsEmpty() {
        int[] expected = {};
        int[] input = {4, 6, 8, 9};
        int targetSum = 3;
        assertTrue(compareElements(DequeProblem.findAddendsThatSum(input, targetSum), expected));
    }

    @Test
    public void smallInputArrayReturnsEmptyArray() {
        int[] expected = {};
        int[] input = {1};
        int targetSum = 100;
        assertTrue(compareElements(DequeProblem.findAddendsThatSum(input, targetSum), expected));
    }

    //tests below for n inputs
    @Test
    public void findsNAddendsThatSumToTarget() {
        int[] expected = {1, 2, 3, 4};
        int[] input = {1, 2, 3, 4, 5, 7};
        int targetSum = 10;
        assertTrue(compareElements(DequeProblem.findAddendsThatSum(input, targetSum, 4), expected));
    }

    @Test
    public void findsOneAddendThatEqualsTarget() {
        int[] expected = {25};
        int[] input = {2, 4, 6, 25};
        int targetSum = 25;
        assertTrue(compareElements(DequeProblem.findAddendsThatSum(input, targetSum, 1), expected));
    }

    @Test
    public void handlesNegativeNumbers() {
        int[] expected = {2, 4, -6};
        int[] input = {2, 4, -6, 24, 34};
        int targetSum = 0;
        assertTrue(compareElements(DequeProblem.findAddendsThatSum(input, targetSum, 3), expected));
    }

    private boolean compareElements(int[] one, int[] two) {
        //arrays must be sorted!
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
