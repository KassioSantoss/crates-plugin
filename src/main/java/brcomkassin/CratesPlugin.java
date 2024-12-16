package brcomkassin;

import brcomkassin.crates.CrateRegistryService;
import brcomkassin.crates.DefaultCrateRegistryService;
import brcomkassin.crates.renderer.CrateRenderer;
import brcomkassin.crates.renderer.DefaultCrateRenderer;
import brcomkassin.crates.cache.CrateCache;
import brcomkassin.crates.cache.CrateCacheService;
import brcomkassin.utils.Config;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

public final class CratesPlugin extends JavaPlugin {

    @Getter
    private static CratesPlugin instance;
    private final Config config = new Config(this, "crate.yml");
    private final CrateRegistryService crateRegistryService = new DefaultCrateRegistryService(this);

    @Override
    public void onLoad() {
        instance = this;
    }

    @Override
    public void onEnable() {
        config.reloadDefaultConfig();
        crateRegistryService.load(config);
        getLogger().info("Plugin CRATES inicializado com sucesso!");
    }

    @Override
    public void onDisable() {
        config.saveConfig();
    }

}


