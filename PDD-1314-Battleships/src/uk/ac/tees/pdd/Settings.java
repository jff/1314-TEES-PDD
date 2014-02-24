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
public class Settings {
    private int SIZE = 10;
    private ArrayList<Ship> SHIPS = new ArrayList<>();
    private boolean blindFoldGame = false;
    
    /**
     * Default settings.
     */
    public Settings() {
        SIZE = 6;
        blindFoldGame = false;
        
        SHIPS.add(new Destroyer());
        SHIPS.add(new BattleShip());
        SHIPS.add(new BattleShip());
    }

    public int getSIZE() {
        return SIZE;
    }

    public void setSIZE(int SIZE) {
        this.SIZE = SIZE;
    }

    public ArrayList<Ship> getSHIPS() {
        return SHIPS;
    }
    
    public void addShip(Ship ship) {
        this.SHIPS.add(ship);
    }

    public void setSHIPS(ArrayList<Ship> SHIPS) {
        this.SHIPS = SHIPS;
    }

    public boolean isBlindFoldGame() {
        return blindFoldGame;
    }

    public void switchBlindFoldGame() {
        if(this.isBlindFoldGame()) {
            this.blindFoldGame = false;
        } else {
            this.blindFoldGame = true;
        }
    }
 
    
}
