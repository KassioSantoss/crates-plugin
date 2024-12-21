package brcomkassin.crates;

import brcomkassin.crates.key.CrateKey;
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
