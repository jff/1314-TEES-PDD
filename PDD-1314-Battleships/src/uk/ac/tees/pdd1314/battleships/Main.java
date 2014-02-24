/*
 * Copyright (c) 2014, Joao F. Ferreira <joao@joaoff.com>
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * * Redistributions of source code must retain the above copyright notice, this
 *   list of conditions and the following disclaimer.
 * * Redistributions in binary form must reproduce the above copyright notice,
 *   this list of conditions and the following disclaimer in the documentation
 *   and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */
package uk.ac.tees.pdd1314.battleships;

import java.util.Scanner;

/**
 *
 * @author Joao F. Ferreira <joao@joaoff.com>
 */
public class Main {

    public static void main(String[] args) {

        Settings settings = new Settings();
        
        Grid grid = new GridArray(settings.getSIZE());
        grid.placeBoats(settings.getSHIPS());

        /* Start by showing grid, unless it's a blindfold game */
        if (!settings.isBlindFoldGame()) {
            System.out.println(grid.toString());
        } else {
            System.out.println("This is a blindfold game. If you want to see the grid, turn the blindfold option off.");
        }

        while (grid.existBoatsFloating()) {
            String coordinate = getInputFromUser(grid, settings);

            try {
                GridCellStatus hit = grid.hit(coordinate);

                if (!settings.isBlindFoldGame()) {
                    System.out.println(grid.toString());
                }

                switch (hit) {
                    case CELL_SUCCESSFUL_SHOT:
                        System.out.println("Well done! Successful shot (" + coordinate + ")");
                        break;
                    case CELL_MISSED_SHOT:
                        System.out.println("Missed shot! Please try again.");
                        break;
                    default: //IMPOSSIBLE
                }

            } catch (InvalidCoordinateException e) {
                System.out.println("ERROR: Problem with coordinate.");
            }
        }

        /* When the loop terminates, there are no boats floating */
        System.out.println("Game over. There are no boats floating.");
    }

    public static String getInputFromUser(Grid b, Settings settings) {
        Scanner s = new Scanner(System.in);
        String coordinate;
        boolean isValidCoordinate = false;
        do {
            System.out.print("Enter coordinate (e.g. B2), 'blindfold', or 'exit': ");
            coordinate = s.nextLine().toUpperCase();
            System.out.println("");
            if (coordinate.equals("BLINDFOLD")) {
                settings.switchBlindFoldGame();
                System.out.println("Blindfold option is now " + (settings.isBlindFoldGame() ? "on" : "off"));
            } else if (coordinate.equals("EXIT")) {
                System.out.println("Goodbye!");
                System.exit(0);
            } else {
                isValidCoordinate = b.isValidCoordinate(coordinate);
                if (!isValidCoordinate) {
                    System.out.println();
                    System.out.println("Invalid coordinate. Please try again.");
                }
            }
        } while (!isValidCoordinate);

        return coordinate;
    }

}
