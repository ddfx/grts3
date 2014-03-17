/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game.units;

import game.Button;
import game.Interface;
import game.Point;
import game.Unit;
import game.UnitUtils;
import game.World;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

/**
 *
 * @author yew_mentzaki
 */
public class Tank_Expa_NoFaction extends Unit {

    public Tank_Expa_NoFaction(double x, double y, double z, float angle, int owner, World world) {
        super(x, y, z, angle, owner, world);
        speed = 0.5f;
        name = "tankexpa";
        hp = maxHp = 125;
        price=500;
        new Tank_Expa_Turret_NoFaction(this);
    }


    
    @Override
    public void arender(World w) {

        fcolor(w);
        UnitUtils.renderPolygon(new Point[]{
            new Point(-2, 2, 1),
            new Point(-3, 1, 2),
            new Point(4, 1, 2),
            new Point(2, 2, 1),}, l(), r());
        UnitUtils.renderPolygon(new Point[]{
            new Point(-2, 2, -1),
            new Point(-3, 1, -2),
            new Point(4, 1, -2),
            new Point(2, 2, -1),}, l(), r());
        color(95, 85, 80);
        UnitUtils.renderPolygon(new Point[]{
            new Point(-2, 2, -1),
            new Point(-2, 2, 1),
            new Point(2, 2, 1),
            new Point(2, 2, -1),}, l(), r());
        color(75, 65, 60);
        UnitUtils.renderPolygon(new Point[]{
            new Point(-2, 2, -1),
            new Point(-2, 2, 1),
            new Point(-3, 1, 2),
            new Point(-3, 1, -2),}, l(), r());
        UnitUtils.renderPolygon(new Point[]{
            new Point(2, 2, -1),
            new Point(2, 2, 1),
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
