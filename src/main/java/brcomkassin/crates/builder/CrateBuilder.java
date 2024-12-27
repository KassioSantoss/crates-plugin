package brcomkassin.crates.builder;

import brcomkassin.crates.Crate;
import brcomkassin.crates.key.CrateKey;
import brcomkassin.crates.CrateMaterial;
import brcomkassin.crates.rewards.Reward;
import brcomkassin.utils.ItemBuilder;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;

public class CrateBuilder {

    private CrateKey crateKey;
    private String id;
    private String namespace;
    private String crateDisplayName;
    private int crateCustomModelData;
    private String baseEntityModel;
    private String animation;
    private final List<Reward> rewardList;

    public CrateBuilder() {
        this.crateKey = null;
        this.crateDisplayName = "Default Crate Name";
        this.crateCustomModelData = 0;
        this.baseEntityModel = "crate_example";
        this.animation = "open";
        this.rewardList = new ArrayList<>();
    }

    public static CrateBuilder builder() {
        return new CrateBuilder();
    }

    public CrateBuilder setNameSpace(String namespace) {
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

    public CrateBuilder addRewards(List<Reward> rewards) {
        this.rewardList.addAll(rewards);
        return this;
    }

    public CrateBuilder setCrateKey(String keyDisplayName, int keyCustomModelData, String namespace) {
        ItemStack keyItem = ItemBuilder.of(CrateMaterial.DEFAULT_KEY_MATERIAL.getMaterial()).setName(keyDisplayName).setCustomModelData(keyCustomModelData).setNameSpacedKey(namespace).build();
        this.crateKey = CrateKey.of(keyDisplayName, keyCustomModelData, namespace, keyItem);
        return this;
    }

    public CrateBuilder setId(String id) {
        this.id = id;
        return this;
    }

    public Crate build() {
        if (namespace == null || namespace.isEmpty())
            throw new IllegalStateException("O NameSpacedKey da caixa não pode ser nulo ou vazio.");

        Objects.requireNonNull(crateKey, "A chave da caixa não pode ser nula ou vazia.");
        Objects.requireNonNull(id, "O ID da caixa não pode ser nulo ou vazio.");

        if (rewardList.isEmpty()) {
            Logger.getGlobal().info("A lista de recompensas da caixa: " + id + " está vazia.");
        }

        ItemStack crateItem = ItemBuilder.of(CrateMaterial.DEFAULT_CRATE_MATERIAL.getMaterial())
                .setName(crateDisplayName)
                .setCustomModelData(crateCustomModelData)
                .setNameSpacedKey(namespace)
                .build();

        return new Crate(crateKey,
                id,
                namespace,
                crateDisplayName,
                crateCustomModelData,
                baseEntityModel,
                animation,
                crateItem,
                rewardList);
    }
}

