package brcomkassin;

import brcomkassin.crates.registrys.CrateRegistryService;
import brcomkassin.crates.registrys.DefaultCrateRegistryService;
import brcomkassin.crates.rewards.cache.CacheDependencyResolver;
import brcomkassin.crates.rewards.cache.CacheDependencyService;
import brcomkassin.crates.rewards.registry.DefaultRewardRegistryService;
import brcomkassin.crates.rewards.registry.RewardRegistryService;
import brcomkassin.utils.Config;
import lombok.Getter;
import org.bukkit.configuration.Configuration;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public final class CratesPlugin extends JavaPlugin {

    @Getter
    private static CratesPlugin instance;
    private final Config itemRarityConfig = new Config(this, "itemrarity.yml");
    private final Config rarityConfig = new Config(this, "rarity.yml");
    private final Config crateConfig = new Config(this, "crate.yml");
    private final CacheDependencyResolver dependencyResolver = new CacheDependencyService();
    private final CrateRegistryService crateRegistryService = new DefaultCrateRegistryService(this, dependencyResolver);
    private final RewardRegistryService rewardRegistryService = new DefaultRewardRegistryService(this, dependencyResolver);

    @Override
    public void onLoad() {
        instance = this;
    }

    @Override
    public void onEnable() {
        rarityConfig.reloadDefaultConfig();
        itemRarityConfig.reloadDefaultConfig();
        crateConfig.reloadDefaultConfig();
        crateRegistryService.registry(crateConfig);
        rewardRegistryService.registry(itemRarityConfig, rarityConfig);
        getLogger().info("Plugin CRATES inicializado com sucesso!");
    }

    @Override
    public void onDisable() {
        itemRarityConfig.saveConfig();
        rarityConfig.saveConfig();
        crateConfig.saveConfig();
    }
}


