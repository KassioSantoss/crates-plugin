package brcomkassin.crates.location.parser;

import brcomkassin.crates.Crate;
import brcomkassin.crates.cache.CrateCache;

public class CrateParser {
    public static Crate fromString(CrateCache cache, String crate) {
        final String[] split = crate.split(":");
        final String crateId = split[4];
        return cache.getCrateById(crateId);
    }
}
