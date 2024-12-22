package brcomkassin.crates.rewards.cache;

import brcomkassin.crates.Crate;
import brcomkassin.crates.rewards.ItemRarity;
import brcomkassin.crates.rewards.Reward;
import brcomkassin.crates.rewards.rarity.RewardsRarity;

import java.util.*;

public class RewardCacheService implements RewardCache {

    private final Map<Reward, RewardsRarity> rarityMap;
    private final Map<String, List<ItemRarity>> itemRarityMap;
    private final Map<String, Reward> idRewardMap;
    private final Set<String> keys;

    public RewardCacheService() {
        this.rarityMap = new HashMap<>();
        this.itemRarityMap = new HashMap<>();
        this.idRewardMap = new HashMap<>();
        this.keys = new HashSet<>();
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
    public void addKeys(String key) {
        keys.add(key);
    }

    @Override
    public List<String> getKeys() {
        return new ArrayList<>(keys);
    }

    @Override
    public RewardsRarity getRarity(Reward reward) {
        return rarityMap.get(reward);
    }

    @Override
    public Reward getReward(String id) {
        return idRewardMap.get(id);
    }

    @Override
    public List<ItemRarity> getItemRarities(String crateID) {
        return itemRarityMap.get(crateID);
    }

    @Override
    public void addItemRarity(String crateID, List<ItemRarity> itemRarities) {
        itemRarityMap.put(crateID, itemRarities);
    }
}
