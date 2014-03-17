/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import java.awt.Color;

/**
 *
 * @author yew_mentzaki
 */
public class Player {
    public int power, epower; 
    public String name;
    public float r, g, b;
    public int money = 7500;
    public Player(String name, float re, float gr, float bl) {
        this.name = name;
        r = re;
        g = gr;
        b = bl;
    }

    
}
