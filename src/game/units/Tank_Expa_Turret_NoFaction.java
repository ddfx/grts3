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
public class Tank_Expa_Turret_NoFaction extends Turret {

    public Tank_Expa_Turret_NoFaction(Unit body) {
        super(body);
        ox = -0.5f;
        oy = 2.3f;
        oz = 0;
        weaponPower = 50;
        
        new Tank_Expa_Turret2_NoFaction(this);
    }

    @Override
    public void arender(World w) {
        color(85, 75, 70);
        //angle+=0.1;
        UnitUtils.renderTPolygon(new Point[]{
            new Point(-1, 1, 0.5f),
            new Point(-2, 0, 1.5f),
            new Point(3, 0, 1.0f),
            new Point(1, 1, 0.5f),}, l(), r(), body.r());
        UnitUtils.renderTPolygon(new Point[]{
            new Point(-1, 1, -0.5f),
            new Point(-2, 0, -1.5f),
            new Point(3, 0, -1.0f),
            new Point(1, 1, -0.5f),}, l(), r(), body.r());
        color(95, 85, 80);
        UnitUtils.renderTPolygon(new Point[]{
            new Point(-1, 1, -0.5),
            new Point(-1, 1, 0.5),
            new Point(1, 1, 0.5),
            new Point(1, 1, -0.5),}, l(), r(), body.r());
        color(75, 65, 60);
        UnitUtils.renderTPolygon(new Point[]{
            new Point(1, 1, -0.5),
            new Point(1, 1, 0.5),
            new Point(3, 0, 1.0),
            new Point(3, 0, -1.0),}, l(), r(), body.r());
        UnitUtils.renderTPolygon(new Point[]{
            new Point(-1, 1, -0.5),
            new Point(-1, 1, 0.5),
            new Point(-2, 0, 1.5),
            new Point(-2, 0, -1.5),}, l(), r(), body.r());

        color(35, 25, 20);

        UnitUtils.renderWPolygon(new Point[]{
            new Point(3, 0.2, 0),
            new Point(3, 0, 0.2),
            new Point(-0, 0, 0.2),
            new Point(-0, 0.2, 0),}, 0, new Point(2.5, 0, 0), l(), r(), body.r());

        UnitUtils.renderWPolygon(new Point[]{
            new Point(3, 0.2, 0),
            new Point(3, 0, -0.2),
            new Point(-0, 0, -0.2),
            new Point(-0, 0.2, 0),}, 0, new Point(2.5, 0, 0), l(), r(), body.r());

        UnitUtils.renderWPolygon(new Point[]{
            new Point(3, -0.2, 0),
            new Point(3, 0, 0.2),
            new Point(-0, 0, 0.2),
            new Point(-0, -0.2, 0),}, 0, new Point(2.5, 0, 0), l(), r(), body.r());

        UnitUtils.renderWPolygon(new Point[]{
            new Point(3, -0.2, 0),
            new Point(2, 0, -0.2),
            new Point(-0, 0, -0.2),
            new Point(-0, -0.2, 0),}, 0, new Point(2.5, 0, 0), l(), r(), body.r());
        GL11.glColor3f(0, 1, 1);
        UnitUtils.renderWPolygon(new Point[]{
            new Point(3, -0.2, 0.2),
            new Point(3, 0.2, 0),
            new Point(3, 0.2, -0.2),
            new Point(3, -0.2, 0),}, 0, new Point(2.5, 0, 0), l(), r(), body.r());

    }

}
