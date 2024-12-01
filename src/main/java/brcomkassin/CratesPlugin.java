package brcomkassin;

import brcomkassin.crates.register.RegistriesManager;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

public final class CratesPlugin extends JavaPlugin {
    @Getter
    private static CratesPlugin instance;
    private final RegistriesManager registriesManager = new RegistriesManager();

    @Override
    public void onLoad() {
        instance = this;
    }

    @Override
    public void onEnable() {
        registriesManager.registerAll();
        getLogger().info("Plugin CRATES inicializado com sucesso!");
    }

    @Override
    public void onDisable() {

    }

}


