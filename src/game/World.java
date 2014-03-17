/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;
import org.lwjgl.opengl.GL11;

/**
 *
 * @author yew_mentzaki
 */
public class World {

    public float wind;
    public int cameraX, cameraY;
    public Player[] players = {
        new Player("LA", 0, 0, 1),
        new Player("WUSR", 1, 0, 0)
    };
    public int player = 0;
    float[][] map;

    public float getMap(int x, int y) {
        if (x > 0 && x < map.length && y > 0 && y < map.length) {
            return map[x][y];
        }
        return 1;
    }

    public Point getCam() {
        double x = 0;
        double z = 0;
        int n = 0;
        for (int i = 0; i < units.size(); i++) {
            if (units.get(i).isSelected()) {
                n++;
                x += (units.get(i).getX());
                z += (units.get(i).getZ());
            }
        }
        if (n > 0) {
            x /= n;
            z /= n;
        }
        return new Point(x, 15, z);
    }
    public Lang lang = new Lang();
    public Interface i = null;
    public ArrayList<Exceptalanum> ex = new ArrayList<Exceptalanum>();
    public int collect(float cx, float cz){
      int r = 0;
        for (int j = 0; j < ex.size(); j++) {
           r += ex.get(j).collect(cx, cz);
        }
        return r;
    }
    public void addWave(){
        int x = 0, z = 0;
        for (int j = 0; j < units.size(); j++) {
           Unit u = units.get(j);
           if(u.owner==player){
           x = (int) u.getX();
           z = (int) u.getZ();
           break;
           }
        }
        for (int j = 0; j < 3+r.nextInt(10); j++) {
            Unit u = UnitList.spawnNewUnit(1, r.nextInt(200), 0, r.nextInt(2000), r.nextFloat()*6, 1, this);
            u.setTx(x);
            u.setTz(z);
            units.add(u);
        }
        for (int j = 0; j < -2+r.nextInt(9); j++) {
            Unit u = UnitList.spawnNewUnit(4, r.nextInt(200), 0, r.nextInt(2000), r.nextFloat()*6, 1, this);
            u.setTx(x);
            u.setTz(z);
            units.add(u);
        }
    }
    public int collect(float cx, float cz, int r_){
      int r = 0;
        for (int j = 0; j < ex.size(); j++) {
           r += ex.get(j).collect(cx, cz, r_);
        }
        return r;
    }
    public void newExpand(){
    int x = r.nextInt(2000);
    int z = r.nextInt(2000);
        for (int j = 0; j < 35+r.nextInt(5); j++) {
            int w = 2;
            while(r.nextInt(10)<9){
            w+=2;
            }
            ex.add(new Exceptalanum(x+200 + r.nextInt(w*2)-w,z+200 + r.nextInt(w*2)-w));
            ex.add(new Exceptalanum(2200 - x+ + r.nextInt(w*2)-w, 2200 - z + r.nextInt(w*2)-w));
        }
        
    }
   
    public World() {
    }

    public World(int arg, float[][] map) {
        this.map = map;
        if (arg == 0) {
            for (int j = 0; j < 10; j++) {
                newExpand();
            }

        }
    }
    
    public String getTarget(int x, int z) {
        for (int i = 0; i < units.size(); i++) {

            if (Math.sqrt(Math.pow(units.get(i).getX() - x, 2) + Math.pow(units.get(i).getZ() - z, 2)) < units.get(i).haborites) {
                return lang.get(units.get(i).getName()) + ": " + lang.get("armor") + " [" + (units.get(i).fhp + units.get(i).bhp + units.get(i).lhp + units.get(i).rhp) + "/" + (units.get(i).lmaxHp + units.get(i).rmaxHp + units.get(i).fmaxHp + units.get(i).bmaxHp) + "], (" + lang.get("crytical") + "=" + units.get(i).hp + ")";

            }

        }
        return "";
    }
    public ArrayList<Fog> fog = new ArrayList<Fog>();

