package brcomkassin.crates.rewards.cache;

import brcomkassin.crates.Crate;
import brcomkassin.crates.rewards.ItemRarity;
import brcomkassin.crates.rewards.Reward;
import brcomkassin.crates.rewards.rarity.RewardsRarity;

import java.util.List;

public interface RewardCache {

    void add(Reward reward, RewardsRarity rarity);

    void add(String id, Reward reward);

    void addKeys(String key);

    List<String> getKeys();

    RewardsRarity getRarity(Reward reward);

    Reward getReward(String id);

    List<ItemRarity> getItemRarities(String crateID);

    void addItemRarity(String crateID, List<ItemRarity> itemRarities);
}
