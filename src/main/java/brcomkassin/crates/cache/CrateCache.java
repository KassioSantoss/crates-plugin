package brcomkassin.crates.cache;

import brcomkassin.crates.Crate;

public interface CrateCache {

    void add(String key, Crate crate);
    void remove(String key, Crate crate);
    void remove(String key);
    Crate getCrate(String key);

}
