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
public class Building_Refinery_NoFaction extends Unit {

    public Building_Refinery_NoFaction(double x, double y, double z, float angle, int owner, World world) {
        super(x, y, z, angle, owner, world);
        speed = 0f;
        haborites = 14f;
        type = TYPE_BUILDING;
        price=1500;
        name = "refinery";
        fhp = fmaxHp = 500;
        bhp = bmaxHp = 500;
        lhp = lmaxHp = 500;
        rhp = rmaxHp = 500;
        fshield = fmaxShield = 1500;

        bshield = bmaxShield = 1500;

        lshield = lmaxShield = 1500;
        status[2] = 10;
        rshield = rmaxShield = 1500;
        // new Tank_Abrams_Turret_LA(this);
    }

    int fog = 3;
    int work = 1;

    @Override
    public void life(World w) {

        if (y <= 2) {
            if (status[2] == 11) {
                status[2] = 12;
                work = 1000;
            }
            if (work == 0) {
                w.players[owner].money += 1000;
                status[2]=10;
            }
            if (work >= 0) {
                work--;
                fog--;
                if (fog == 0) {
                    w.fog.add(new Fog((float) x, (float) y + 15, (float) z + 8));
                    w.fog.add(new Fog((float) x, (float) y + 15, (float) z - 8));
                    fog = 3;
                }
            }
        }
        super.life(w); //To change body of generated methods, choose Tools | Templates.
    }

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
            new Point(-5, 8, -6),
            new Point(5, 8, -6),
            new Point(5, 8, 6),
            new Point(-5, 8, 6),}, l(), r());
        color(85, 75, 70);
        UnitUtils.renderPolygon(new Point[]{
            new Point(5, 1, -6),
            new Point(5, 8, -6),
            new Point(-5, 8, -6),
            new Point(-5, 1, -6),}, l(), r());
        UnitUtils.renderPolygon(new Point[]{
            new Point(5, 1, 6),
            new Point(5, 8, 6),
            new Point(-5, 8, 6),
            new Point(-5, 1, 6),}, l(), r());
        color(75, 65, 60);
        UnitUtils.renderPolygon(new Point[]{
            new Point(-5, 8, -6),
            new Point(-5, 1, -6),
            new Point(-5, 1, 6),
            new Point(-5, 8, 6),}, l(), r());
        UnitUtils.renderPolygon(new Point[]{
            new Point(5, 8, -6),
            new Point(5, 1, -6),
            new Point(5, 1, 6),
            new Point(5, 8, 6),}, l(), r());
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
        color(75, 65, 60);
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

        fcolor(w);

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
