package brcomkassin;

import brcomkassin.crate.renderer.RendererCrate;
import brcomkassin.crate.renderer.RendererCrateService;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

public final class CratesPlugin extends JavaPlugin {
    @Getter
    private static CratesPlugin instance;
    private final RendererCrateService rendererCrate = new RendererCrate();

    @Override
    public void onLoad() {
        instance = this;
    }

    @Override
    public void onEnable() {
        rendererCrate.renderer();
        getLogger().info("Plugin CRATES inicializado com sucesso!");
    }

    @Override
    public void onDisable() {

    }

}


