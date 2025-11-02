package org.poker.HandEval;

import java.util.ArrayList;
import org.poker.Card;
import org.poker.CFR.History.AbstractHistory;
import org.poker.CFR.History.KuhnPokerHistory;

/**
 * Kuhn Poker–specific hand evaluator.
 */
public class KuhnPokerHandEval extends HandEval {

    @Override
    public int compareHands(ArrayList<Card> a, ArrayList<Card> b) {
        if (a.isEmpty() || b.isEmpty()) {
            throw new IllegalArgumentException("Hands must each contain one card.");
        }
        Card c1 = a.get(0);
        Card c2 = b.get(0);
        int r1 = c1.getRank();
        int r2 = c2.getRank();
        return Integer.compare(r1, r2);
    }


    @Override
    public ArrayList<Double> utilityFromHistory(AbstractHistory history) {
        KuhnPokerHistory h = (KuhnPokerHistory) history;

        ArrayList<ArrayList<Card>> hands = new ArrayList<>();
        hands.add(h.getHand(0));
        hands.add(h.getHand(1));

        // Base contributions: antes
        ArrayList<Double> contributions = new ArrayList<>();
        contributions.add(1.0);
        contributions.add(1.0);

        // Track additional bets/calls
        for (String action : h.getActions()) {
            if (action.endsWith("Bet") || action.endsWith("Call")) {
                int p = action.startsWith("P0:") ? 0 : 1;
                contributions.set(p, contributions.get(p) + 1.0);
            }
        }

        // Handle folding
        for (String action : h.getActions()) {
            if (action.endsWith("Fold")) {
                ArrayList<Double> utils = new ArrayList<>();
                utils.add(0.0);
                utils.add(0.0);
                int foldingPlayer = action.startsWith("P0:") ? 0 : 1;
                int winner = 1 - foldingPlayer;
                utils.set(winner, contributions.get(foldingPlayer));
                utils.set(foldingPlayer, -contributions.get(foldingPlayer));
                return utils;
            }
        }

        // Showdown
        return utility(contributions, hands);
    }
}
