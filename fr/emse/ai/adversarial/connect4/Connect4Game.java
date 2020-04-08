package fr.emse.ai.adversarial.connect4;

import fr.emse.ai.adversarial.Game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Connect4Game implements Game<String[][], Integer, String> {
    /**
     * This game, after some research, is only an extension of TicTacToe with a gameplay tweak and table extension from 3x3 to 6x7.
     * Code "recycling" from TicTacToe is to be expected!
     */

    public final static String[] players = {"O", "X"};
    public final static String[][] initialState = {{"X"},
            {"_", "_", "_", "_", "_", "_", "_"},
            {"_", "_", "_", "_", "_", "_", "_"},
            {"_", "_", "_", "_", "_", "_", "_"},
            {"_", "_", "_", "_", "_", "_", "_"},
            {"_", "_", "_", "_", "_", "_", "_"},
            {"_", "_", "_", "_", "_", "_", "_"},
    };

    public Connect4Game(String player) {
        if (player.equals("O") || player.equals("X")) {
            initialState[0][0] = player;
        }
    }

    @Override
    public String[][] getInitialState() {
        return initialState;
    }


    @Override
    public String[] getPlayers() {
        return players;
    }

    @Override
    public String getPlayer(String[][] state) {
        return state[0][0];
    }

    /*
    I modeled the actions as the column number 1-7 until a designated column is filled. To check if an action is
    possible, we only need to check whether it's summit is full or not, i.e. state[1][action] = "_" i = 1 being
    the summit.
     */
    @Override
    public List<Integer> getActions(String[][] state) {
        List<Integer> actions = new ArrayList<>();
        for (int j = 0; j < 7; j++) {
            if (state[6][j].equals("_")) {
                // Actions are numerated 1-7 instead of 0-6.
                actions.add(j + 1);
            }
        }
        return actions;
    }

    @Override
    public String[][] getResult(String[][] state, Integer action) {
        String newPlayer = (state[0][0].equals("X")) ? "O" : "X";
        String[][] newState = {{newPlayer},
                {"_", "_", "_", "_", "_", "_", "_"},
                {"_", "_", "_", "_", "_", "_", "_"},
                {"_", "_", "_", "_", "_", "_", "_"},
                {"_", "_", "_", "_", "_", "_", "_"},
                {"_", "_", "_", "_", "_", "_", "_"},
                {"_", "_", "_", "_", "_", "_", "_"}
        };
        if (!state[6][action-1].equals("_"))
            return state;
        for (int i = 1; i < 7; i++)
            System.arraycopy(state[i], 0, newState[i], 0, 7);
        for (int i = 1; i < 7; i++) {
            if (newState[i][action - 1].equals("_")) {
                newState[i][action - 1] = newPlayer;
                break;
            }
        }
        return newState;
    }

    @Override
    public boolean isTerminal(String[][] state) {
        /*
        The are 69 possible legal 4-cell combinations in the game. I'll generate them all and check one by one
        for a winner. Optimizations are always possible.
         */
        List<String[]> allPossibleLines = new ArrayList<>();
        String[] XXXX = {"X", "X", "X", "X"};
        String[] OOOO = {"O", "O", "O", "O"};

        // adding the rows: 4x6 = 24 possible combinations
        for (int i = 1; i < 7; i++) {
            for (int j = 0; j < 4; j++) {
                allPossibleLines.add(new String[]{state[i][j], state[i][j + 1], state[i][j + 2], state[i][j + 3]});
            }
        }
        // adding the columns: 3x7 = 21 possible combinations (total 45)
        for (int j = 0; j < 7; j++) {
            for (int i = 1; i < 4; i++) {
                allPossibleLines.add(new String[]{state[i][j], state[i + 1][j], state[i + 2][j], state[i + 3][j]});
            }
        }
        // adding the first diagonals. A bit trickier! I found a pattern, however. 2x6 combs = 12 (total 57)
        for (int k = 4; k < 10; k++) {
            for (int i = Math.max(k - 6, 1); i <= Math.min(k - 3, 3); i++) {
                allPossibleLines.add(new String[]{state[i][k - i], state[i + 1][k - i - 1], state[i + 2][k - i - 2], state[i + 3][k - i - 3]});
            }
        }
        // adding the second diagonals.  2x6 combs = 12 (total 69)
        for (int l = -3; l < 3; l++) {
            for (int i = Math.max(-l, 1); i <= Math.min(3, 3 - l); i++) {
                allPossibleLines.add(new String[]{state[i][i + l], state[i + 1][i + l + 1], state[i + 2][i + l + 2], state[i + 3][i + l + 3]});
            }
        }
        // if there's a winner
        for (String[] line : allPossibleLines) {
            if (Arrays.equals(line, XXXX) || Arrays.equals(line, OOOO)) {
                return true;
            }
        }
        // if there's still one empty column summit to fill, i.e. more room to play
        for (int j = 0; j < 7; j++) {
            if (state[6][j].equals("_")) {
                return false;
            }
        }
        // if the board is completely filled => a draw
        return true;
    }

    @Override
    public double getUtility(String[][] state, String player) {
        // The problem is that isTerminal(..) could return true regardless of whether someone won or it is a draw.
        // I need to make the distinction.
        boolean isDraw = true;
        for (int i = 1; i < 7; i++) {
            for (int j = 0; j < 7; j++) {
                if (state[i][j].equals("_")) {
                    isDraw = false;
                    break;
                }
            }
        }
        if (isDraw)
            return 0;
        else {
            return (state[0][0].equals(player)) ? 0 : 1;
        }
    }

    public String stringify(String[][] state) {
        StringBuilder sBuilder = new StringBuilder();
        for (int i = 1; i < 7; i++) {
            for (int j = 0; j < 7; j++) {
                sBuilder.append(state[7 - i][j]).append(" ");
            }
            sBuilder.append("\n");
        }
        // columns name
        sBuilder.append("1 2 3 4 5 6 7\n");
        return sBuilder.toString();
    }

    public boolean equals(String[][] state1, String[][] state2) {
        if (!state1[0][0].equals(state2[0][0])) {
            return false;
        } else {
            for (int i = 1; i < 7; i++) {
                for (int j = 0; j < 7; j++) {
                    if (!state2[i][j].equals(state1[i][j])) {
                        return false;
                    }
                }
            }
        }
        return true;
    }
}
