package brcomkassin.listener;

import brcomkassin.CratesPlugin;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;

public class CratePlaceListener implements Listener {

    @EventHandler
    public void onPlace(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        ItemStack itemInHand = event.getItemInHand();

        if (itemInHand.getType() != Material.COAL) return;

        if (!itemInHand.getItemMeta().getPersistentDataContainer()
                .has(new NamespacedKey(CratesPlugin.getInstance(), "black-crate"))) return;

        crateService.spawn();

    }

}
