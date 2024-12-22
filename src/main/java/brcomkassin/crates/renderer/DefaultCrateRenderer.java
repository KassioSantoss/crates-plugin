package brcomkassin.crates.renderer;

import brcomkassin.crates.Crate;
import brcomkassin.crates.builder.CrateBuilder;
import brcomkassin.crates.cache.CrateCache;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.PluginLogger;

import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

public class DefaultCrateRenderer implements CrateRenderer {

    private final CrateCache crateCache;

    public DefaultCrateRenderer(CrateCache crateCache) {
        this.crateCache = crateCache;
    }

    @Override
    public void load(FileConfiguration config) {
        if (!config.contains("crates")) {
            throw new IllegalArgumentException("Configuração inválida! Seção 'crates' não encontrada.");
        }

        ConfigurationSection crates = config.getConfigurationSection("crates");

        Objects.requireNonNull(crates, "Configuração inválida! Seção 'crates' não encontrada.");

        int cratesAmount = 0;
        String PATH = "crates.";

        for (String crateID : crates.getKeys(false)) {
            ConfigurationSection section = crates.getConfigurationSection(PATH + crateID);
            final Crate crate = ConfigurationSectionAdapterForCrateRenderer.of().adapt(section);

            CrateRendererDetailsDisplayer.of(crate).display();

            crateCache.add(crate.nameSpace(), crate);
            crateCache.addKeys(crateID);
            crateCache.addCrateById(crateID, crate);
            cratesAmount++;
        }

        PluginLogger.getGlobal().info("Quantidade de caixas e keys carregadas: " + cratesAmount);
    }

    public void showRenderedCrates(String... strings) {
        for (String string : strings) {
            PluginLogger.getGlobal().info(string);
        }
    }
}
