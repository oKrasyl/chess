package org.example.piece;

import java.util.HashSet;
import java.util.Set;

public interface IBishop {
    default Set<CoordinatesShift> getBishopMoves(){
        Set<CoordinatesShift> result = new HashSet<>();

        //botom-left to right-down

        for (int i = -7; i <= 7 ; i++) {
            if (i==0)continue;
            result.add(new CoordinatesShift(i, i));
        }
        //top-left botom-right
        for (int i = -7; i <=7 ; i++) {
            if (i==0)continue;
            result.add(new CoordinatesShift(i, -i));

        }
        return result;
    }
}
