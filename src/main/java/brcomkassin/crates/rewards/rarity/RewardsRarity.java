package brcomkassin.crates.rewards.rarity;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.EnumMap;

@AllArgsConstructor
@Getter
public enum RewardsRarity {
    COMMON(50.0, "COMMON"),      // 50% de chance
    RARE(25.0, "RARE"),          // 25% de chance
    SUPER_RARE(12.5, "SUPER_RARE"), // 12.5% de chance
    EPIC(7.5, "EPIC"),           // 7.5% de chance
    MYTHICAL(4.0, "MYTHICAL"),   // 4% de chance
    LEGENDARY(1.0, "LEGENDARY"); // 1% de chance

    private final double percentage;
    private final String rarityName;

    private static final EnumMap<RewardsRarity, Double> rarityPercentageMap = new EnumMap<>(RewardsRarity.class);

    static {
        for (RewardsRarity rarity : values()) {
            rarityPercentageMap.put(rarity, rarity.percentage);
        }
    }

    public static RewardsRarity getByString(String rarityName) {
        for (RewardsRarity rarity : values()) {
            if (rarity.getRarityName().equalsIgnoreCase(rarityName.toUpperCase())) {
                return rarity;
            }
        }
        throw new IllegalArgumentException("Raridade inválida: " + rarityName);
    }

    public static Double getPercentage(RewardsRarity rarity) {
        return rarityPercentageMap.get(rarity);
    }
}

