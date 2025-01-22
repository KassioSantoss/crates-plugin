package brcomkassin.crates.rewards.cache;

import brcomkassin.crates.cache.CrateCacheService;

public interface CacheDependencyResolver {
    RewardCache getRewardCache();
    CrateCacheService getCrateCache();
}
