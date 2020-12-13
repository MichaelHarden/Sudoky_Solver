import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
/**
 * The Sudoku class provides methods to solve a sudoku. A sudoku is a 9x9 matrix and can be
 * read in from a file using the readFile method or by using the Sudoky constructor with 
 * parameter boardIn.
 */
public class Sudoku {

    private int[][] board = new int[9][9];

    /**
     * The Sudoku constructor creates a emtpy Sudoku object
     */
    public Sudoku() {
    }
    
    /**
     * The Sudoku constructor creates a Sudoku object with a 9x9 board. If the boardIn 
     * parameter is not a 9x9 matrix, the board field will not be set to the input and 
     * will instead remain an empty 9x9 matrix
     * 
     * @param boardIn must be a 9x9 matrix
     */
    public Sudoku(int[][] boardIn) {
        if (boardIn.length == 9 && boardIn[1].length == 9) {
            board = boardIn;
        }
    }

    /**
     * The solveBoard method solves the sudoku board by using recursive techniques. 
     * It looks at the boarda nd finds the first space whose value is 0 and the checks to 
     * see which number from 1 through 9 can be used in that spot. The continues on to the 
     * next space whose value is 0. If it reaches a space that cannot be assigned a value 
     * between 1 through 9 then it uses back tracking to reassign previusly assigned spaces.
     * 
     * @return a boolean value returns false to back track and true to continue to next 
     *         space on the board
     */
    public boolean solveBoard() {

        int[] position = findNextEmptySpot(board);

        if (position[0] == -1) {
            return true;
        }

        int row = position[0];
        int columb = position[1];
        for(int i = 1; i < 10; i++) {
            if (isNumberValid(board, position, i)) {
                board[row][columb] = i;

                if (solveBoard()) {
                    return true;
                }
            }
            board[row][columb] = 0;
        }
        return false;
    }

    /**
     * The findNextEmptySpot is a helper method to the solveBoard method and finds the next
     * spot whose value is 0 on the board and returns the position as a array of lenght 2 
     * s\with the index 0 being the row and the index 1 being the position. If no spaces 
     * whose value is 0 is left then [-1, -1] is returned meaning the board has been solved.
     * 
     * @param boardIn a 9x9 matrix representing the board to find the next position of.
     * @return a array of lenth  2 for the position.
     */
    private int[] findNextEmptySpot(int[][] boardIn) {
        for(int i = 0; i < boardIn.length; i++) {
            for (int j = 0; j < boardIn[i].length; j++) {
                if (boardIn[i][j] == 0) {
                    int[] emptySpot = {i, j};
                    return emptySpot;
                }
            }
        }
        int[] noEmptySpots = {-1, -1};
        return noEmptySpots;
    }

    /**
     * The isNumberValid method is a helper method of the solveBoard method and checks if a
     * number between 1 through 9 is valid at the position found on the board.
     * 
     * @param boardIn a 9x9 board that the number is to be checked against.
     * @param position an array lenght 2 for the position on the board.
     * @param number a number between 1 throught 9 that is checked at the position on the board.
     * @return true if number is valid at position \ false if number is not valid at positon.
     */
    private boolean isNumberValid(int[][] boardIn, int[] position, int number) {
        
        //check row
        int row = position[0];
        int columb = position[1];

        for(int num: boardIn[row]) {
            if (num == number) {
                return false;
            }
        }

        //check columb
        for(int[] i: boardIn) {
            if (i[columb] == number) {
                return false;
            }
        }

        //check block 
        int x = position[1] / 3;
        int y = position[0] / 3;

        for(int i = y*3; i < y*3 + 3; i++) {
            for (int j = x*3; j < x*3 + 3; j++) {
                if (boardIn[i][j] == number) {
                    return false;
                }
            }
        }
        
        return true;
    }

    /**
     * The readFile method reads a sudoku file with the name fileName and assigned the sudoku board to the board field. If fthe file is not found a 
     * FileNotFoundException is thrown.
     * 
     * @param fileName the name of the file to read
     * @throws FileNotFoundException thrown when no file with the name entered is found.
     */
    public void readFile(String fileName) throws FileNotFoundException {

        Scanner scanFile = new Scanner(new File(fileName));

        int row = 0;

        while (scanFile.hasNext()) {

            String line = scanFile.nextLine();
            int columb = 0;

            Scanner scanLine = new Scanner(line).useDelimiter(",");

            while (scanLine.hasNext()) {
                String currentNum = scanLine.next();
                board[row][columb] = Integer.parseInt(currentNum.trim());
                columb++;
            }
            scanLine.close();
            row++;
        }
        scanFile.close();
    }

    /**
     * The toString method returns a String value of the boad in a sudoku format.
     * 
     * @return sudoku board.
     */
    public String toString() {
        String output = "\n";
        
        for(int i = 0; i < board.length; i++) {
            if(i % 3 == 0 && i != 0) {
                output +="---------------------\n";
            }
            for(int j = 0; j < board[i].length; j++) {
                if(j % 3 == 0 && j != 0) {
                    output += "| ";
                }
                output += board[i][j] + " ";
            }
            output += "\n";
        }
        return output;
    }
}