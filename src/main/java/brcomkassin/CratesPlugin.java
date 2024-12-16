package brcomkassin;

import brcomkassin.crates.CrateRenderer;
import brcomkassin.crates.DefaultCrateRenderer;
import brcomkassin.utils.Config;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

public final class CratesPlugin extends JavaPlugin {

    @Getter
    private static CratesPlugin instance;
    private final Config config = new Config(this, "crate.yml");
    private final CrateRenderer crateRenderer = new DefaultCrateRenderer();

    @Override
    public void onLoad() {
        instance = this;
    }

    @Override
    public void onEnable() {
        config.reloadDefaultConfig();
        crateRenderer.load(config);

        getLogger().info("Plugin CRATES inicializado com sucesso!");
    }

    @Override
    public void onDisable() {
    }

}


