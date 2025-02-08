package brcomkassin.crates.rewards.animation.service;

import brcomkassin.crates.Crate;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public interface AnimationService {

    void run(Player player, Entity crateEntity, Crate crate);

}
