/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game.units;

import game.Point;
import game.Turret;
import game.Unit;
import game.UnitUtils;
import game.World;
import org.lwjgl.opengl.GL11;

/**
 *
 * @author yew_mentzaki
 */
public class Building_Cannon_Turret_NoFaction extends Turret {

    public Building_Cannon_Turret_NoFaction(Unit body) {
        super(body);
        ox = -0.5f;
        oy = 3.3f;
        oz = 0;
        turnspeed = 0.005f;
        weaponReloadTime = 200;
        weaponPower = 250;
        weaponDistance = 500;
        new Tank_Expa_Turret2_NoFaction(this);
    }

    @Override
    public void arender(World w) {
        color(85, 75, 70);
        //angle+=0.1;
        UnitUtils.renderTPolygon(new Point[]{
            new Point(-3, 3, 2.5f),
            new Point(-4, 0, 3.5f),
            new Point(5, 0, 3.0f),
            new Point(3, 3, 2.5f),}, l(), r(), body.r());
        UnitUtils.renderTPolygon(new Point[]{
            new Point(-3, 3, -2.5f),
            new Point(-4, 0, -3.5f),
            new Point(5, 0, -3.0f),
            new Point(3, 3, -2.5f),}, l(), r(), body.r());
        color(95, 85, 80);
        UnitUtils.renderTPolygon(new Point[]{
            new Point(-3, 3, -2.5),
            new Point(-3, 3, 2.5),
            new Point(3, 3, 2.5),
            new Point(3, 3, -2.5),}, l(), r(), body.r());
        color(75, 65, 60);
        UnitUtils.renderTPolygon(new Point[]{
            new Point(3, 3, -2.5),
            new Point(3, 3, 2.5),
            new Point(5, 0, 3.0),
            new Point(5, 0, -3.0),}, l(), r(), body.r());
        UnitUtils.renderTPolygon(new Point[]{
            new Point(-3, 3, -2.5),
            new Point(-3, 3, 2.5),
            new Point(-4, 0, 3.5),
            new Point(-4, 0, -3.5),}, l(), r(), body.r());

        color(35, 25, 20);

        UnitUtils.renderWPolygon(new Point[]{
            new Point(10, 0.4, 0),
            new Point(10, 0, 0.4),
            new Point(-0, 0, 0.4),
            new Point(-0, 0.4, 0),}, 0, new Point(4, 1, 0), l(), r(), body.r());

        UnitUtils.renderWPolygon(new Point[]{
            new Point(10, 0.4, 0),
            new Point(10, 0, -0.4),
            new Point(-0, 0, -0.4),
            new Point(-0, 0.4, 0),}, 0, new Point(4, 1, 0), l(), r(), body.r());

        UnitUtils.renderWPolygon(new Point[]{
            new Point(10, -0.4, 0),
            new Point(10, 0, 0.4),
            new Point(-0, 0, 0.4),
            new Point(-0, -0.4, 0),}, 0, new Point(4, 1, 0), l(), r(), body.r());

        UnitUtils.renderWPolygon(new Point[]{
            new Point(10, -0.4, 0),
            new Point(10, 0, -0.4),
            new Point(-0, 0, -0.4),
            new Point(-0, -0.4, 0),}, 0, new Point(4, 1, 0), l(), r(), body.r());
        GL11.glColor3f(0, 1, 1);
        UnitUtils.renderWPolygon(new Point[]{
            new Point(10, -0.4, 0.4),
            new Point(10, 0.4, 0),
            new Point(10, 0.4, -0.4),
            new Point(10, -0.4, 0),}, 0, new Point(4, 1, 0), l(), r(), body.r());

        
        color(35, 25, 20);

        UnitUtils.renderWPolygon(new Point[]{
            new Point(10, 0.4, 0),
            new Point(10, 0, 0.4),
            new Point(-0, 0, 0.4),
            new Point(-0, 0.4, 0),}, 0, new Point(4, 2, 0), l(), r(), body.r());

        UnitUtils.renderWPolygon(new Point[]{
            new Point(10, 0.4, 0),
            new Point(10, 0, -0.4),
            new Point(-0, 0, -0.4),
            new Point(-0, 0.4, 0),}, 0, new Point(4, 2, 0), l(), r(), body.r());

        UnitUtils.renderWPolygon(new Point[]{
            new Point(10, -0.4, 0),
            new Point(10, 0, 0.4),
            new Point(-0, 0, 0.4),
            new Point(-0, -0.4, 0),}, 0, new Point(4, 2, 0), l(), r(), body.r());

        UnitUtils.renderWPolygon(new Point[]{
            new Point(10, -0.4, 0),
            new Point(10, 0, -0.4),
            new Point(-0, 0, -0.4),
            new Point(-0, -0.4, 0),}, 0, new Point(4, 2, 0), l(), r(), body.r());
        GL11.glColor3f(0, 1, 1);
        UnitUtils.renderWPolygon(new Point[]{
            new Point(10, -0.4, 0.4),
            new Point(10, 0.4, 0),
            new Point(10, 0.4, -0.4),
            new Point(10, -0.4, 0),}, 0, new Point(4, 2, 0), l(), r(), body.r());

        
    }

}
