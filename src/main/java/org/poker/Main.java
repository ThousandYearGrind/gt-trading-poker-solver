package org.poker;

import org.poker.GameSim.KuhnPokerGameSim;
import org.poker.CFR.CFR;
import org.poker.CFR.History.KuhnPokerHistory;
import org.poker.CFR.InfoSet;

import java.util.Map;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("Enter number of iterations:");
        int iterations = sc.nextInt();

        KuhnPokerGameSim game = new KuhnPokerGameSim();
        KuhnPokerHistory initial = new KuhnPokerHistory();

        CFR trainer = new CFR(game);
        trainer.train(iterations, initial);
        printStrategies(trainer.getInfoSets());

        sc.close();
    }

    private static void printStrategies(Map<String, InfoSet> infoSets) {
        System.out.println("Average strategies:");
        for (InfoSet iset : infoSets.values()) {
            System.out.println(iset.getKey() + " : " + iset.getAverageStrategy());
        }
    }


}
