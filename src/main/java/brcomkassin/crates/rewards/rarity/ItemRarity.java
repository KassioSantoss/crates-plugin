package brcomkassin.crates.rewards.rarity;

import brcomkassin.crates.rewards.Reward;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import java.util.List;

@Data(staticConstructor = "of") public class ItemRarity {

    private final RewardsRarity rarity;
    private final List<Reward> rewards;

}
