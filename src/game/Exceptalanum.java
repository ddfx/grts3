/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package game;

import java.util.Random;
import org.lwjgl.opengl.GL11;
import static org.lwjgl.opengl.GL11.*;

/**
 *
 * @author yew_mentzaki
 */
public class Exceptalanum {
    public float x, y = 1, z;
    private float s;
    public Exceptalanum(){
    Random r = new Random();
    x = r.nextInt(2000);
    z = r.nextInt(2000);
            s = r.nextFloat()*2;
    }

    public Exceptalanum(float x, float z) {
        this.x = x;
        this.z = z;
        Random r = new Random();
        s = r.nextFloat()*2;
    }
    public int dist(float cx, float cz){
    return y>0?((int)Math.sqrt(Math.pow(cx-x,2)+Math.pow(cz-z,2))):5000;
    }
    public int collect(float cx, float cz){
    if(Math.sqrt(Math.pow(cx-x,2)+Math.pow(cz-z,2))<5&y>=0){
        y = -10;
        return 1;
    }
    return 0;
    }
        public int collect(float cx, float cz, int r){
    if(Math.sqrt(Math.pow(cx-x,2)+Math.pow(cz-z,2))<r&y>=0){
        y = -10;
        return 1;
    }
    return 0;
    }
    public void render(){
        if(y<1)y+=0.01f;
        glColor3f(0.5f, 1f, 0.8f);
        glBegin(GL_POLYGON);
        
                    
                    GL11.glTexCoord2f(0.1f, 0.1f);
        glVertex3f(x-1, y, z);
        
                    
                    GL11.glTexCoord2f(0.5f, 0.1f);
        glVertex3f(x+1, y, z);
        
                    
                    GL11.glTexCoord2f(0.1f, 0.5f);
        glVertex3f(x, y+2+s, z);
        glEnd();
        glBegin(GL_POLYGON);
        
                    
                    GL11.glTexCoord2f(0.1f, 0.1f);
        glVertex3f(x, y, z-1);
        
                    
                    GL11.glTexCoord2f(0.5f, 0.1f);
        glVertex3f(x, y, z+1);
        
                    
                    GL11.glTexCoord2f(0.1f, 0.5f);
        glVertex3f(x, y+2+s, z);
        glEnd();
    }
}
