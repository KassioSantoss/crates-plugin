package brcomkassin.cachedepency;

import brcomkassin.crates.cache.CrateCacheService;
import brcomkassin.crates.rewards.cache.RewardCacheService;

public interface CacheDependencyResolverService {
    RewardCacheService getRewardCache();
    CrateCacheService getCrateCache();
}
