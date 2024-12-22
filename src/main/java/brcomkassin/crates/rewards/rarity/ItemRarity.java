package brcomkassin.crates.rewards.rarity;

import brcomkassin.crates.rewards.Reward;
import lombok.AllArgsConstructor;
import lombok.Getter;
import java.util.List;

@Getter
@AllArgsConstructor
public class ItemRarity {
    private RewardsRarity rarity;
    private List<Reward> rewards;
}
