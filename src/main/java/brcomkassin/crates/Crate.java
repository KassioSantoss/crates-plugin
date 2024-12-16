package brcomkassin.crates;

import brcomkassin.CratesPlugin;
import brcomkassin.utils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import java.util.List;
import java.util.Objects;

public record Crate(
        String id,
        String crateDisplayName,
        int crateCustomModelData,
        String keyDisplayName,
        int keyCustomModelData,
        String entityModel,
        String animation,
        List<String> rewards) {

    private static final Material DEFAULT_CRATE_MATERIAL = Material.CHEST;
    private static final Material DEFAULT_KEY_MATERIAL = Material.TRIPWIRE_HOOK;

    public ItemStack createCrateItem() {
        return ItemBuilder.of(DEFAULT_CRATE_MATERIAL)
                .setName(crateDisplayName)
                .setCustomModelData(crateCustomModelData)
                .setItemMetaData(id)
                .build();
    }

    public ItemStack createKeyItem() {
        return ItemBuilder.of(DEFAULT_KEY_MATERIAL)
                .setName(keyDisplayName)
                .setCustomModelData(keyCustomModelData)
                .setItemMetaData(id)
                .build();
    }

    public boolean isKey(ItemStack item) {
        if (item == null || !item.hasItemMeta() || item.getType() != Material.TRIPWIRE_HOOK) return false;
        ItemMeta meta = item.getItemMeta();
        NamespacedKey key = new NamespacedKey(CratesPlugin.getInstance(), id);

        return meta.hasCustomModelData() &&
                meta.getCustomModelData() == keyCustomModelData &&
                meta.getPersistentDataContainer().has(key, PersistentDataType.STRING) &&
                Objects.equals(meta.getPersistentDataContainer().get(key, PersistentDataType.STRING), id);
    }

    public boolean isCrate(ItemStack item) {
        if (item == null || !item.hasItemMeta() || item.getType() != Material.CHEST) return false;
        ItemMeta meta = item.getItemMeta();

        NamespacedKey key = new NamespacedKey(CratesPlugin.getInstance(), id);

        return meta.hasCustomModelData() &&
                meta.getCustomModelData() == crateCustomModelData &&
                meta.getPersistentDataContainer().has(key, PersistentDataType.STRING) &&
                Objects.equals(meta.getPersistentDataContainer().get(key, PersistentDataType.STRING), id);
    }

}
