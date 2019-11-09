package digital.newshoes.metropolis.desktop;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import digital.newshoes.metropolis.Metropolis;

import static com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration.HdpiMode.Logical;

public class DesktopLauncher {
    public static void main(String[] arg) {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        config.setTitle("Metropolis");
        config.setWindowedMode(1920,1080);
        config.setHdpiMode(Logical);
        new Lwjgl3Application(new Metropolis(), config);
    }
}
