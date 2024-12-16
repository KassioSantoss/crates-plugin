package brcomkassin.crates.services;

import brcomkassin.crates.Crate;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.ItemStack;

public interface CrateService {

     void summonCrate(Location location, Crate crate);
     void openCrate(Entity entity, ItemStack item);

}
