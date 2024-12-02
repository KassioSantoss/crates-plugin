package brcomkassin.crates.cache;

import brcomkassin.crates.Crate;

public interface CrateCacheService {
    void addCrate(String key, Crate crate);

    boolean hasCrate(String key);

    void removeCrate(String key);

    Crate getCrate(String key);
}
