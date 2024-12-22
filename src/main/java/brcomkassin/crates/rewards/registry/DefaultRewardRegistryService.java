package brcomkassin.crates.rewards.registry;

import brcomkassin.commands.RewardCommand;
import brcomkassin.crates.cache.CrateCache;
import brcomkassin.crates.rewards.renderer.DefaultRewardRenderer;
import brcomkassin.crates.rewards.renderer.RewardRenderer;
import brcomkassin.crates.rewards.cache.CacheDependencyResolver;
import brcomkassin.crates.rewards.cache.RewardCache;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class DefaultRewardRegistryService implements RewardRegistryService {

    private final RewardRenderer rewardRenderer;
    private final JavaPlugin plugin;
    private final RewardCommand rewardCommand;

    public DefaultRewardRegistryService(JavaPlugin plugin, CacheDependencyResolver resolver) {
        this.plugin = plugin;
        RewardCache rewardCache = resolver.getRewardCache();
        CrateCache crateCache = resolver.getCrateCache();
        this.rewardRenderer = new DefaultRewardRenderer(rewardCache, crateCache);
        this.rewardCommand = new RewardCommand(rewardCache);
    }

    @Override
    public void registry(FileConfiguration itemRarityConfiguration, FileConfiguration rarityConfiguration) {
        rewardRenderer.renderer(itemRarityConfiguration, rarityConfiguration);
        plugin.getCommand("reward").setExecutor(rewardCommand);
    }
}
