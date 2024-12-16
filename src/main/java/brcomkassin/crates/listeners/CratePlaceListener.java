package brcomkassin.crates.listeners;

import brcomkassin.crates.Crate;
import brcomkassin.crates.manager.CrateManager;
import brcomkassin.crates.services.CrateService;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class CratePlaceListener implements Listener {

    private final CrateService crateService;
    private final CrateManager crateManager;

    public CratePlaceListener(CrateService crateService, CrateManager crateManager) {
        this.crateService = crateService;
        this.crateManager = crateManager;
    }

    @EventHandler
    public void onPlace(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack itemInMainHand = player.getInventory().getItemInMainHand();

        if (!crateManager.isCrateItem(itemInMainHand)) return;
        Crate crate = crateManager.getCrateFromItem(itemInMainHand);

        crateService.summonCrate(event.getInteractionPoint(), crate);
        player.sendMessage("Caixa spawnada com sucesso!");
    }

}
