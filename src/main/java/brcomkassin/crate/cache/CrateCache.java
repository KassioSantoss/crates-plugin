package brcomkassin.crate.cache;

import brcomkassin.crate.Crate;

import java.util.HashMap;
import java.util.Map;

public class CrateCache implements CrateCacheService {

    private final Map<String, Crate> cache;

    public CrateCache() {
        this.cache = new HashMap<>();
    }

    @Override
    public void addCrate(String key, Crate crate) {
        cache.put(key, crate);
    }

    @Override
    public void removeCrate(String key) {
        cache.remove(key);
    }

}
