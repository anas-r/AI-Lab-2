package fr.emse.ai.util;

import java.util.Iterator;

public class SimpleMinimax {
    public int solve(SimpleTwoPlyGameTree<Integer> tree) {
        if (tree.isLeaf()) {
            return tree.getValue();
        } else {
            Iterator<SimpleTwoPlyGameTree<Integer>> childrenIterator;
            childrenIterator = tree.getChildren().iterator();
            int val;
            if (tree.isMax()) {
                val = Integer.MIN_VALUE;
                while (childrenIterator.hasNext()) {
                    val = Math.max(val, solve(childrenIterator.next()));
                }
            } else {
                val = Integer.MAX_VALUE;
                while (childrenIterator.hasNext()) {
                    val = Math.min(val, solve(childrenIterator.next()));
                }
            }
            tree.setValue(val);
            return val;
        }
    }
}