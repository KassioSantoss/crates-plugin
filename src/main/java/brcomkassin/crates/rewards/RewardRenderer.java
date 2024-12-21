package brcomkassin.crates.rewards;

import org.bukkit.configuration.file.FileConfiguration;

public interface RewardRenderer {

    void renderer(FileConfiguration itemRarityConfiguration, FileConfiguration rarityConfiguration);

}
