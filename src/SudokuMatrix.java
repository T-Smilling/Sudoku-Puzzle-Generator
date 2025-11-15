import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class SudokuMatrix {
    private static final int SIZE = 9;
    private static final int EMPTY_CELL = 0;
    private int[][] sudoku;

    /**
     * Khởi tạo một bảng 9x9 trống
     */
    public SudokuMatrix() {
        this.sudoku = new int[SIZE][SIZE];
    }

    /**
     * Lấy giá trị của một ô
     * @param row Hàng
     * @param col Cột
     */
    public int getCell (int row, int col){
        return sudoku[row][col];
    }

    /**
     * Gán giá trị cho một ô
     * @param row Hàng
     * @param col Cột
     * @param val Giá trị cần gán
     */
    public void setCell (int row, int col, int val){
        sudoku[row][col] = val;
    }

    /**
     * Kiểm tra có thể gán một giá trị hợp lệ vào một ô
     * @param row Hàng
     * @param col Cột
     * @param num Số cần kiểm tra
     */
    public boolean isValidNumber (int row, int col, int num){
        // Kiểm tra hàng
        for (int c = 0; c < SIZE; c++){
            if (sudoku[row][c] == num) return false;
        }

        // Kiểm tra cột
        for (int r = 0; r < SIZE; r++){
            if (sudoku[r][col] == num) return false;
        }

        // Kiểm tra khối 3x3 chứa ô đang xét
        int startRow = (row / 3) * 3;
        int startCol = (col / 3) * 3;

        for (int i = 0; i < 3; i++ ){
            for (int j = 0; j < 3; j++){
                if (sudoku[startRow + i][startCol + j] == num) return false;
            }
        }
        return true;
    }

    /**
     * In ma trận Sudoku
     */
    public void printSudoku () {
        for (int r = 0; r < SIZE; r++) {
            for (int c = 0; c < SIZE; c++) {
                if (c % 3 == 0 && c != 0) {
                    System.out.print("| ");
                }

                int cell = sudoku[r][c];
                System.out.print((cell == EMPTY_CELL ? "_" : String.valueOf(cell)) + " ");
            }
            System.out.println("\n");
        }
        System.out.println("-------------------------");
    }

    /**
     * Lưu đề Sudoku ra file
     * @param filename Tên file
     */
    public void saveToFile (String filename) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (int r = 0; r < SIZE; r++) {
                for (int c = 0; c < SIZE; c++) {
                    if (c % 3 == 0 && c != 0) {
                        writer.write("| ");
                    }

                    int cell = sudoku[r][c];
                    writer.write((cell == EMPTY_CELL ? "_" : String.valueOf(cell)) + " ");
                }
                writer.newLine();
            }
        }
    }
}
