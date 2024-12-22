package brcomkassin.crates.registrys;

import brcomkassin.commands.CrateCommand;
import brcomkassin.crates.cache.CrateCache;
import brcomkassin.crates.listeners.CrateInteractListener;
import brcomkassin.crates.listeners.CratePlaceListener;
import brcomkassin.crates.manager.CrateManager;
import brcomkassin.crates.renderer.CrateRenderer;
import brcomkassin.crates.renderer.DefaultCrateRenderer;
import brcomkassin.crates.rewards.cache.CacheDependencyResolver;
import brcomkassin.crates.rewards.cache.RewardCache;
import brcomkassin.crates.services.CrateService;
import brcomkassin.crates.services.DefaultCrateService;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class DefaultCrateRegistryService implements CrateRegistryService {

    private final CrateInteractListener crateInteractListener;
    private final CratePlaceListener cratePlaceListener;
    private final CrateCommand crateCommand;
    private final CrateRenderer crateRenderer;
    private final JavaPlugin plugin;

    public DefaultCrateRegistryService(JavaPlugin plugin, CacheDependencyResolver resolver) {
        this.plugin = plugin;
        CrateCache crateCache = resolver.getCrateCache();
        RewardCache rewardCache = resolver.getRewardCache();
        CrateManager crateManager = new CrateManager(crateCache);
        CrateService crateService = new DefaultCrateService(crateManager, crateCache, rewardCache);
        this.crateInteractListener = new CrateInteractListener(crateService);
        this.cratePlaceListener = new CratePlaceListener(crateService, crateManager);
        this.crateCommand = new CrateCommand(crateCache);
        this.crateRenderer = new DefaultCrateRenderer(crateCache);
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
