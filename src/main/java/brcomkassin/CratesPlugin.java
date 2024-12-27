package brcomkassin;

import brcomkassin.crates.location.registry.CrateLocationRegistryService;
import brcomkassin.crates.location.registry.DefaultCrateLocationRegistryService;
import brcomkassin.crates.registrys.CrateRegistryService;
import brcomkassin.crates.registrys.DefaultCrateRegistryService;
import brcomkassin.crates.rewards.cache.CacheDependencyResolver;
import brcomkassin.crates.rewards.cache.CacheDependencyService;
import brcomkassin.crates.rewards.registry.DefaultRewardRegistryService;
import brcomkassin.crates.rewards.registry.RewardRegistryService;
import brcomkassin.utils.Config;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

public final class CratesPlugin extends JavaPlugin {

    @Getter
    private static CratesPlugin instance;
    private final Config itemRarityConfig = new Config(this, "itemrarity.yml");
    private final Config crateConfig = new Config(this, "crate.yml");
    private final Config locationConfig = new Config(this, "cratelocation.yml");
    private final CacheDependencyResolver dependencyResolver = new CacheDependencyService();
    private final CrateRegistryService crateRegistryService = new DefaultCrateRegistryService(this, dependencyResolver, locationConfig);
    private final RewardRegistryService rewardRegistryService = new DefaultRewardRegistryService(this, dependencyResolver);
    private final CrateLocationRegistryService crateLocationRegistryService = new DefaultCrateLocationRegistryService(dependencyResolver);

    @Override
    public void onLoad() {
        instance = this;
    }

    @Override
    public void onEnable() {
        locationConfig.reloadDefaultConfig();
        itemRarityConfig.reloadDefaultConfig();
        crateConfig.reloadDefaultConfig();
        rewardRegistryService.registry(itemRarityConfig);
        crateRegistryService.registry(crateConfig);
        crateLocationRegistryService.registry(locationConfig);
        getLogger().info("Plugin CRATES inicializado com sucesso!");
    }

    @Override
    public void onDisable() {
        itemRarityConfig.saveConfig();
        crateConfig.saveConfig();
        locationConfig.saveConfig();
    }
}


