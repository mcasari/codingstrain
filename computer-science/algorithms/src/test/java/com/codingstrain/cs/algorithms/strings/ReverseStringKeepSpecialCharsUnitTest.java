package com.codingstrain.cs.algorithms.strings;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class ReverseStringKeepSpecialCharsUnitTest {

    @Test
    public void reverseStringKeepSpecialTest() throws Exception {

        String toReverse = "abcd%ef";
        char[] chArray = toReverse.toCharArray();

        int i = 0, j = chArray.length - 1;
        while (i < j) {
            if (!Character.isAlphabetic(chArray[i])) {
                ++i;
                continue;
            }
            if (!Character.isAlphabetic(chArray[j])) {
                --j;
                continue;
            }
            char temp = chArray[i];
            chArray[i] = chArray[j];
            chArray[j] = temp;
            ++i;
            --j;
        }

        String reversed = String.valueOf(chArray);
        System.out.println(reversed);
        for (int n = 0, m = chArray.length - 1; n < chArray.length; n++, m--) {
            if (!Character.isAlphabetic(chArray[n])) {
                ++n;
            }
            if (!Character.isAlphabetic(chArray[m])) {
                --m;
            }
            assertEquals(toReverse.toCharArray()[n], chArray[m]);
        }
        assertTrue(!Character.isAlphabetic(toReverse.toCharArray()[4]));
    }

}
