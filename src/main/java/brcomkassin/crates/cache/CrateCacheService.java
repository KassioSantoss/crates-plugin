package brcomkassin.crates.cache;

import brcomkassin.crates.Crate;
import java.util.HashMap;
import java.util.Map;

public class CrateCacheService implements CrateCache {

    private final Map<String, Crate> cache;

    public CrateCacheService() {
        this.cache = new HashMap<>();
    }

    @Override
    public void add(String key, Crate crate) {
        cache.put(key, crate);
    }

    @Override
    public void remove(String key, Crate crate) {
        cache.remove(key, crate);
    }

    @Override
    public void remove(String key) {
        cache.remove(key);
    }

    @Override
    public Crate getCrate(String key) {
        return cache.get(key);
    }
}
