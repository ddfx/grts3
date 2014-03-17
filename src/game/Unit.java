/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import java.util.ArrayList;
import java.util.Random;
import org.lwjgl.opengl.GL11;

/**
 *
 * @author yew_mentzaki
 */
public class Unit {

    public static final int TYPE_NOTHING = 0,
            TYPE_BUILDING = 1,
            TYPE_TANK = 2,
            TYPE_SPACECRAFT = 3,
            TYPE_WARPLANE = 4;
    protected int price = 200;
    protected int prepareTime = 200;
    protected int type = TYPE_TANK;
    protected double x, y, z;
    protected int tx, ty, tz;
    protected float angle, fangle, sangle;
    protected float haborites = 4;
    protected boolean selected = false;
////////////////////////
    protected String name;
    protected int serverID;
    protected int status[] = new int[16];
    protected int owner;
    protected float speed = 0.3f, turnspeed = 0.05f, turn = 0;
////////////////////////
    protected int hp, maxHp;
    protected int fhp, fmaxHp;
    protected int lhp, lmaxHp;
    protected int rhp, rmaxHp;
    protected int bhp, bmaxHp;
    protected int fshield, fmaxShield;
    protected int lshield, lmaxShield;
    protected int rshield, rmaxShield;
    protected int bshield, bmaxShield;

    public float f(double d) {
        return (float) d;
    }

    public Point l() {
        if (scanner) {
            return new Point(-6, -10, -12);
        }
        return new Point(getX(), getY(), getZ());
    }
    private boolean scanner;

    public Point r() {

        return new Point(sangle, getAngle(), getFangle());
    }
    public World world;
    public ArrayList<Turret> turret = new ArrayList<Turret>();

    public void renderScanner(World w) {
        scanner = true;
        render(w);
        scanner = false;
    }
    public Interface createMenu(Interface i){
        return null;
    
    }
    public Unit(double x, double y, double z, float angle, int owner, World w) {
        this.x = x;
        this.y = y;
        this.z = z;
        tx = (int) x + 20;
        ty = (int) y;
        tz = (int) z;
        this.angle = angle;
        this.owner = owner;
        world = w;
        fhp = fmaxHp = 300;
        bhp = bmaxHp = 200;
        rhp = rmaxHp = 200;
        lhp = lmaxHp = 200;
        fshield = fmaxShield = 300;
        bshield = bmaxShield = 200;
        rshield = rmaxShield = 200;
        lshield = lmaxShield = 200;
    }

    public void hit(Point p, int power, int type) {
        float a = (float) Math.atan2(z - p.z, x - p.x) + angle;
        turn = a - angle + (float) Math.PI;
        a /= (float) Math.PI;
        a *= 180;
        if (Math.abs(a) < 45) {
            fhp -= ((float) (fmaxShield - Math.max(fshield, 0)) / (float) fmaxShield) * power;
            fshield -= power;
        }
        if (Math.abs(a) > 128) {
            bhp -= ((float) (bmaxShield - Math.max(bshield, 0)) / (float) bmaxShield) * power;
            bshield -= power;
        }
        if (a >= 45 && a <= 128) {
            rhp -= ((float) (rmaxShield - Math.max(rshield, 0)) / (float) rmaxShield) * power;
            rshield -= power;
        }
        if (a <= -45 && a >= -128) {
            lhp -= ((float) (lmaxShield - Math.max(lshield, 0)) / (float) lmaxShield) * power;
            lshield -= power;
        }
    }

    public void life(World w) {

        if (getType() == TYPE_WARPLANE) {
            setX(getX() + Math.cos(getAngle()) * getSpeed());
            setZ(getZ() + Math.sin(getAngle()) * getSpeed());
        }
        hp = Math.min(Math.min(fhp, bhp), Math.min(lhp, rhp));
        if (hp > 0) {
            move(w);
        } else {
            y -= 0.1f;
            if (getType() == TYPE_WARPLANE) {
                y -= 0.4f;
            }
        }
        if (getType() == TYPE_WARPLANE) {
            setX(getX() + Math.cos(getAngle()) * getSpeed());
            setZ(getZ() + Math.sin(getAngle()) * getSpeed());
        }
        for (int i = 0; i < turret.size(); i++) {
            turret.get(i).life(w);

        }

        if (fshield < fmaxShield) {
            fshield++;
        }

        if (bshield < bmaxShield) {
            bshield++;
        }

        if (rshield < rmaxShield) {
            rshield++;
        }

        if (lshield < lmaxShield) {
            lshield++;
        }

    }

