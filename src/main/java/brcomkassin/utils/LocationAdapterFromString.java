package brcomkassin.utils;

import org.bukkit.Bukkit;
import org.bukkit.Location;

public class LocationAdapterFromString {

    public static Location from(String location) {
        final String[] split = location.split(":");
        final String world = split[0];
        return new Location(
                Bukkit.getWorld(world),
                Double.parseDouble(split[1]),
                Double.parseDouble(split[2]),
                Double.parseDouble(split[3])
        );
    }
}
