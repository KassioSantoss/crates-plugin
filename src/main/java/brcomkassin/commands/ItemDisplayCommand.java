package brcomkassin.commands;

import brcomkassin.CratesPlugin;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.ItemDisplay;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Transformation;
import org.jetbrains.annotations.NotNull;

public class ItemDisplayCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (!(commandSender instanceof Player player)) return false;
        animReward(player, player.getInventory().getItemInMainHand().getType());
        return false;
    }

    private void animReward(Entity entity, Material material) {
        if (material == Material.AIR) return;

        Location spawn = new Location(entity.getWorld(),entity.getX(),entity.getY() + 1,entity.getZ());
        ItemDisplay itemDisplay = entity.getWorld().spawn(spawn, ItemDisplay.class);
        ItemStack itemStack = new ItemStack(material);
        itemDisplay.setItemStack(itemStack);
        BukkitTask task = new BukkitRunnable() {
            int value = 0;

            @Override
            public void run() {
                if (value >= 150) {
                    entity.sendMessage("cancelou");
                    cancel();
                    return;
                }
                Transformation transformation = itemDisplay.getTransformation();
                transformation.getLeftRotation().rotateY(0.1F);
                itemDisplay.setTransformation(transformation);
                value++;
            }
        }.runTaskTimer(CratesPlugin.getInstance(), 0, 1);
    }
}
