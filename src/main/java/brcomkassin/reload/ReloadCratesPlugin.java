package brcomkassin.reload;

import brcomkassin.CratesPlugin;
import brcomkassin.cachedepency.CacheDependencyResolverService;

public class ReloadCratesPlugin {

    private final CacheDependencyResolverService resolverService;
    private final CratesPlugin plugin;

    public ReloadCratesPlugin(CacheDependencyResolverService resolverService, CratesPlugin plugin) {
        this.resolverService = resolverService;
        this.plugin = plugin;
    }

    public void reload() {
        plugin.getCrateConfig().reloadDefaultConfig();
        plugin.getRewardConfig().reloadDefaultConfig();

        resolverService.getCrateCache().clear();
        resolverService.getCrateCache().clear();

        plugin.getRewardRendererService().renderer(plugin.getRewardConfig());
        plugin.getCrateRendererService().renderer(plugin.getCrateConfig());
    }

}
