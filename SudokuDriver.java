import java.util.Scanner;
import java.io.FileNotFoundException;

public class SudokuDriver {
    public static void main(String[] args) throws FileNotFoundException {

        System.out.print("\nPlease enter the file name of your sudoku: ");
        Scanner userInput = new Scanner(System.in);

        Sudoku sudoku = new Sudoku();

        try{ 
            sudoku.readFile("sudokus/" + userInput.nextLine());  
            sudoku.solve();
            System.out.println(sudoku);

        } catch (FileNotFoundException fileNotFoundException) {

            System.out.println("Unable to find the file entered.");
        }
        
        userInput.close();
    }
}
