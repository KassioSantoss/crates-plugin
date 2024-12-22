package brcomkassin.crates.rewards.cache;

import brcomkassin.crates.rewards.rarity.ItemRarity;
import brcomkassin.crates.rewards.Reward;
import java.util.*;

public class RewardCacheService implements RewardCache {

    private final Map<String, List<ItemRarity>> itemRarityMap;
    private final Map<String, Reward> idRewardMap;
    private final Set<String> keys;

    public RewardCacheService() {
        this.itemRarityMap = new HashMap<>();
        this.idRewardMap = new HashMap<>();
        this.keys = new HashSet<>();
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
