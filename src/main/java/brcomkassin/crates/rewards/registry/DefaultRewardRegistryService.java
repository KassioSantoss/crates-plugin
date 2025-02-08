package brcomkassin.crates.rewards.registry;

import brcomkassin.cachedepency.CacheDependencyResolverService;
import brcomkassin.commands.RewardCommand;
import brcomkassin.crates.rewards.renderer.RewardRendererService;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class DefaultRewardRegistryService implements RewardRegistryService {

    private final RewardRendererService rewardRenderer;
    private final JavaPlugin plugin;
    private final RewardCommand rewardCommand;

    public DefaultRewardRegistryService(JavaPlugin plugin, CacheDependencyResolverService resolverService, RewardRendererService rewardRenderer) {
        this.rewardRenderer = rewardRenderer;
        this.plugin = plugin;
        this.rewardCommand = new RewardCommand(resolverService);
    }

    @Override
    public void registry(FileConfiguration itemRarityFileConfiguration) {
        rewardRenderer.renderer(itemRarityFileConfiguration);
        plugin.getCommand("reward").setExecutor(rewardCommand);
    }
}
