package fr.emse.ai.adversarial.tictactoe;

import fr.emse.ai.adversarial.Game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TicTacToeGame implements Game<String[][], Integer, String> {

    public final static String[] players = {"O", "X"};
    public final static String[][] initialState = {{"X"},
            {"_", "_", "_"},
            {"_", "_", "_"},
            {"_", "_", "_"}};

    public TicTacToeGame(String player) {
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

    @Override
    public List<Integer> getActions(String[][] state) {
        List<Integer> actions = new ArrayList<>();
        for (int i = 1; i < 4; i++) {
            for (int j = 0; j < 3; j++) {
                // If a case if free (i.e. the underscore is still there), add its number to the possible actions
                if (state[i][j].equals("_")) {
                    actions.add(3 * i + j - 2);
                }
            }
        }
        return actions;
    }

    @Override
    public String[][] getResult(String[][] state, Integer action) {
        String newPlayer = (state[0][0].equals("X")) ? "O" : "X";
        String[][] newState = {{newPlayer},
                {"_", "_", "_"},
                {"_", "_", "_"},
                {"_", "_", "_"}};
        // extracting the coordinates from the action number, e.g. case 5 => (i=2, j=1)
        int k = (action + 2) / 3;
        int l = (action + 2) % 3;
        if (state[k][l].equals("_")) {
            for (int i = 1; i < 4; i++) {
                System.arraycopy(state[i], 0, newState[i], 0, 3);
            }
            newState[k][l] = state[0][0];
            return newState;
        } else {
            // the state is unchanged if the actions is illegal (i.e. case already filled)
            return state;
        }
    }

    @Override
    public boolean isTerminal(String[][] state) {
        String[] XXX = {"X", "X", "X"};
        String[] OOO = {"O", "O", "O"};
        // adding the rows
        List<String[]> allPossibleLines = new ArrayList<>(Arrays.asList(state).subList(1, 4));
        // now the columns
        for (int j = 0; j < 3; j++) {
            allPossibleLines.add(new String[]{state[1][j], state[2][j], state[3][j]});
        }
        // finally the 2 diagonals
        allPossibleLines.add(new String[]{state[1][0], state[2][1], state[3][2]});
        allPossibleLines.add(new String[]{state[3][0], state[2][1], state[1][2]});
        // check if someone won, ie X-X-X or O-O-O in any possible line
        for (String[] line : allPossibleLines) {
            if (Arrays.equals(line, XXX) || Arrays.equals(line, OOO)) {
                return true;
            }
        }
        // if there's still one empty case to fill, i.e. more room to play
        for (int i = 1; i < 4; i++) {
            for (int j = 0; j < 3; j++) {
                if (state[i][j].equals("_")) {
                    return false;
                }
            }
        }
        // if the board is completely filled
        return true;
    }

    /* state is supposed to be final: either a draw or a play won. */
    @Override
    public double getUtility(String[][] state, String player) {
        boolean isDraw = true;
        for (int i = 1; i < 4; i++) {
            for (int j = 0; j < 3; j++) {
                if (state[i][j].equals("_")) {
                    isDraw = false;
                    break;
                }
            }
        }
        if (isDraw)
            return 0.5;
        else {
            return (state[0][0].equals(player)) ? 0 : 1;
        }
    }

    public String stringify(String[][] state) {
        StringBuilder sBuilder = new StringBuilder();
        for (int i = 1; i < 4; i++) {
            for (int j = 0; j < 3; j++) {
                sBuilder.append(state[i][j]).append(" | ");
            }
            sBuilder.setCharAt(sBuilder.length()-2,' ');
            sBuilder.append("\n");
        }
        return sBuilder.toString();
    }


    public boolean equals(String[][] state1, String[][] state2) {
        if (!state1[0][0].equals(state2[0][0])){
            return false;
        } else {
            for (int i = 1; i < 4; i++) {
                for (int j = 0; j < 3; j++) {
                    if (!state2[i][j].equals(state1[i][j])) {
                        return false;
                    }
                }
            }
        }
        return true;
    }
}