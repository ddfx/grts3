package main;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.awt.BorderLayout;
import java.awt.Canvas;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;

import java.applet.Applet;
import java.util.Random;

/**
 *
 * @author yew_mentzaki
 */
public class AGame extends Applet {

    Canvas display_parent;

    public void startLWJGL() {
        try {
            Display.setParent(display_parent);
            Display.create();
        } catch (LWJGLException e) {
            e.printStackTrace();
        }
    }

    private void stopLWJGL() {
        Display.destroy();
    }

    public void start() {

    }

    public void stop() {

    }

    public void destroy() {
        remove(display_parent);
        super.destroy();
    }

    public void init() {
        setLayout(new BorderLayout());
        try {
            display_parent = new Canvas() {
                public final void addNotify() {
                    super.addNotify();
                    startLWJGL();
                }

                public final void removeNotify() {
                    stopLWJGL();
                    super.removeNotify();
                }
            };
            display_parent.setSize(getWidth(), getHeight());
            add(display_parent);
            display_parent.setFocusable(true);
            display_parent.requestFocus();
            display_parent.setIgnoreRepaint(true);
            setVisible(true);
            Game game = new Game();
            game.init3D();
            Random r = new Random();
            for (int i = 0; i < 1024; i++) {
                for (int j = 0; j < 1024; j++) {
                    Game.terrain[i][j] = r.nextFloat() * 1;
                }
            }
            while (!Display.isCloseRequested()) {
                game.render();
                game.update();
                Display.update();
                Display.sync(Game.frameRate);
            }
        } catch (Exception e) {
            System.err.println(e);
            throw new RuntimeException("Unable to create display");
        }
    }

    // TODO overwrite start(), stop() and destroy() methods
}
