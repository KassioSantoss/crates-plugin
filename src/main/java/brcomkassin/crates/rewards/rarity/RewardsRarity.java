package brcomkassin.crates.rewards.rarity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum RewardsRarity {

    COMMON(50.0, "COMMON"),      // 50% de chance
    RARE(25.0, "RARE"),        // 25% de chance
    SUPER_RARE(12.5, "SUPER_RARE"),  // 12.5% de chance
    EPIC(7.5, "EPIC"),         // 7.5% de chance
    MYTHICAL(4.0, "MYTHICAL"),     // 4% de chance
    LEGENDARY(1.0, "LEGENDARY");    // 1% de chance

    private final double percentage;
    private final String rarityName;

    public static RewardsRarity getByString(String rarityName) {
        for (RewardsRarity rarity : values()) {
            if (rarity.getRarityName().equalsIgnoreCase(rarityName)) {
                return rarity;
            }
        }
        throw new IllegalArgumentException("Raridade inválida: " + rarityName);
    }
}
