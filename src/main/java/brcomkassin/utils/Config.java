package brcomkassin.utils;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public final class Config extends YamlConfiguration {

    private final File file;
    private final String name;
    private final JavaPlugin plugin;

    public Config(JavaPlugin plugin, String name) {
        this.plugin = plugin;
        this.name = name;
        file = new File(plugin.getDataFolder(), name);
    }

    public void saveConfig() {
        try {
            save(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void reloadConfig() {
        try {
            load(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void reloadDefaultConfig() {
        plugin.saveResource(name, false);
        reloadConfig();
    }
}

