package fr.emse.ai.adversarial.tictactoe;

import fr.emse.ai.adversarial.AlphaBetaSearch;
import fr.emse.ai.adversarial.IterativeDeepeningAlphaBetaSearch;
import fr.emse.ai.adversarial.MinimaxSearch;

import java.util.Random;
import java.util.Scanner;

public class TicTacToeGameplay {
    public static void main(String[] args) {
        // TIC TAC TOE Game rules
        System.out.println("Welcome to the Tic Tac Toe Game: The Impossible Edition");
        System.out.println("Rule: enter an action 1-9 corresponding to the following grid:");
        System.out.println("1 2 3");
        System.out.println("4 5 6");
        System.out.println("7 8 9");
        System.out.println("Note: Player order is assigned randomly");
        // Random assignment of "O" and "X" to our player
        int randomNumber01 = (new Random()).nextInt(2);
        String humanPlayer = (randomNumber01 == 0)? "O" : "X";
        String computerPlayer = (humanPlayer.equals("O")) ? "X" : "O";
        // Random first player
        randomNumber01 = (new Random()).nextInt(2);
        String randomPlayer = (randomNumber01 == 0)? "O" : "X";
        TicTacToeGame game = new TicTacToeGame(randomPlayer);

        MinimaxSearch<String[][], Integer, String> minimaxSearch = MinimaxSearch.createFor(game);
        AlphaBetaSearch<String[][], Integer, String> alphabetaSearch = AlphaBetaSearch.createFor(game);
        IterativeDeepeningAlphaBetaSearch<String[][], Integer, String> iterativeDeepeningAlphaBetaSearch = IterativeDeepeningAlphaBetaSearch.createFor(game, -1, 1, 1);
        String[][] state = game.getInitialState();
        Scanner humanScanner = new Scanner(System.in);
        // Declaring to the user who plays first (human player / computer)
        if (randomPlayer.equals(humanPlayer)) {
            System.out.println("You are playing first. Your marker is: " + humanPlayer);
        } else {
            System.out.println("Computer is playing first. Your marker is: " + humanPlayer);
        }
        // Game is going on ...
        while (!game.isTerminal(state)) {
            System.out.println("==============");
            System.out.println(game.stringify(state));
            int action = 0;
            if (game.getPlayer(state).equals(humanPlayer)) {
                while (action > 9 || action < 1 || game.equals(state, game.getResult(state, action))) {
                    System.out.println("Please put in your action 1-9");
                    action = humanScanner.nextInt();
                }
            } else {
                action = iterativeDeepeningAlphaBetaSearch.makeDecision(state);
                System.out.println("Computer made action " + action);
                System.out.println("Metrics for Iterative Alpha-Beta Pruning: " + iterativeDeepeningAlphaBetaSearch.getMetrics());
                alphabetaSearch.makeDecision(state);
                System.out.println("Metrics for Alpha-Beta Pruning: " + alphabetaSearch.getMetrics());
                minimaxSearch.makeDecision(state);
                System.out.println("Metrics for Minimax: " + minimaxSearch.getMetrics());
            }
            state = game.getResult(state, action);
        }
        // Declaring a winner
        System.out.println(game.stringify(state));
        System.out.println("Game is over. WINNER: ");
        String result = (game.getUtility(state, humanPlayer) == 0.5) ? "= Nobody! It's a draw" :
                (game.getUtility(state, humanPlayer) == 0) ? "===== Computer! =====" : "======== You! =======";
        System.out.println(result);
    }
}

