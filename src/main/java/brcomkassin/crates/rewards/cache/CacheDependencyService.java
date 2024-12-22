package brcomkassin.crates.rewards.cache;

import brcomkassin.crates.cache.CrateCache;
import brcomkassin.crates.cache.CrateCacheService;

public class CacheDependencyService implements CacheDependencyResolver {
    private final RewardCache rewardCache;
    private final CrateCache crateCache;

    public CacheDependencyService() {
        this.rewardCache = new RewardCacheService();
        this.crateCache = new CrateCacheService();
    }

    @Override
    public RewardCache getRewardCache() {
        return rewardCache;
    }

    @Override
    public CrateCache getCrateCache() {
        return crateCache;
    }
}
