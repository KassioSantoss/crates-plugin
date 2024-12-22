package brcomkassin.crates.rewards;

import brcomkassin.crates.rewards.rarity.RewardsRarity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.inventory.ItemStack;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class Reward {
    private ItemStack item;
    private String displayName;
    private List<String> lore;
    private String nameSpace;
    private int customModelData;

}
