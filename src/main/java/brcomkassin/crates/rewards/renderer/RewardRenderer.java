package brcomkassin.crates.rewards.renderer;

import org.bukkit.configuration.file.FileConfiguration;

public interface RewardRenderer {

    void renderer(FileConfiguration itemRarityConfiguration, FileConfiguration rarityConfiguration);

}
