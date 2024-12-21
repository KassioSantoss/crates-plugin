package brcomkassin.crates.rewards.registry;

import brcomkassin.crates.rewards.DefaultRewardRenderer;
import brcomkassin.crates.rewards.RewardRenderer;
import brcomkassin.crates.rewards.cache.RewardCache;
import brcomkassin.crates.rewards.cache.RewardCacheService;
import org.bukkit.configuration.file.FileConfiguration;

public class DefaultRewardRegistryService implements RewardRegistryService {

    private final RewardRenderer rewardRenderer;

    public DefaultRewardRegistryService() {
        RewardCache cache = new RewardCacheService();
        this.rewardRenderer = new DefaultRewardRenderer(cache);
    }

    @Override
    public void registry(FileConfiguration itemRarityConfiguration, FileConfiguration rarityConfiguration) {
        rewardRenderer.renderer(itemRarityConfiguration, rarityConfiguration);
    }
}
