package com.codingstrain.cs.algorithms.binary;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class CheckSparseUnitTest {

    private boolean isSparseEfficientWay(int num) {
        int current = num;
        int shifted = num >> 1;
        if ((current & shifted) == 0) {
            return true;
        }
        return false;
    }

    @Test
    public void isSparseEfficientWayTest() throws Exception {

        assertTrue(isSparseEfficientWay(10));
        assertFalse(isSparseEfficientWay(11));

    }

    private boolean isSparseTrivialWay(int num) {
        int beforeShift;

        while (num > 0) {
            beforeShift = num & 1;
            num = num >> 1;
            int afterShift = num & 1;
            if (beforeShift == afterShift && beforeShift == 1) {
                return false;
            }
            beforeShift = afterShift;
        }
 
        return true;
    }

    @Test
    public void isSparseTrivialWayTest() throws Exception {

        assertTrue(isSparseTrivialWay(10));
        assertFalse(isSparseTrivialWay(11));

    }

}
