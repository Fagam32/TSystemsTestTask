package com.tsystems.javaschool.tasks.subsequence;

import java.util.Iterator;
import java.util.List;

public class Subsequence {

    /**
     * Checks if it is possible to get a sequence which is equal to the first
     * one by removing some elements from the second one.
     *
     * @param x first sequence
     * @param y second sequence
     * @return <code>true</code> if possible, otherwise <code>false</code>
     */
    @SuppressWarnings("rawtypes")
    public boolean find(List x, List y) {
        if ((x == null && y != null) || (x != null && y == null))
            throw new IllegalArgumentException();
        if (x == null) {
            return false;
        }
        if (x.isEmpty()) {
            return true;
        }
        if (y.isEmpty()) {
            return false;
        }
        Iterator iteratorX = x.iterator();
        Iterator iteratorY = y.iterator();
        Object currentX = iteratorX.next(), currentY = iteratorY.next();
        while (iteratorX.hasNext() && iteratorY.hasNext()) {
            if (currentX.equals(currentY)) {
                currentX = iteratorX.next();
            }
            currentY = iteratorY.next();
        }
        return !iteratorX.hasNext();
    }
}