    public void move(World w) {
        if (getType() != TYPE_BUILDING) {

            if (getType() == TYPE_TANK) {
                w.collect((float)x, (float)z, (int)haborites);
                if (Math.abs(getX() - getTx()) + Math.abs(getZ() - getTz()) > 2 * speed || getY() > 4) {

                    if (Math.sqrt(Math.pow(w.cameraX - getX(), 2) + Math.pow(w.cameraY - getZ(), 2)) < 125) {
                        float h = (float) (w.getMap(Math.round((float) getX() / 5.0f), Math.round((float) getZ() / 5.0f)) * 3 + 0.2f);

                        float h2 = w.getMap(Math.round((float) (getX() + Math.cos(getAngle()) * 5) / 5.0f), Math.round((float) (getZ() + Math.sin(getAngle()) * 5) / 5.0f)) * 3 + 0.2f;
                        float f = (float) -Math.atan2(h2 - h, 5)/*-(float)(Math.PI/2)*/;
                        if (getFangle() < f) {
                            setFangle(getFangle() + 0.05f);
                        }
                        if (getFangle() > f) {
                            setFangle(getFangle() - 0.05f);
                        }

                        if (Math.abs(getY() - (h + h2) / 2) < 0.30) {
                            setY((h + h2) / 2);
                        } else {
                            if (getY() < (h + h2) / 2) {
                                setY(getY() + 0.30f);

                            }

                            if (getY() > (h + h2) / 2) {
                                setY(getY() - 0.30f);
                            }
                        }
                    }

                } else {
                    setY(1.5f);
                    setFangle(0);

                }
            } else {
                if (getType() == TYPE_SPACECRAFT) {
                    setY(8);
                }
                if (getType() == TYPE_WARPLANE) {
                    setY(30);
                }
            }

            if (angle > Math.PI) {
                angle -= 2 * Math.PI;
            }

            if (angle < -Math.PI) {
                angle += 2 * Math.PI;
            }

            float a = (Math.abs(getX() - getTx()) + Math.abs(getZ() - getTz()) > 2 * speed) ? ((float) Math.atan2(getTz() - getZ(), getTx() - getX())) : (turn);
            double de = a - angle;
            double clampedDelta = de - (float) (Math.floor(de / (2.0f * Math.PI) + 0.5f) * 2.0f * Math.PI);//Вычисляем угол
            if (Math.abs(a - angle) < 0.05 || Math.abs(a + 2 * Math.PI - angle) < 0.05) {
                angle = a;
            } else {
                if (clampedDelta > Math.PI) {
                    clampedDelta -= Math.PI;
                }
                if (clampedDelta > 0) {
                    angle += turnspeed;
                    if (sangle < (getType() == TYPE_WARPLANE ? 2 : 0.6)) {
                        sangle += ((getType() == TYPE_WARPLANE) ? 0.06f : 0.02f);
                    }
                }
                if (clampedDelta < 0) {
                    angle -= turnspeed;
                    if (sangle > (getType() == TYPE_WARPLANE ? -2 : -0.6)) {
                        sangle -= ((getType() == TYPE_WARPLANE) ? 0.06f : 0.02f);
                    }

                }
            }
            sangle *= 0.95f;
            if (Math.abs(getX() - getTx()) + Math.abs(getZ() - getTz()) > 2 * speed && Math.abs(clampedDelta) < 1.0 & getType() != TYPE_WARPLANE) {
                setX(getX() + Math.cos(getAngle()) * getSpeed());
                setZ(getZ() + Math.sin(getAngle()) * getSpeed());
                turn=a;
                if (Math.sqrt(Math.pow(w.cameraX - getX(), 2) + Math.pow(w.cameraY - getZ(), 2)) < 220) {

                    for (int i = 0; i < w.units.size(); i++) {
                        Unit u = w.units.get(i);
                        if (u != this && u != null) {
                            if (Math.abs(u.getX() - getX()) < getHaborites() + u.getHaborites()) {
                                float h = getHaborites() + u.getHaborites();
                                double d = Math.sqrt(Math.pow(getX() - u.getX(), 2) + Math.pow(getZ() - u.getZ(), 2));
                                if (h > d) {
                                    d = h - d;
                                    d /= 8;
                                    a = (float) Math.atan2(u.getZ() - getZ(), u.getX() - getX());

                                    if (u.getHaborites() == getHaborites()) {
                                        u.setX(u.getX() + Math.cos(a) * d);
                                        u.setZ(u.getZ() + Math.sin(a) * d);
                                        setX(getX() - Math.cos(a) * d);
                                        setZ(getZ() - Math.sin(a) * d);
                                    }

                                    if (u.getHaborites() > getHaborites()) {
                                        setX(getX() - Math.cos(a) * d * 2);
                                        setZ(getZ() - Math.sin(a) * d * 2);
                                    }

                                    if (u.getHaborites() < getHaborites()) {
                                        u.setX(u.getX() + Math.cos(a) * d * 2);
                                        u.setZ(u.getZ() + Math.sin(a) * d * 2);
                                    }

                                    if (Math.abs(getX() - getTx()) + Math.abs(getZ() - getTz()) < 5) {

                                        setTx((int) (getTx() - Math.cos(a) * d));
                                        setTz((int) (getTz() - Math.sin(a) * d));
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } else {
            if (y > 1.0) {
                y -= 0.5 + Math.sqrt(y) * 0.05;
            }
        }
    }

    public void color(int r, int g, int b) {
        GL11.glColor3b((byte) r, (byte) g, (byte) b);
    }

    public void fcolor(World w) {
        GL11.glColor3f(w.players[getOwner()].r, w.players[getOwner()].g, w.players[getOwner()].b);
    }

    public void arender(World w) {
    }
    float box = 10;

    public void render(World w) {

        int d = (int) Math.sqrt(Math.pow(w.cameraX - getX(), 2) + Math.pow(w.cameraY - getZ(), 2));
        if (selected && !scanner) {
            GL11.glColor3f(0, 1, 0);
            UnitUtils.renderCycle(0.2f, haborites + 1, new Point(x, 3.5, z));

        }

        if (d > 100 && !scanner) {
            fcolor(w); //GL11.glColor4f(0.3f, 0.6f, 1f, 0.5f);
            UnitUtils.renderPolygon(new Point[]{
                new Point(-1, 10, 0),
                new Point(0, 5, 0),
                new Point(1, 10, 0),}, l(), r());
            fcolor(w);
            UnitUtils.renderPolygon(new Point[]{
                new Point(0, 10, -1),
                new Point(0, 5, 0),
                new Point(0, 10, 1),}, l(), r());
        }
        if (getType() == TYPE_BUILDING && box > 0) {
            if (y <= 1.5) {
                box -= 0.2f;
            }
        }
        if (d > 200 && !scanner) {
            return;
        }
        if (getType() == TYPE_BUILDING && box > 0) {

            color(95, 85, 80);
            UnitUtils.renderPolygon(new Point[]{
                new Point(10, box, 10),
                new Point(-10, box, 10),
                new Point(-10, 0, 10),
                new Point(10, 0, 10),}, l(), r());
            UnitUtils.renderPolygon(new Point[]{
                new Point(10, box, -10),
                new Point(-10, box, -10),
                new Point(-10, 0, -10),
                new Point(10, 0, -10),}, l(), r());

            color(75, 65, 60);
            UnitUtils.renderPolygon(new Point[]{
                new Point(10, box, 10),
                new Point(10, box, -10),
                new Point(10, 0, -10),
                new Point(10, 0, 10),}, l(), r());
            UnitUtils.renderPolygon(new Point[]{
                new Point(-10, box, 10),
                new Point(-10, box, -10),
                new Point(-10, 0, -10),
                new Point(-10, 0, 10),}, l(), r());

            {
                int lx = -10;
                int lz = -10;
                int a = 2;

                color(95, 85, 80);
                UnitUtils.renderPolygon(new Point[]{
                    new Point(lx + a, box / 2, lz + a),
                    new Point(lx - a, box / 2, lz + a),
                    new Point(lx - a, 0, lz + a),
                    new Point(lx + a, 0, lz + a),}, l(), r());
                UnitUtils.renderPolygon(new Point[]{
                    new Point(lx + a, box / 2, lz - a),
                    new Point(lx - a, box / 2, lz - a),
                    new Point(lx - a, 0, lz - a),
                    new Point(lx + a, 0, lz - a),}, l(), r());

                color(75, 65, 60);
                UnitUtils.renderPolygon(new Point[]{
                    new Point(lx + a, box / 2, lz + a),
                    new Point(lx + a, box / 2, lz - a),
                    new Point(lx + a, 0, lz - a),
                    new Point(lx + a, 0, lz + a),}, l(), r());
                UnitUtils.renderPolygon(new Point[]{
                    new Point(lx - a, box / 2, lz + a),
                    new Point(lx - a, box / 2, lz - a),
                    new Point(lx - a, 0, lz - a),
                    new Point(lx - a, 0, lz + a),}, l(), r());

                color(50, 80, 120);
                UnitUtils.renderPolygon(new Point[]{
                    new Point(lx, -box, lz),
                    new Point(lx, 0, lz - a),
                    new Point(lx, 0, lz + a),}, l(), r());
                UnitUtils.renderPolygon(new Point[]{
                    new Point(lx, -box, lz),
                    new Point(lx + a, 0, lz),
                    new Point(lx - a, 0, lz),}, l(), r());
            }

            {
                int lx = -10;
                int lz = 10;
                int a = 2;

                color(95, 85, 80);
                UnitUtils.renderPolygon(new Point[]{
                    new Point(lx + a, box / 2, lz + a),
                    new Point(lx - a, box / 2, lz + a),
                    new Point(lx - a, 0, lz + a),
                    new Point(lx + a, 0, lz + a),}, l(), r());
                UnitUtils.renderPolygon(new Point[]{
                    new Point(lx + a, box / 2, lz - a),
                    new Point(lx - a, box / 2, lz - a),
                    new Point(lx - a, 0, lz - a),
                    new Point(lx + a, 0, lz - a),}, l(), r());

                color(75, 65, 60);
                UnitUtils.renderPolygon(new Point[]{
                    new Point(lx + a, box / 2, lz + a),
                    new Point(lx + a, box / 2, lz - a),
                    new Point(lx + a, 0, lz - a),
                    new Point(lx + a, 0, lz + a),}, l(), r());
                UnitUtils.renderPolygon(new Point[]{
                    new Point(lx - a, box / 2, lz + a),
                    new Point(lx - a, box / 2, lz - a),
                    new Point(lx - a, 0, lz - a),
                    new Point(lx - a, 0, lz + a),}, l(), r());

                color(50, 80, 120);
                UnitUtils.renderPolygon(new Point[]{
                    new Point(lx, -box, lz),
                    new Point(lx, 0, lz - a),
                    new Point(lx, 0, lz + a),}, l(), r());
                UnitUtils.renderPolygon(new Point[]{
                    new Point(lx, -box, lz),
                    new Point(lx + a, 0, lz),
                    new Point(lx - a, 0, lz),}, l(), r());
            }

            {
                int lx = 10;
                int lz = -10;
                int a = 2;

                color(95, 85, 80);
                UnitUtils.renderPolygon(new Point[]{
                    new Point(lx + a, box / 2, lz + a),
                    new Point(lx - a, box / 2, lz + a),
                    new Point(lx - a, 0, lz + a),
                    new Point(lx + a, 0, lz + a),}, l(), r());
                UnitUtils.renderPolygon(new Point[]{
                    new Point(lx + a, box / 2, lz - a),
                    new Point(lx - a, box / 2, lz - a),
                    new Point(lx - a, 0, lz - a),
                    new Point(lx + a, 0, lz - a),}, l(), r());

                color(75, 65, 60);
                UnitUtils.renderPolygon(new Point[]{
                    new Point(lx + a, box / 2, lz + a),
                    new Point(lx + a, box / 2, lz - a),
                    new Point(lx + a, 0, lz - a),
                    new Point(lx + a, 0, lz + a),}, l(), r());
                UnitUtils.renderPolygon(new Point[]{
                    new Point(lx - a, box / 2, lz + a),
                    new Point(lx - a, box / 2, lz - a),
                    new Point(lx - a, 0, lz - a),
                    new Point(lx - a, 0, lz + a),}, l(), r());

                color(50, 80, 120);
                UnitUtils.renderPolygon(new Point[]{
                    new Point(lx, -box, lz),
                    new Point(lx, 0, lz - a),
                    new Point(lx, 0, lz + a),}, l(), r());
                UnitUtils.renderPolygon(new Point[]{
                    new Point(lx, -box, lz),
                    new Point(lx + a, 0, lz),
                    new Point(lx - a, 0, lz),}, l(), r());
            }

            {
                int lx = 10;
                int lz = 10;
                int a = 2;

                color(95, 85, 80);
                UnitUtils.renderPolygon(new Point[]{
                    new Point(lx + a, box / 2, lz + a),
                    new Point(lx - a, box / 2, lz + a),
                    new Point(lx - a, 0, lz + a),
                    new Point(lx + a, 0, lz + a),}, l(), r());
                UnitUtils.renderPolygon(new Point[]{
                    new Point(lx + a, box / 2, lz - a),
                    new Point(lx - a, box / 2, lz - a),
                    new Point(lx - a, 0, lz - a),
                    new Point(lx + a, 0, lz - a),}, l(), r());

                color(75, 65, 60);
                UnitUtils.renderPolygon(new Point[]{
                    new Point(lx + a, box / 2, lz + a),
                    new Point(lx + a, box / 2, lz - a),
                    new Point(lx + a, 0, lz - a),
                    new Point(lx + a, 0, lz + a),}, l(), r());
                UnitUtils.renderPolygon(new Point[]{
                    new Point(lx - a, box / 2, lz + a),
                    new Point(lx - a, box / 2, lz - a),
                    new Point(lx - a, 0, lz - a),
                    new Point(lx - a, 0, lz + a),}, l(), r());

                color(50, 80, 120);
                UnitUtils.renderPolygon(new Point[]{
                    new Point(lx, -box, lz),
                    new Point(lx, 0, lz - a),
                    new Point(lx, 0, lz + a),}, l(), r());
                UnitUtils.renderPolygon(new Point[]{
                    new Point(lx, -box, lz),
                    new Point(lx + a, 0, lz),
                    new Point(lx - a, 0, lz),}, l(), r());
            }

        }
        GL11.glColor4f(0f, 1.0f, 0f, 0.5f);
        UnitUtils.renderPolygon(new Point[]{
            new Point(haborites, y + 4, (fmaxHp - fhp) * (haborites / fmaxHp)),
            new Point(haborites, y + 3.5f, (fmaxHp - fhp) * (haborites / fmaxHp)),
            new Point(haborites, y + 3.5f, -(fmaxHp - fhp) * (haborites / fmaxHp)),
            new Point(haborites, y + 4, -(fmaxHp - fhp) * (haborites / fmaxHp)),}, l(), r());
        UnitUtils.renderPolygon(new Point[]{
            new Point(-haborites, y + 4, (bmaxHp - bhp) * (haborites / bmaxHp)),
            new Point(-haborites, y + 3.5f, (bmaxHp - bhp) * (haborites / bmaxHp)),
            new Point(-haborites, y + 3.5f, -(bmaxHp - bhp) * (haborites / bmaxHp)),
            new Point(-haborites, y + 4, -(bmaxHp - bhp) * (haborites / bmaxHp)),}, l(), r());
        UnitUtils.renderPolygon(new Point[]{
            new Point((lmaxHp - lhp) * (haborites / lmaxHp), y + 4, haborites),
            new Point((lmaxHp - lhp) * (haborites / lmaxHp), y + 3.5f, haborites),
            new Point(-(lmaxHp - lhp) * (haborites / lmaxHp), y + 3.5f, haborites),
            new Point(-(lmaxHp - lhp) * (haborites / lmaxHp), y + 4, haborites),}, l(), r());
        UnitUtils.renderPolygon(new Point[]{
            new Point((rmaxHp - rhp) * (haborites / rmaxHp), y + 4, -haborites),
            new Point((rmaxHp - rhp) * (haborites / rmaxHp), y + 3.5f, -haborites),
            new Point(-(rmaxHp - rhp) * (haborites / rmaxHp), y + 3.5f, -haborites),
            new Point(-(rmaxHp - rhp) * (haborites / rmaxHp), y + 4, -haborites),}, l(), r());
        GL11.glColor4f(0f, 0.8f, 1f, 0.8f);
        UnitUtils.renderPolygon(new Point[]{
            new Point(haborites, y + 3, (fmaxShield - fshield) * (haborites / fmaxShield)),
            new Point(haborites, y + 3.5f, (fmaxShield - fshield) * (haborites / fmaxShield)),
            new Point(haborites, y + 3.5f, -(fmaxShield - fshield) * (haborites / fmaxShield)),
            new Point(haborites, y + 3, -(fmaxShield - fshield) * (haborites / fmaxShield)),}, l(), r());
        UnitUtils.renderPolygon(new Point[]{
            new Point(-haborites, y + 3, (bmaxShield - bshield) * (haborites / bmaxShield)),
            new Point(-haborites, y + 3.5f, (bmaxShield - bshield) * (haborites / bmaxShield)),
            new Point(-haborites, y + 3.5f, -(bmaxShield - bshield) * (haborites / bmaxShield)),
            new Point(-haborites, y + 3, -(bmaxShield - bshield) * (haborites / bmaxShield)),}, l(), r());
        UnitUtils.renderPolygon(new Point[]{
            new Point((lmaxShield - lshield) * (haborites / lmaxShield), y + 3, haborites),
            new Point((lmaxShield - lshield) * (haborites / lmaxShield), y + 3.5f, haborites),
            new Point(-(lmaxShield - lshield) * (haborites / lmaxShield), y + 3.5f, haborites),
            new Point(-(lmaxShield - lshield) * (haborites / lmaxShield), y + 3, haborites),}, l(), r());
        UnitUtils.renderPolygon(new Point[]{
            new Point((rmaxShield - rshield) * (haborites / rmaxShield), y + 3, -haborites),
            new Point((rmaxShield - rshield) * (haborites / rmaxShield), y + 3.5f, -haborites),
            new Point(-(rmaxShield - rshield) * (haborites / rmaxShield), y + 3.5f, -haborites),
            new Point(-(rmaxShield - rshield) * (haborites / rmaxShield), y + 3, -haborites),}, l(), r());
        arender(w);
        for (int i = 0; i < turret.size(); i++) {
            turret.get(i).render(w);
        }

    }

    /**
     * @return the x
     */
    public double getX() {
        return x;
    }

    /**
     * @param x the x to set
     */
    public void setX(double x) {
        this.x = x;
    }

    /**
     * @return the y
     */
    public double getY() {
        return y;
    }

    /**
     * @param y the y to set
     */
    public void setY(double y) {
        this.y = y;
    }

    /**
     * @return the z
     */
    public double getZ() {
        return z;
    }

    /**
     * @param z the z to set
     */
    public void setZ(double z) {
        this.z = z;
    }

    /**
     * @return the tx
     */
    public int getTx() {
        return tx;
    }

    /**
     * @param tx the tx to set
     */
    public void setTx(int tx) {
        this.tx = tx;
    }

    /**
     * @return the ty
     */
    public int getTy() {
        return ty;
    }

    /**
     * @param ty the ty to set
     */
    public void setTy(int ty) {
        this.ty = ty;
    }

    /**
     * @return the tz
     */
    public int getTz() {
        return tz;
    }

    /**
     * @param tz the tz to set
     */
    public void setTz(int tz) {
        this.tz = tz;
    }

    /**
     * @return the angle
     */
    public float getAngle() {
        return angle;
    }

    /**
     * @param angle the angle to set
     */
    public void setAngle(float angle) {
        this.angle = angle;
    }

    /**
     * @return the fangle
     */
    public float getFangle() {
        return fangle;
    }

    /**
     * @param fangle the fangle to set
     */
    public void setFangle(float fangle) {
        this.fangle = fangle;
    }

    /**
     * @return the haborites
     */
    public float getHaborites() {
        return haborites;
    }

    /**
     * @param haborites the haborites to set
     */
    public void setHaborites(float haborites) {
        this.haborites = haborites;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the serverID
     */
    public int getServerID() {
        return serverID;
    }

    /**
     * @param serverID the serverID to set
     */
    public void setServerID(int serverID) {
        this.serverID = serverID;
    }

    /**
     * @return the status
     */
    public int[] getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(int[] status) {
        this.status = status;
    }

    /**
     * @return the owner
     */
    public int getOwner() {
        return owner;
    }

    /**
     * @param owner the owner to set
     */
    public void setOwner(int owner) {
        this.owner = owner;
    }

    /**
     * @return the speed
     */
    public float getSpeed() {
        return speed;
    }

    /**
     * @param speed the speed to set
     */
    public void setSpeed(float speed) {
        this.speed = speed;
    }

    /**
     * @return the selected
     */
    public boolean isSelected() {
        return selected;
    }

    /**
     * @param selected the selected to set
     */
    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    /**
     * @return the price
     */
    public int getPrice() {
        return price;
    }

    /**
     * @param price the price to set
     */
    public void setPrice(int price) {
        this.price = price;
    }

    /**
     * @return the prepareTime
     */
    public int getPrepareTime() {
        return prepareTime;
    }

    /**
     * @param prepareTime the prepareTime to set
     */
    public void setPrepareTime(int prepareTime) {
        this.prepareTime = prepareTime;
    }

    /**
     * @return the type
     */
    public int getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(int type) {
        this.type = type;
    }

}
