/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

/**
 *
 * @author yew_mentzaki
 */
public class UnitList {

    public static Unit spawnNewUnit(int id, int x, int y, int z, float a, int o, World w) {
        switch (id) {
            case 1:
                return new game.units.Tank_Expa_NoFaction(x, y, z, a, o, w);
            case 2:
                return new game.units.Tank_Builder_NoFaction(x, y, z, a, o, w);
            case 3:
                return new game.units.Tank_Harvester_NoFaction(x, y, z, a, o, w);
            case 4:
                return new game.units.Plane_Expa_NoFaction(x, y, z, a, o, w);

            case 51:
                return new game.units.Building_Factory_NoFaction(x, y, z, a, o, w);
            case 52:
                return new game.units.Building_Refinery_NoFaction(x, y, z, a, o, w);
            case 53:
                return new game.units.Building_Cannon_NoFaction(x, y, z, a, o, w);

            default:
                return null;
        }
    }
}
