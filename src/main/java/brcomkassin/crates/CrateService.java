package brcomkassin.crates;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public interface CrateService {

    void spawnCrate(Player player, Location location, String keyId);

    void interactCrate(Entity entity, ItemStack item,String id);

}
