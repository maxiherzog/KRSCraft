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
import com.jme3.light.AmbientLight;
import com.jme3.light.DirectionalLight;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.post.FilterPostProcessor;
import com.jme3.post.ssao.SSAOFilter;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.shadow.DirectionalLightShadowFilter;
import com.jme3.shadow.DirectionalLightShadowRenderer;

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
        initLight();
    }

    private void initRoom() {
        // Raum Model
        Spatial room = assetManager.loadModel("Models/Kantine_V2_3/Kantine_V2_3.j3o");
        RigidBodyControl roomControl = new RigidBodyControl(0f);
        room.addControl(roomControl);
        physics.getPhysicsSpace().add(roomControl);
        rootNode.attachChild(room);
        
       
    }
    
    private void initLight(){
        // Ambient light
        AmbientLight al = new AmbientLight();
        al.setColor(ColorRGBA.White.mult(0.5f));
        rootNode.addLight(al);

        // Directionnal light
        DirectionalLight sun = new DirectionalLight();
        sun.setColor(ColorRGBA.White);
        sun.setDirection(new Vector3f(-1,-1,-1));
        rootNode.addLight(sun);

        // Filter post processor
        FilterPostProcessor fpp = new FilterPostProcessor(assetManager);

        // Ambient Occlusion
        SSAOFilter ssaoFilter = new SSAOFilter(5f, 15f, 0.33f, 0.6f);
        fpp.addFilter(ssaoFilter);
        app.getViewPort().addProcessor(fpp);
        
        /*
        // Directionnal light shadows
        final int SHADOWMAP_SIZE=1024;
        DirectionalLightShadowRenderer dlsr = new DirectionalLightShadowRenderer(assetManager, SHADOWMAP_SIZE, 3);
        dlsr.setLight(sun);////
        app.getViewPort().addProcessor(dlsr);
        DirectionalLightShadowFilter dlsf = new DirectionalLightShadowFilter(assetManager, SHADOWMAP_SIZE, 3);
        dlsf.setLight(sun);
        dlsf.setEnabled(true);
        fpp.addFilter(dlsf);*/
    }
}
