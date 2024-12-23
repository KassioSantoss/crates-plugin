package brcomkassin.crates.location.registry;

import brcomkassin.crates.cache.CrateCache;
import brcomkassin.crates.location.renderer.CrateLocationRenderer;
import brcomkassin.crates.location.renderer.DefaultCrateLocationRenderer;
import brcomkassin.crates.rewards.cache.CacheDependencyResolver;
import org.bukkit.configuration.file.FileConfiguration;

public class DefaultCrateLocationRegistryService implements CrateLocationRegistryService {

    private final CrateLocationRenderer crateLocationRenderer;

    public DefaultCrateLocationRegistryService(CacheDependencyResolver resolver) {
        CrateCache crateCache = resolver.getCrateCache();
        this.crateLocationRenderer = new DefaultCrateLocationRenderer(crateCache);
    }

    @Override
    public void registry(FileConfiguration fileConfiguration) {
        crateLocationRenderer.renderer(fileConfiguration);
    }
}
