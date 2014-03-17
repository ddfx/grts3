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
public class Plane_Expa_Turret_NoFaction extends Turret {

    public Plane_Expa_Turret_NoFaction(Unit body) {
        super(body);
        ox = -0.5f;
        oy = 0f;
        oz = 0;
        weaponPower = 50;
        turnspeed=0.5f;
    }

  

}
