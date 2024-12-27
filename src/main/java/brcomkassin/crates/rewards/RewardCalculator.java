package brcomkassin.crates.rewards;

import brcomkassin.crates.Crate;
import brcomkassin.crates.rewards.cache.RewardCache;

import java.util.*;

public class RewardCalculator {

    private final RewardCache rewardCache;

    public RewardCalculator(RewardCache rewardCache) {
        this.rewardCache = rewardCache;
    }

    /**
     * Calcula uma recompensa aleatória com base nas probabilidades definidas para a caixa.
     *
     * @param crate A caixa de onde as recompensas serão sorteadas.
     * @return Uma recompensa aleatória.
     */
    public Reward calculateReward(Crate crate) {
        List<Reward> rewardsForCrate = rewardCache.getRewardsForCrate(crate);

        if (rewardsForCrate.isEmpty()) {
            throw new IllegalStateException("Nenhuma recompensa configurada para a caixa: " + crate.getId());
        }

        // Escolhe uma recompensa aleatória com base na probabilidade.
        return getRandomReward(rewardsForCrate);
    }

    /**
     * Seleciona uma recompensa aleatória considerando as probabilidades de cada uma.
     *
     * @param rewards A lista de recompensas disponíveis.
     * @return Uma recompensa aleatória.
     */
    private Reward getRandomReward(List<Reward> rewards) {
        double totalWeight = rewards.stream()
                .mapToDouble(reward -> reward.getRarity().getPercentage())
                .sum();

        double randomValue = new Random().nextDouble() * totalWeight;
        double cumulativeWeight = 0.0;

        for (Reward reward : rewards) {
            cumulativeWeight += reward.getRarity().getPercentage();
            if (randomValue <= cumulativeWeight) {
                return reward;
            }
        }

        throw new IllegalStateException("Erro ao calcular recompensa aleatória.");
    }
}
