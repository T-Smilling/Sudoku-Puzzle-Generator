import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class SudokuGenerator {
    private static final int SIZE = 9;
    private static final int EMPTY_CELL = 0;
    private Random rand = new Random();

    // Danh sách các số từ 1-9 và được xáo trộn để gán vào ma trận
    private static final List<Integer> numbers = new ArrayList<>(List.of(1, 2, 3, 4, 5, 6, 7, 8, 9));

    /**
     * Sinh một đề Sudoku
     * @param emptyCells Số lượng ô trống cần tạo
     * @return Một SudokuMatrix
     */
    public SudokuMatrix generateSudokuPuzzle(int emptyCells) {
        SudokuMatrix sudokuMatrix = new SudokuMatrix();

        // Sinh một Sudoku đầy đủ
        if (!generateFullSudokuMatrix(sudokuMatrix)){
            throw new RuntimeException("Lỗi sinh sudoku đầy đủ");
        }

        // Xóa một số ô để tạo đề Sudoku và đảm bảo có ít nhất 1 nghiệm
        removeCells(sudokuMatrix, emptyCells);

        return sudokuMatrix;
    }

    /**
     * Dùng backtracking để sinh một Sudoku đầy đủ.
     * @param sudokuMatrix Ma trận Sudoku
     * @return true or false
     */
    private boolean generateFullSudokuMatrix(SudokuMatrix sudokuMatrix) {
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                if (sudokuMatrix.getCell(row, col) == EMPTY_CELL) {
                    // Xáo trộn danh sách số để gán vào ma trận
                    Collections.shuffle(numbers);
                    for (int num : numbers) {
                        if (sudokuMatrix.isValidNumber(row, col, num)){
                            sudokuMatrix.setCell(row, col, num);

                            // Gọi đệ quy
                            if (generateFullSudokuMatrix(sudokuMatrix)){
                                return true;
                            }

                            // Nếu đệ quy thất bại, quay lui
                            sudokuMatrix.setCell(row, col, EMPTY_CELL);
                        }
                    }
                    // Không số nào hợp lệ, quay lại
                    return false;
                }
            }
        }
        // Hoàn thành
        return true;
    }

    /**
     * Xóa một số ô ngẫu nhiên trong một sudoku đầy đủ.
     * @param sudokuMatrix Ma trận Sudoku
     * @param emptyCells Số lượng ô cần xóa
     */
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
