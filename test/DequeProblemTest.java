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
