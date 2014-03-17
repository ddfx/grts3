/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import java.util.Random;
import org.lwjgl.opengl.GL11;

/**
 *
 * @author yew_mentzaki
 */
public class Turret extends Unit {

    public float ox, oy, oz;

    @Override
    public void life(World w) {
        this.x = ox;
        this.y = oy;
        this.z = oz;

        {
            double x = this.x * Math.cos(-body.getFangle()) - this.y * Math.sin(-body.getFangle());
            double y = this.x * Math.sin(-body.getFangle()) + this.y * Math.cos(-body.getFangle());
            this.x = x;
            this.y = y;
        }

        {
            double z = this.z * Math.cos(-body.sangle) - this.y * Math.sin(-body.sangle);
            double y = this.z * Math.sin(-body.sangle) + this.y * Math.cos(-body.sangle);
            this.z = z;
            this.y = y;
        }

        {
            double x = this.x * Math.cos(body.getAngle()) - this.z * Math.sin(body.getAngle());
            double z = this.x * Math.sin(body.getAngle()) + this.z * Math.cos(body.getAngle());
            this.x = x;
            this.z = z;
        }
        x += body.getX();
        y += body.getY();
        z += +body.getZ();

        this.setX(x);

        this.setZ(z);
        this.setY(y);
        if (body.hp > 0) {
            move(w);

        }
        hp = body.hp;
        for (int i = 0; i < turret.size(); i++) {
            turret.get(i).life(w);

        }
    }

    public Point weapon(double px, double py, double pz) {
        int i = 0;
        Point rotation = r();
        Point urotation = body.r();
        Point[] points = new Point[]{new Point(px, py, pz)};

        {
            double y = points[i].y * Math.cos(rotation.z) - points[i].x * Math.sin(rotation.z);
            double x = points[i].y * Math.sin(rotation.z) + points[i].x * Math.cos(rotation.z);
            points[i].y = y;
            points[i].x = x;
        }

        {
            double y = points[i].y * Math.cos(rotation.x) - points[i].z * Math.sin(rotation.x);
            double z = points[i].y * Math.sin(rotation.x) + points[i].z * Math.cos(rotation.x);
            points[i].y = y;
            points[i].z = z;
        }
        {
            double x = points[i].x * Math.cos(rotation.y - urotation.y) - points[i].z * Math.sin(rotation.y - urotation.y);
            double z = points[i].x * Math.sin(rotation.y - urotation.y) + points[i].z * Math.cos(rotation.y - urotation.y);
            points[i].x = x;
            points[i].z = z;
        }

        {
            double y = points[i].y * Math.cos(urotation.z) - points[i].x * Math.sin(urotation.z);
            double x = points[i].y * Math.sin(urotation.z) + points[i].x * Math.cos(urotation.z);
            points[i].y = y;
            points[i].x = x;
        }
        {
            double y = points[i].y * Math.cos(urotation.x) - points[i].z * Math.sin(urotation.x);
            double z = points[i].y * Math.sin(urotation.x) + points[i].z * Math.cos(urotation.x);
            points[i].y = y;
            points[i].z = z;
        }
        {
            double x = points[i].x * Math.cos(urotation.y) - points[i].z * Math.sin(urotation.y);
            double z = points[i].x * Math.sin(urotation.y) + points[i].z * Math.cos(urotation.y);
            points[i].x = x;
            points[i].z = z;
        }

        points[i].x += this.x;

        points[i].y += this.y;
        points[i].z += this.z;

        return points[i];
    }
    protected int weaponPower, weaponType, weaponReload, weaponReloadTime = 75, weaponDistance = 125;
    protected float backer = 0.1f;

    @Override
    public void move(World w) {
         if (angle > Math.PI) {
                angle -= 2 * Math.PI;
            }

            if (angle < -Math.PI) {
                angle += 2 * Math.PI;
            }
        float t = body.getAngle();
        int d = weaponDistance;
        if (weaponReload > 0) {
            weaponReload--;
        }
        for (int i = 0; i < w.units.size(); i++) {
            if (w.units.get(i).getOwner() != getOwner() && w.units.get(i).hp > 0) {
                Unit target = w.units.get(i);
                int d_ = (int) Math.sqrt(Math.pow(target.x - x, 2) + Math.pow(target.z - z, 2));
                if (d_ < d) {
                    t = (float) Math.atan2(target.getZ() - body.z, target.getX() - body.x);
                    d = d_;
                    if (Math.abs(angle - t) < 0.1 && weaponReload == 0) {
                        GL11.glColor3f(0f, 1f, 1f);
                        TextureLoader.bind("laser.jpg");
                        UnitUtils.renderLaser((weaponPower) * 0.01f, weapon(0, 0, 0), target.l());
                        TextureLoader.bind("iron.jpg");
                        weaponReload = weaponReloadTime + new Random().nextInt(weaponReloadTime / 5);
                        target.hit(this.l(), weaponPower, weaponType);

                        body.x -= Math.cos(angle) * backer;

                        body.z -= Math.sin(angle) * backer;
                    }
                }
            }

        }
        if (angle < t) {
            angle += turnspeed;
        }
        if (angle > t) {
            angle -= turnspeed;
        }

        double de = t - angle;
        double clampedDelta = de - (float) (Math.floor(de / (2.0f * Math.PI) + 0.5f) * 2.0f * Math.PI);//Вычисляем угол
        if (Math.abs(t - angle) < 0.05 || Math.abs(t + 2 * Math.PI - angle) < 0.05) {
            angle = t;
        } else {
            if (clampedDelta > Math.PI) {
                clampedDelta -= Math.PI;
            }
            if (clampedDelta > 0) {
                angle += turnspeed;

            }
            if (clampedDelta < 0) {
                angle -= turnspeed;

            }
            if (angle > Math.PI) {
                angle -= 2 * Math.PI;
            }

            if (angle < -Math.PI) {
                angle += 2 * Math.PI;
            }
        }
    }

    protected Unit body;

    public Turret(Unit body) {
        super(body.x, body.y, body.z, 0, body.owner, body.world);
        this.body = body;
        //world.addUnit(this);
        body.turret.add(this);
    }

    @Override
    public void render(World w) {
        arender(w);
        for (int i = 0; i < turret.size(); i++) {
            turret.get(i).render(w);
        }

    }

}
