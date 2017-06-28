/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.asset.AssetManager;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.control.BetterCharacterControl;
import com.jme3.input.InputManager;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;


/**
 *
 * @author Maximilian
 */
public class PlayerManager extends AbstractAppState implements ActionListener{
    private SimpleApplication app;
    private AppStateManager   stateManager;
    private AssetManager      assetManager;
    private BulletAppState    physics;
    public  Player            player;
    private InputManager inputManager;
    
    private final static float WALK_SPEED_FORWARDS  = 4F;
    private final static float WALK_SPEED_SIDEWARDS = 3F;  
    
    private Vector3f walkDirection = new Vector3f();
    private Vector3f camDir        = new Vector3f();
    private Vector3f camLeft       = new Vector3f();
    public boolean left  = false;
    public boolean right = false;
    public boolean up    = false;
    public boolean down  = false;
    public boolean space = false;
  
    @Override
    public void initialize(AppStateManager stateManager, Application app){
        super.initialize(stateManager, app);
        System.out.println("PlayerManager wird initialisiert...");
        this.app          = (SimpleApplication) app;
        this.stateManager = this.app.getStateManager();
        this.assetManager = this.app.getAssetManager();
        this.inputManager = this.app.getInputManager();
        this.physics      = this.stateManager.getState(SceneManager.class).physics;
        initPlayer();
        initKeys();
    }
  
    private void initKeys(){
        inputManager.addMapping("Up", new KeyTrigger(KeyInput.KEY_W));
        inputManager.addMapping("Down", new KeyTrigger(KeyInput.KEY_S));
        inputManager.addMapping("Left", new KeyTrigger(KeyInput.KEY_A));
        inputManager.addMapping("Right", new KeyTrigger(KeyInput.KEY_D));
        inputManager.addMapping("Space", new KeyTrigger(KeyInput.KEY_SPACE));
        inputManager.addListener(this, "Up");
        inputManager.addListener(this, "Down");
        inputManager.addListener(this, "Left");
        inputManager.addListener(this, "Right");
        inputManager.addListener(this, "Space");
    }
    
    private void initPlayer(){
        player               = new Player();
        player.isDead        = false;
        player.model         = new Node();
        player.playerControl = new BetterCharacterControl(0.2f, 1f, 10f);
        //player.playerControl.setGravity(new Vector3f(0, -9.81f, 0));
        player.playerControl.setJumpForce(new Vector3f(0, 20F, 0));
        player.attachChild(player.model);
        player.addControl(player.playerControl);
        this.app.getRootNode().attachChild(player);
        player.model.setLocalTranslation(0f, 1f, 0f);
        physics.getPhysicsSpace().add(player.playerControl);
        player.playerControl.warp(new Vector3f(0f, 0f, 0f));
    }

    @Override
    public void onAction(String name, boolean isPressed, float tpf) {
        switch (name) {
            case "Up":
                up    = isPressed;
                break;
            case "Down":
                down  = isPressed;
                break;
            case "Left":
                left  = isPressed;
                break;
            case "Right":
                right = isPressed;
                break;
            case "Space":
                space = isPressed;
                break;
            default:
                break;
        }
        
        
    }
    
    /**
     * Spielerbewegung abhängig von Richtungsbooleans
     * @param tpf 
     */
    @Override
    public void update(float tpf){
        camDir.set(this.app.getCamera().getDirection()).multLocal(
                WALK_SPEED_FORWARDS,
                0.0f,
                WALK_SPEED_SIDEWARDS);
        camLeft.set(this.app.getCamera().getLeft()).multLocal(WALK_SPEED_SIDEWARDS);
        walkDirection.set(0, 0, 0);
        
        if (left)  walkDirection.addLocal(camLeft);
        if (right) walkDirection.addLocal(camLeft.negate());   
        if (up)    walkDirection.addLocal(camDir);
        if (down)  walkDirection.addLocal(camDir.negate());
        if (space) player.playerControl.jump();
        
       player.playerControl.setWalkDirection(walkDirection.multLocal(1));
       player.playerControl.setViewDirection(camDir);

    }
  
}
  
