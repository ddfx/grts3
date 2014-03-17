/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game.units;

import game.Button;
import game.Exceptalanum;
import game.Interface;
import game.Point;
import game.Unit;
import game.UnitUtils;
import game.World;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.Sphere;

/**
 *
 * @author yew_mentzaki
 */
public class Tank_Harvester_NoFaction extends Unit {

    public Tank_Harvester_NoFaction(double x, double y, double z, float angle, int owner, World world) {
        super(x, y, z, angle, owner, world);
        speed = 0.4f;
        name = "harvester";
        hp = maxHp = 125;

        price=1000;
    }

    int tank = 0;

    @Override
    public void life(World w) {
        int collect = w.collect((float) x, (float) z, 10);
        tank = Math.min(tank + collect, 40);
        super.life(w);
        if (bhp < 0 && fangle > -1.5) {
            fangle -= 0.2f;
        }
        int j = 0;
        int d = 2000;
        if (tank < 40) {
            for (int i = 0; i < w.ex.size(); i++) {
                Exceptalanum e = w.ex.get(i);
                if (e.dist((float) x, (float) z) < d) {
                    d = e.dist((float) x, (float) z);
                    j = i;
                }
            }
            Exceptalanum ee = w.ex.get(j);
            if (ee != null) {
                tx = (int) ee.x;
                tz = (int) ee.z;
            }
        } else {
            for (int i = 0; i < w.units.size(); i++) {
                Unit e = w.units.get(i);
                if (e.getStatus()[2] == 10) {
                    if (Math.sqrt(Math.pow(e.getX() - x, 2) + Math.pow(e.getZ() - z, 2)) < d) {
                        d = (int) Math.sqrt(Math.pow(e.getX() - x, 2) + Math.pow(e.getZ() - z, 2));
                        j = i;

                    }
                }
            }
           
            Unit ee = w.units.get(j);
            if (ee.getStatus()[2] == 10) {
                tx = (int) ee.getX();
                tz = (int) ee.getZ();
                
                if (d < 20) {
                tank = 0;
                ee.getStatus()[2] = 11;
            }

            }
        }

    }

    @Override
    public void arender(World w) {

        fcolor(w);
        UnitUtils.renderPolygon(new Point[]{
            new Point(-2, 5, 1),
            new Point(-3, 1, 2),
            new Point(4, 1, 2),
            new Point(2, 5, 1),}, l(), r());
        UnitUtils.renderPolygon(new Point[]{
            new Point(-2, 5, -1),
            new Point(-3, 1, -2),
            new Point(4, 1, -2),
            new Point(2, 5, -1),}, l(), r());
        color(95, 85, 80);
        UnitUtils.renderPolygon(new Point[]{
            new Point(-2, 5, -1),
            new Point(-2, 5, 1),
            new Point(2, 5, 1),
            new Point(2, 5, -1),}, l(), r());
        color(75, 65, 60);
        UnitUtils.renderPolygon(new Point[]{
            new Point(-2, 5, -1),
            new Point(-2, 5, 1),
            new Point(-3, 1, 2),
            new Point(-3, 1, -2),}, l(), r());
        UnitUtils.renderPolygon(new Point[]{
            new Point(2, 5, -1),
            new Point(2, 5, 1),
            new Point(4, 1, 2),
            new Point(4, 1, -2),}, l(), r());
        color(20, 20, 20);
        UnitUtils.renderPolygon(new Point[]{
            new Point(-2, 0, -2),
            new Point(3, 0, -2),
            new Point(4, 1, -2),
            new Point(-3, 1, -2),}, l(), r());
        UnitUtils.renderPolygon(new Point[]{
            new Point(-2, 0, -1.5),
            new Point(3, 0, -1.5),
            new Point(4, 1, -1.5),
            new Point(-3, 1, -1.5),}, l(), r());
        UnitUtils.renderPolygon(new Point[]{
            new Point(4, 1, -1.5),
            new Point(3, 0, -1.5),
            new Point(3, 0, -2),
            new Point(4, 1, -2),}, l(), r());
        UnitUtils.renderPolygon(new Point[]{
            new Point(-3, 1, -1.5),
            new Point(-2, 0, -1.5),
            new Point(-2, 0, -2),
            new Point(-3, 1, -2),}, l(), r());
        /////////////
        UnitUtils.renderPolygon(new Point[]{
            new Point(-2, 0, 2),
            new Point(3, 0, 2),
            new Point(4, 1, 2),
            new Point(-3, 1, 2),}, l(), r());
        UnitUtils.renderPolygon(new Point[]{
            new Point(-2, 0, 1.5),
            new Point(3, 0, 1.5),
            new Point(4, 1, 1.5),
            new Point(-3, 1, 1.5),}, l(), r());
        UnitUtils.renderPolygon(new Point[]{
            new Point(4, 1, 1.5),
            new Point(3, 0, 1.5),
            new Point(3, 0, 2),
            new Point(4, 1, 2),}, l(), r());
        UnitUtils.renderPolygon(new Point[]{
            new Point(-3, 1, 1.5),
            new Point(-2, 0, 1.5),
            new Point(-2, 0, 2),
            new Point(-3, 1, 2),}, l(), r());
    }

}
