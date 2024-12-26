package brcomkassin.crates.rewards.animation;

import brcomkassin.CratesPlugin;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.ItemDisplay;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Transformation;

import java.awt.*;

public class RewardHandlerAnimation {

    public static void animReward(Entity crateEntity, ItemStack reward) {
        if (reward.getType() == Material.AIR) return;

        Location spawn = new Location(crateEntity.getWorld(), crateEntity.getX(), crateEntity.getY() + 0.6, crateEntity.getZ());
        ItemDisplay itemDisplay = crateEntity.getWorld().spawn(spawn, ItemDisplay.class);
        itemDisplay.setItemStack(reward);
        Transformation transformation = itemDisplay.getTransformation();
        transformation.getScale().set(1, 1, 1);
        task(reward);
    }

    private static void task(ItemStack item) {
        Bukkit.getScheduler().runTaskTimer(CratesPlugin.getInstance(), task -> new BukkitRunnable() {
            @Override
            public void run() {

            }
        }, 1, 1);
    }

}
