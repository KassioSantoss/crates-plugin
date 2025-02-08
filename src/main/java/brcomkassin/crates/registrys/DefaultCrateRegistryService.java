package brcomkassin.crates.registrys;

import brcomkassin.cachedepency.CacheDependencyResolverService;
import brcomkassin.commands.CrateCommand;
import brcomkassin.crates.listeners.CrateInteractListener;
import brcomkassin.crates.listeners.CratePlaceListener;
import brcomkassin.crates.manager.CrateManager;
import brcomkassin.crates.renderer.CrateRendererService;
import brcomkassin.crates.services.CrateService;
import brcomkassin.crates.services.DefaultCrateService;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class DefaultCrateRegistryService implements CrateRegistryService {

    private final CrateInteractListener crateInteractListener;
    private final CratePlaceListener cratePlaceListener;
    private final CrateCommand crateCommand;
    private final CrateRendererService crateRenderer;
    private final JavaPlugin plugin;

    public DefaultCrateRegistryService(JavaPlugin plugin, CacheDependencyResolverService resolverService, CrateRendererService crateRenderer) {
        this.plugin = plugin;
        this.crateRenderer = crateRenderer;
        CrateManager crateManager = new CrateManager(resolverService.getCrateCache());
        CrateService crateService = new DefaultCrateService(crateManager, resolverService);
        this.crateInteractListener = new CrateInteractListener(crateService);
        this.cratePlaceListener = new CratePlaceListener(crateService, crateManager);
        this.crateCommand = new CrateCommand(resolverService);
    }

    @Override
    public void registry(FileConfiguration fileConfiguration) {
        crateRenderer.renderer(fileConfiguration);
        plugin.getServer().getPluginManager().registerEvents(crateInteractListener, plugin);
        plugin.getServer().getPluginManager().registerEvents(cratePlaceListener, plugin);
        plugin.getCommand("crate").setExecutor(crateCommand);
        plugin.getLogger().info(ChatColor.GREEN + "DefaultCrateRegistryService carregado!");
    }
}
