package brcomkassin.crates.builder;

import brcomkassin.crates.Crate;
import brcomkassin.crates.CrateMaterial;
import brcomkassin.utils.ItemBuilder;
import org.bukkit.inventory.ItemStack;
import java.util.ArrayList;
import java.util.List;

public class CrateBuilder {

    private String id;
    private String crateDisplayName = "Default Crate Name";
    private int crateCustomModelData = 0;
    private String keyDisplayName = "Default Key Name";
    private int keyCustomModelData = 0;
    private String entityModel = "default_entity_model";
    private String animation = "default_animation";
    private final List<String> rewards = new ArrayList<>();

    public static CrateBuilder builder() {
        return new CrateBuilder();
    }

    public CrateBuilder setId(String id) {
        this.id = id;
        return this;
    }

    public CrateBuilder setCrateDisplayName(String crateDisplayName) {
        this.crateDisplayName = crateDisplayName;
        return this;
    }

    public CrateBuilder setCrateCustomModelData(int crateCustomModelData) {
        this.crateCustomModelData = crateCustomModelData;
        return this;
    }

    public CrateBuilder setKeyDisplayName(String keyDisplayName) {
        this.keyDisplayName = keyDisplayName;
        return this;
    }

    public CrateBuilder setKeyCustomModelData(int keyCustomModelData) {
        this.keyCustomModelData = keyCustomModelData;
        return this;
    }

    public CrateBuilder setEntityModel(String entityModel) {
        this.entityModel = entityModel;
        return this;
    }

    public CrateBuilder setAnimation(String animation) {
        this.animation = animation;
        return this;
    }

    public CrateBuilder addReward(String reward) {
        this.rewards.add(reward);
        return this;
    }

    public Crate build() {
        if (id == null || id.isEmpty()) {
            throw new IllegalStateException("O ID da caixa não pode ser nulo ou vazio.");
        }

        ItemStack crateItem = ItemBuilder.of(CrateMaterial.DEFAULT_CRATE_MATERIAL.getMaterial())
                .setName(crateDisplayName)
                .setCustomModelData(crateCustomModelData)
                .setItemMetaData(id)
                .build();
        ItemStack keyItem = ItemBuilder.of(CrateMaterial.DEFAULT_KEY_MATERIAL.getMaterial())
                .setName(keyDisplayName)
                .setCustomModelData(keyCustomModelData)
                .setItemMetaData(id)
                .build();

        return new Crate(
                id,
                crateDisplayName,
                crateCustomModelData,
                keyDisplayName,
                keyCustomModelData,
                entityModel,
                animation,
                new ArrayList<>(rewards),
                keyItem,
                crateItem
        );
    }
}

