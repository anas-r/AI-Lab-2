package fr.emse.ai.util;

import java.util.Iterator;

public class SimpleAlphaBeta {
    public int solve(SimpleTwoPlyGameTree<Integer> tree, int alpha, int beta) {
        if (tree.isLeaf()) {
            return tree.getValue();
        } else {
            Iterator<SimpleTwoPlyGameTree<Integer>> childrenIterator = tree.getChildren().iterator();
            if (tree.isMax()) {
                int val = Integer.MIN_VALUE;
                while (childrenIterator.hasNext()) {
                    val = Math.max(val, solve(childrenIterator.next(), alpha, beta));
                    alpha = Math.max(alpha, val);
                    if (alpha >= beta) {
                        break;
                    }
                }
                return val;
            } else {
                int val = Integer.MAX_VALUE;
                while (childrenIterator.hasNext()) {
                    val = Math.min(val, solve(childrenIterator.next(), alpha, beta));
                    beta = Math.min(beta, val);
                    if (alpha >= beta) {
                        break;
                    }
                }
                return val;
            }
        }
    }
}
