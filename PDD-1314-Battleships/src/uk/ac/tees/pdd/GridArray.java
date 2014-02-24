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
package uk.ac.tees.pdd;

import java.util.ArrayList;

/**
 *
 * @author Joao F. Ferreira <joao@joaoff.com>
 */
public class GridArray extends Grid {

    private GridCell[][] board;

    public GridArray(int size) {
        super(size);
        board = new GridCell[this.getSize()][this.getSize()];
        for(int row=0; row<this.getSize(); row++) {
            for (int col = 0; col < this.getSize(); col++) {
                board[row][col] = new GridCell();
            }
        }
    }

    @Override
    public void placeBoats(ArrayList<Ship> ships) {
        /* Basic strategy for placing ships. This method assumes that all the ships in the arraylist fit into the board */
        int currentRow = 0;
        int currentCol = 0;
        for(Ship ship: ships) {
            if(ship.getSize() < this.getSize() - currentCol) {
                // We can place the ship at position (currentRow, currentCol)
                for(int i=currentCol; i<ship.getSize(); i++) {
                    board[currentRow][i] = new GridCell(GridCellStatus.CELL_UNTARGETED, ship);
                    currentCol = currentCol + ship.getSize();
                }
            } else {
                 // We place the ship at position (currentRow+1, 0)
                currentRow++;
                currentCol = 0;
               
                for(int i=currentCol; i<ship.getSize(); i++) {
                    board[currentRow][i] = new GridCell(GridCellStatus.CELL_UNTARGETED, ship);
                    currentCol = currentCol + ship.getSize();
                }
            }
            this.setNumberOfShips(this.getNumberOfShipsFloating()+1);
        }
    }

    @Override
    public GridCell getCoordinate(String coordinate) throws InvalidCoordinateException {
        if (!this.isValidCoordinate(coordinate)) {
            throw new InvalidCoordinateException("The coordinate " + coordinate + " is invalid.");
        } else {
            int col = coordinate.charAt(0) - 'A';
            String tail = coordinate.substring(1);
            int row = Integer.parseInt(tail);
            
            return this.board[row-1][col];
        }
    }

}
