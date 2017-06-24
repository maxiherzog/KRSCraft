import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.input.ChaseCamera;

/**
 *
 * @author Maximilian
 */
public class CameraManager extends AbstractAppState{
    private SimpleApplication app;
    private AppStateManager   stateManager;
    private Player            player;
    public  ChaseCamera       cam;
  
    @Override
    public void initialize(AppStateManager stateManager, Application app){
        super.initialize(stateManager, app);
        System.out.println("CameraManger wird initialisiert...");
        this.app          = (SimpleApplication) app;
        this.stateManager = this.app.getStateManager();
        this.player       = this.stateManager.getState(PlayerManager.class).player;
        initCamera();
    }
  
    /** Erstellt eine 
     * Kamera(ChaseCamera folgt dem Spielermodel player.model)
     */
    public void initCamera(){
        cam = new ChaseCamera(this.app.getCamera(), player.model, this.app.getInputManager());
        cam.setMinDistance(1);
        cam.setMaxDistance(1);
        cam.setInvertVerticalAxis(true);
    }
}
