package brcomkassin.crates.rewards.cache;

import brcomkassin.crates.rewards.rarity.ItemRarity;
import brcomkassin.crates.rewards.Reward;
import brcomkassin.crates.rewards.rarity.RewardsRarity;

import java.util.List;

public interface RewardCache {


    void add(String id, Reward reward);

    void addKeys(String key);

    List<String> getKeys();

    Reward getReward(String id);

    List<ItemRarity> getItemRarities(String crateID);

    void addItemRarity(String crateID, List<ItemRarity> itemRarities);
}
