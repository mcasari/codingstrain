package com.codingstrain.cs.algorithms.backtracking;
public class QueenPlacement {

    private int size;

    public QueenPlacement(int n) {
        this.size = n;
    }

    public void solve() {
        int[] positions = new int[size];
        placeQueen(0, positions);
    }

    private void placeQueen(int currentRow, int[] positions) {
        if (currentRow == size) {
            showBoard(positions);
            return;
        }

        for (int column = 0; column < size; column++) {
            if (canPlace(currentRow, column, positions)) {
                positions[currentRow] = column;
                placeQueen(currentRow + 1, positions);
            }
        }
    }

    private boolean canPlace(int row, int col, int[] positions) {
        for (int previousRow = 0; previousRow < row; previousRow++) {
            int previousCol = positions[previousRow];

            boolean sameColumn = previousCol == col;
            boolean sameDiagonal = Math.abs(previousCol - col) == Math.abs(previousRow - row);

            if (sameColumn || sameDiagonal) {
                return false;
            }
        }
        return true;
    }

    private void showBoard(int[] positions) {
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                if (positions[row] == col) {
                    System.out.print("Q ");
                } else {
                    System.out.print(". ");
                }
            }
            System.out.println();
        }
        System.out.println();
    }

    public static void main(String[] args) {
        int n = 4; // Change this value for different board sizes
        QueenPlacement queenSolver = new QueenPlacement(n);
        queenSolver.solve();
    }
}