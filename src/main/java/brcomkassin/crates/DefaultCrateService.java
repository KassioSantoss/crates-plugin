package brcomkassin.crates;

import brcomkassin.CratesPlugin;
import brcomkassin.crates.cache.CrateCacheService;
import com.ticxo.modelengine.api.ModelEngineAPI;
import com.ticxo.modelengine.api.animation.handler.AnimationHandler;
import com.ticxo.modelengine.api.model.ActiveModel;
import com.ticxo.modelengine.api.model.ModeledEntity;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import java.util.Map;

public class DefaultCrateService implements CrateService {

    private final CrateCacheService cacheService;

    public DefaultCrateService(CrateCacheService cacheService) {
        this.cacheService = cacheService;
    }

    @Override
    public void spawnCrate(Player player, Location location, String keyId) {
        ItemStack itemInHand = player.getInventory().getItemInMainHand();
        String id = getCrateIdFromItem(itemInHand, keyId);

        if (id == null) return;
        Crate crate = cacheService.getCrate(id);

        if (crate == null) return;
        summonCrate(location, crate);
    }

    @Override
    public void interactCrate(Entity entity, ItemStack item, String key) {
        if (!(entity instanceof ArmorStand)) return;

        ModeledEntity modeledEntity = ModelEngineAPI.getModeledEntity(entity);
        if (modeledEntity == null) return;

        Map<String, ActiveModel> activeModels = modeledEntity.getModels();
        if (activeModels.isEmpty()) return;

        String id = getCrateIdFromItem(item, key);
        Crate crate = cacheService.getCrate(id);

        if (crate == null) return;

        if (!crate.getId().equalsIgnoreCase(id)) return;

        ActiveModel activeModel = activeModels.get(crate.getId());
        AnimationHandler animationHandler = activeModel.getAnimationHandler();
        animationHandler.playAnimation("open", 0.3, 0.3, 1, true);
    }

    private void summonCrate(Location location, Crate crate) {
        ArmorStand box = location.getWorld().spawn(location.getBlock().getLocation(), ArmorStand.class);
        ModeledEntity modeledEntity = ModelEngineAPI.createModeledEntity(box);
        ActiveModel activeModel = ModelEngineAPI.createActiveModel(crate.getId());
        box.setVisible(false);
        box.setInvulnerable(true);
        box.setCanMove(false);
        box.setSmall(false);
        activeModel.setHitboxScale(1);
        modeledEntity.addModel(activeModel, true);
    }

    private String getCrateIdFromItem(ItemStack item, String keyId) {
        if (item == null || item.getType() == Material.AIR) return null;

        ItemMeta itemMeta = item.getItemMeta();
        if (itemMeta == null) return null;

        PersistentDataContainer container = itemMeta.getPersistentDataContainer();
        NamespacedKey namespacedKey = new NamespacedKey(CratesPlugin.getInstance(), keyId);
        return container.get(namespacedKey, PersistentDataType.STRING);
    }

}
