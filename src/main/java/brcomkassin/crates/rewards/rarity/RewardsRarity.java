package brcomkassin.crates.rewards.rarity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum RewardsRarity {

    COMMON(50.0),      // 50% de chance
    RARE(25.0),        // 25% de chance
    SUPER_RARE(12.5),  // 12.5% de chance
    EPIC(7.5),         // 7.5% de chance
    MYTHICAL(4.0),     // 4% de chance
    LEGENDARY(1.0);    // 1% de chance

    private final double percentage;

}
