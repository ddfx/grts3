/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import main.GRTS3;

import java.util.ArrayList;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.util.ResourceLoader;

/**
 *
 * @author yew_mentzaki
 */
public class TextureLoader {

    private static ArrayList<Texture> tx = new ArrayList<Texture>();
    private static ArrayList<String> st = new ArrayList<String>();

    public static void bind(String path) {
        String home = GRTS3.gameHome;
        path = home + "/res/images/" + path;
        if (st.contains(path)) {
            tx.get(st.indexOf(path)).bind();
            
        } else {
            try {
                Texture tex = org.newdawn.slick.opengl.TextureLoader.getTexture("JPG", ResourceLoader.getResourceAsStream(path));
                tx.add(tex);
                st.add(path);
                tex.bind();
            } catch (Exception e) {
                System.exit(-1);
            }

        }
    }
}
