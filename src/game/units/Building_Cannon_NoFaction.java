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
public class Building_Cannon_NoFaction extends Unit {

    public Building_Cannon_NoFaction(double x, double y, double z, float angle, int owner, World world) {
        super(x, y, z, angle, owner, world);
        speed = 0f;
        haborites = 14f;
        type = TYPE_BUILDING;
        price=1000;
        name = "cannon";
        fhp = fmaxHp = 500;
        bhp = bmaxHp = 500;
        lhp = lmaxHp = 500;
        rhp = rmaxHp = 500;
        fshield = fmaxShield = 1500;

        bshield = bmaxShield = 1500;

        lshield = lmaxShield = 1500;
        status[2] = 10;
        rshield = rmaxShield = 1500;
         new Building_Cannon_Turret_NoFaction(this);
    }

    int fog = 3;
    int work = 1;

    @Override
    public void life(World w) {

        
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
