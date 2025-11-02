package org.poker.GameSim;

import java.util.ArrayList;
import java.util.Collections;
import org.poker.Card;
import org.poker.CFR.History.AbstractHistory;
import org.poker.CFR.History.KuhnPokerHistory;
import org.poker.HandEval.KuhnPokerHandEval;

public class KuhnPokerGameSim extends GameSim {

    private final ArrayList<Card> deck;
    private final KuhnPokerHandEval handEval;

    public KuhnPokerGameSim() {
        deck = new ArrayList<>();
        deck.add(new Card("1", 'H')); // Jack
        deck.add(new Card("2", 'H')); // Queen
        deck.add(new Card("3", 'H')); // King
        handEval = new KuhnPokerHandEval();
    }

    @Override
    public int currentPlayer(AbstractHistory history) {
        return ((KuhnPokerHistory) history).getCurrentPlayer();
    }

    @Override
    public ArrayList<String> getAvailableActions(AbstractHistory history) {
        KuhnPokerHistory kh = (KuhnPokerHistory) history;
        ArrayList<String> acts = kh.getActions();
        ArrayList<String> available = new ArrayList<>();
        if (isTerminal(kh)) return available;

        // Find the last non-Deal action
        String last = null;
        for (int i = acts.size() - 1; i >= 0; i--) {
            String action = acts.get(i);
            if (!action.startsWith("Deal")) {
                last = action;
                break;
            }
        }

        if (last == null || last.endsWith("Check")) {
            // First action or after a check - can check or bet
            available.add("Check");
            available.add("Bet");
        } else if (last.endsWith("Bet")) {
            // After a bet - can call or fold
            available.add("Call");
            available.add("Fold");
        } else {
            // Default: can check or bet
            available.add("Check");
            available.add("Bet");
        }
        return available;
    }

    @Override
    public void dealInitialCards(AbstractHistory history) {
        KuhnPokerHistory kh = (KuhnPokerHistory) history;
        Collections.shuffle(deck);
        kh.addCard(0, deck.get(0));
        kh.addCard(1, deck.get(1));
        kh.addAction("Deal P0:" + deck.get(0).getRank());
        kh.addAction("Deal P1:" + deck.get(1).getRank());
        kh.setCurrentPlayer(0); // ensure player 0 starts
    }

    @Override
    public boolean isTerminal(AbstractHistory history) {
        return ((KuhnPokerHistory) history).isTerminal();
    }

    @Override
    public double[] terminalUtility(AbstractHistory history) {
        ArrayList<Double> utils = handEval.utilityFromHistory(history);
        double[] result = new double[utils.size()];
        for (int i = 0; i < utils.size(); i++) {
            result[i] = utils.get(i);
        }
        return result;
    }

    @Override
    public int numPlayers() {
        return 2;
    }

    @Override
    public AbstractHistory randomDeal(AbstractHistory history) {
        AbstractHistory copy = history.copy();
        dealInitialCards(copy);
        return copy;
    }

    @Override
    public ArrayList<AbstractHistory> generateAllDeals(AbstractHistory history) {
        ArrayList<AbstractHistory> deals = new ArrayList<>();
        Card[] cards = { new Card("1",'H'), new Card("2",'H'), new Card("3",'H') };
        for (int i = 0; i < cards.length; i++) {
            for (int j = 0; j < cards.length; j++) {
                if (i == j) continue;
                KuhnPokerHistory kh = new KuhnPokerHistory();
                kh.addCard(0, cards[i]);
                kh.addCard(1, cards[j]);
                kh.addAction("Deal P0:" + cards[i].getRank());
                kh.addAction("Deal P1:" + cards[j].getRank());
                kh.setCurrentPlayer(0);
                deals.add(kh);
            }
        }
        return deals;
    }
}
