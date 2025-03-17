package com.codingstrain.cs.algorithms.backtracking;

import org.junit.jupiter.api.Test;

class StringPermutationsUnitTest {

    private void performAllPermutations(char[] characters, int left, int rigth) {
        if (left == rigth) {
            System.out.println(characters);
        } else {
            for (int i = left; i <= rigth; i++) {
                characters = swapCharacters(characters, left, i);
                performAllPermutations(characters, left + 1, rigth);
                characters = swapCharacters(characters, left, i);
            }
        }
    }

    @Test
    public void stringPermutationTest() throws Exception {
        String toPermute = "ABC";
        char[] toPermuteArray = toPermute.toCharArray();
        performAllPermutations(toPermuteArray, 0, toPermuteArray.length - 1);
    }

    public char[] swapCharacters(char[] characters, int i, int j) {
        char temp = characters[i];
        characters[i] = characters[j];
        characters[j] = temp;
        return characters;
    }

}
