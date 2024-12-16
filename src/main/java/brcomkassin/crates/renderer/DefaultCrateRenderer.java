package brcomkassin.crates.renderer;

import brcomkassin.crates.Crate;
import brcomkassin.crates.builder.CrateBuilder;
import brcomkassin.crates.cache.CrateCache;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.PluginLogger;

import java.util.List;

public class DefaultCrateRenderer implements CrateRenderer {

    private final CrateCache cache;

    public DefaultCrateRenderer(CrateCache cache) {
        this.cache = cache;
    }

    @Override
    public void load(FileConfiguration config) {
        if (!config.contains("crates")) {
            throw new IllegalArgumentException("Configuração inválida! Seção 'crates' não encontrada.");
        }
        ConfigurationSection crates = config.getConfigurationSection("crates");

        if (crates == null) {
            throw new IllegalArgumentException("Configuração inválida! Seção 'crates' não encontrada."); // depois arrumar exceçoes
        }

        int cratesAmount = 0;
        String PATH = "crates.";

        for (String crateID : crates.getKeys(false)) {
            String keyAndCrateID = config.getString(PATH + crateID + ".key_crate_id", "id_default");
            String crateDisplayName = config.getString(PATH + crateID + ".display_name", "Caixa Sem Nome");
            int crateCustomModelData = config.getInt(PATH + crateID + ".item_model.custom_model_data", 0);
            String keyDisplayName = config.getString(PATH + crateID + ".key_item.display_name", "Chave Sem Nome");
            int keyCustomModelData = config.getInt(PATH + crateID + ".key_item.custom_model_data", 0);
            String entityModel = config.getString(PATH + crateID + ".entity_model.model_id", "default_model");
            String animation = config.getString(PATH + crateID + ".entity_model.animation", "default_animation");
            List<String> rewards = config.getStringList(PATH + crateID + ".rewards");

            PluginLogger.getGlobal().info("=========================================");
            PluginLogger.getGlobal().info("ID: " + crateID + " -> keyAndCrateID: " + keyAndCrateID);
            PluginLogger.getGlobal().info("ID: " + crateID + " -> crateDisplayName: " +crateDisplayName);
            PluginLogger.getGlobal().info("ID: " + crateID + " -> crateCustomModelData: " +crateCustomModelData);
            PluginLogger.getGlobal().info("ID: " + crateID + " -> keyDisplayName: " +keyDisplayName);
            PluginLogger.getGlobal().info("ID: " + crateID + " -> keyCustomModelData: " +keyCustomModelData);
            PluginLogger.getGlobal().info("ID: " + crateID + " -> entityModel: " +entityModel);
            PluginLogger.getGlobal().info("ID: " + crateID + " -> animation: " +animation);
            PluginLogger.getGlobal().info("ID: " + crateID + " -> rewards: " +rewards);
            PluginLogger.getGlobal().info("=========================================");

            Crate crate = CrateBuilder.get()
                    .setId(keyAndCrateID)
                    .setCrateDisplayName(crateDisplayName)
                    .setKeyDisplayName(keyDisplayName)
                    .setCrateCustomModelData(crateCustomModelData)
                    .setKeyCustomModelData(keyCustomModelData)
                    .setEntityModel(entityModel)
                    .setAnimation(animation)
                    .build();
            cache.add(keyAndCrateID, crate);
            cache.addKeys(keyAndCrateID);
            cratesAmount++;
        }
        PluginLogger.getGlobal().info("Quantidade de caixas e keys carregadas: " + cratesAmount);
    }
}
