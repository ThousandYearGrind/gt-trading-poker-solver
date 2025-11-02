package org.poker.HandEval;

import org.poker.Card;
import org.poker.CFR.History.AbstractHistory;
import java.util.ArrayList;

/**
 * Abstract hand evaluator — provides generic utility logic.
 */
public abstract class HandEval {

    /**
     * Returns utility for a specific player relative to their contribution (ante + bets)
     */
    public double utility(ArrayList<Double> contributions,
                          ArrayList<ArrayList<Card>> hands,
                          int player) {

        ArrayList<Card> playerHand = hands.get(player);

        for (int i = 0; i < hands.size(); i++) {
            if (i == player) continue;

            // ✅ This calls the abstract compareHands() implemented in subclasses
            int comparison = compareHands(playerHand, hands.get(i));

            if (comparison < 0) {
                // Player loses
                return -contributions.get(player);
            } else if (comparison == 0) {
                // Tie: split pot, minus own contribution
                double totalPot = 0;
                for (double contrib : contributions) totalPot += contrib;
                return (totalPot / hands.size()) - contributions.get(player);
            }
        }

        // Player wins: gain others' contributions
        double winnings = 0;
        for (int i = 0; i < contributions.size(); i++) {
            if (i != player) winnings += contributions.get(i);
        }
        return winnings;
    }

    /**
     * Returns utilities for all players
     */
    public ArrayList<Double> utility(ArrayList<Double> contributions,
                                     ArrayList<ArrayList<Card>> hands) {
        ArrayList<Double> arr = new ArrayList<>();
        for (int player = 0; player < hands.size(); player++) {
            arr.add(utility(contributions, hands, player));
        }
        return arr;
    }

    // ✅ REQUIRED abstract methods for subclasses to implement
    public abstract int compareHands(ArrayList<Card> a, ArrayList<Card> b);

    public abstract ArrayList<Double> utilityFromHistory(AbstractHistory history);
}
