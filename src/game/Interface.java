/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import java.util.ArrayList;
import org.lwjgl.input.Keyboard;
import org.newdawn.slick.TrueTypeFont;

/**
 *
 * @author yew_mentzaki
 */
public class Interface {

    public String tooltip;
    public ArrayList<Button> buttons = new ArrayList<Button>();
    private Unit owner;

    public boolean toHide() {
        return owner != null ? !owner.selected | owner.hp <= 0 : true;
    }

    public Interface(Unit owner) {
        System.out.println("Init interface");
        this.owner = owner;
    }

    public int click(boolean[] keys, boolean mb, int x, int y, TrueTypeFont ttf) {
        if (owner != null ? owner.selected | owner.hp > 0 : false) {
            if (mb) {
                for (int i = 0; i < buttons.size(); i++) {
                    if (buttons.get(i).click(x, y, ttf)) {
                        return i + 1;
                    }

                }
            }
            for (int i = 0; i < buttons.size(); i++) {
                if ((keys[Keyboard.KEY_RSHIFT]|keys[Keyboard.KEY_LSHIFT])?buttons.get(i).key(keys, ttf):false) {
                    return i + 1;
                }
            }
        }
        return 0;
    }

    public void render(TrueTypeFont ttf) {
        if (owner != null ? owner.selected | owner.hp > 0 : false) {
            for (int i = 0; i < buttons.size(); i++) {
                buttons.get(i).render(ttf);
            }
        }
    }
}
