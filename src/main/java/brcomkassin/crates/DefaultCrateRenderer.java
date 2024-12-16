package brcomkassin.crates;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import java.util.List;

public class DefaultCrateRenderer implements CrateRenderer {

    @Override
    public void load(FileConfiguration config) {
        if (!config.contains("crates")) {
            throw new IllegalArgumentException("Configuração inválida! Seção 'crates' não encontrada.");
        }
        ConfigurationSection crates = config.getConfigurationSection("crates");

        if (crates == null) {
            throw new IllegalArgumentException("Configuração inválida! Seção 'crates' não encontrada."); // depois arrumar exceçoes
        }

        for (String crateID : crates.getKeys(false)) {
            String PATH = "crates";
            String keyAndCrateID = config.getString(PATH + crateID + ".key_crate_id");
            String crateDisplayName = config.getString(PATH + crateID + ".display_name", "Caixa Sem Nome");
            int crateCustomModelData = config.getInt(PATH + crateID + ".item_model.custom_model_data", 0);

            String keyDisplayName = config.getString(PATH + crateID + ".key_item.display_name", "Chave Sem Nome");
            int keyCustomModelData = config.getInt(PATH + crateID + ".key_item.custom_model_data", 0);

            String entityModel = config.getString(PATH + crateID + ".entity_model.model_id", "default_model");
            String animation = config.getString(PATH + crateID + ".entity_model.animation", "default_animation");

            List<String> rewards = config.getStringList(PATH + crateID + ".rewards");

            Crate crate = CrateBuilder.get()
                    .setId(keyAndCrateID)
                    .setCrateDisplayName(crateDisplayName)
                    .setKeyDisplayName(keyDisplayName)
                    .setCrateCustomModelData(crateCustomModelData)
                    .setKeyCustomModelData(keyCustomModelData)
                    .setEntityModel(entityModel)
                    .setAnimation(animation)
                    .build();
        }
    }
}
