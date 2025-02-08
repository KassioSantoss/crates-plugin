package brcomkassin.cachedepency;

import brcomkassin.crates.cache.CrateCache;
import brcomkassin.crates.cache.CrateCacheService;
import brcomkassin.crates.rewards.cache.RewardCache;
import brcomkassin.crates.rewards.cache.RewardCacheService;

public class CacheDependencyResolver implements CacheDependencyResolverService {

    private final RewardCacheService rewardCache;
    private final CrateCacheService crateCacheService;

    public CacheDependencyResolver() {
        this.rewardCache = new RewardCache();
        this.crateCacheService = new CrateCache();
    }

    @Override
    public RewardCacheService getRewardCache() {
        return rewardCache;
    }

    @Override
    public CrateCacheService getCrateCache() {
        return crateCacheService;
    }
}
