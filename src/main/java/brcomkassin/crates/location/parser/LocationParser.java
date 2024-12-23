package brcomkassin.crates.location.parser;

import brcomkassin.crates.location.CrateLocation;
import org.bukkit.Bukkit;
import org.bukkit.Location;

public class LocationParser {
    public static CrateLocation from(String locationString) {
        final String[] split = locationString.split(":");
        final String world = split[0];
        final Location location = new Location(
                Bukkit.getWorld(world),
                Double.parseDouble(split[1]),
                Double.parseDouble(split[2]),
                Double.parseDouble(split[3])
        );

        return CrateLocation.of(location);
    }
}
