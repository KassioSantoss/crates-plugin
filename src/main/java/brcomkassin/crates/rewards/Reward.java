package brcomkassin.crates.rewards;

import lombok.Getter;
import org.bukkit.inventory.ItemStack;

@Getter
public record Reward(ItemStack item
        , String displayName
        , String lore
        , String nameSpace
        , int customModelData) {
}
