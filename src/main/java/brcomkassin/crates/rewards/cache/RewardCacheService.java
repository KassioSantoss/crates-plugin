package brcomkassin.crates.rewards.cache;

import brcomkassin.crates.rewards.Reward;
import brcomkassin.crates.rewards.rarity.RewardsRarity;

import java.util.HashMap;
import java.util.Map;

public class RewardCacheService implements RewardCache {

    private final Map<Reward, RewardsRarity> rarityMap;
    private final Map<String, Reward> idRewardMap;

    public RewardCacheService() {
        this.rarityMap = new HashMap<>();
        this.idRewardMap = new HashMap<>();
    }

    @Override
    public void add(Reward reward, RewardsRarity rarity) {
        rarityMap.put(reward, rarity);
    }

    @Override
    public void add(String id, Reward reward) {
        idRewardMap.put(id, reward);
    }

    @Override
    public RewardsRarity getRarity(Reward reward) {
        return rarityMap.get(reward);
    }

    @Override
    public Reward getReward(String id) {
        return idRewardMap.get(id);
    }

}
