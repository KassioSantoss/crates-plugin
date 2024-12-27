package brcomkassin.crates.rewards.animation;

import brcomkassin.CratesPlugin;
import brcomkassin.crates.Crate;
import brcomkassin.crates.rewards.Reward;
import brcomkassin.crates.rewards.cache.RewardCache;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.ItemDisplay;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Transformation;

public class RewardHandlerAnimation {

    private final RewardCache rewardCache;

    public RewardHandlerAnimation(RewardCache rewardCache) {
        this.rewardCache = rewardCache;
    }

    public void animReward(Entity crateEntity, ItemStack reward) {
        if (reward.getType() == Material.AIR) return;

        Location spawn = new Location(crateEntity.getWorld(), crateEntity.getX(), crateEntity.getY() + 0.6, crateEntity.getZ());
        ItemDisplay itemDisplay = crateEntity.getWorld().spawn(spawn, ItemDisplay.class);
        itemDisplay.setItemStack(reward);
        Transformation transformation = itemDisplay.getTransformation();
        transformation.getScale().set(1, 1, 1);
    }

    private void task(Crate crate, ItemStack reward) {
//        List<ItemRarity> itemRarities = rewardCache.getItemRarities(crate.id());
//        List<Reward> itemTemporary = new ArrayList<>();
//
//        for (ItemRarity itemRarity : itemRarities) {
//            List<Reward> rewards = itemRarity.getRewards();
//            itemTemporary.addAll(rewards);
//        }
//
//        Bukkit.getScheduler().runTaskTimer(CratesPlugin.getInstance(), task -> new BukkitRunnable() {
//
//            int value = 0;
//
//            @Override
//            public void run() {
//                Random random = new Random();
//                random.nextInt(itemRarities.size());
//            }
//
//        }, 1, 1);
    }

}
