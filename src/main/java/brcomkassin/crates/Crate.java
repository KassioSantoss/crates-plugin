package brcomkassin.crates;

import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public record Crate(
        String id,
        String crateDisplayName,
        int crateCustomModelData,
        String keyDisplayName,
        int keyCustomModelData,
        String entityModel,
        String animation,
        List<String> rewards,
        ItemStack keyItem,
        ItemStack crateItem
        ) {
}
