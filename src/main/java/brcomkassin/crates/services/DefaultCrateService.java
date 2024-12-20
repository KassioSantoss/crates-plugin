package brcomkassin.crates.services;

import brcomkassin.CrateLocation;
import brcomkassin.CratesPlugin;
import brcomkassin.crates.Crate;
import brcomkassin.crates.cache.CrateCache;
import brcomkassin.crates.manager.CrateManager;
import brcomkassin.utils.ItemBuilder;
import com.ticxo.modelengine.api.ModelEngineAPI;
import com.ticxo.modelengine.api.animation.handler.AnimationHandler;
import com.ticxo.modelengine.api.model.ActiveModel;
import com.ticxo.modelengine.api.model.ModeledEntity;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.ItemDisplay;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Transformation;
import java.util.Map;

public class DefaultCrateService implements CrateService {

    private final CrateManager crateManager;
    private final CrateCache crateCache;

    public DefaultCrateService(CrateManager crateManager, CrateCache crateCache) {
        this.crateManager = crateManager;
        this.crateCache = crateCache;
    }

    @Override
    public void summonCrate(Player player, Location location, Crate crate) {
        Location centeredLocation = location.getBlock().getLocation().add(0.5, 0, 0.5);
        CrateLocation crateLocation = new CrateLocation(centeredLocation.getX(), centeredLocation.getY(), centeredLocation.getZ());

        if (crateCache.getLocationCrateMap().containsKey(crateLocation)) return;

        ArmorStand box = centeredLocation.getWorld().spawn(centeredLocation, ArmorStand.class);
        ModeledEntity modeledEntity = ModelEngineAPI.createModeledEntity(box);
        ActiveModel activeModel = ModelEngineAPI.createActiveModel(crate.baseEntityModel());

        if (activeModel == null) {
            throw new RuntimeException("Não foi encontrado nenhum modelo com esse id de caixa: " + crate.id());
        }

        double deltaX = player.getLocation().getX() - centeredLocation.getX();
        double deltaZ = player.getLocation().getZ() - centeredLocation.getZ();
        float yaw = (float) Math.toDegrees(Math.atan2(-deltaX, deltaZ));
        yaw -= 180.0f;

        box.setVisible(false);
        box.setInvulnerable(true);
        box.setCanMove(false);
        box.setSmall(false);
        box.setRotation(yaw, 0.0f);
        activeModel.setHitboxScale(1);
        modeledEntity.addModel(activeModel, true);
        crateCache.addCrateByLocation(crateLocation, crate);
        player.sendMessage("Caixa spawnada com sucesso!");
    }

    @Override
    public void openCrate(Player player, Entity entity, ItemStack item) {
        if (!crateManager.isCrateEntity(entity)) return;

        ModeledEntity modeledEntity = ModelEngineAPI.getModeledEntity(entity);
        if (modeledEntity == null) return;

        Map<String, ActiveModel> activeModels = modeledEntity.getModels();
        if (activeModels.isEmpty()) return;

        boolean keyItem = crateManager.isKeyItem(item);
        if (!keyItem) return;

        Crate crate = crateManager.getCrateFromKey(item);
        CrateLocation crateLocation = new CrateLocation(entity.getLocation().getX(), entity.getLocation().getY(), entity.getLocation().getZ());
        Crate crateTarget = crateCache.getCrateByLocation(crateLocation);

        if (crate == null || crateTarget == null) return;
        if (!crate.id().equals(crateTarget.id())) return;

        ActiveModel activeModel = activeModels.get(crate.baseEntityModel());
        AnimationHandler animationHandler = activeModel.getAnimationHandler();
        animationHandler.playAnimation(crate.animation(), 0.3, 0.3, 1, true);
        ItemStack itemStack = new ItemStack(Material.DIAMOND_SWORD);
        animReward(player, entity, item);
        player.sendMessage("Caixa aberta com sucesso!");
    }

    private void animReward(Player player, Entity crateEntity, ItemStack item) {
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
            double valueTemporario = 0;

            @Override
            public void run() {
                if (value >= 150) {
                    valueTemporario = 0;
                    itemDisplay.remove();
                    cancel();
                    return;
                }
                if (valueTemporario <= 1.2) {
                    valueTemporario += 0.03;
                }
                transformation.getLeftRotation().rotateY(0.08F);
                itemDisplay.setTransformation(transformation);
                double y = spawn.getY() + valueTemporario;
                Location teleportLocation = new Location(spawn.getWorld(), spawn.getX(), y, spawn.getZ());
                itemDisplay.teleport(teleportLocation);
                value++;
            }
        }.runTaskTimer(CratesPlugin.getInstance(), 0, 1);
    }

}
