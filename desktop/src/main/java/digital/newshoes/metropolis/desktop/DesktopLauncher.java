package digital.newshoes.metropolis.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import digital.newshoes.metropolis.Metropolis;

public class DesktopLauncher {
    public static void main(String[] arg) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.width = 1920;
        config.height = 1080;
        config.useHDPI = true;
        new LwjglApplication(new Metropolis(), config);
    }
}