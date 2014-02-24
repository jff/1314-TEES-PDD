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

import java.util.ArrayList;

/**
 *
 * @author Joao F. Ferreira <joao@joaoff.com>
 */
public abstract class Grid {

    private final int size;
    private int numShipsFloating;

    Grid(int size) {
        this.size = size;
    }
    
    public abstract void placeBoats(ArrayList<Ship> ships);
    protected abstract GridCell getCoordinate(String coordinate) throws InvalidCoordinateException;

    public int getSize() {
        return size;
    }

    public boolean existBoatsFloating() {
        return numShipsFloating != 0;
    }
    
    public void setNumberOfShips(int numShips) {
        this.numShipsFloating = numShips;
    }
    
    public int getNumberOfShipsFloating() {
        return this.numShipsFloating;
    }

    public boolean isValidCoordinate(String coordinate) {
        
        if(coordinate.length() < 2) {
            return false;
        }
        
        char first = coordinate.charAt(0);
        String tail = coordinate.substring(1);

        boolean colIsValid = (first >= 'A') && (first < 'A' + size);

        int row;
        try {
            row = Integer.parseInt(tail);
        } catch (NumberFormatException e) {
            return false;
        }
        boolean rowIsValid = (row >= 1) && (row <= size);

        return rowIsValid && colIsValid;
    }

    public GridCellStatus hit(String coordinate) throws InvalidCoordinateException {
        GridCell cell = this.getCoordinate(coordinate);
        switch(cell.getStatus()) {
            case CELL_UNTARGETED:
                if(cell.getShip() != null) {
                    /* There is a boat in this cell. Successful shot! */
                    cell.setStatus(GridCellStatus.CELL_SUCCESSFUL_SHOT);
                    cell.getShip().hit();

                    /* If the boat has sunk, decrease numBoatsFloating */
                    if(!cell.getShip().isFloating()) {
                        this.numShipsFloating--;
                    }
                } else {
                    /* There is no boat in this cell. Missed shot. */
                    cell.setStatus(GridCellStatus.CELL_MISSED_SHOT);
                }
                break;

             /* For the remaining two cases, the cell does not need to be updated. */
            case CELL_MISSED_SHOT: break;
            case CELL_SUCCESSFUL_SHOT: break;
            default: break;
        }

        /* Return new status of cell */
        return cell.getStatus();
    }


    @Override
    public String toString() {

        int leftIndent = 3;

        String s = "";
        s = s + stringRepeat(" ", leftIndent+2);
        for (int col = 'A'; col < 'A' + this.getSize(); col++) {
            s = s + (char) col + stringRepeat(" ", 3);
        }
        s = s + "\n";
        for (int row = 1; row <= this.getSize(); row++) {
            int lenNum = String.valueOf(row).length();
            s = s + stringRepeat(" ", leftIndent) + "+";
            s = s + stringRepeat("---+", this.getSize());
            s = s + "\n" + row + stringRepeat(" ", leftIndent-lenNum) + "+";

            for(int col='A'; col< 'A' + this.getSize(); col++) {
                try {
                GridCell cell = this.getCoordinate((""+(char)col+row));
                
                switch(cell.getStatus()) {
                    case CELL_UNTARGETED: s = s + "   +"; break;
                    case CELL_MISSED_SHOT:    s = s + " X +"; break;
                    case CELL_SUCCESSFUL_SHOT:
                        Ship ship = cell.getShip();
                        if(ship.isFloating()) {
                            s = s + " V +";
                        } else {
                            s = s + " " + ship.toChar() + " +";
                        }
                        break;
                    default: s = s + "   +";
                }
                } catch(InvalidCoordinateException e) {
                    e.printStackTrace();
                }

            }

            s = s + "\n";
        }
        s = s + stringRepeat(" ", leftIndent) + "+";
        s = s + new String(new char[this.getSize()]).replace("\0", "---+");
        return s;
    }

    private String stringRepeat(String s, int n) {
        return new String(new char[n]).replace("\0", s);
    }
}
