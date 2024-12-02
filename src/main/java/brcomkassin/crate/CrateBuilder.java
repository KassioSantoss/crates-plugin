package brcomkassin.crate;

import brcomkassin.crate.cache.CrateCacheService;
import org.bukkit.Material;

public class CrateBuilder {

    private Crate crate;
    private CrateCacheService cacheService;

    public CrateBuilder(CrateCacheService cacheService) {
        this.cacheService = cacheService;
    }

    public CrateBuilder(Crate crate) {
        this.crate = crate;
    }

    public static CrateBuilder of(Crate crate) {
        return new CrateBuilder(crate);
    }

    public static CrateBuilder of(CrateCacheService cacheService) {
        return new CrateBuilder(cacheService);
    }

    public CrateBuilder setName(String name) {
        crate.setName(name);
        return this;
    }

    public CrateBuilder setId(String id) {
        crate.setId(id);
        return this;
    }

    public CrateBuilder setItem(Material item) {
        crate.setItem(item);
        return this;
    }

    public Crate build() {
        cacheService.addCrate(crate.getId(), this.crate);
        return this.crate;
    }

}
