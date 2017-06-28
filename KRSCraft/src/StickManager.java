
import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.asset.AssetManager;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.math.Vector3f;

/**
 *
 * @author Maximilian
 */
public class StickManager extends AbstractAppState{
    private SimpleApplication app;
    private AppStateManager   stateManager;
    private AssetManager      assetManager;
    private BulletAppState    physics;
    public  Item              stick;
    
    
    @Override
    public void initialize(AppStateManager stateManager, Application app){
        super.initialize(stateManager, app);
        System.out.println("PlayerManager wird initialisiert...");
        this.app          = (SimpleApplication) app;
        this.stateManager = this.app.getStateManager();
        this.assetManager = this.app.getAssetManager();
        this.physics      = this.stateManager.getState(SceneManager.class).physics;
        
        initStick();
    }
    
    private void initStick(){
        // Model laden!
        stick.name = "Stock";
        //stick.model = assetManager.loadAsset("LOL");
        stick.itemControl = new RigidBodyControl(20F);
        stick.attachChild(stick.model);
        stick.addControl(stick.itemControl);
        physics.getPhysicsSpace().add(stick.itemControl);
    }
}
