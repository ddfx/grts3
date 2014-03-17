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
public class Tank_Expa_Turret2_NoFaction extends Turret {

    public Tank_Expa_Turret2_NoFaction(Unit body) {
        super(body);
        ox = -0.5f;
        oy = 0.8f;
        oz = -1.5f;
        weaponPower = 8;
        turnspeed=0.5f;
        weaponReloadTime=10;
        
    }

    @Override
    public void arender(World w) {
        color(35, 25, 20);

        UnitUtils.renderWPolygon(new Point[]{
            new Point(1, 0.2, 0),
            new Point(1, 0, 0.2),
            new Point(-0, 0, 0.2),
            new Point(-0, 0.2, 0),}, 0, new Point(0, 0, 0), l(), r(), body.r());

        UnitUtils.renderWPolygon(new Point[]{
            new Point(1, 0.2, 0),
            new Point(1, 0, -0.2),
            new Point(-0, 0, -0.2),
            new Point(-0, 0.2, 0),}, 0, new Point(0, 0, 0), l(), r(), body.r());

        UnitUtils.renderWPolygon(new Point[]{
            new Point(1, -0.2, 0),
            new Point(1, 0, 0.2),
            new Point(-0, 0, 0.2),
            new Point(-0, -0.2, 0),}, 0, new Point(0, 0, 0), l(), r(), body.r());

        UnitUtils.renderWPolygon(new Point[]{
            new Point(1, -0.2, 0),
            new Point(1, 0, -0.2),
            new Point(-0, 0, -0.2),
            new Point(-0, -0.2, 0),}, 0, new Point(0, 0, 0), l(), r(), body.r());
        GL11.glColor3f(0, 1, 1);
        UnitUtils.renderWPolygon(new Point[]{
            new Point(1, -0.2, 0.2),
            new Point(1, 0.2, 0),
            new Point(1, 0.2, -0.2),
            new Point(1, -0.2, 0),}, 0, new Point(0, 0, 0), l(), r(), body.r());

    }

}
