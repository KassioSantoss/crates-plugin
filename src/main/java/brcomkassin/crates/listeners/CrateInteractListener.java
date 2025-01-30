package brcomkassin.crates.listeners;

import brcomkassin.crates.rewards.animation.RewardAnimationHandler;
import brcomkassin.crates.services.CrateService;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerMoveEvent;
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
        crateService.openCrate(player, entity, itemInMainHand);
    }

    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        if (RewardAnimationHandler.PLAYERS.contains(event.getPlayer())) {
            event.setCancelled(true);
        }
    }

}
