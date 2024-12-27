package brcomkassin.crates.location.renderer;

import brcomkassin.crates.Crate;
import brcomkassin.crates.cache.CrateCache;
import brcomkassin.crates.location.CrateLocation;
import brcomkassin.crates.location.parser.CrateParser;
import brcomkassin.crates.location.parser.LocationParser;
import org.bukkit.configuration.file.FileConfiguration;
import java.util.List;
import java.util.logging.Logger;

public class DefaultCrateLocationRenderer implements CrateLocationRenderer {

    private final CrateCache crateCache;

    public DefaultCrateLocationRenderer(CrateCache crateCache) {
        this.crateCache = crateCache;
    }

    @Override
    public void renderer(FileConfiguration fileConfiguration) {
        if (!fileConfiguration.contains("crateLocations")) {
            throw new IllegalArgumentException("Configuração inválida! Seção 'crateLocations' não encontrada.");
        }

        List<String> stringLocations = fileConfiguration.getStringList("crateLocations.locations");

        if (stringLocations.isEmpty()) {
            Logger.getGlobal().warning("A seção 'crateLocations' existe, mas está vazia.");
            return;
        }

        for (String location : stringLocations) {
            Crate crate = CrateParser.fromString(crateCache, location);
            CrateLocation crateLocation = LocationParser.from(location);
            Logger.getGlobal().info("crateLocation: " + crateLocation.toString());
            Logger.getGlobal().info("crate: " + crate.getId());
            crateCache.addCrateByLocation(crateLocation, crate);
        }
    }

}
