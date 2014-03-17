/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game.units;

import game.Button;
import game.Interface;
import game.Point;
import game.TextureLoader;
import game.Unit;
import game.UnitList;
import game.UnitUtils;
import game.World;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.Sphere;

/**
 *
 * @author yew_mentzaki
 */
public class Tank_Builder_NoFaction extends Unit {

    public Tank_Builder_NoFaction(double x, double y, double z, float angle, int owner, World world) {
        super(x, y, z, angle, owner, world);
        speed = 0.4f;
        name = "builder";
        hp = maxHp = 125;
        new Tank_Expa_Turret2_NoFaction(this).oy=3;
        turret.get(0).ox=1.5f;
        
    }

    
    
    public Interface createMenu(Interface i) {
       i = new Interface(this);
       i.buttons.add(new Button(Keyboard.KEY_F, 202, 435, "factory"){

           @Override
           public void event() {
            if(hp>0||bhp>0){
            Unit u = UnitList.spawnNewUnit(51, (int)x, 200, (int)z, 0, owner, world);
            if(world.players[owner].money>=u.getPrice()){
            bhp = -100;
            world.players[owner].money-=u.getPrice();
            u = null;
            selected = false;
            world.spawnUnit(51, (int)x, 200, (int)z, 0, owner);}
            }
           }
       });
       i.buttons.add(new Button(Keyboard.KEY_R, 202, 450, "refinery"){

           @Override
           public void event() {
            if(hp>0||bhp>0){
                        Unit u = UnitList.spawnNewUnit(52, (int)x, 200, (int)z, 0, owner, world);
            if(world.players[owner].money>=u.getPrice()){
            bhp = -100;
            world.players[owner].money-=u.getPrice();
            u = null;
            selected = false;
            bhp = -100;
            world.spawnUnit(52, (int)x, 200, (int)z, 0, owner);}
            }
           }
       }     
       );
       i.buttons.add(new Button(Keyboard.KEY_C, 202, 465, "cannon"){

           @Override
           public void event() {
            if(hp>0||bhp>0){
                        Unit u = UnitList.spawnNewUnit(53, (int)x, 200, (int)z, 0, owner, world);
            if(world.players[owner].money>=u.getPrice()){
            bhp = -100;
            world.players[owner].money-=u.getPrice();
            u = null;
            selected = false;
            bhp = -100;
            world.spawnUnit(53, (int)x, 200, (int)z, 0, owner);}
            }
           }
       }     
       );    
     return i;
    }

    @Override
    public void life(World w) {
        super.life(w);
        if(bhp<0&&fangle>-1.5)fangle-=0.05f;
    
    }
    
    @Override
    public void arender(World w) {
        
        fcolor(w);
        UnitUtils.renderPolygon(new Point[]{
            new Point(-0, 3, 1),
            new Point(-1, 1, 2),
            new Point(4, 1, 2),
            new Point(2, 3, 1),}, l(), r());
        UnitUtils.renderPolygon(new Point[]{
            new Point(-0, 3, -1),
            new Point(-1, 1, -2),
            new Point(4, 1, -2),
            new Point(2, 3, -1),}, l(), r());
        color(95, 85, 80);
       
        UnitUtils.renderPolygon(new Point[]{
            new Point(0, 3, -1),
            new Point(0, 3, 1),
            new Point(2, 3, 1),
            new Point(2, 3, -1),}, l(), r());
        color(75, 65, 60);
        UnitUtils.renderPolygon(new Point[]{
            new Point(-0, 3, -1),
            new Point(-0, 3, 1),
            new Point(-1, 1, 2),
            new Point(-1, 1, -2),}, l(), r());
        UnitUtils.renderPolygon(new Point[]{
            new Point(2, 3, -1),
            new Point(2, 3, 1),
            new Point(4, 1, 2),
            new Point(4, 1, -2),}, l(), r());
        color(95, 85, 90);
        UnitUtils.renderPolygon(new Point[]{
            new Point(0, 3, 1),
            new Point(0, 3, -1),
            new Point(-4, 2, 0),
            }, l(), r());
        UnitUtils.renderPolygon(new Point[]{
            new Point(0, 1, 1),
            new Point(0, 1, -1),
            new Point(-4, 2, 0),
            }, l(), r());
        color(85, 75, 80);
                UnitUtils.renderPolygon(new Point[]{
            new Point(0, 3, -1),
            new Point(0, 1, -1),
            new Point(-4, 2, 0),
            }, l(), r());
                UnitUtils.renderPolygon(new Point[]{
            new Point(0, 3, 1),
            new Point(0, 1, 1),
            new Point(-4, 2, 0),
            }, l(), r());
                
        
        
        
        
        
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
