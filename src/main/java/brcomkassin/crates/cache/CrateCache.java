package brcomkassin.crates.cache;

import brcomkassin.CrateLocation;
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

    Crate getCrateByLocation(CrateLocation location);

    void addCrateByLocation(CrateLocation location, Crate crate);

    Map<CrateLocation, Crate> getLocationCrateMap();
}
