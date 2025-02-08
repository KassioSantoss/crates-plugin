package brcomkassin.crates.cache;

import brcomkassin.crates.Crate;

import java.util.List;

public interface CrateCacheService {

    void add(String key, Crate crate);

    void remove(String key);

    Crate getCrate(String key);

    void addNameSpacedToList(String key);

    List<String> getNameSpacedList();

    List<String> getCratesByIdList();

    void addCrateById(String name, Crate crate);

    Crate getCrateById(String name);

    void clear();
}
