package brcomkassin.crates.rewards.cache;

import brcomkassin.crates.cache.CrateCache;
import brcomkassin.crates.cache.CrateCacheService;

public class CacheDependencyService implements CacheDependencyResolver {
    private final RewardCache rewardCache;
    private final CrateCacheService crateCacheService;

    public CacheDependencyService() {
        this.rewardCache = new RewardCacheService();
        this.crateCacheService = new CrateCache();
    }

    @Override
    public RewardCache getRewardCache() {
        return rewardCache;
    }

    @Override
    public CrateCacheService getCrateCache() {
        return crateCacheService;
    }
}
