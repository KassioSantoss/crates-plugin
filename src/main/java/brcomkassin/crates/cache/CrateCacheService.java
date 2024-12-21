package brcomkassin.crates.cache;

import brcomkassin.crates.CrateLocation;
import brcomkassin.crates.Crate;

import java.util.*;

public class CrateCacheService implements CrateCache {

    private final Map<String, Crate> cache;
    private final Set<String> cratesSet;
    private final Map<CrateLocation, Crate> locationCrateMap;

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
    public Crate getCrateByLocation(CrateLocation location) {
        return locationCrateMap.get(location);
    }

    @Override
    public void addCrateByLocation(CrateLocation location, Crate crate) {
        locationCrateMap.put(location, crate);
    }

    @Override
    public Map<CrateLocation, Crate> getLocationCrateMap() {
        return locationCrateMap;
    }

}
