package brcomkassin.crates.rewards.cache;

import brcomkassin.crates.cache.CrateCache;

public interface CacheDependencyResolver {
    RewardCache getRewardCache();
    CrateCache getCrateCache();
}
