package main;


/*
 * IMPORTS FROM: 	lwjgl.jar + Native Libraries
 * 					lwjgl_util.jar
 * 					slick-util.jar
 */
import de.matthiasmann.twl.utils.PNGDecoder;
import de.matthiasmann.twl.utils.PNGDecoder.Format;
import game.Button;
import game.Point;
import game.UnitUtils;
import game.World;
import java.awt.Font;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import static org.lwjgl.opengl.GL11.*;
import org.lwjgl.util.glu.GLU;
import org.newdawn.slick.Color;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

public class Game {

    public static final String VERSION = "Pre-aplha 0.1";
    private Map<String, Texture> textureMap = new HashMap<String, Texture>();
    private Texture texFloor;
    private Texture texIron;
    private Texture texSky;
    private Texture texWhite;

    public Texture getTexture(String path) {
        int floorTexture = glGenTextures();
        /*{
         InputStream in = null;
         try {
         in = new FileInputStream("res/images/floor.png");
         PNGDecoder decoder = new PNGDecoder(in);

         ByteBuffer buffer = BufferUtils.createByteBuffer(4 * decoder.getWidth() * decoder.getHeight());
         decoder.decode(buffer, decoder.getWidth() * 4, Format.RGBA);
         buffer.flip();
         glBindTexture(GL_TEXTURE_2D, floorTexture);
         glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
         glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
         glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, decoder.getWidth(), decoder.getHeight(), 0, GL_RGBA,
         GL_UNSIGNED_BYTE, buffer);
         glBindTexture(GL_TEXTURE_2D, 0);
         } catch (FileNotFoundException ex) {
         System.err.println("Failed to find the texture files.");
         ex.printStackTrace();
         Display.destroy();


         } catch (IOException ex) {
         System.err.println("Failed to load the texture files.");
         ex.printStackTrace();
         Display.destroy();
 
         } finally {
         if (in != null) {
         try {
         in.close();
         } catch (IOException e) {
         e.printStackTrace();
         }
         }
         }
         }*/
        return null;
    }

    static float[][] terrain = new float[1024][1024];
    final static int width = 800, height = 600;
    final static int frameRate = 90;
    boolean mouseSwitch = true;
    boolean[] keys = new boolean[256];
    Camera camera;

    public static void main(String[] args) throws LWJGLException {
        Display.setTitle("gRTS3 - The war continies");

        Display.setDisplayMode(new DisplayMode(width, height));
        Display.create();
        Game game = new Game();
        game.init3D();
        Random r = new Random();
        for (int i = 0; i < 1024; i++) {
            for (int j = 0; j < 1024; j++) {
                terrain[i][j] = r.nextFloat() * 1;
            }
        }
        while (!Display.isCloseRequested()) {
            game.render();
            game.update();
            Display.update();
            Display.sync(frameRate);
        }
        Display.destroy();
        System.exit(0);
    }

    public Game() {
        camera = new Camera(this);
        loadTextures();
        w = new World(0, terrain);
    }

