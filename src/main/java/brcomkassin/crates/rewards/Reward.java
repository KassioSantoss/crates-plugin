package brcomkassin.crates.rewards;

import brcomkassin.crates.rewards.rarity.RewardsRarity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.inventory.ItemStack;
import java.util.List;

@Data(staticConstructor = "of") public class Reward {

    private final ItemStack item;
    private final String displayName;
    private final List<String> lore;
    private final String nameSpace;
    private final int customModelData;

}
