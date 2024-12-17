package brcomkassin.crates.listeners;

import brcomkassin.crates.services.CrateService;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;

public class CrateInteractListener implements Listener {

    private final CrateService crateService;

    public CrateInteractListener(CrateService crateService) {
        this.crateService = crateService;
    }

    @EventHandler
    public void onInteract(PlayerInteractEntityEvent event) {
        Player player = event.getPlayer();
        Entity entity = event.getRightClicked();
        ItemStack itemInMainHand = player.getInventory().getItemInMainHand();
        crateService.openCrate(entity, itemInMainHand);
    }

}
