package brcomkassin;

import brcomkassin.crates.registrys.CrateRegistryService;
import brcomkassin.crates.registrys.DefaultCrateRegistryService;
import brcomkassin.crates.rewards.registry.DefaultRewardRegistryService;
import brcomkassin.crates.rewards.registry.RewardRegistryService;
import brcomkassin.utils.Config;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

public final class CratesPlugin extends JavaPlugin {

    @Getter
    private static CratesPlugin instance;
    private final Config itemRarityConfig = new Config(this, "itemRarity.yml");
    private final Config rarityConfig = new Config(this, "rarity.yml");
    private final Config crateConfig = new Config(this, "crate.yml");
    private final CrateRegistryService crateRegistryService = new DefaultCrateRegistryService(this);
    private final RewardRegistryService rewardRegistryService = new DefaultRewardRegistryService();

    @Override
    public void onLoad() {
        instance = this;
    }

    @Override
    public void onEnable() {
        rewardRegistryService.registry(itemRarityConfig, rarityConfig);
        crateConfig.reloadDefaultConfig();
        crateRegistryService.registry(crateConfig);

        getLogger().info("Plugin CRATES inicializado com sucesso!");
    }

    @Override
    public void onDisable() {
        itemRarityConfig.saveConfig();
        rarityConfig.saveConfig();
        crateConfig.saveConfig();
    }

}


