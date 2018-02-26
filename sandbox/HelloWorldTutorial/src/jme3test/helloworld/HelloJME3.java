package jme3test.helloworld;

import com.jme3.app.SimpleApplication;
import static com.jme3.audio.AudioData.DataType.Buffer;
import com.jme3.audio.AudioNode;
import static com.jme3.audio.AudioSource.Status.Playing;
import com.jme3.material.Material;
import com.jme3.material.RenderState.BlendMode;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.texture.Texture2D;
import com.jme3.ui.Picture;
import org.lwjgl.input.Mouse;

/**
 * Sample 1 - how to get started with the most simple JME 3 application. Display
 * a blue 3D cube and view from all sides by moving the mouse and pressing the
 * WASD keys.
 */
public class HelloJME3 extends SimpleApplication {
    private Spatial player;
    private Spatial ball;
    AudioNode boing = null;

    public static void main(String[] args) {
        HelloJME3 app = new HelloJME3();
        app.start(); // start the game
    }

    @Override
    public void simpleInitApp() {
        cam.setParallelProjection(true);
        getFlyByCamera().setEnabled(false);
        setDisplayStatView(false);
        cam.setLocation(new Vector3f(0, 0, 0));
        
        //load boing sound effect
        boing = new AudioNode(assetManager, "Sounds/boing.wav", Buffer);
        boing.setPositional(false);
        
        /*/        setup the player
        player = getSpatial("player", new Vector2f(50,50));
        player.setUserData("alive",true);
        player.setUserData("radius",25f);
        player.setUserData("mass",10f);
        player.setUserData("bounce",50f);
        player.move(settings.getWidth()/2, settings.getHeight()/3, 0);
        guiNode.attachChild(player);
        player.addControl(new PlayerControl());*/
        
        //        setup the ball
        ball = getSpatial("ball", new Vector2f(100,100));
        ball.setUserData("radius", 50f);
        ball.setUserData("mass",20f);
        ball.setUserData("bounce",200f);
        ball.setUserData("gravity", 98.1f/1);
        ball.move(settings.getWidth()/2, settings.getHeight()/2, 0);
        ball.addControl(new BallControl());
        guiNode.attachChild(ball);
    }

    private Spatial getSpatial(String name, Vector2f size) {
        Node node = new Node(name);
//        load picture
        Picture pic = new Picture(name);
        Texture2D tex = (Texture2D) assetManager.loadTexture("Textures/" + name + ".png");
        pic.setTexture(assetManager, tex, true);

//        adjust picture
        float width = tex.getImage().getWidth();
        float height = tex.getImage().getHeight();
        if (size != null){
            width = size.x;
            height= size.y;
        }
        pic.setWidth(width);
        pic.setHeight(height);
        pic.move(-width / 2f, -height / 2f, 0);

//        add a material to the picture
        Material picMat = new Material(assetManager, "Common/MatDefs/Gui/Gui.j3md");
        picMat.getAdditionalRenderState().setBlendMode(BlendMode.AlphaAdditive);
        node.setMaterial(picMat);

//        set the radius of the spatial
//        (using width only as a simple approximation)
        node.setUserData("radius", width / 2);

//        attach the picture to the node and return it
        node.attachChild(pic);
        return node;
    }
    int temp=0;
    @Override
    public void simpleUpdate(float tpf){
        /*//Handle collisions
        PlayerControl playercontrol = player.getControl(PlayerControl.class);
        BallControl ballcontrol = ball.getControl(BallControl.class);
        Vector2f dist = playercontrol.getPosition().subtract(ballcontrol.getPosition());
        float minDist = playercontrol.getRadius() + ballcontrol.getRadius();
        float actDist = dist.length();
        if (actDist < minDist){
            //They're colliding
            
            Vector2f playerspeed = dist.normalize().mult((float)Math.cos(playercontrol.getSpeed().angleBetween(dist)));
            Vector2f ballspeed = dist.normalize().mult((float)Math.cos(ballcontrol.getSpeed().angleBetween(dist)));
            float speeddiff = playerspeed.subtract(ballspeed).length();
            float playerdeflect = speeddiff*(ballcontrol.getMass()/(ballcontrol.getMass()+playercontrol.getMass()))+playercontrol.getBounce();
            float balldeflect = speeddiff*(playercontrol.getMass()/(ballcontrol.getMass()+playercontrol.getMass()))+ballcontrol.getBounce();
            
            Vector2f playerDisplacement = dist.normalize().mult((actDist-minDist)*(ballcontrol.getMass()/(playercontrol.getMass()+ballcontrol.getMass()))).negate();
            Vector2f ballDisplacement = dist.normalize().mult((actDist-minDist)*(playercontrol.getMass()/(playercontrol.getMass()+ballcontrol.getMass())));
            
            System.out.println("collision! - " + temp);temp++;
            playercontrol.onCollide(dist.normalize(), playerDisplacement, playerdeflect);
            ballcontrol.onCollide(dist.negate().normalize(), ballDisplacement, balldeflect);
            
            if (boing.getStatus() != Playing){
                boing.play();
            }
        }*/
        
        BallControl ballcontrol = ball.getControl(BallControl.class);
        Vector2f mousepos = new Vector2f(Mouse.getX(), Mouse.getY());
        Vector2f ballpos = ballcontrol.getPosition();
        Vector2f diff = mousepos.subtract(ballpos);
        Vector2f speed = ballcontrol.getSpeed().mult((float)Math.cos(ballcontrol.getSpeed().normalize().smallestAngleBetween(diff.normalize())));
        
        if ( ballpos.subtract(mousepos).length() < ballcontrol.getRadius() ){
            ballcontrol.onCollide(diff.negate().normalize(), diff.normalize().mult(diff.length()-ballcontrol.getRadius()), speed.length()*1.5f);
        }
        
    }
}
