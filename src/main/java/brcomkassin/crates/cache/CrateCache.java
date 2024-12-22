package brcomkassin.crates.cache;

import brcomkassin.crates.CrateLocation;
import brcomkassin.crates.Crate;

import java.util.List;
import java.util.Map;

public interface CrateCache {

    void add(String key, Crate crate);

    void remove(String key, Crate crate);

    void remove(String key);

    Crate getCrate(String key);

    void addKeys(String key);

    List<String> getKeys();

    Crate getCrateByLocation(CrateLocation location);

    void addCrateByLocation(CrateLocation location, Crate crate);

    Map<CrateLocation, Crate> getLocationCrateMap();

    void addCrateById(String name, Crate crate);

    Crate getCrateById(String name);
}
