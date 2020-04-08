package fr.emse.ai.util;

import java.util.ArrayList;

public class SimpleTwoPlyGameTest {
    public static void main(String[] args) {
        // First node is a 'MAX' node
        SimpleTwoPlyGameTree<Integer> tree1 = new SimpleTwoPlyGameTree<>(Integer.MAX_VALUE, true);
        SimpleTwoPlyGameTree<Integer> tree2 = new SimpleTwoPlyGameTree<>(Integer.MAX_VALUE, true);
        ArrayList<SimpleTwoPlyGameTree<Integer>> sublist1 = new ArrayList<SimpleTwoPlyGameTree<Integer>>();
        sublist1.add(new SimpleTwoPlyGameTree<Integer>(3, true));
        sublist1.add(new SimpleTwoPlyGameTree<Integer>(12, true));
        sublist1.add(new SimpleTwoPlyGameTree<Integer>(8, true));
        SimpleTwoPlyGameTree<Integer> subtree1 = new SimpleTwoPlyGameTree<Integer>(Integer.MIN_VALUE, false, sublist1);
        ArrayList<SimpleTwoPlyGameTree<Integer>> sublist2 = new ArrayList<SimpleTwoPlyGameTree<Integer>>();
        sublist2.add(new SimpleTwoPlyGameTree<Integer>(2, true));
        sublist2.add(new SimpleTwoPlyGameTree<Integer>(4, true));
        sublist2.add(new SimpleTwoPlyGameTree<Integer>(6, true));
        SimpleTwoPlyGameTree<Integer> subtree2 = new SimpleTwoPlyGameTree<Integer>(Integer.MIN_VALUE, false, sublist2);
        ArrayList<SimpleTwoPlyGameTree<Integer>> sublist3 = new ArrayList<SimpleTwoPlyGameTree<Integer>>();
        sublist3.add(new SimpleTwoPlyGameTree<Integer>(14, true));
        sublist3.add(new SimpleTwoPlyGameTree<Integer>(5, true));
        sublist3.add(new SimpleTwoPlyGameTree<Integer>(2, true));
        SimpleTwoPlyGameTree<Integer> subtree3 = new SimpleTwoPlyGameTree<Integer>(Integer.MIN_VALUE, false, sublist3);

        tree1.addChild(subtree1);
        tree1.addChild(subtree2);
        tree1.addChild(subtree3);

        tree2.addChild(subtree1);
        tree2.addChild(subtree2);
        tree2.addChild(subtree3);
        // "Naive" minimax
        long t = System.nanoTime();
        System.out.println("The Minimax value is: "+new SimpleMinimax().solve(tree1));
        System.out.println(System.nanoTime() - t + " ns");
        // Minimax with Alpha-Beta pruning
        t = System.nanoTime();
        System.out.println("The Minimax value is: "+new SimpleAlphaBeta().solve(tree2, Integer.MIN_VALUE, Integer.MAX_VALUE));
        System.out.println(System.nanoTime() - t + " ns");
        /*
        One example of a successful execution:

        The Minimax value is: 3
        1211614 ns
        The Minimax value is: 3
        511374 ns

        roughly 60% faster
        */
    }
}
