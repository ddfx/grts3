/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game.units;

import game.Button;
import game.Fog;
import game.Interface;
import game.Point;
import game.TextureLoader;
import game.Unit;
import game.UnitList;
import game.UnitUtils;
import game.World;
import java.util.ArrayList;
import java.util.Random;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

/**
 *
 * @author yew_mentzaki
 */
public class Building_Factory_NoFaction extends Unit {

    public Building_Factory_NoFaction(double x, double y, double z, float angle, int owner, World world) {
        super(x, y, z, angle, owner, world);
        speed = 0f;
        haborites = 14f;
        type = TYPE_BUILDING;
        name = "factory";
        price = 1500;
        fhp = fmaxHp = 500;
        bhp = bmaxHp = 500;
        lhp = lmaxHp = 500;
        rhp = rmaxHp = 500;
        fshield = fmaxShield = 1500;

        bshield = bmaxShield = 1500;

        lshield = lmaxShield = 1500;

        rshield = rmaxShield = 1500;
        // new Tank_Abrams_Turret_LA(this);
    }

    @Override
    public Interface createMenu(Interface i) {
        i = new Interface(this);
        i.buttons.add(new Button(Keyboard.KEY_T, 202, 435, "tank") {
            @Override
            public void event() {
                otsch.add("1");
            }
        }
        );
        i.buttons.add(new Button(Keyboard.KEY_B, 202, 450, "builder") {
            @Override
            public void event() {
                otsch.add("2");
            }
        }
        );
        i.buttons.add(new Button(Keyboard.KEY_H, 202, 465, "harvester") {
            @Override
            public void event() {
                otsch.add("3");
            }
        }
        );
        i.buttons.add(new Button(Keyboard.KEY_P, 202, 480, "plane") {
            @Override
            public void event() {
                otsch.add("4");
            }
        }
        );
        i.buttons.add(new Button(Keyboard.KEY_C, 202, 495, "stop") {
            @Override
            public void event() {
                if (otsch.size() > 0) {
                    otsch.remove(otsch.size() - 1);
                }
            }
        }
        );
        return i;
    }
    int fog = 3;

    @Override
    public void life(World w) {

        if (y <= 2) {
            if (factory_building >= 0) {
                factory_building--;

                if (factory_building >= 25) {

                    fog--;
                    if (fog == 0) {
                        w.fog.add(new Fog((float) x, (float) y + 15, (float) z + 8));
                        w.fog.add(new Fog((float) x, (float) y + 15, (float) z - 8));
                        fog = 3;
                    }
                }
            }
            if (factory_building == 0) {
                if (factory_id != 0) {
                    world.spawnUnit(factory_id, (int) x + 3, (int) y + 5, (int) z, angle, owner);
                    factory_id = 0;
                }
            }
            if (factory_building < 20 && factory_building > 0) {
                gate += 1f;
            }
            if (gate > 1) {
                gate -= 0.5f;
            }
            if (factory_building <= 0) {
                if (otsch.size() > 0) {

                    int id = Integer.parseInt(otsch.get(0));
                    
                    Unit u = UnitList.spawnNewUnit(id, tx, ty, tz, s, id, null);
                    if(w.players[owner].money>=u.getPrice()){
                    w.players[owner].money-=u.getPrice();
                    otsch.remove(0);
                    factory_building = u.getPrepareTime();
                    factory_id = id;
                    }
                }
            }
        }
        s += 0.1f;
        super.life(w); //To change body of generated methods, choose Tools | Templates.
    }
    ArrayList<String> otsch = new ArrayList<>();
    int factory_building = 0;
    int factory_id = 0;
    float s = 0;
    float gate = 1;

