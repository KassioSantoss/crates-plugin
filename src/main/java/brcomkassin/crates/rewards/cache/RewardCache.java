package brcomkassin.crates.rewards.cache;

import brcomkassin.crates.rewards.Reward;
import brcomkassin.crates.rewards.rarity.RewardsRarity;

public interface RewardCache {

    void add(Reward reward, RewardsRarity rarity);

    void add(String id, Reward reward);

    RewardsRarity getRarity(Reward reward);
    Reward getReward(String id);
}
