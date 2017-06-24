

import com.jme3.app.SimpleApplication;
import com.jme3.system.AppSettings;


/**
 * This is the Main Class of your Game. You should only do initialization here.
 * Move your Logic into AppStates or Controls
 * @author normenhansen
 */
public class Main extends SimpleApplication {
    
    
    
    public static void main(String[] args) {
        Main app = new Main();
        System.out.println("Dennis was here");
        AppSettings cfg = new AppSettings(true);
        cfg.setFrameRate(60); // set to less than or equal screen refresh rate
        cfg.setVSync(true);   // prevents page tearing
        cfg.setFrequency(60); // set to screen refresh rate
        cfg.setResolution(1024, 768);   
        cfg.setFullscreen(false); 
        cfg.setSamples(2);    // anti-aliasing
        cfg.setTitle("KRSCraft"); // branding: window name
        //cfg.setSettingsDialogImage("Interface/splash.png"); 
        app.setShowSettings(false); // or don't display splashscreen
        app.setSettings(cfg);
        app.start();

    }

    @Override
    public void simpleInitApp() {
        System.out.println("Spiel wird initialisiert...");
        stateManager.attach(new SceneManager());
        stateManager.attach(new PlayerManager());
        stateManager.attach(new CameraManager());
    }
}
    
