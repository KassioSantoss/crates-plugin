package brcomkassin.crates;

import brcomkassin.crates.key.CrateKey;
import brcomkassin.crates.rewards.Reward;
import brcomkassin.crates.rewards.animation.AnimationProperties;
import lombok.Data;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;

import java.util.List;

@Data
public class Crate {
    private final CrateKey key;
    private final String id;
    private final String nameSpace;
    private final String crateDisplayName;
    private final int crateCustomModelData;
    private final String baseEntityModel;
    private final ItemStack crateItem;
    private final List<Reward> rewards;
    private final AnimationProperties animationProperties;
    private final EntityType entityType = EntityType.ARMOR_STAND;
}