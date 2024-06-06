/**
* Proj6.java
* Hudson Boldt / Lab Section 02B | Thu 4:30
* 
* This program simulates Conway's Game of Life. 
* It reads an initial board configuration from a file and "evolves" the 
* board based on specific rules. The user can choose to run the simulation 
* indefinitely or for a limited number of iterations.
*
* EXTRA CREDIT INCLUDED
*/

import java.util.*;
import java.io.*;

/**
 * Main class 
 * !!CLICK TO VIEW CLASS!!
*/
public class Proj6 {

    /**
     * Default constructor for the Proj6 class.
     * Only added because of warning while using javadoc
     */
    public Proj6() {

    }

    /**
    * main handles the flow of the program
    *
    * @param args Array of Strings entered at the command line (optional)
    * @throws IOException for File I/O
    */
    public static void main(String[] args) throws IOException {
        Scanner s = new Scanner(System.in);
        String fn = "";
        if (args.length > 0) {
            // Use the filename provided as a command-line argument
            fn = args[0];
        } else {
            // If no filename is provided, prompt the user to enter it
            System.out.print("Usage: java Proj6 \"filename.txt\"... exiting");
            System.exit(0);
        }

        File file = new File(fn);

        while (!file.exists()) {
            System.out.print("Error: file does not exist... \nEnter input file name: ");
            fn = s.nextLine();
            file = new File(fn);
            System.out.println();
        }
        
        int rows = getRows(fn);
        int cols = getCols(fn);
        
        int[][] board = readBoard(fn, rows, cols);

        // Initial board
        System.out.println("Initial Board: ");
        boardDisplay(board, rows, cols);
        

        System.out.print("Enter 1 for infite or 2 for a limited amount of iterations: ");
        int option = Integer.parseInt(s.nextLine());
        int iterations = 0;
        if (option == 2){
            System.out.print("\nEnter amount of iterations: ");
            iterations = Integer.parseInt(s.nextLine());

        }
        s.close();
        

        if (option == 1){
            while(true){
                try {
                    Thread.sleep(500);
                }
                catch (InterruptedException e) {}
                System.out.println();
                board = neighbors(board, rows, cols);
            }
        }
        else{
            for(int i = 0; i < iterations; i++){
                try {
                    Thread.sleep(500);
                }
                catch (InterruptedException e) {}
                System.out.println();
                board = neighbors(board, rows, cols);
            }
        }
    }
    // Created two extra methods to access number of rows and columns
    /**
     * Retrieves the number of rows from the input file.
     *
     * @param fn The name of the input file
     * @return The number of rows
     * @throws IOException for File I/O
     */
    public static int getRows(String fn) throws IOException {
        Scanner inFile = new Scanner (new File(fn));
    
        // Read the dimensions of the board (first two lines)
        int rows = Integer.parseInt(inFile.nextLine());
        inFile.close();
        return rows;
    }
    /**
     * Retrieves the number of columns from the input file.
     *
     * @param fn The name of the input file
     * @return The number of columns
     * @throws IOException for File I/O
     */
    public static int getCols(String fn) throws IOException {
        Scanner inFile = new Scanner (new File(fn));
    
        // Read the dimensions of the board (first two lines)
                        inFile.nextLine();
                        int column = Integer.parseInt(inFile.nextLine());
        inFile.close();
        return column;
    }

    /**
     * Reads the board from the input file and stores it in a 2D array.
     *
     * @param fn The name of the input file
     * @param rows The number of rows in the board
     * @param cols The number of columns in the board
     * @return A 2D array representing the board
     * @throws IOException for File I/O
     */
    public static int[][] readBoard(String fn, int rows, int cols) throws IOException {

        Scanner scnr = new Scanner(new File(fn));
        scnr.nextLine(); // skip rows
        scnr.nextLine(); // skip columns
        
        int[][] board = new int[rows][cols];

        // stores 0's and 1's into board array
        for (int i = 0; i < rows; i++) {
            String line = scnr.nextLine();
            for (int j = 0; j < cols; j++) {
                board[i][j] = Character.getNumericValue(line.charAt(j));
            }
            System.out.println();
        }
    
        scnr.close();
        // I had it return board because idk how else to use it throughout the program
        return board; 
    }

