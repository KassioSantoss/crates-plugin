package brcomkassin;

import brcomkassin.commands.CrateCommand;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

public final class CratesPlugin extends JavaPlugin {

    @Getter
    private static CratesPlugin instance;

    @Override
    public void onLoad() {
        instance = this;
    }

    @Override
    public void onEnable() {
        getCommand("item").setExecutor(new CrateCommand());
        getLogger().info("Plugin CRATES inicializado com sucesso!");
    }

    @Override
    public void onDisable() {

    }

}


