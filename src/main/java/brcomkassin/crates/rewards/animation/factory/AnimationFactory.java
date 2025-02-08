package brcomkassin.crates.rewards.animation.factory;

import brcomkassin.cachedepency.CacheDependencyResolverService;
import brcomkassin.crates.rewards.RewardCalculator;
import brcomkassin.crates.rewards.animation.AnimationType;
import brcomkassin.crates.rewards.animation.effects.DefaultParticleEffect;
import brcomkassin.crates.rewards.animation.effects.FirstParticleEffect;
import brcomkassin.crates.rewards.animation.service.AnimationService;

import java.util.HashMap;
import java.util.Map;

public class AnimationFactory {
    private final Map<AnimationType, AnimationService> animations = new HashMap<>();
    private final RewardCalculator rewardCalculator;

    public AnimationFactory(CacheDependencyResolverService resolverService) {
        this.rewardCalculator = new RewardCalculator(resolverService);
        init();
    }

    private void init() {
        animations.put(AnimationType.DEFAULT, new DefaultParticleEffect(rewardCalculator));
        animations.put(AnimationType.FIRST, new FirstParticleEffect(rewardCalculator));
    }

    public AnimationService getAnimation(AnimationType animationType) {
        return animations.getOrDefault(animationType, new DefaultParticleEffect(rewardCalculator));
    }

}
