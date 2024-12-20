package brcomkassin.crates;

import org.bukkit.entity.Entity;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public record Crate(
        CrateKey key,
        String id,
        String crateDisplayName,
        int crateCustomModelData,
        String baseEntityModel,
        String animation,
        ItemStack crateItem,
        List<String> rewards
) {
}
