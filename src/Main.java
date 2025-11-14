import java.util.Random;

public class Main {
    private static final Random random = new Random();

    public static void main(String[] args) {
        int numberOfGeneratePuzzles = 60;

        System.out.println("Sinh " + numberOfGeneratePuzzles + " đề Sudoku");

        SudokuGenerator sudokuGenerator = new SudokuGenerator();

        long startTime = System.currentTimeMillis();

        for (int i = 1; i <= numberOfGeneratePuzzles; i++) {
            int emptyCells = 50 + random.nextInt(10);
            SudokuMatrix sudokuMatrix = sudokuGenerator.generateSudokuPuzzle(emptyCells);

            System.out.println("\n--- Đề bài " + i + " (" + emptyCells + " lỗ) ---");
            sudokuMatrix.printSudoku();
        }
        long endTime = System.currentTimeMillis();
        double totalTime = (endTime - startTime);
        double avgTimePerPuzzle = totalTime / numberOfGeneratePuzzles;

        System.out.println("\n--- 🎯 HIỆU NĂNG 🎯 ---");
        System.out.println("Hoàn thành sinh " + numberOfGeneratePuzzles + " đề.");
        System.out.println("Tổng thời gian: " + totalTime + " ms");
        System.out.println("Thời gian trung bình: " + String.format("%.2f", avgTimePerPuzzle) + " ms/đề");
        System.out.println("---");
    }
}
