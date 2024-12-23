package brcomkassin.crates.renderer;

import brcomkassin.crates.Crate;
import brcomkassin.crates.builder.CrateBuilder;
import lombok.Data;
import org.bukkit.configuration.ConfigurationSection;

@Data(staticConstructor = "of") public class ConfigurationSectionAdapterForCrateRenderer {

    public Crate adapt(ConfigurationSection section) {
        String id = section.getName();
        String namespace = section.getString(".namespace", id);
        String crateDisplayName = section.getString(".display_name", "Caixa Sem Nome");
        int crateCustomModelData = section.getInt("crate_item_model.custom_model_data", 0);
        String keyDisplayName = section.getString("key_item.display_name", "Chave Sem Nome");
        int keyCustomModelData = section.getInt("key_item.custom_model_data", 0);
        String baseEntityModel = section.getString("base_entity_model.model_id", "crate_example");
        String animation = section.getString("base_entity_model.animation", "open");

        return CrateBuilder.builder()
                .setCrateKey(keyDisplayName, keyCustomModelData, namespace)
                .setId(id)
                .setNameSpace(namespace)
                .setCrateDisplayName(crateDisplayName)
                .setCrateCustomModelData(crateCustomModelData)
                .setBaseEntityModel(baseEntityModel)
                .setAnimation(animation)
                .build();
    }
}
