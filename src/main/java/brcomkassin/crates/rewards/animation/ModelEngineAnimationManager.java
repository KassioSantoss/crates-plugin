package brcomkassin.crates.rewards.animation;

import com.ticxo.modelengine.api.ModelEngineAPI;
import com.ticxo.modelengine.api.animation.handler.AnimationHandler;
import com.ticxo.modelengine.api.model.ActiveModel;
import com.ticxo.modelengine.api.model.ModeledEntity;
import org.bukkit.entity.Entity;

import java.util.Map;

public class ModelEngineAnimationManager {

    public static void play(Entity entity, String baseModel, String animation) {
        ModeledEntity modeledEntity = ModelEngineAPI.getModeledEntity(entity);
        if (modeledEntity == null) return;

        Map<String, ActiveModel> activeModels = modeledEntity.getModels();

        if (activeModels.isEmpty()) return;

        ActiveModel activeModel = activeModels.get(baseModel);
        AnimationHandler animationHandler = activeModel.getAnimationHandler();
        animationHandler.playAnimation(animation, 0.3, 0.3, 1, true);
    }

    public static class AnimationType {
        public final static String OPEN = "open";
    }

}
