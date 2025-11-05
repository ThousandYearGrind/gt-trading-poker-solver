package org.poker.HandEval;

import org.poker.CFR.History.AbstractHistory;
import org.poker.Card;

import java.util.ArrayList;

public class BitmaskHandEval extends HandEval {
    @Override
    public int compareHands(ArrayList<Card> a, ArrayList<Card> b) {
        return 0;
    }

    @Override
    public ArrayList<Double> utilityFromHistory(AbstractHistory history) {
        return null;
    }

    /**
     * Converts list of cards into a bitset.
     *
     * Reading from right to left, bit 1 represents a 2 value,
     * bit 2 represents a 3 value ... and bit 13 represents an Ace
     *
     * Suits:
     * Bits 1-13: Spades
     * Bits 14-26: Hearts
     * Bits 27-39: Diamonds
     * Bits 40-52: Clubs
     *
     * @param hand Array of cards, presumably 7
     * @return Bitset represented as long
     */
    private long handToBitmask(ArrayList<Card> hand) {
        long result = 0;
        for (Card card : hand) {
            //assuming Ace is rank 14
            long mask = 1L << 13 * suitToNumber(card.getSuite()) + card.getRank() - 2;
            result |= mask;
        }
        return result;
    }

    private int suitToNumber(char suit) {
        return switch (suit) {
            case 'S' -> 0;
            case 'H' -> 1;
            case 'D' -> 2;
            case 'C' -> 3;
            default -> throw new RuntimeException("Invalid suit: " + suit);
        };
    }
}
