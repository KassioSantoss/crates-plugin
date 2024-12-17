package brcomkassin.crates.services;

import brcomkassin.crates.Crate;
import brcomkassin.crates.cache.CrateCache;
import brcomkassin.crates.manager.CrateManager;
import com.ticxo.modelengine.api.ModelEngineAPI;
import com.ticxo.modelengine.api.animation.handler.AnimationHandler;
import com.ticxo.modelengine.api.model.ActiveModel;
import com.ticxo.modelengine.api.model.ModeledEntity;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
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
        if (crateCache.getLocationCrateMap().containsKey(location.getBlock().getLocation().add(0.5, 0, 0.5))) return;

        Location centeredLocation = location.getBlock().getLocation().add(0.5, 0, 0.5);
        ArmorStand box = centeredLocation.getWorld().spawn(centeredLocation, ArmorStand.class);
        ModeledEntity modeledEntity = ModelEngineAPI.createModeledEntity(box);
        ActiveModel activeModel = ModelEngineAPI.createActiveModel(crate.entityModel());

        if (activeModel == null) {
            throw new RuntimeException("Não foi encontrado nenhum modelo com esse id de caixa: " + crate.id());
        }

        box.setVisible(false);
        box.setInvulnerable(true);
        box.setCanMove(false);
        box.setSmall(false);
        activeModel.setHitboxScale(1);
        modeledEntity.addModel(activeModel, true);

        crateCache.addCrateByLocation(centeredLocation, crate);
        player.sendMessage("Caixa spawnada com sucesso!");
    }

    @Override
    public void openCrate(Entity entity, ItemStack item) {
        if (!crateManager.isCrateEntity(entity)) return;

        ModeledEntity modeledEntity = ModelEngineAPI.getModeledEntity(entity);
        if (modeledEntity == null) return;

        Map<String, ActiveModel> activeModels = modeledEntity.getModels();
        if (activeModels.isEmpty()) return;

        boolean keyItem = crateManager.isKeyItem(item);

        if (!keyItem) return;

        Crate crate = crateManager.getCrateFromKey(item);
        Crate crateTarget = crateCache.getCrateByLocation(entity.getLocation());

        if (crate == null || crateTarget == null) return;

        if (!crate.id().equals(crateTarget.id())) return;

        ActiveModel activeModel = activeModels.get(crate.entityModel());
        AnimationHandler animationHandler = activeModel.getAnimationHandler();
        animationHandler.playAnimation("open", 0.3, 0.3, 1, true);
        if (!(entity instanceof Player player)) return;
        player.sendMessage("Caixa aberta com sucesso!");
    }

}
