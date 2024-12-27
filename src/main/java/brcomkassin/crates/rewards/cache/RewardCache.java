package brcomkassin.crates.rewards.cache;

import brcomkassin.crates.Crate;
import brcomkassin.crates.rewards.Reward;

import java.util.List;

public interface RewardCache {

    void addRewardById(String id, Reward reward);

    void addRewardKey(String key);

    void addRewardsForCrate(Crate crate, List<Reward> rewards);

    List<String> getAllRewardKeys();

    List<Reward> getRewardsForCrate(Crate crate);

    Reward findRewardById(String id);

}
