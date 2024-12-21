package brcomkassin.crates.builder;

import brcomkassin.crates.Crate;
import brcomkassin.crates.key.CrateKey;
import brcomkassin.crates.CrateMaterial;
import brcomkassin.utils.ItemBuilder;
import org.bukkit.inventory.ItemStack;
import java.util.ArrayList;
import java.util.List;

public class CrateBuilder {

    private CrateKey crateKey = null;
    private String namespace;
    private String crateDisplayName = "Default Crate Name";
    private int crateCustomModelData = 0;
    private String baseEntityModel = "crate_example";
    private String animation = "open";
    private final List<String> rewards = new ArrayList<>();

    public static CrateBuilder builder() {
        return new CrateBuilder();
    }

    public CrateBuilder setId(String namespace) {
        this.namespace = namespace;
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

    public CrateBuilder setBaseEntityModel(String baseEntityModel) {
        this.baseEntityModel = baseEntityModel;
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

    public CrateBuilder setCrateKey(String keyDisplayName, int keyCustomModelData, String namespace) {
        ItemStack keyItem = ItemBuilder.of(CrateMaterial.DEFAULT_KEY_MATERIAL.getMaterial())
                .setName(keyDisplayName)
                .setCustomModelData(keyCustomModelData)
                .setNameSpacedKey(namespace)
                .build();
        this.crateKey = new CrateKey(keyDisplayName, keyCustomModelData, namespace, keyItem);
        return this;
    }

    public Crate build() {
        if (namespace == null || namespace.isEmpty()) {
            throw new IllegalStateException("O NameSpacedKey da caixa não pode ser nulo ou vazio.");
        }
        if (crateKey == null) {
            throw new IllegalStateException("A key da caixa não pode ser nulo ou vazio.");
        }

        ItemStack crateItem = ItemBuilder.of(CrateMaterial.DEFAULT_CRATE_MATERIAL.getMaterial())
                .setName(crateDisplayName)
                .setCustomModelData(crateCustomModelData)
                .setNameSpacedKey(namespace)
                .build();

        return new Crate(
                crateKey,
                namespace,
                crateDisplayName,
                crateCustomModelData,
                baseEntityModel,
                animation,
                crateItem,
                new ArrayList<>(rewards)
        );
    }
}

