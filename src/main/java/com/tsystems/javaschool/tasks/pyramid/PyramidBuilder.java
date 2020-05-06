package com.tsystems.javaschool.tasks.pyramid;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class PyramidBuilder {

    /**
     * Builds a pyramid with sorted values (with minimum value at the top line and maximum at the bottom,
     * from left to right). All vacant positions in the array are zeros.
     *
     * @param inputNumbers to be used in the pyramid
     * @return 2d array with pyramid inside
     * @throws {@link CannotBuildPyramidException} if the pyramid cannot be build with given input
     */
    public int[][] buildPyramid(List<Integer> inputNumbers) {
        int height = getHeight(inputNumbers);

        try {
            Collections.sort(inputNumbers);
        } catch (NullPointerException e) {
            throw new CannotBuildPyramidException();
        }

        int[][] pyramid = new int[height][2 * height - 1];

        //Starting position in every row
        int start = height - 1;
        int totalRowInserts = 1;
        Iterator<Integer> iterator = inputNumbers.iterator();

        //Insert in every row numberOfInsertions fields;
        for (int row = 0; row < height; row++) {

            for (int pointer = start, currentRowInsertNumber = 0;
                 currentRowInsertNumber < totalRowInserts;
                 currentRowInsertNumber++, pointer += 2) {
                pyramid[row][pointer] = iterator.next();
            }
            start--;
            totalRowInserts++;
        }
        return pyramid;
    }

    private int getHeight(List<Integer> inputNumbers) {
        for (int x = 1; x <= inputNumbers.size(); x++) {
            if (x * (x + 1) / 2 == inputNumbers.size())
                return x;
        }
        throw new CannotBuildPyramidException();
    }


}
