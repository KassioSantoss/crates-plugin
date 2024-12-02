package brcomkassin.crate.cache;

import brcomkassin.crate.Crate;

public interface CrateCacheService {

    void addCrate(String key, Crate crate);
    void removeCrate(String key);

}
