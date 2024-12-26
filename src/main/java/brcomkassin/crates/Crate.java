package brcomkassin.crates;

import brcomkassin.crates.key.CrateKey;
import brcomkassin.crates.rewards.Reward;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public record Crate(
        CrateKey key,
        String id,
        String nameSpace,
        String crateDisplayName,
        int crateCustomModelData,
        String baseEntityModel,
        String animation,
        ItemStack crateItem) {
}
