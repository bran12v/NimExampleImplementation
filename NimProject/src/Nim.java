import java.util.Random;

/**
 * A Java program to implement Game of Nim. The program
 * assumes that both players are playing optimally
 * Java equivalent of the GeeksforGeeks Nim solver code found at:
 *   https://www.geeksforgeeks.org/combinatorial-game-theory-set-2-game-nim/
 *   
 * @author Brandon Vanek 
 * @version 12/12/2020
 */
public class Nim {
    public static int COMPUTER = 0;
    public static int HUMAN = 1;


    /**
     * A Java function to output the current game state.
     * 
     * @param piles
     *            - Array having the initial count of stones/coins
     *            in each piles before the game has started.
     * @param n
     *            - Number of piles
     */
    public void showPiles(int[] piles, int n) {
        System.out.print("Current Game Status -> ");
        for (int i = 0; i < n; i++) {
            System.out.print(piles[i] + " ");
        }
        System.out.print("\n");
    }


    /**
     * A C function that returns True if game has ended and
     * False if game is not yet over
     * 
     * @param piles
     *            - Array having the initial count of stones/coins
     *            in each piles before the game has started.
     * @param n
     *            - Number of piles
     */
    public boolean gameOver(int[] piles, int n) {
        for (int i = 0; i < n; i++) {
            if (piles[i] != 0) {
                return false;
            }
        }
        return true;
    }


    /**
     * A Java function to declare the winner of the game
     * 
     * @param whoseTurn
     *            - either the computer's or the human's turn (0 || 1)
     */
    public void declareWinner(int whoseTurn) {
        if (whoseTurn == COMPUTER) {
            System.out.println("\nHUMAN won\n");
        }
        else {
            System.out.println("\nCOMPUTER won\n");
        }
    }


    /**
     * A Java function to calculate the Nim-Sum at any point of the game.
     * 
     * @param piles
     *            - Array having the initial count of stones/coins
     *            in each piles before the game has started.
     * @param n
     *            - Number of piles
     * @return the Nim Sum
     */
    public int calculateNimSum(int[] piles, int n) {
        int nimSum = piles[0];
        for (int i = 1; i < n; i++) {
            nimSum ^= piles[i];
        }
        return nimSum;
    }


    /**
     * A C function to make moves of the Nim Game
     * 
     * @param piles
     *            - Array having the initial count of stones/coins
     *            in each piles before the game has started.
     * @param n
     *            - Number of piles
     * @param moves
     *            - An Integer array to hold the two parameters of a move
     *            A moves has two parameters-
     *            1) moves[0] = The index of pile from which stone is
     *            going to be removed
     *            2) moves[1] = Number of stones removed from the
     *            pile indexed = pile_index
     */
    public void makeMove(int[] piles, int n, int[] moves) {
        int nimSum = calculateNimSum(piles, n);
        // The player having the current turn is on a winning
        // position. So he/she/it play optimally and tries to make
        // Nim-Sum as 0
        if (nimSum != 0) {
            for (int i = 0; i < n; i++) {
                // If this is not an illegal move
                // then make this move.
                if ((piles[i] ^ nimSum) < piles[i]) {
                    moves[0] = i;
                    moves[1] = piles[i] - (piles[i] ^ nimSum);
                    piles[i] ^= nimSum;
                    break;
                }
            }
        }
        // The player having the current turn is on losing
        // position, so he/she/it can only wait for the opponent
        // to make a mistake(which doesn't happen in this program
        // as both players are playing optimally). He randomly
        // choose a non-empty pile and randomly removes few stones
        // from it. If the opponent doesn't make a mistake,then it
        // doesn't matter which pile this player chooses, as he is
        // destined to lose this game.

        // If you want to input yourself then remove the rand()
        // functions and modify the code to take inputs.
        // But remember, you still won't be able to change your
        // fate/prediction.
        else {
            // Create an array to hold indices of non-empty piles
            int[] nonZeroIndices = new int[n];
            int count = 0;
            for (int i = 0; i < n; i++) {
                if (piles[i] > 0) {
                    nonZeroIndices[count++] = i;
                }
            }
            Random rand = new Random();
            moves[0] = rand.nextInt(32767) % count;
            if (moves[0] != 0) {
                moves[1] = 1 + (rand.nextInt(32767) % (piles[moves[0]]));
            }
            else {
                moves[1] = 1 + (rand.nextInt(32767) % 1);
            }
            piles[moves[0]] -= moves[1];
            if (piles[moves[0]] < 0) {
                piles[moves[0]] = 0;
            }
        }
    }


    /**
     * A Java function to play the Game of Nim
     * 
     * @param piles
     *            - Array having the initial count of stones/coins
     *            in each piles before the game has started.
     * @param n
     *            - Number of piles
     * @param whoseTurn
     *            - either the computer's or the human's turn (0 || 1)
     */
    public void playGame(int[] piles, int n, int whoseTurn) {
        System.out.print("\nGAME STARTS\n");
        int[] moves = new int[2];
        while (gameOver(piles, n) == false) {
            showPiles(piles, n);
            makeMove(piles, n, moves);
            if (whoseTurn == COMPUTER) {
                System.out.print("COMPUTER removes " + moves[1]
                    + " stones from pile at index " + moves[0] + "\n");
                whoseTurn = HUMAN;
            }
            else {
                System.out.print("HUMAN removes " + moves[1]
                    + " stones from pile at index " + moves[0] + "\n");
                whoseTurn = COMPUTER;
            }
        }
        showPiles(piles, n);
        declareWinner(whoseTurn);
    }


    /**
     * A Java function to display the predicted winner of the current game of Nim
     * 
     * @param piles
     *            - Array having the initial count of stones/coins
     *            in each piles before the game has started.
     * @param n
     *            - Number of piles
     * @param whoseTurn
     *            - either the computer's or the human's turn (0 || 1)
     */
    public void knowWinnerBeforePlaying(int[] piles, int n, int whoseTurn) {
        System.out.print("Prediction before playing the game -> ");
        if (calculateNimSum(piles, n) != 0) {
            if (whoseTurn == COMPUTER)
                System.out.print("COMPUTER will win\n");
            else
                System.out.print("HUMAN will win\n");
        }
        else {
            if (whoseTurn == COMPUTER)
                System.out.print("HUMAN will win\n");
            else
                System.out.print("COMPUTER will win\n");
        }
    }
}
