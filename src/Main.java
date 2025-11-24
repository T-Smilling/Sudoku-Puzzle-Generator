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
        int numberOfGeneratePuzzles = 40;

        System.out.println("Generating " + numberOfGeneratePuzzles + " Sudoku puzzles");

        // Tạo thư mục samples
        Files.createDirectories(Paths.get(folderSave));

        SudokuGenerator sudokuGenerator = new SudokuGenerator();

        for (int i = 1; i <= numberOfGeneratePuzzles; i++) {
            // Random 50-60 ô trống trong đề
            int emptyCells = 40 + random.nextInt(10);

            // Sinh đề sudoku
            SudokuMatrix sudokuMatrix = sudokuGenerator.generateSudokuPuzzle(emptyCells);

            boolean isUnique = sudokuGenerator.checkResult(sudokuMatrix);
            String status = isUnique ? "[1 NGHIỆM]" : "[ĐA NGHIỆM]";
            System.out.println(status);

            // Lưu đề vào file
            String filename = String.format("%s/Sudoku_%02d.txt", folderSave, i);
            sudokuMatrix.saveToFile(filename);
            System.out.println("--- Puzzle " + i + " saved to file: " + filename);

            System.out.println("\n--- Puzzle " + i + " (" + emptyCells + " empty cells) ---");
            sudokuMatrix.printSudoku();
        }
        // Thời gian kết thúc sinh đề
        long endTime = System.currentTimeMillis();

        // Tổng thời gian chạy
        double totalTime = (endTime - startTime);

        // Thời gian sinh từng đề
        double avgTimePerPuzzle = totalTime / numberOfGeneratePuzzles;

        String osName = System.getProperty("os.name");
        String osArch = System.getProperty("os.arch");
        String osVersion = System.getProperty("os.version");
        int cores = Runtime.getRuntime().availableProcessors();
        long maxMemory = Runtime.getRuntime().maxMemory() / (1024 * 1024);
        long totalMemory = Runtime.getRuntime().totalMemory() / (1024 * 1024);

        System.out.println("\n--- PERFORMANCE ---");
        System.out.println("Generated " + numberOfGeneratePuzzles + " puzzles.");
        System.out.println("Total time: " + totalTime + " ms");
        System.out.println("Average time per puzzle: " + String.format("%.2f", avgTimePerPuzzle) + " ms");
        System.out.println("---");

        System.out.println("\n--- SYSTEM INFO ---");
        System.out.println("Name      : " + osName);
        System.out.println("Arch      : " + osArch);
        System.out.println("Version   : " + osVersion);
        System.out.println("Cores    : " + cores + " logical cores");
        System.out.println("JVM Max RAM  : " + maxMemory + " MB");
        System.out.println("Total Memory  : " + totalMemory);
    }
}