    private void loadTextures() {
        getTexture(null);
        Font awtFont = new Font("Times New Roman", Font.BOLD, 15);
        font = new TrueTypeFont(awtFont, false);

        try {
            InputStream inputStream = ResourceLoader.getResourceAsStream(GRTS3.gameHome + "/res/fonts/font.ttf");

            Font awtFont2 = Font.createFont(Font.TRUETYPE_FONT, inputStream);
            awtFont2 = awtFont2.deriveFont(15f); // set font size
            font2 = new TrueTypeFont(awtFont2, true);

        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            texFloor = TextureLoader.getTexture("JPG", ResourceLoader.getResourceAsStream("res/images/sand.jpg"));

            texIron = TextureLoader.getTexture("JPG", ResourceLoader.getResourceAsStream("res/images/iron.jpg"));

            texSky = TextureLoader.getTexture("JPG", ResourceLoader.getResourceAsStream("res/images/sky.jpg"));

            texWhite = TextureLoader.getTexture("JPG", ResourceLoader.getResourceAsStream("res/images/floor.jpg"));
            /*try {
             InputStream inputStream	= ResourceLoader.getResourceAsStream("res/fonts/font.ttf");
            
             Font awtFont2 = Font.createFont(Font.TRUETYPE_FONT, inputStream);
             awtFont2 = awtFont2.deriveFont(0.05f); // set font size
             font = new TrueTypeFont(awtFont2, false);
            
             } catch (Exception e) {
             e.printStackTrace();
             }*/
        } catch (IOException ex) {
            Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    World w;
    int mb;
    int lmb;
    int sx, sz, sd;
    int ax, az, ad;
    boolean bm;
    int bw = 0;
    TrueTypeFont font, font2;

    void init_l() {
    }

    public void render() {
        clearScreen();
////////////////////////////////////////////////////
        glDisable(GL_LIGHTING);										// enables lighting
        glDisable(GL_LIGHT0);

        if (bw > 0) {
            bw--;
        }

        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

        GL11.glMatrixMode(GL11.GL_MODELVIEW);

        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glLoadIdentity();
        //GL11.glMatrixMode(GL11.GL_MODELVIEW);
        double cd = Math.cos(camera.rotation.x / 180 * Math.PI) * (22 / Math.sin(camera.rotation.x / 180 * Math.PI));
        cd = Math.min(cd, 250.0);
        if (cd < 0) {
            cd = 250;
        }
        double cdir = (camera.rotation.y - 90) / 180 * Math.PI;

        glOrtho(0, 800, 600, 0, -1, 1);

        if (w.i != null) {
            w.i.render(font2);
            if (bw <= 0 && w.i.click(keys, Mouse.isButtonDown(0) && !mouseSwitch, Mouse.getX(), 600 - Mouse.getY(), font2) != 0) {
                bw = 5;
            }
        }

        font2.drawString(50, 530, "gRTS3 - " + VERSION + " " + w.players[w.player].money + "$", Color.yellow);
        if (bm) {
            font2.drawString(225, 530, "SC", Color.green);
        }
        if (mouseSwitch) {
            font2.drawString(245, 530, "SM", Color.cyan);
        }

        int ssx = (int) (camera.vector.x + (float) (Math.cos(cdir) * cd));
        int ssz = (int) (camera.vector.z + (float) (Math.sin(cdir) * cd));

        font2.drawString(70, 515, w.getTarget(ssx, ssz), Color.yellow);

        GL11.glTranslatef(0, 0, -15);
        Color.white.bind();

        GL11.glTranslatef(0, 0, 15);

        GL11.glDisable(GL11.GL_BLEND);

        init3D();

        ////////////////////////////////////   
        GL11.glBegin(GL11.GL_QUADS);
        if (cd > 65) {
            GL11.glColor3f(0f, 1f, 0f);
            GL11.glVertex3f(-0.03f, 0.01f, -2);
            GL11.glVertex3f(0.03f, 0.01f, -2);
            GL11.glVertex3f(0.03f, -0.01f, -2);
            GL11.glVertex3f(-0.03f, -0.01f, -2);
            GL11.glVertex3f(-0.005f, 0.04f, -2);
            GL11.glVertex3f(0.005f, 0.04f, -2);
            GL11.glVertex3f(0.005f, -0.04f, -2);
            GL11.glVertex3f(-0.005f, -0.04f, -2);
        }
        GL11.glEnd();
        Font f = null;
        game.TextureLoader.bind("sky.jpg");
        GL11.glBegin(GL11.GL_QUADS);
        GL11.glColor3f(1, 1, 1);

        GL11.glTexCoord2f(0.15f, 0.15f);
        GL11.glVertex3f(-1500, 1200 + camera.rotation.x * 8, -600);

        GL11.glTexCoord2f(0.75f, 0.15f);
        GL11.glVertex3f(1500, 1200 + camera.rotation.x * 8, -600);

        GL11.glTexCoord2f(0.75f, 0.75f);
        GL11.glVertex3f(1500, -1500 + camera.rotation.x * 3, -600);

        GL11.glTexCoord2f(0.15f, 0.75f);
        GL11.glVertex3f(-1500, -1500 + camera.rotation.x * 3, -600);
        GL11.glEnd();
        texWhite.bind();
        GL11.glColor3f(1, 1, 0);
        UnitUtils.renderLine(0.02f, new Point(0.4, -2, -2), new Point(0.4, -2, -4));
        UnitUtils.renderLine(0.02f, new Point(0.4, -2, -4), new Point(2.4, -2, -4));
        UnitUtils.renderLine(0.02f, new Point(2.4, -2, -2), new Point(2.4, -2, -4));
        UnitUtils.renderLine(0.02f, new Point(0.4, -2, -2), new Point(2.4, -2, -2));

        UnitUtils.renderLine(0.02f, new Point(-0.4, -2, -2), new Point(-0.4, -2, -4));
        UnitUtils.renderLine(0.02f, new Point(-0.4, -2, -4), new Point(-2.4, -2, -4));
        UnitUtils.renderLine(0.02f, new Point(-2.4, -2, -2), new Point(-2.4, -2, -4));
        UnitUtils.renderLine(0.02f, new Point(-0.4, -2, -2), new Point(-2.4, -2, -2));
        GL11.glColor3f(0, 0, 0);
        GL11.glBegin(GL_QUADS);

        GL11.glVertex3f(-2.4f, -2.01f, -2);

        GL11.glVertex3f(-0.4f, -2.01f, -2);

        GL11.glVertex3f(-0.4f, -2.01f, -4);

        GL11.glVertex3f(-2.4f, -2.01f, -4);

        GL11.glVertex3f(2.4f, -2.01f, -2);

        GL11.glVertex3f(0.4f, -2.01f, -2);

        GL11.glVertex3f(0.4f, -2.01f, -4);

        GL11.glVertex3f(2.4f, -2.01f, -4);
        GL11.glEnd();

        GL11.glColor3f(1, 1, 0);

        UnitUtils.renderLine(0.02f, new Point(0.0 + camera.vector.x / 512, -1.9, -4.4 + camera.vector.z / 512), new Point(0.0 + camera.vector.x / 512 - Math.cos(camera.rotation.y / 180f * Math.PI + 1.67) * (cd / 512), -1.95, -4.4 + camera.vector.z / 512 - Math.sin(camera.rotation.y / 180f * Math.PI + 1.67) * (cd / 512)));

        UnitUtils.renderLine(0.02f, new Point(0, -2, -2), new Point(0, -2 - Math.sin(-camera.rotation.x / 180f * Math.PI) * 0.3f, -2 - 0 - Math.cos(-camera.rotation.x / 180f * Math.PI) * 0.3f));

        GL11.glColor3f(1, 0, 0);
        UnitUtils.renderLine(0.02f, new Point(0, -2, -3), new Point(0 - Math.cos(-camera.rotation.y / 180f * Math.PI - 1.67) * 0.3f, -2, -3 - Math.sin(-camera.rotation.y / 180f * Math.PI - 1.67) * 0.3f));

        GL11.glColor3f(0, 0, 1);
        UnitUtils.renderLine(0.02f, new Point(0, -2, -3), new Point(0 + Math.cos(-camera.rotation.y / 180f * Math.PI - 1.67) * 0.3f, -2, -3 + Math.sin(-camera.rotation.y / 180f * Math.PI - 1.67) * 0.3f));

        w.renderRadar();
        camera.translatePostion();
        init_l();
        w.cameraX = (int) camera.vector.x;
        w.cameraY = (int) camera.vector.z;

        if (keys[Keyboard.KEY_INSERT] | keys[Keyboard.KEY_Q] && bw <= 0) {
            bm = !bm;
            bw = 4;
        }

        if (keys[Keyboard.KEY_HOME] | keys[Keyboard.KEY_E] && bw <= 0) {
            mouseSwitch = !mouseSwitch;
            bw = 4;
        }

        game.TextureLoader.bind("iron.jpg");
        w.render();
        if (cd <= 65) {
            GL11.glBegin(GL11.GL_QUADS);
            GL11.glColor3f(0f, 1f, 0f);
            GL11.glTexCoord2f(0.15f, 0.15f);
            GL11.glVertex3f(-2f + camera.vector.x + (float) (Math.cos(cdir) * cd), 3.5f, 0.2f + camera.vector.z + (float) (Math.sin(cdir) * cd));
            GL11.glTexCoord2f(0.15f, 0.75f);
            GL11.glVertex3f(2f + camera.vector.x + (float) (Math.cos(cdir) * cd), 3.5f, 0.2f + camera.vector.z + (float) (Math.sin(cdir) * cd));
            GL11.glTexCoord2f(0.75f, 0.75f);
            GL11.glVertex3f(2f + camera.vector.x + (float) (Math.cos(cdir) * cd), 3.5f, -0.2f + camera.vector.z + (float) (Math.sin(cdir) * cd));
            GL11.glTexCoord2f(0.75f, 0.15f);
            GL11.glVertex3f(-2f + camera.vector.x + (float) (Math.cos(cdir) * cd), 3.5f, -0.2f + camera.vector.z + (float) (Math.sin(cdir) * cd));
            GL11.glTexCoord2f(0.15f, 0.15f);
            GL11.glVertex3f(-0.2f + camera.vector.x + (float) (Math.cos(cdir) * cd), 3.5f, 2f + camera.vector.z + (float) (Math.sin(cdir) * cd));
            GL11.glTexCoord2f(0.15f, 0.75f);
            GL11.glVertex3f(0.2f + camera.vector.x + (float) (Math.cos(cdir) * cd), 3.5f, 2f + camera.vector.z + (float) (Math.sin(cdir) * cd));
            GL11.glTexCoord2f(0.75f, 0.75f);
            GL11.glVertex3f(0.2f + camera.vector.x + (float) (Math.cos(cdir) * cd), 3.5f, -2f + camera.vector.z + (float) (Math.sin(cdir) * cd));
            GL11.glTexCoord2f(0.75f, 0.15f);
            GL11.glVertex3f(-0.2f + camera.vector.x + (float) (Math.cos(cdir) * cd), 3.5f, -2f + camera.vector.z + (float) (Math.sin(cdir) * cd));

            GL11.glEnd();
        }
        mb = Mouse.isButtonDown(0) ? 1 : 0;
        if (Mouse.isButtonDown(1)) {
            w.deselect();
        }
        mb = Mouse.isButtonDown(2) ? 2 : mb;

        if (mb == 1 && lmb == 0) {

            sx = (int) (camera.vector.x + (float) (Math.cos(cdir) * cd));
            sz = (int) (camera.vector.z + (float) (Math.sin(cdir) * cd));
        }
        if (mb == 1 && lmb == 1) {
            int sdx = (int) (camera.vector.x + (float) (Math.cos(cdir) * cd));

            int sdz = (int) (camera.vector.z + (float) (Math.sin(cdir) * cd));
            sd = (int) Math.sqrt(Math.pow(sx - sdx, 2) + Math.pow(sz - sdz, 2));

            GL11.glBegin(GL11.GL_QUADS);
            GL11.glColor3f(1f, 1f, 0f);
            GL11.glTexCoord2f(0.15f, 0.15f);
            GL11.glVertex3f(-sd + sx, 3.5f, 0.2f + sz);
            GL11.glTexCoord2f(0.15f, 0.75f);
            GL11.glVertex3f(sd + sx, 3.5f, 0.2f + sz);
            GL11.glTexCoord2f(0.75f, 0.75f);
            GL11.glVertex3f(sd + sx, 3.5f, -0.2f + sz);
            GL11.glTexCoord2f(0.75f, 0.15f);
            GL11.glVertex3f(-sd + sx, 3.5f, -0.2f + sz);
            GL11.glTexCoord2f(0.15f, 0.15f);
            GL11.glVertex3f(-0.2f + sx, 3.5f, sd + sz);
            GL11.glTexCoord2f(0.15f, 0.75f);
            GL11.glVertex3f(0.2f + sx, 3.5f, sd + sz);
            GL11.glTexCoord2f(0.75f, 0.75f);
            GL11.glVertex3f(0.2f + sx, 3.5f, -sd + sz);
            GL11.glTexCoord2f(0.75f, 0.15f);
            GL11.glVertex3f(-0.2f + sx, 3.5f, -sd + sz);
            GL11.glEnd();
            game.UnitUtils.renderCycle(
                    0.2f,
                    sd,
                    new game.Point(sx, 3.5, sz)
            );
        }
        if (mb == 0 && lmb == 1) {
            if (sd >= 3) {
                w.selectInRadius(sx, sz, sd);
            } else {
                w.setTarget(sx, sz);
            }

            sd = 0;
            sx = 0;
            sz = 0;
        }
        if (bm) {

            Point p = w.getCam();
            if (p.x != 0) {
                camera.vector.x = (float) p.x;

                camera.vector.z = (float) p.z;
            }
        }
        if (mb == 2 && lmb == 0) {

            sx = (int) (camera.vector.x + (float) (Math.cos(cdir) * cd));
            sz = (int) (camera.vector.z + (float) (Math.sin(cdir) * cd));
        }
        if (mb == 2 && lmb == 2) {
            int sdx = (int) (camera.vector.x + (float) (Math.cos(cdir) * cd));

            int sdz = (int) (camera.vector.z + (float) (Math.sin(cdir) * cd));
            sd = (int) Math.sqrt(Math.pow(sx - sdx, 2) + Math.pow(sz - sdz, 2));

            GL11.glBegin(GL11.GL_QUADS);
            GL11.glColor3f(1f, 0f, 0f);
            GL11.glTexCoord2f(0.15f, 0.15f);
            GL11.glVertex3f(-sd + sx, 3.5f, 0.2f + sz);
            GL11.glTexCoord2f(0.15f, 0.75f);
            GL11.glVertex3f(sd + sx, 3.5f, 0.2f + sz);
            GL11.glTexCoord2f(0.75f, 0.75f);
            GL11.glVertex3f(sd + sx, 3.5f, -0.2f + sz);
            GL11.glTexCoord2f(0.75f, 0.15f);
            GL11.glVertex3f(-sd + sx, 3.5f, -0.2f + sz);
            GL11.glTexCoord2f(0.15f, 0.15f);
            GL11.glVertex3f(-0.2f + sx, 3.5f, sd + sz);
            GL11.glTexCoord2f(0.15f, 0.75f);
            GL11.glVertex3f(0.2f + sx, 3.5f, sd + sz);
            GL11.glTexCoord2f(0.75f, 0.75f);
            GL11.glVertex3f(0.2f + sx, 3.5f, -sd + sz);
            GL11.glTexCoord2f(0.75f, 0.15f);
            GL11.glVertex3f(-0.2f + sx, 3.5f, -sd + sz);
            GL11.glEnd();
            game.UnitUtils.renderCycle(
                    0.2f,
                    sd,
                    new game.Point(sx, 3.5, sz)
            );
        }
        if (mb == 0 && lmb == 2) {

            sd = 0;
            sx = 0;
            sz = 0;
        }

        lmb = mb;

        int floorTexture = glGenTextures();
        InputStream in = null;

        //        glBindTexture(GL_TEXTURE_2D, tex.getTextureID());
        GL11.glColor3f(0.85f, 1f, 0.95f);

        Random r = new Random();
        for (int j = 0; j < r.nextInt(70) - 65; j++) {
            int x = r.nextInt(500) - 250 + (int) camera.vector.x;
            int y = r.nextInt(500) - 250 + (int) camera.vector.z;
            for (int i = 0; i < 200; i++) {
                int x2 = x + r.nextInt(3) - 1;
                int y2 = y + r.nextInt(3) - 1;
                GL11.glBegin(GL11.GL_QUADS);

                GL11.glVertex3f(x - 0.4f, i, y);

                GL11.glVertex3f(x2 - 0.4f, i + 1, y2);

                GL11.glVertex3f(x2 + 0.4f, i + 1, y2);

                GL11.glVertex3f(x + 0.4f, i, y);

                GL11.glVertex3f(x, i, y - 0.4f);

                GL11.glVertex3f(x2, i + 1, y2 - 0.4f);

                GL11.glVertex3f(x2, i + 1, y2 + 0.4f);

                GL11.glVertex3f(x, i, y + 0.4f);
                GL11.glEnd();
                x = x2;
                y = y2;
            }

        }
        int lopata = GL11.GL_QUADS;
        for (int i
                = 0; i < 255; i++) {
            for (int j = 0; j < 255; j++) {
                double d = Math.sqrt(Math.pow(i * 5 - camera.vector.x, 2) + Math.pow(j * 5 - camera.vector.z, 2));

                if (d < 200) {

                    float t = terrain[i][j] + 0.2f;
                    GL11.glColor3d(0.05 * terrain[i][j] + 0.35, 0.05 * terrain[i][j] + 0.35, 0.05 * terrain[i][j] + 0.25);

                    game.TextureLoader.bind("sand.jpg");
                    GL11.glBegin(lopata);

                    GL11.glTexCoord2f(0.1f, 0.1f);
                    GL11.glVertex3f(-0f + i * 5, terrain[i][j] * 3, -0f + j * 5);

                    GL11.glTexCoord2f(0.5f, 0.1f);
                    GL11.glVertex3f(5f + i * 5, terrain[i + 1][j] * 3, -0f + j * 5);

                    GL11.glTexCoord2f(0.5f, 0.5f);
                    GL11.glVertex3f(5f + i * 5, terrain[i + 1][j + 1] * 3, 5f + j * 5);

                    GL11.glTexCoord2f(0.1f, 0.5f);
                    GL11.glVertex3f(-0f + i * 5, terrain[i][j + 1] * 3, 5f + j * 5);

                    GL11.glEnd();/*
                     GL11.glColor4f((terrain[i][j] * 0.2f + 0.5f) / 3f, (terrain[i][j] * 0.2f + 1.0f) / 3f, (terrain[i][j] * 0.2f + 0.7f) / 3f, 0.2f);
                     GL11.glBegin(GL11.GL_QUADS);
                     GL11.glTexCoord2f(0.15f, 0.15f);
                     GL11.glVertex3f(-0f + i * 5, terrain[i][j] * 2 + 55, -0f + j * 5);

                     GL11.glTexCoord2f(0.75f, 0.15f);
                     GL11.glVertex3f(5f + i * 5, terrain[i + 1][j] * 2 + 55, -0f + j * 5);

                     GL11.glTexCoord2f(0.75f, 0.75f);
                     GL11.glVertex3f(5f + i * 5, terrain[i + 1][j + 1] * 2 + 55, 5f + j * 5);

                     GL11.glTexCoord2f(0.15f, 0.75f);
                     GL11.glVertex3f(-0f + i * 5, terrain[i][j + 1] * 2 + 55, 5f + j * 5);
                     GL11.glEnd();*/

                }
            }
        }
        //Render a textured rectangular floor at 0,0 to 10,10

    }

    public void update() {
        mapKeys();
        camera.update();

    }

    private void mapKeys() {
        //Update keys
        for (int i = 0; i < keys.length; i++) {
            keys[i] = Keyboard.isKeyDown(i);
        }
    }

    public void init3D() {
        //Start 3D Stuff
        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glLoadIdentity();

        GLU.gluPerspective((float) 100, width / height, 0.001f, 1000);
        GL11.glMatrixMode(GL11.GL_MODELVIEW);

        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glShadeModel(GL11.GL_SMOOTH);
        GL11.glClearColor(0.0f, 0.0f, 0.0f, 0.5f);
        GL11.glClearDepth(1.0f);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glDepthFunc(GL11.GL_LEQUAL);
    }

    public void clearScreen() {
        //Clear the screen
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
        GL11.glLoadIdentity();
    }
}
