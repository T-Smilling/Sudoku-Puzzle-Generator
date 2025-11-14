import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class SudokuGenerator {
    private static final int SIZE = 9;
    private static final int EMPTY_CELL = 0;
    private Random rand = new Random();
    private static final List<Integer> numbers = new ArrayList<>(List.of(1, 2, 3, 4, 5, 6, 7, 8, 9));

    public SudokuMatrix generateSudokuPuzzle(int emptyCells) {
        SudokuMatrix sudokuMatrix = new SudokuMatrix();

        generateFullSudokuMatrix(sudokuMatrix);

        removeCells(sudokuMatrix, emptyCells);

        return sudokuMatrix;
    }

    private boolean generateFullSudokuMatrix(SudokuMatrix sudokuMatrix) {
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                if (sudokuMatrix.getCell(row, col) == EMPTY_CELL) {
                    Collections.shuffle(numbers);
                    for (int num : numbers) {
                        if (sudokuMatrix.isValidNumber(row, col, num)){
                            sudokuMatrix.setCell(row, col, num);
                            if (generateFullSudokuMatrix(sudokuMatrix)){
                                return true;
                            }
                            sudokuMatrix.setCell(row, col, EMPTY_CELL);
                        }
                    }
                    return false;
                }
            }
        }
        return true;
    }

    private void removeCells(SudokuMatrix sudokuMatrix, int emptyCells) {
        int countEmptyCells = 0;
        while (countEmptyCells < emptyCells) {
            int row = rand.nextInt(SIZE);
            int col = rand.nextInt(SIZE);
            if (sudokuMatrix.getCell(row, col) != EMPTY_CELL) {
                sudokuMatrix.setCell(row, col, EMPTY_CELL);
                countEmptyCells++;
            }
        }
    }
}
