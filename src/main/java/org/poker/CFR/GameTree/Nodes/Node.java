package org.poker.CFR.GameTree.Nodes;

public abstract class Node {
    public abstract Node getNext();
    public abstract boolean isTerminal();
}
