/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;
import org.newdawn.slick.TrueTypeFont;

/**
 *
 * @author yew_mentzaki
 */
public class Button {

    byte key;
    int x, y;
    String text;
    int et;
    public Button(int key, int x, int y, String text) {
        this.key = (byte) key;
        this.x = x;
        this.y = y;
        this.text = "[" + Keyboard.getKeyName(key) + "] " + text;
                
    }

    public void event() {

    }

    public boolean click(int mx, int my, TrueTypeFont ttf) {
        if (mx >= x - 5 && my >= y - 5 && mx <= x + 1005 && my <= y + 5 + ttf.getHeight(text)) {
            et=5;
            event();
            return true;

        }
        return false;
    }

    public boolean key(boolean[] keys, TrueTypeFont ttf) {
        if (keys[key]) {
            et=5;
            event();
            
            return true;
            
        }
        return false;
    }

    public void render(TrueTypeFont ttf) {
        /*
         GL11.glColor3f(1f, 1f, 0);
         GL11.glBegin(GL11.GL_QUADS);
        
         GL11.glVertex2f(x-8, y-8);
         GL11.glVertex2f(x+ttf.getWidth(text)+8, y-8);
        
         GL11.glVertex2f(x+ttf.getWidth(text)+8, y+ttf.getHeight(text)+8);
        
         GL11.glVertex2f(x-8, y+ttf.getHeight(text)+8);
         GL11.glEnd();
         */
        

        ttf.drawString(x, y, text, et==0?Color.yellow:Color.gray);
        if(et>0)et--;
    }

}
