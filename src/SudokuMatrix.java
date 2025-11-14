public class SudokuMatrix {
    private static final int SIZE = 9;
    private static final int EMPTY_CELL = 0;
    private int[][] sudoku;

    public SudokuMatrix() {
        this.sudoku = new int[SIZE][SIZE];
    }

    public int getCell (int row, int col){
        return sudoku[row][col];
    }
    public void setCell (int row, int col, int val){
        sudoku[row][col] = val;
    }

    public boolean isValidNumber (int row, int col, int num){
        for (int c = 0; c < SIZE; c++){
            if (sudoku[row][c] == num) return false;
        }
        for (int r = 0; r < SIZE; r++){
            if (sudoku[r][col] == num) return false;
        }

        int startRow = (row / 3) * 3;
        int startCol = (col / 3) * 3;

        for (int i = 0; i < 3; i++ ){
            for (int j = 0; j < 3; j++){
                if (sudoku[startRow + i][startCol + j] == num) return false;
            }
        }
        return true;
    }

    public void printSudoku () {
        for (int r = 0; r < SIZE; r++) {
            for (int c = 0; c < SIZE; c++) {
                int cell = sudoku[r][c];
                System.out.print((cell == EMPTY_CELL ? "_" : cell) + " ");
            }
            System.out.println();
        }
        System.out.println("-------------------------");
    }
}
