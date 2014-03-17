/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package game;

import java.util.Random;
import org.lwjgl.opengl.GL11;
import static org.lwjgl.opengl.GL11.GL_ALPHA_TEST;
import static org.lwjgl.opengl.GL11.GL_BLEND;
import static org.lwjgl.opengl.GL11.GL_ONE_MINUS_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.glBlendFunc;
import static org.lwjgl.opengl.GL11.glEnable;

/**
 *
 * @author yew_mentzaki
 */
public class Fog {
    Random r = new Random();
    float x, y, z;
    float spdW = 1;
    public int life = 500 - r.nextInt(250);
    
    float s = r.nextFloat()*4;
    public Fog(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
     
    }
    public void render(World w){
        life--;
        if(life>0){
            
        
    x-=r.nextFloat()*Math.cos(w.wind);
    y+=spdW;
    if(spdW>0)spdW-=0.01f;
    y+=r.nextFloat()*0.1f;
    if(life<50&&s>0)s-=0.05f;
    z-=r.nextFloat()*Math.sin(w.wind);
    GL11.glColor4f(0.3f, 0.3f, 0.3f, 0.5f);
    GL11.glBegin(GL11.GL_QUADS);
    GL11.glVertex3f(x+s, y, z);
    
    GL11.glVertex3f(x, y+s, z);
    
    GL11.glVertex3f(x-s, y, z);
    
    GL11.glVertex3f(x, y-s, z);
    
    
    
    
    GL11.glVertex3f(x+s, y, z);
    
    GL11.glVertex3f(x, y, z+s);
    
    GL11.glVertex3f(x-s, y, z);
    
    GL11.glVertex3f(x, y, z-s);
    
    
    
    GL11.glVertex3f(x, y+s, z);
    
    GL11.glVertex3f(x, y, z+s);
    
    GL11.glVertex3f(x, y-s, z);
    
    GL11.glVertex3f(x, y, z-s);
    
    
    
    GL11.glEnd();
        }
    }
}
