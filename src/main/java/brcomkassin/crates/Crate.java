package brcomkassin.crates;

import brcomkassin.crates.key.CrateKey;
import brcomkassin.crates.rewards.Reward;
import lombok.Data;
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
    private final String animation;
    private final ItemStack crateItem;
    private final List<Reward> rewards;
}