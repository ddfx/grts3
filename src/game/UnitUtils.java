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
public class UnitUtils {

    public static void renderCycle(float size, float d, Point c) {
        int f = (int) (d * 3.1415) / 5;
        if (f < 4) {
            f = 4;
        }
        while (f % 4 != 0) {
            f++;
        }
        double r = Math.PI * 2 / f;

        double n = r;
        double ln = 0;
        for (int i = 0; i < f; i++) {
            renderLine(size, new Point(c.x + d * Math.cos(n), c.y, c.z + d * Math.sin(n)), new Point(c.x + d * Math.cos(ln), c.y, c.z + d * Math.sin(ln)));
            ln = n;
            n += r;
        }
    }

    public static void renderRadarCycle(float size, float d, Point c) {
        int f = (int) ((d * 3.1415) / 0.25);
        if (f < 4) {
            f = 4;
        }
        while (f % 4 != 0) {
            f++;
        }
        double r = Math.PI * 2 / f;

        double n = r;
        double ln = 0;
        for (int i = 0; i < f; i++) {
            renderLine(size, new Point(c.x + d * Math.cos(n), c.y, c.z + d * Math.sin(n)), new Point(c.x + d * Math.cos(ln), c.y, c.z + d * Math.sin(ln)));
            ln = n;
            n += r;
        }
    }

    public static void renderLaser(float size, Point f, Point t) {

        t.y += new Random().nextFloat() * 2;
        t.x += new Random().nextFloat() * 4 - 2;
        t.z += new Random().nextFloat() * 4 - 2;
        double angle = Math.atan2(t.z - f.z, t.x - f.x);
        GL11.glBegin(GL11.GL_POLYGON);
        GL11.glTexCoord2f(0.15f, 0.15f);
        GL11.glVertex3d(f.x - Math.cos(angle - 1.57) * size, f.y, f.z - Math.sin(angle - 1.57) * size);
        GL11.glTexCoord2f(0.75f, 0.15f);
        GL11.glVertex3d(t.x - Math.cos(angle - 1.57) * size, t.y, t.z - Math.sin(angle - 1.57) * size);
        GL11.glTexCoord2f(0.75f, 0.75f);
        GL11.glVertex3d(t.x + Math.cos(angle - 1.57) * size, t.y, t.z + Math.sin(angle - 1.57) * size);
        GL11.glTexCoord2f(0.15f, 0.75f);
        GL11.glVertex3d(f.x + Math.cos(angle - 1.57) * size, f.y, f.z + Math.sin(angle - 1.57) * size);

        GL11.glEnd();
        GL11.glBegin(GL11.GL_POLYGON);
        GL11.glTexCoord2f(0.15f, 0.15f);
        GL11.glVertex3d(f.x, f.y + size, f.z);
        GL11.glTexCoord2f(0.75f, 0.15f);
        GL11.glVertex3d(t.x, t.y + size, t.z);
        GL11.glTexCoord2f(0.75f, 0.75f);
        GL11.glVertex3d(t.x, t.y - size, t.z);
        GL11.glTexCoord2f(0.15f, 0.75f);
        GL11.glVertex3d(f.x, f.y - size, f.z);

        GL11.glEnd();
    }

    public static void renderLine(float size, Point f, Point t) {
        double angle = Math.atan2(t.z - f.z, t.x - f.x);
        GL11.glBegin(GL11.GL_POLYGON);
        GL11.glTexCoord2d(0.1, 0.1);
        GL11.glVertex3d(f.x - Math.cos(angle - 1.57) * size, f.y, f.z - Math.sin(angle - 1.57) * size);
        GL11.glTexCoord2d(0.5, 0.1);
        GL11.glVertex3d(t.x - Math.cos(angle - 1.57) * size, t.y, t.z - Math.sin(angle - 1.57) * size);
        GL11.glTexCoord2d(0.5, 0.5);
        GL11.glVertex3d(t.x + Math.cos(angle - 1.57) * size, t.y, t.z + Math.sin(angle - 1.57) * size);
        GL11.glTexCoord2d(0.1, 0.5);
        GL11.glVertex3d(f.x + Math.cos(angle - 1.57) * size, f.y, f.z + Math.sin(angle - 1.57) * size);
        GL11.glEnd();
    }

