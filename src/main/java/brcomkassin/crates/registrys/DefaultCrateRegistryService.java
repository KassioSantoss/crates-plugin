package brcomkassin.crates.registrys;

import brcomkassin.commands.CrateCommand;
import brcomkassin.crates.cache.CrateCacheService;
import brcomkassin.crates.listeners.CrateInteractListener;
import brcomkassin.crates.listeners.CratePlaceListener;
import brcomkassin.crates.manager.CrateManager;
import brcomkassin.crates.renderer.CrateRendererService;
import brcomkassin.crates.renderer.DefaultCrateRenderer;
import brcomkassin.crates.rewards.RewardCalculator;
import brcomkassin.crates.rewards.animation.RewardAnimationHandler;
import brcomkassin.crates.rewards.cache.CacheDependencyResolver;
import brcomkassin.crates.rewards.cache.RewardCache;
import brcomkassin.crates.services.CrateService;
import brcomkassin.crates.services.DefaultCrateService;
import brcomkassin.utils.Config;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class DefaultCrateRegistryService implements CrateRegistryService {

    private final CrateInteractListener crateInteractListener;
    private final CratePlaceListener cratePlaceListener;
    private final CrateCommand crateCommand;
    private final CrateRendererService crateRenderer;
    private final JavaPlugin plugin;

    public DefaultCrateRegistryService(JavaPlugin plugin, CacheDependencyResolver resolver) {
        this.plugin = plugin;
        CrateCacheService crateCacheD = resolver.getCrateCache();
        RewardCache rewardCache = resolver.getRewardCache();
        CrateManager crateManager = new CrateManager(crateCacheD);
        RewardCalculator rewardCalculator = new RewardCalculator(rewardCache);
        RewardAnimationHandler rewardAnimationHandler = new RewardAnimationHandler(plugin, rewardCalculator);
        CrateService crateService = new DefaultCrateService(crateManager, rewardAnimationHandler);
        this.crateInteractListener = new CrateInteractListener(crateService);
        this.cratePlaceListener = new CratePlaceListener(crateService, crateManager);
        this.crateCommand = new CrateCommand(resolver.getCrateCache());
        this.crateRenderer = new DefaultCrateRenderer(crateCacheD, rewardCache);
    }

    @Override
    public void registry(FileConfiguration fileConfiguration) {
        crateRenderer.load(fileConfiguration);
        plugin.getServer().getPluginManager().registerEvents(crateInteractListener, plugin);
        plugin.getServer().getPluginManager().registerEvents(cratePlaceListener, plugin);
        plugin.getCommand("crate").setExecutor(crateCommand);
        plugin.getLogger().info(ChatColor.GREEN + "DefaultCrateRegistryService carregado!");
    }
}
