package brcomkassin.crates.cache;

import brcomkassin.crates.Crate;
import com.ticxo.modelengine.api.nms.NMSHandler;

import java.util.HashMap;
import java.util.Map;

public class CrateCache implements CrateCacheService {

    private final Map<String, Crate> cache;

    public CrateCache() {
        cache = new HashMap<>();
    }

    @Override
    public void addCrate(String key, Crate crate) {
        cache.put(key, crate);
    }

    @Override
    public boolean hasCrate(String key) {
        return cache.containsKey(key);
    }

    @Override
    public void removeCrate(String key) {
        cache.remove(key);
    }

    @Override
    public Crate getCrate(String key) {
        return cache.get(key);
    }
}
