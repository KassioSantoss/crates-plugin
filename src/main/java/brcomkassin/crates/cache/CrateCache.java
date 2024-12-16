package brcomkassin.crates.cache;

import brcomkassin.crates.Crate;
import org.bukkit.Location;

import java.util.List;
import java.util.Map;

public interface CrateCache {

    void add(String key, Crate crate);

    void remove(String key, Crate crate);

    void remove(String key);

    Crate getCrate(String key);

    String getCrateId(String key);

    void addKeys(String key);

    List<String> getKeys();

    Crate getCrateByLocation(Location location);

    void addCrateByLocation(Location location, Crate crate);

    Map<Location, Crate> getLocationCrateMap();
}
