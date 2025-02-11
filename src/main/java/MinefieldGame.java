import java.util.Random;
import java.util.Scanner;

public class MinefieldGame {
    private char[][] board; // Game board
    private int playerRow, playerCol; // Player's position
    private int lives; // Number of lives the player has
    private int moves; // Number of moves taken
    private static final int SIZE = 8; // Board size
    private static final int MINES = 10; // Number of mines
    private static final char EMPTY = '.'; // Empty space
    private static final char MINE = 'M'; // Mine character
    private static final char PLAYER = 'P'; // Player character
    
    /**
     * Constructor: Initializes the game by setting up the board, placing mines,
     * and setting the player's starting position.
     */
    public MinefieldGame() {
        board = new char[SIZE][SIZE];
        lives = 3;
        moves = 0;
        initializeBoard();
        placeMines();
        playerRow = SIZE / 2; // Start in the middle of the left column
        playerCol = 0;
        board[playerRow][playerCol] = PLAYER;
    }

    /**
     * Returns the current number of lives the player has.
     * @return number of lives
     */
    public int getLives() {
        return lives;
    }
    
    /**
     * Returns the number of moves the player has taken.
     * @return number of moves
     */
    public int getMoves() {
        return moves;
    }
    
    /**
     * Returns a copy of the game board to prevent external modification.
     * @return a copy of the board
     */
    public char[][] getBoard() {
        return board.clone(); 
    }    
    
    /**
     * Returns the player's current row position.
     * @return player row index
     */
    public int getPlayerRow() {
        return playerRow;
    }

    /**
     * Returns the player's current column position.
     * @return player column index
     */
    public int getPlayerCol() {
        return playerCol;
    }

    /**
     * Returns the size of the board.
     * @return board size
     */
    public int getSize() {
        return SIZE;
    }

    /**
     * Initializes the game board with empty spaces.
     */
    private void initializeBoard() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                board[i][j] = EMPTY;
            }
        }
    }
    
    /**
     * Randomly places mines on the board, ensuring they are not placed in the first column.
     */
    private void placeMines() {
        Random rand = new Random();
        int placedMines = 0;
        while (placedMines < MINES) {
            int row = rand.nextInt(SIZE);
            int col = rand.nextInt(SIZE);
            if (board[row][col] == EMPTY && col != 0) { // Avoid placing on the start position
                board[row][col] = MINE;
                placedMines++;
            }
        }
    }
    
    /**
     * Displays the current state of the board, showing the player's position and remaining lives.
     */
    private void displayBoard() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (i == playerRow && j == playerCol) {
                    System.out.print(PLAYER + " ");
                } else {
                    System.out.print(EMPTY + " ");
                }
            }
            System.out.println();
        }
        System.out.println("Lives: " + lives + " | Moves: " + moves);
    }
    
    /**
     * Moves the player in the specified direction.
     * @param direction The direction to move ('W' = up, 'S' = down, 'A' = left, 'D' = right)
     * @return true if the player reaches the goal or loses all lives, false otherwise.
     */
    public boolean movePlayer(char direction) {
        int newRow = playerRow;
        int newCol = playerCol;
        
        switch (direction) {
            case 'W': newRow--; break; // Up
            case 'S': newRow++; break; // Down
            case 'A': newCol--; break; // Left
            case 'D': newCol++; break; // Right
            default: System.out.println("Invalid move!"); return false;
        }
        
        if (newRow < 0 || newRow >= SIZE || newCol < 0 || newCol >= SIZE) {
            System.out.println("Move out of bounds!");
            return false;
        }
        
        moves++;
        
        if (board[newRow][newCol] == MINE) {
            lives--;
            System.out.println("Boom! You hit a mine. Lives left: " + lives);
            if (lives == 0) return true; // Game over
        }
        
        board[playerRow][playerCol] = EMPTY;
        playerRow = newRow;
        playerCol = newCol;
        board[playerRow][playerCol] = PLAYER;
        
        return playerCol == SIZE - 1; // Reached the goal
    }
    
    /**
     * Starts the game loop, allowing the player to make moves until they win or lose.
     */
    public void startGame() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to the Minefield Game!");
        
        while (true) {
            displayBoard();
            System.out.print("Move (W/A/S/D): ");
            char move = scanner.next().toUpperCase().charAt(0);
            
            if (movePlayer(move)) {
                if (lives == 0) {
                    System.out.println("Game Over! You lost all your lives.");
                } else {
                    displayBoard();
                    System.out.println("Congratulations! You reached the goal in " + moves + " moves.");      
                }
                break;
            }
        }
        scanner.close();
    }
    
    /**
     * Main method to launch the game.
     * @param args Command-line arguments (not used)
     */
    public static void main(String[] args) {
        MinefieldGame game = new MinefieldGame();
        game.startGame();
    }
}
