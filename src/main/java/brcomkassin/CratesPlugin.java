package brcomkassin;

import brcomkassin.cachedepency.CacheDependencyResolver;
import brcomkassin.cachedepency.CacheDependencyResolverService;
import brcomkassin.commands.ReloadCrates;
import brcomkassin.crates.registrys.CrateRegistryService;
import brcomkassin.crates.registrys.DefaultCrateRegistryService;
import brcomkassin.crates.renderer.CrateRendererService;
import brcomkassin.crates.renderer.DefaultCrateRenderer;
import brcomkassin.crates.rewards.registry.DefaultRewardRegistryService;
import brcomkassin.crates.rewards.registry.RewardRegistryService;
import brcomkassin.crates.rewards.renderer.DefaultRewardRenderer;
import brcomkassin.crates.rewards.renderer.RewardRendererService;
import brcomkassin.utils.Config;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

public final class CratesPlugin extends JavaPlugin {

    @Getter
    private static CratesPlugin instance;
    @Getter
    private final Config rewardConfig = new Config(this, "rewards.yml");
    @Getter
    private final Config crateConfig = new Config(this, "crate.yml");
    private final CacheDependencyResolverService resolver = new CacheDependencyResolver();
    @Getter
    private final RewardRendererService rewardRendererService = new DefaultRewardRenderer(resolver);
    @Getter
    private final CrateRendererService crateRendererService = new DefaultCrateRenderer(resolver);
    @Getter
    private final RewardRegistryService rewardRegistryService = new DefaultRewardRegistryService(this, resolver, rewardRendererService);
    private final CrateRegistryService crateRegistryService = new DefaultCrateRegistryService(this, resolver, crateRendererService);

    @Override
    public void onLoad() {
        instance = this;
    }

    @Override
    public void onEnable() {
        rewardConfig.reloadDefaultConfig();
        crateConfig.reloadDefaultConfig();
        rewardRegistryService.registry(rewardConfig);
        crateRegistryService.registry(crateConfig);
        getCommand("cratesreload").setExecutor(new ReloadCrates(this, resolver));
        getLogger().info("Plugin CRATES inicializado com sucesso!");
//      this.getServer().getPluginManager().registerEvents(new PlayerJoinListener(), this);
    }

    @Override
    public void onDisable() {
    }
}


