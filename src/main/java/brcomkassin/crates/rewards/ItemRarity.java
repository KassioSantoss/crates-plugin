package brcomkassin.crates.rewards;

import brcomkassin.crates.rewards.rarity.RewardsRarity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import java.util.List;

@Getter
@AllArgsConstructor
public class ItemRarity {
    private RewardsRarity rarity;
    private List<Reward> rewards;
}