    public void renderRadar() {
        float bx = 0.0f;
        float by = -1.98f;
        float bz = -4.4f;
        float mapSize = 512f;
        for (int i = 0; i < units.size(); i++) {
            units.get(i).fcolor(this);
            UnitUtils.renderLine(0.02f, new Point(bx + units.get(i).getX() / mapSize, by, bz + units.get(i).getZ() / mapSize), new Point(bx + units.get(i).getX() / mapSize, by + 0.05f, bz + units.get(i).getZ() / mapSize - 0.01f));
            GL11.glColor3f(1.0f, 1.0f, 0f);
            if (units.get(i).selected) {
                UnitUtils.renderLine(0.02f, new Point(bx + units.get(i).getX() / mapSize, by - 0.02, bz + units.get(i).getZ() / mapSize), new Point(bx + units.get(i).getX() / mapSize, by + 0.03f, bz + units.get(i).getZ() / mapSize - 0.01f));
            }

        }
    }

    public void setTarget(int x, int z) {
        boolean select = true;
        int lx = x;
        int lz = z;
        int r = 0;
        int e = 2;
        int d = 1;
        int oo = 1;

        for (int i = 0; i < units.size(); i++) {
            if (units.get(i).isSelected()) {

                float h = units.get(i).getHaborites() * 2;

                if (e == 0) {
                    e = 2;
                    d += 1;
                }
                oo--;
                if (oo == 0) {
                    oo = d;
                    r++;
                    e--;
                }
                if (r == 4) {
                    r = 0;
                }
                lx += ((r == 2) ? 1 : ((r == 0) ? -1 : 0)) * h;

                lz += ((r == 3) ? 1 : ((r == 1) ? -1 : 0)) * h;
                units.get(i).setTx(lx);
                units.get(i).setTz(lz);
                select = false;
            }
        }
        if (select) {
            for (int i = 0; i < units.size(); i++) {
                if (units.get(i).getOwner() == player) {
                    if (Math.sqrt(Math.pow(units.get(i).getX() - x, 2) + Math.pow(units.get(i).getZ() - z, 2)) < units.get(i).getHaborites()) {
                        units.get(i).setSelected(true);
                    }
                }
            }
        }

    }

    public void selectInRadius(int x, int z, int d) {
        if (d < 3) {
            for (int i = 0; i < units.size(); i++) {
                if (units.get(i).isSelected()) {
                    setTarget(x, z);
                    return;
                }
            }
        }
        for (int i = 0; i < units.size(); i++) {
            units.get(i).setSelected(false);

        }
        for (int i = 0; i < units.size(); i++) {
            if (units.get(i).getOwner() == player) {
                if (Math.sqrt(Math.pow(units.get(i).getX() - x, 2) + Math.pow(units.get(i).getZ() - z, 2)) < d && units.get(i).getType() != Unit.TYPE_BUILDING) {
                    units.get(i).setSelected(true);
                }
            }
        }
    }

    public void deselect() {
        for (int i = 0; i < units.size(); i++) {
            units.get(i).setSelected(false);
        }
    }

    public ArrayList<Unit> units = new ArrayList<Unit>();

    public void spawnUnit(int id, int x, int y, int z, float a, int o) {
        Unit u = UnitList.spawnNewUnit(id, x, y, z, a, o, this);
        System.out.println("Spawning " + u.getName());
        units.add(u);

    }

    public void addUnit(Unit u) {
        units.add(u);
    }
    Random r = new Random();
    int wavetime = 5000;
    int e = 50;
    public void render() {
        if(e>0){
        e--;
        if(e==2){
                   
                    spawnUnit(51, 1000, 1, 1000, 0, 0);
                    spawnUnit(52, 1000, 1, 1050, 0, 0);
                    spawnUnit(53, 950, 1, 1025, 0, 0);
        }
        return;
        }
        for (int j = 0; j < ex.size(); j++) {
            ex.get(j).render();
        }
        wind += (r.nextFloat() - 0.5f) * 0.3f;
        if (i == null) {
            for (int j = 0; j < units.size(); j++) {
                if (units.get(j).selected && units.get(j).hp > 0) {
                    i = units.get(j).createMenu(i);
                    break;
                }
            }
        } else {
            if (i.toHide()) {
                i = null;
            }
        }
        wavetime--;
        if(wavetime<=0){
        wavetime=2000+r.nextInt(2000);
        addWave();
        }
        for (int i = 0; i < units.size(); i++) {
            if (i >= units.size()) {
                break;
            }
            units.get(i).life(this);
            units.get(i).render(this);
            if (units.get(i).getY() < -10) {
                units.remove(i);
            }
        }
        for (int i = 0; i < fog.size(); i++) {
            if (i < fog.size()) {
                if (fog.get(i).life > 0) {
                    fog.get(i).render(this);
                } else {
                    fog.remove(i);
                }
            }
        }
    }
}
