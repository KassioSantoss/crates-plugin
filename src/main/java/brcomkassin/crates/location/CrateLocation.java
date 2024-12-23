package brcomkassin.crates.location;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.bukkit.Location;

@Data
@AllArgsConstructor
public final class CrateLocation {

    private final String world;
    private final double x, y, z;

    private CrateLocation(Location location) {
        this.world = location.getWorld().getName();
        this.x = location.getX();
        this.y = location.getY();
        this.z = location.getZ();
    }

    public static CrateLocation of(Location location) {
        return new CrateLocation(location);
    }

    @Override
    public String toString() {
        return this.world + ":" + this.x + ":" + this.y + ":" + this.z;
    }
}
