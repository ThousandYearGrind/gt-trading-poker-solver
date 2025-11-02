package org.poker.GameSim;

import org.poker.CFR.History.AbstractHistory;
import java.util.ArrayList;

public abstract class GameSim {
    public abstract int currentPlayer(AbstractHistory history);
    public abstract ArrayList<String> getAvailableActions(AbstractHistory history);
    public abstract void dealInitialCards(AbstractHistory history);
    public abstract boolean isTerminal(AbstractHistory history);
    public abstract double[] terminalUtility(AbstractHistory history);
    public abstract int numPlayers();

    // NEW: generate all possible initial deals
    public abstract ArrayList<AbstractHistory> generateAllDeals(AbstractHistory history);

    public abstract AbstractHistory randomDeal(AbstractHistory history);
}
