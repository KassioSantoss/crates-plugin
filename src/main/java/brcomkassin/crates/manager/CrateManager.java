package brcomkassin.crates.manager;

import brcomkassin.crates.Crate;
import brcomkassin.crates.CrateMaterial;
import brcomkassin.crates.cache.CrateCache;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import java.util.Objects;

public class CrateManager {

    private final CrateCache cache;

    public CrateManager(CrateCache cache) {
        this.cache = cache;
    }

    public boolean isKeyItem(ItemStack item) {
        if (item == null || !item.hasItemMeta() || item.getType() != CrateMaterial.DEFAULT_KEY_MATERIAL.getMaterial())
            return false;

        ItemMeta meta = item.getItemMeta();
        PersistentDataContainer container = meta.getPersistentDataContainer();
        return container.getKeys().stream()
                .anyMatch(key -> cache.getCrate(key.getKey()) != null && container.has(key, PersistentDataType.STRING));
    }

    public boolean isCrateItem(ItemStack item) {
        if (item == null || !item.hasItemMeta() || item.getType() != CrateMaterial.DEFAULT_CRATE_MATERIAL.getMaterial())
            return false;

        ItemMeta meta = item.getItemMeta();
        PersistentDataContainer container = meta.getPersistentDataContainer();
        return container.getKeys().stream()
                .anyMatch(key -> cache.getCrate(key.getKey()) != null && container.has(key, PersistentDataType.STRING));
    }

    public Crate getCrateFromItem(ItemStack item) {
        if (item == null || !item.hasItemMeta() || item.getType() != CrateMaterial.DEFAULT_CRATE_MATERIAL.getMaterial())
            return null;

        ItemMeta meta = item.getItemMeta();
        PersistentDataContainer container = meta.getPersistentDataContainer();

        return container.getKeys().stream()
                .map(key -> cache.getCrate(key.getKey()))
                .filter(Objects::nonNull)
                .findFirst()
                .orElse(null);
    }
    public Crate getCrateFromKey(ItemStack item) {
        if (item == null || !item.hasItemMeta() || item.getType() != CrateMaterial.DEFAULT_KEY_MATERIAL.getMaterial())
            return null;

        ItemMeta meta = item.getItemMeta();
        PersistentDataContainer container = meta.getPersistentDataContainer();

        return container.getKeys().stream()
                .map(key -> cache.getCrate(key.getKey()))
                .filter(Objects::nonNull)
                .findFirst()
                .orElse(null);
    }

    public boolean isCrateEntity(Entity entity) {
        return entity instanceof ArmorStand;
    }

}
