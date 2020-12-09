import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class Sudoku {

    private int[][] board = new int[9][9];

    public Sudoku() {
	}

	public int[][] solve() {
        solveBoard(board);
        return board;
    }

    private boolean solveBoard(int[][] boardIn) {

        int[] position = findNextEmptySpot(boardIn);

        if (position[0] == -1) {
            return true;
        }

        int row = position[0];
        int columb = position[1];
        for(int i = 1; i < 10; i++) {
            if (isNumberValid(boardIn, position, i)) {
                boardIn[row][columb] = i;

                if (solveBoard(boardIn)) {
                    return true;
                }
            }
            boardIn[row][columb] = 0;
        }
        return false;
    }

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

    private boolean isNumberValid(int[][] boardIn, int[] position, int number) {
        
        //check row
        int row = position[0];
        int columb = position[1];

        for(int num: boardIn[row]) {
            if (num == number) {
                return false;
            }
        }

        //chech columb
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