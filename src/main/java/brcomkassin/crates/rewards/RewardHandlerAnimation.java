package brcomkassin.crates.rewards;

import brcomkassin.CratesPlugin;
import brcomkassin.utils.ItemBuilder;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.ItemDisplay;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Transformation;

public class RewardHandlerAnimation {

    private static void animReward(Player player, Entity crateEntity, ItemStack item) {
        if (item.getType() == Material.AIR) return;

        Location spawn = new Location(crateEntity.getWorld(), crateEntity.getX(), crateEntity.getY() + 0.6, crateEntity.getZ());
        ItemDisplay itemDisplay = crateEntity.getWorld().spawn(spawn, ItemDisplay.class);

        ItemMeta itemMeta = item.getItemMeta();
        item = ItemBuilder.of(item)
                .build();

        itemDisplay.setItemStack(item);

        if (itemMeta != null && itemMeta.hasCustomModelData()) {
            item = ItemBuilder.of(item)
                    .setCustomModelData(itemMeta.getCustomModelData())
                    .build();
        }

        Transformation transformation = itemDisplay.getTransformation();
        transformation.getScale().set(1, 1, 1);

        BukkitTask task = new BukkitRunnable() {
            int value = 0;
            double temporaryValue = 0;

            @Override
            public void run() {
                if (value >= 150) {
                    temporaryValue = 0;
                    itemDisplay.remove();
                    cancel();
                    return;
                }
                if (temporaryValue <= 1.2) {
                    temporaryValue += 0.03;
                }
                transformation.getLeftRotation().rotateY(0.08F);
                itemDisplay.setTransformation(transformation);
                double y = spawn.getY() + temporaryValue;
                Location teleportLocation = new Location(spawn.getWorld(), spawn.getX(), y, spawn.getZ());
                itemDisplay.teleportAsync(teleportLocation);
                value++;
            }
        }.runTaskTimer(CratesPlugin.getInstance(), 0, 1);
    }

}