    public static void renderWPolygon(Point[] points, float target, Point onHead, Point center, Point rotation, Point urotation) {

        for (int i = 0; i < points.length; i++) {
            double y = points[i].y * Math.cos(target - urotation.z) - points[i].x * Math.sin(target - urotation.z);
            double x = points[i].y * Math.sin(target - urotation.z) + points[i].x * Math.cos(target - urotation.z);
            points[i].y = y;
            points[i].x = x;
        }

        for (int i = 0; i < points.length; i++) {
            points[i].x += onHead.x;
            points[i].y += onHead.y;
            points[i].z += onHead.z;
        }

        for (int i = 0; i < points.length; i++) {
            double y = points[i].y * Math.cos(rotation.z) - points[i].x * Math.sin(rotation.z);
            double x = points[i].y * Math.sin(rotation.z) + points[i].x * Math.cos(rotation.z);
            points[i].y = y;
            points[i].x = x;
        }

        for (int i = 0; i < points.length; i++) {
            double y = points[i].y * Math.cos(rotation.x) - points[i].z * Math.sin(rotation.x);
            double z = points[i].y * Math.sin(rotation.x) + points[i].z * Math.cos(rotation.x);
            points[i].y = y;
            points[i].z = z;
        }
        for (int i = 0; i < points.length; i++) {
            double x = points[i].x * Math.cos(rotation.y - urotation.y) - points[i].z * Math.sin(rotation.y - urotation.y);
            double z = points[i].x * Math.sin(rotation.y - urotation.y) + points[i].z * Math.cos(rotation.y - urotation.y);
            points[i].x = x;
            points[i].z = z;
        }

        for (int i = 0; i < points.length; i++) {
            double y = points[i].y * Math.cos(urotation.z) - points[i].x * Math.sin(urotation.z);
            double x = points[i].y * Math.sin(urotation.z) + points[i].x * Math.cos(urotation.z);
            points[i].y = y;
            points[i].x = x;
        }

        for (int i = 0; i < points.length; i++) {
            double y = points[i].y * Math.cos(urotation.x) - points[i].z * Math.sin(urotation.x);
            double z = points[i].y * Math.sin(urotation.x) + points[i].z * Math.cos(urotation.x);
            points[i].y = y;
            points[i].z = z;
        }
        for (int i = 0; i < points.length; i++) {
            double x = points[i].x * Math.cos(urotation.y) - points[i].z * Math.sin(urotation.y);
            double z = points[i].x * Math.sin(urotation.y) + points[i].z * Math.cos(urotation.y);
            points[i].x = x;
            points[i].z = z;
        }

        for (int i = 0; i < points.length; i++) {
            points[i].x += center.x;
            points[i].y += center.y;
            points[i].z += center.z;
        }
        GL11.glBegin(GL11.GL_POLYGON);

        for (int i = 0; i < points.length; i++) {
            GL11.glTexCoord2d(tx[i], ty[i]);
            GL11.glVertex3d(points[i].x, points[i].y, points[i].z);

        }
        GL11.glEnd();
    }
    static double tx[] = {0.05, 0.75, 0.75, 0.05}, ty[] = {0.75, 0.75, 0.05, 0.05};

    public static void renderTPolygon(Point[] points, Point center, Point rotation, Point urotation) {
        for (int i = 0; i < points.length; i++) {
            double y = points[i].y * Math.cos(rotation.z) - points[i].x * Math.sin(rotation.z);
            double x = points[i].y * Math.sin(rotation.z) + points[i].x * Math.cos(rotation.z);
            points[i].y = y;
            points[i].x = x;
        }

        for (int i = 0; i < points.length; i++) {
            double y = points[i].y * Math.cos(rotation.x) - points[i].z * Math.sin(rotation.x);
            double z = points[i].y * Math.sin(rotation.x) + points[i].z * Math.cos(rotation.x);
            points[i].y = y;
            points[i].z = z;
        }
        for (int i = 0; i < points.length; i++) {
            double x = points[i].x * Math.cos(rotation.y - urotation.y) - points[i].z * Math.sin(rotation.y - urotation.y);
            double z = points[i].x * Math.sin(rotation.y - urotation.y) + points[i].z * Math.cos(rotation.y - urotation.y);
            points[i].x = x;
            points[i].z = z;
        }

        for (int i = 0; i < points.length; i++) {
            double y = points[i].y * Math.cos(urotation.z) - points[i].x * Math.sin(urotation.z);
            double x = points[i].y * Math.sin(urotation.z) + points[i].x * Math.cos(urotation.z);
            points[i].y = y;
            points[i].x = x;
        }

        for (int i = 0; i < points.length; i++) {
            double y = points[i].y * Math.cos(urotation.x) - points[i].z * Math.sin(urotation.x);
            double z = points[i].y * Math.sin(urotation.x) + points[i].z * Math.cos(urotation.x);
            points[i].y = y;
            points[i].z = z;
        }
        for (int i = 0; i < points.length; i++) {
            double x = points[i].x * Math.cos(urotation.y) - points[i].z * Math.sin(urotation.y);
            double z = points[i].x * Math.sin(urotation.y) + points[i].z * Math.cos(urotation.y);
            points[i].x = x;
            points[i].z = z;
        }

        for (int i = 0; i < points.length; i++) {
            points[i].x += center.x;
            points[i].y += center.y;
            points[i].z += center.z;
        }
        GL11.glBegin(GL11.GL_POLYGON);
        for (int i = 0; i < points.length; i++) {
            GL11.glTexCoord2d(tx[i], ty[i]);
            GL11.glVertex3d(points[i].x, points[i].y, points[i].z);
        }
        GL11.glEnd();
    }

    public static void renderPolygon(Point[] points, Point center, Point rotation) {

        for (int i = 0; i < points.length; i++) {
            double y = points[i].y * Math.cos(rotation.z) - points[i].x * Math.sin(rotation.z);
            double x = points[i].y * Math.sin(rotation.z) + points[i].x * Math.cos(rotation.z);
            points[i].y = y;
            points[i].x = x;
        }

        for (int i = 0; i < points.length; i++) {
            double y = points[i].y * Math.cos(rotation.x) - points[i].z * Math.sin(rotation.x);
            double z = points[i].y * Math.sin(rotation.x) + points[i].z * Math.cos(rotation.x);
            points[i].y = y;
            points[i].z = z;
        }
        for (int i = 0; i < points.length; i++) {
            double x = points[i].x * Math.cos(rotation.y) - points[i].z * Math.sin(rotation.y);
            double z = points[i].x * Math.sin(rotation.y) + points[i].z * Math.cos(rotation.y);
            points[i].x = x;
            points[i].z = z;
        }
        for (int i = 0; i < points.length; i++) {
            points[i].x += center.x;
            points[i].y += center.y;
            points[i].z += center.z;
        }
        GL11.glBegin(GL11.GL_POLYGON);
        for (int i = 0; i < points.length; i++) {
            GL11.glTexCoord2d(tx[i], ty[i]);
            GL11.glVertex3d(points[i].x, points[i].y, points[i].z);
        }
        GL11.glEnd();
    }
}
