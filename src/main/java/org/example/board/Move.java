package org.example.board;

import org.example.Coordinates;

public class Move {
    public Move(Coordinates from, Coordinates to) {
        this.from = from;
        this.to = to;
    }

    public final Coordinates from, to;
}
