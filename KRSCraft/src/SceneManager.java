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
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.light.PointLight;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;

/**
 *
 * @author Maximilian
 */
public class SceneManager extends AbstractAppState{
    private SimpleApplication app;
    private AssetManager      assetManager;
    public  Node              scene;
    private Node              rootNode;
    public  BulletAppState    physics;
    
    @Override
    public void initialize(AppStateManager stateManager, Application app){ 
        super.initialize(stateManager, app);
        System.out.println("SceneManger wird initialisiert...");
        this.app          = (SimpleApplication) app;
        this.assetManager = this.app.getAssetManager();
        this.rootNode     = this.app.getRootNode();
        this.physics      = new BulletAppState();
        stateManager.attach(physics);
        initRoom();
    }

    private void initRoom() {
        // Raum Model
        Spatial room = assetManager.loadModel("Models/Texturentest/Texturentest.j3o");
        RigidBodyControl roomControl = new RigidBodyControl(0f);
        room.addControl(roomControl);
        physics.getPhysicsSpace().add(roomControl);
        rootNode.attachChild(room);
        
        // PunktLampe(TEST)
        PointLight lamp = new PointLight();
        lamp.setPosition(new Vector3f(0, 0, 2));
        lamp.setColor(ColorRGBA.White);
        rootNode.addLight(lamp);  
    }
}
