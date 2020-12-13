import student.TestCase;

public class NimGame extends TestCase {

    /**
     * The project runner for this Nim solver.
     */
    public void testLetsPlayNim() {
        Nim nim = new Nim();
        int[] piles = { 3, 4, 5 };
        int n = 3;
        
        // We will predict the results before playing
        // The COMPUTER starts first
        
        nim.knowWinnerBeforePlaying(piles, n, 1);
        // Let us play the game with COMPUTER starting first
        // and check whether our prediction was right or not
        nim.playGame(piles, n, 1);
    }
}
