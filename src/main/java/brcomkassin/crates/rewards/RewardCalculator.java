package brcomkassin.crates.rewards;

import brcomkassin.crates.Crate;
import brcomkassin.crates.cache.CrateCache;
import brcomkassin.crates.rewards.cache.RewardCache;
import brcomkassin.crates.rewards.rarity.ItemRarity;
import brcomkassin.crates.rewards.rarity.RewardsRarity;
import java.util.*;

public class RewardCalculator {

    private final CrateCache cache;

    public RewardCalculator(CrateCache cache) {
        this.cache = cache;
    }

    public Reward calculateReward(String crateId, RewardCache rewardCache) {
        Crate crate = cache.getCrateById(crateId);

        List<ItemRarity> itemRarities = rewardCache.getItemRarities(crate.id());
        RewardsRarity selectedRarity = getRandomRarity(itemRarities);

        List<Reward> rewards = itemRarities.stream()
                .filter(itemRarity -> itemRarity.getRarity() == selectedRarity)
                .flatMap(itemRarity -> itemRarity.getRewards().stream())
                .toList();

        if (rewards.isEmpty()) {
            throw new IllegalStateException("Nenhuma raridade ou item configurado para a caixa: " + crate.id());
        }
        return getRandomReward(rewards);
    }

    private RewardsRarity getRandomRarity(List<ItemRarity> itemRarities) {
        double totalWeight = itemRarities.stream()
                .mapToDouble(itemRarity -> itemRarity.getRarity().getPercentage())
                .sum();

        double randomValue = new Random().nextDouble() * totalWeight;
        double cumulativeWeight = 0.0;

        for (ItemRarity itemRarity : itemRarities) {
            cumulativeWeight += itemRarity.getRarity().getPercentage();
            if (randomValue <= cumulativeWeight) {
                return itemRarity.getRarity();
            }
        }
        throw new IllegalStateException("Erro ao calcular raridade aleatória.");
    }

    private Reward getRandomReward(List<Reward> rewards) {
        Random random = new Random();
        return rewards.get(random.nextInt(rewards.size()));
    }
}