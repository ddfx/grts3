package main;


import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector3f;

public class Camera {

    Vector3f vector = new Vector3f();
    Vector3f rotation = new Vector3f();
    Vector3f vectorPrevious = new Vector3f();
    boolean moveForward = false, moveBackward = false;
    boolean strafeLeft = false, strafeRight = false;
    static final float speed = 1.0f;
    Game game;

    public Camera(Game game) {
        this.game = game;
        Mouse.setGrabbed(true);
        vector.x = 1000;
        vector.z = 1050;
    }

    public void update() {
        updatePrevious();
        input();
        updateVector();
        /*
         if(rotation.x<0){
        
         vector.x -= (float) (Math.sin(-rotation.y * Math.PI / 180) * (-rotation.x/20));
         vector.z -= (float) (Math.cos(-rotation.y * Math.PI / 180) * (-rotation.x/20));
         }*/
    }

    public void updateVector() {

        if (game.mouseSwitch) {
            Mouse.setGrabbed(true);
        } else {
            Mouse.setGrabbed(false);
        }
        if (vector.z < 200) {
            vector.z = 200;
        }

        if (vector.z > 1075) {
            vector.z = 1075;
        }
        if (vector.x < 200) {
            vector.x = 200;
        }

        if (vector.x > 1075) {
            vector.x = 1075;
        }
        if (moveForward) {
            vector.x -= (float) (Math.sin(-rotation.y * Math.PI / 180) * speed);
            vector.z -= (float) (Math.cos(-rotation.y * Math.PI / 180) * speed);
        }
        if (moveBackward) {
            vector.x += (float) (Math.sin(-rotation.y * Math.PI / 180) * speed);
            vector.z += (float) (Math.cos(-rotation.y * Math.PI / 180) * speed);
        }
        if (strafeLeft) {
            vector.x += (float) (Math.sin((-rotation.y - 90) * Math.PI / 180) * speed);
            vector.z += (float) (Math.cos((-rotation.y - 90) * Math.PI / 180) * speed);
        }
        if (strafeRight) {
            vector.x += (float) (Math.sin((-rotation.y + 90) * Math.PI / 180) * speed);
            vector.z += (float) (Math.cos((-rotation.y + 90) * Math.PI / 180) * speed);
        }
    }

    public void translatePostion() {
        //This is the code that changes 3D perspective to the camera's perspective.
        GL11.glRotatef(rotation.x, 1, 0, 0);
        GL11.glRotatef(rotation.y, 0, 1, 0);
        GL11.glRotatef(rotation.z, 0, 0, 1);
        //-vector.y-2.4f means that your y is your feet, and y+2.4 is your head.
        GL11.glTranslatef(-vector.x, -vector.y - 25f, -vector.z);
    }

    public void updatePrevious() {
        //Update last position (for collisions (later))
        vectorPrevious.x = vector.x;
        vectorPrevious.y = vector.y;
        vectorPrevious.z = vector.z;
    }

    public void input() {
        //Keyboard Input for Movement
        moveForward = game.keys[Keyboard.KEY_W] | game.keys[Keyboard.KEY_UP];
        moveBackward = game.keys[Keyboard.KEY_S] | game.keys[Keyboard.KEY_DOWN];
        strafeLeft = game.keys[Keyboard.KEY_A] | game.keys[Keyboard.KEY_LEFT];
        strafeRight = game.keys[Keyboard.KEY_D] | game.keys[Keyboard.KEY_RIGHT];

        //Mouse Input for looking around...
        if (Mouse.isGrabbed()) {
            float mouseDX = Mouse.getDX() * 0.8f * 0.16f;
            float mouseDY = Mouse.getDY() * 0.8f * 0.16f;
            if (rotation.y + mouseDX >= 360) {
                rotation.y = rotation.y + mouseDX - 360;
            } else if (rotation.y + mouseDX < 0) {
                rotation.y = 360 - rotation.y + mouseDX;
            } else {
                rotation.y += mouseDX;
            }
            if (rotation.x - mouseDY >= -89 && rotation.x - mouseDY <= 89) {
                rotation.x += -mouseDY;
            } else if (rotation.x - mouseDY < -89) {
                rotation.x = -89;
            } else if (rotation.x - mouseDY > 89) {
                rotation.x = 89;
            }
        }
    }
}
