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
            throw new IllegalArgumentException("Erro ao salvar arquivo de configuração: " + name, e);
        }
    }

    private void reloadConfig() {
        try {
            load(file);
        } catch (Exception e) {
            throw new IllegalArgumentException("Erro ao carregar arquivo de configuração: " + name, e);
        }
    }

    public void reloadDefaultConfig() {
        plugin.saveResource(name, false);
        reloadConfig();
    }
}

