package brcomkassin.crates.rewards;

import lombok.Getter;
import org.bukkit.inventory.ItemStack;

import java.util.List;

@Getter
public record Reward(ItemStack item
        , String displayName
        , List<String> lore
        , String nameSpace
        , int customModelData) {
}
