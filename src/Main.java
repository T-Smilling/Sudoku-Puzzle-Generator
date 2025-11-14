import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Random;

public class Main {
    private static final Random random = new Random();
    private static final String folderSave = "samples";
    public static void main(String[] args) throws IOException {
        // Thời gian bắt đầu sinh đề
        long startTime = System.currentTimeMillis();

        // Số lượng đề cần sinh
        int numberOfGeneratePuzzles = 60;

        System.out.println("Sinh " + numberOfGeneratePuzzles + " đề Sudoku");

        // Tạo thư mục samples
        Files.createDirectories(Paths.get(folderSave));

        SudokuGenerator sudokuGenerator = new SudokuGenerator();


        for (int i = 1; i <= numberOfGeneratePuzzles; i++) {
            // Random 50-60 ô trống trong đề
            int emptyCells = 50 + random.nextInt(10);

            // Sinh đề sudoku
            SudokuMatrix sudokuMatrix = sudokuGenerator.generateSudokuPuzzle(emptyCells);

            // Lưu đề vào file
            String filename = String.format("%s/Sudoku_%02d.txt", folderSave, i);
            sudokuMatrix.saveToFile(filename);

            System.out.println("\n--- Đề bài " + i + " (" + emptyCells + " lỗ) ---");
            sudokuMatrix.printSudoku();
        }
        // Thời gian kết thúc sinh đề
        long endTime = System.currentTimeMillis();

        // Tổng thời gian chạy
        double totalTime = (endTime - startTime);

        // Thời gian sinh từng đề
        double avgTimePerPuzzle = totalTime / numberOfGeneratePuzzles;

        System.out.println("\n--- 🎯 HIỆU NĂNG 🎯 ---");
        System.out.println("Hoàn thành sinh " + numberOfGeneratePuzzles + " đề.");
        System.out.println("Tổng thời gian: " + totalTime + " ms");
        System.out.println("Thời gian trung bình: " + String.format("%.2f", avgTimePerPuzzle) + " ms/đề");
        System.out.println("---");
    }
}
