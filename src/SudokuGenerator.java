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
     * Xóa một số ô ngẫu nhiên nhưng đảm bảo sudoku có nghiệm duy nhất.
     * @param sudokuMatrix Ma trận Sudoku
     * @param emptyCells Số lượng ô cần xóa
     */
    private void removeCells(SudokuMatrix sudokuMatrix, int emptyCells) {
        // Danh sách 81 vị trí
        List<Integer> positions = new ArrayList<>();
        for (int i = 0; i < SIZE * SIZE; i++) {
            positions.add(i);
        }
        Collections.shuffle(positions);
        int countEmptyCells = 0;

        for (int pos : positions){
            if (countEmptyCells >= emptyCells){
                break;
            }
            // Lấy vị trí và giá trị của ô đó
            int row = pos / SIZE;
            int col = pos % SIZE;
            int val = sudokuMatrix.getCell(row, col);
            if (val != EMPTY_CELL){
                sudokuMatrix.setCell(row, col, EMPTY_CELL);
                // Kiểm tra trên sudoku copy tránh làm hỏng sudoku gốc
                SudokuMatrix tempSudoku = copySudokuMatrix(sudokuMatrix);

                // Quyết định giữ hay xóa ô
                if (countSolveSudoku(tempSudoku) == 1){
                    countEmptyCells++;
                } else {
                    sudokuMatrix.setCell(row, col, val);
                }
            }
        }
    }

    /**
     * Tạo bản sao sudoku để kiểm tra nghiệm
     */
    private SudokuMatrix copySudokuMatrix(SudokuMatrix sudokuMatrix) {
        SudokuMatrix newSudoku = new SudokuMatrix();
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                newSudoku.setCell(row, col, sudokuMatrix.getCell(row, col));
            }
        }
        return newSudoku;
    }

    /**
     * Đếm số lượng nghiệm của Sudoku.
     * @param matrix Ma trận cần giải
     */
    private int countSolveSudoku(SudokuMatrix matrix) {
        int row = -1;
        int col = -1;
        boolean isEmpty = true;

        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (matrix.getCell(i, j) == EMPTY_CELL) {
                    isEmpty = false;
                    row = i;
                    col = j;
                    break;
                }
            }
            if (!isEmpty){
                break;
            }
        }

        // Nếu không còn ô trống có 1 nghiệm
        if (isEmpty) {
            return 1;
        }
        int count = 0;
        for (int num = 0; num <= SIZE; num++) {
            if (matrix.isValidNumber(row, col, num)){
                matrix.setCell(row, col, num);

                // Đệ quy cộng dồn nghiệm tìm được
                count += countSolveSudoku(matrix);
                if (count > 1){
                    return count;
                }
                matrix.setCell(row, col, EMPTY_CELL);
            }
        }
        return count;
    }

    /**
     * Kiểm tra thỏa mãn nghiệm duy nhất của đề.
     * @param matrix Ma trận cần kiêm tra
     */
    public boolean checkResult(SudokuMatrix matrix) {
        SudokuMatrix testMatrix = copySudokuMatrix(matrix);

        int solutions = countSolveSudoku(testMatrix);

        return solutions == 1;
    }
}
