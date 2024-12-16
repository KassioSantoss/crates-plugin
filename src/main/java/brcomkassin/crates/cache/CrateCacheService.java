package brcomkassin.crates.cache;

import brcomkassin.crates.Crate;
import org.bukkit.Location;

import java.util.*;

public class CrateCacheService implements CrateCache {

    private final Map<String, Crate> cache;
    private final Set<String> cratesSet;
    private final Map<Location, Crate> locationCrateMap;

    public CrateCacheService() {
        this.cache = new HashMap<>();
        this.cratesSet = new HashSet<>();
        this.locationCrateMap = new HashMap<>();
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

    @Override
    public String getCrateId(String key) {
        return cache.get(key).id();
    }

    @Override
    public void addKeys(String key) {
        cratesSet.add(key);
    }

    @Override
    public List<String> getKeys() {
        if (cratesSet.isEmpty()) return null;
        return new ArrayList<>(cratesSet.stream().toList());
    }

    @Override
    public Crate getCrateByLocation(Location location) {
        return locationCrateMap.get(location);
    }

    @Override
    public void addCrateByLocation(Location location, Crate crate) {
        locationCrateMap.put(location, crate);
    }

    @Override
    public Map<Location, Crate> getLocationCrateMap() {
        return locationCrateMap;
    }

}
