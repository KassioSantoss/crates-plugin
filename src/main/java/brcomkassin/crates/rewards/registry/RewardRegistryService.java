package brcomkassin.crates.rewards.registry;

import org.bukkit.configuration.file.FileConfiguration;

public interface RewardRegistryService {
    void registry(FileConfiguration crateFileConfiguration);
}
