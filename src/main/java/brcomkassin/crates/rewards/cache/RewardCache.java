package brcomkassin.crates.rewards.cache;

import brcomkassin.crates.Crate;
import brcomkassin.crates.rewards.Reward;

import java.util.*;

public class RewardCache implements RewardCacheService {

    private final Map<Crate, List<Reward>> crateToRewards;
    private final Map<String, Reward> rewardById;
    private final Set<String> rewardKeys;

    public RewardCache() {
        this.crateToRewards = new HashMap<>();
        this.rewardById = new HashMap<>();
        this.rewardKeys = new HashSet<>();
    }

    @Override
    public void addRewardById(String id, Reward reward) {
        rewardById.put(id, reward);
    }

    @Override
    public void addRewardKey(String key) {
        rewardKeys.add(key);
    }

    @Override
    public List<String> getAllRewardKeys() {
        return new ArrayList<>(rewardKeys);
    }

    @Override
    public Reward findRewardById(String id) {
        return rewardById.get(id);
    }

    @Override
    public void clear() {
        crateToRewards.clear();
        rewardById.clear();
        rewardKeys.clear();
    }

    @Override
    public List<Reward> getRewardsForCrate(Crate crate) {
        return crateToRewards.get(crate);
    }

    @Override
    public void addRewardsForCrate(Crate crate, List<Reward> rewards) {
        crateToRewards.put(crate, rewards);
    }
}