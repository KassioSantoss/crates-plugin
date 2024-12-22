package brcomkassin.crates.renderer;

import brcomkassin.crates.Crate;
import brcomkassin.crates.builder.CrateBuilder;
import brcomkassin.crates.cache.CrateCache;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.PluginLogger;

import java.util.List;
import java.util.logging.Logger;

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

        if (crates == null) {
            throw new IllegalArgumentException("Configuração inválida! Seção 'crates' não encontrada."); // depois arrumar exceçoes
        }

        int cratesAmount = 0;
        String PATH = "crates.";

        for (String crateID : crates.getKeys(false)) {
            String namespace = config.getString(PATH + crateID + ".namespace", crateID);
            String crateDisplayName = config.getString(PATH + crateID + ".display_name", "Caixa Sem Nome");
            int crateCustomModelData = config.getInt(PATH + crateID + ".crate_item_model.custom_model_data", 0);
            String keyDisplayName = config.getString(PATH + crateID + ".key_item.display_name", "Chave Sem Nome");
            int keyCustomModelData = config.getInt(PATH + crateID + ".key_item.custom_model_data", 0);
            String baseEntityModel = config.getString(PATH + crateID + ".base_entity_model.model_id", "crate_example");
            String animation = config.getString(PATH + crateID + ".base_entity_model.animation", "open");
            List<String> rewards = config.getStringList(PATH + crateID + ".rewards");

            PluginLogger.getGlobal().info("\u001B[32m============================================================================================================");
            PluginLogger.getGlobal().info("\u001B[36mID: " + crateID + "\u001B[37m -> namespace: " + namespace);
            PluginLogger.getGlobal().info("\u001B[36mID: " + crateID + "\u001B[37m -> crateDisplayName: " + crateDisplayName);
            PluginLogger.getGlobal().info("\u001B[36mID: " + crateID + "\u001B[37m -> crateCustomModelData: " + crateCustomModelData);
            PluginLogger.getGlobal().info("\u001B[36mID: " + crateID + "\u001B[37m -> keyDisplayName: " + keyDisplayName);
            PluginLogger.getGlobal().info("\u001B[36mID: " + crateID + "\u001B[37m -> keyCustomModelData: " + keyCustomModelData);
            PluginLogger.getGlobal().info("\u001B[36mID: " + crateID + "\u001B[37m -> baseEntityModel: " + baseEntityModel);
            PluginLogger.getGlobal().info("\u001B[36mID: " + crateID + "\u001B[37m -> animation: " + animation);
            PluginLogger.getGlobal().info("\u001B[36mID: " + crateID + "\u001B[37m -> rewards: " + rewards);
            PluginLogger.getGlobal().info("\u001B[32m============================================================================================================\u001B[0m");

            Crate crate = CrateBuilder.builder()
                    .setCrateKey(keyDisplayName, keyCustomModelData, namespace)
                    .setId(crateID)
                    .setNameSpace(namespace)
                    .setCrateDisplayName(crateDisplayName)
                    .setCrateCustomModelData(crateCustomModelData)
                    .setBaseEntityModel(baseEntityModel)
                    .setAnimation(animation)
                    .build();

            crateCache.add(namespace, crate);
            crateCache.addKeys(crateID);
            crateCache.addCrateById(crateID, crate);
            Logger.getGlobal().info("cache: " + crateCache.getCrateById(crateID));
            cratesAmount++;
        }
        PluginLogger.getGlobal().info("Quantidade de caixas e keys carregadas: " + cratesAmount);
    }
}