    @Override
    public void arender(World w) {

        fcolor(w);
        UnitUtils.renderPolygon(new Point[]{
            new Point(-8, 3, 8),
            new Point(-10, 1, 10),
            new Point(10, 1, 10),
            new Point(8, 3, 8),}, l(), r());
        UnitUtils.renderPolygon(new Point[]{
            new Point(-8, 3, -8),
            new Point(-10, 1, -10),
            new Point(10, 1, -10),
            new Point(8, 3, -8),}, l(), r());
        color(95, 85, 80);
        UnitUtils.renderPolygon(new Point[]{
            new Point(-8, 3, -8),
            new Point(-8, 3, 8),
            new Point(8, 3, 8),
            new Point(8, 3, -8),}, l(), r());
        UnitUtils.renderPolygon(new Point[]{
            new Point(-5, 8, -3.0),
            new Point(12, 10, -3.0),
            new Point(12, 10,3.0),
            new Point(-5, 8,3.0),}, l(), r());
        color(85, 75, 70);
        UnitUtils.renderPolygon(new Point[]{
            new Point(12, 1, -3.0),
            new Point(12, 10, -3.0),
            new Point(-5, 8, -3.0),
            new Point(-5, 1, -3.0),}, l(), r());
        UnitUtils.renderPolygon(new Point[]{
            new Point(12, 1,3.0),
            new Point(12, 10,3.0),
            new Point(-5, 8,3.0),
            new Point(-5, 1,3.0),}, l(), r());
        color(75,65,60);
        UnitUtils.renderPolygon(new Point[]{
            new Point(-5, 8, -3.0),
            new Point(-5, 1, -3.0),
            new Point(-5, 1,3.0),
            new Point(-5, 8,3.0),}, l(), r());
        color(65, 55, 50);
        UnitUtils.renderPolygon(new Point[]{
            new Point(1, 15, -9),
            new Point(1, 1, -9),
            new Point(-0, 1, -7),
            new Point(-0, 15, -7),}, l(), r());
        UnitUtils.renderPolygon(new Point[]{
            new Point(1, 15, 9),
            new Point(1, 1, 9),
            new Point(-0, 1, 7),
            new Point(-0, 15, 7),}, l(), r());
        color(75,65,60);
        UnitUtils.renderPolygon(new Point[]{
            new Point(-1, 15, -9),
            new Point(-1, 1, -9),
            new Point(-0, 1, -7),
            new Point(-0, 15, -7),}, l(), r());
        UnitUtils.renderPolygon(new Point[]{
            new Point(1, 15, -9),
            new Point(1, 1, -9),
            new Point(-1, 1, -9),
            new Point(-1, 15, -9),}, l(), r());
        UnitUtils.renderPolygon(new Point[]{
            new Point(-1, 15, 9),
            new Point(-1, 1, 9),
            new Point(-0, 1, 7),
            new Point(-0, 15, 7),}, l(), r());
        UnitUtils.renderPolygon(new Point[]{
            new Point(1, 15, 9),
            new Point(1, 1, 9),
            new Point(-1, 1, 9),
            new Point(-1, 15, 9),}, l(), r());
        color(0, 0, 0);
        UnitUtils.renderPolygon(new Point[]{
            new Point(10, 1, 5),
            new Point(10, 9, 5),
            new Point(10, 9, 5),
            new Point(10, 1, 5),}, l(), r());

          color(75,65,60);
         TextureLoader.bind("flag"+owner+".jpg");
        UnitUtils.renderPolygon(new Point[]{
            new Point(12, gate, 3),
            
            new Point(12, gate, -3),
            new Point(12, 10, -3),
            new Point(12, 10, 3),
            }, l(), r());
          TextureLoader.bind("iron.jpg");
        UnitUtils.renderPolygon(new Point[]{
            new Point(-8, 3, -8),
            new Point(-8, 3, 8),
            new Point(-10, 1, 10),
            new Point(-10, 1, -10),}, l(), r());
        UnitUtils.renderPolygon(new Point[]{
            new Point(8, 3, -8),
            new Point(8, 3, 8),
            new Point(10, 1, 10),
            new Point(10, 1, -10),}, l(), r());

    }

}