    /**
     * Displays the board on the console.
     *
     * @param cells The 2D array representing the board
     * @param rows The number of rows in the board
     * @param cols The number of columns in the board
     */
    public static void boardDisplay(int[][] cells, int rows, int cols) {
        // Make cell array
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                // Check if dead or alive
                System.out.print(cells[i][j]);
            }
            System.out.println();
        }
    }

    // Removed update method because i couldnt find a good use for it
    /*
    public static int[][] update (int[][] currArray, int rows, int cols){
        StringBuilder display = new StringBuilder();
        int[][] updatedArray = new int[rows][cols];
        // Make cell array
        for (int x = 0; x < rows; x++) {
            for (int y = 0; y < cols; y++) {
                int counta = 0;
                // Check if dead or alive
                counta = neighobors(currArray, x, y);
                if (currArray[x][y] == 0 && counta == 3){
                    updatedArray[x][y] = 1;
                }
                else{
                    updatedArray[x][y] = 0;
                }
                if (currArray[x][y] == 1 && (counta == 2 || counta == 3)){
                    updatedArray[x][y] = 1;
                }
                else{
                    updatedArray[x][y] = 0;
                }
            }
        }
        return updatedArray;
    }
    */

    /**
     * Calculates the next state of the board based on the Game of Life rules.
     *
     * @param currArray The current state of the board
     * @param rows The number of rows in the board
     * @param cols The number of columns in the board
     * @return The next state of the board
     */
    public static int[][] neighbors(int[][] currArray, int rows, int cols) {
        int[][] updatedArray = new int[rows][cols];
        for (int x = 0; x < rows; x++) {
            for (int y = 0; y < cols; y++) {
                int counta = 0;
                for (int checkInd = 0; checkInd < 8; checkInd++) {
                    switch (checkInd) {
                        case 0:
                            if (x - 1 >= 0 && y - 1 >= 0) {
                                if (currArray[x - 1][y - 1] == 1) {
                                    counta++;
                                }
                            }
                            break;
                        case 1:
                            if (y - 1 >= 0) {
                                if (currArray[x][y - 1] == 1) {
                                    counta++;
                                }
                            }
                            break;
                        case 2:
                            if (x + 1 < rows && y - 1 >= 0) {
                                if (currArray[x + 1][y - 1] == 1) {
                                    counta++;
                                }
                            }
                            break;
                        case 3:
                            if (x + 1 < rows) {
                                if (currArray[x + 1][y] == 1) {
                                    counta++;
                                }
                            }
                            break;
                        case 4:
                            if (x + 1 < rows && y + 1 < cols) {
                                if (currArray[x + 1][y + 1] == 1) {
                                    counta++;
                                }
                            }
                            break;
                        case 5:
                            if (y + 1 < cols) {
                                if (currArray[x][y + 1] == 1) {
                                    counta++;
                                }
                            }
                            break;
                        case 6:
                            if (x - 1 >= 0 && y + 1 < cols) {
                                if (currArray[x - 1][y + 1] == 1) {
                                    counta++;
                                }
                            }
                            break;
                        case 7:
                            if (x - 1 >= 0) {
                                if (currArray[x - 1][y] == 1) {
                                    counta++;
                                }
                            }
                            break;
                    }
                }
                if (currArray[x][y] == 1) {
                    if (counta == 2 || counta == 3) {
                        updatedArray[x][y] = 1;
                    } else {
                        updatedArray[x][y] = 0;
                    }
                }
                if (currArray[x][y] == 0) {
                    if (counta == 3) {
                        updatedArray[x][y] = 1;
                    } else {
                        updatedArray[x][y] = 0;
                    }
                }
            }
        }
        boardDisplay(updatedArray, rows, cols);
        return updatedArray;
    }
}