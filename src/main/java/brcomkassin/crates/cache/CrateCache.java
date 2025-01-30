package brcomkassin.crates.cache;

import brcomkassin.crates.Crate;

import java.util.*;

public class CrateCache implements CrateCacheService {

    private final Map<String, Crate> cache;
    private final List<String> cratesNameSpacedList;
    private final Map<String, Crate> crateIdMap;

    public CrateCache() {
        this.cache = new HashMap<>();
        this.cratesNameSpacedList = new ArrayList<>();
        this.crateIdMap = new HashMap<>();
    }

    @Override
    public void add(String key, Crate crate) {
        cache.put(key, crate);
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
    public void addNameSpacedToList(String key) {
        cratesNameSpacedList.add(key);
    }

    @Override
    public List<String> getNameSpacedList() {
        return cratesNameSpacedList;
    }

    @Override
    public List<String> getCratesByIdList() {
        return crateIdMap.keySet().stream().toList();
    }

    @Override
    public void addCrateById(String name, Crate crate) {
        crateIdMap.put(name, crate);
    }

    @Override
    public Crate getCrateById(String name) {
        return crateIdMap.get(name);
    }

}
