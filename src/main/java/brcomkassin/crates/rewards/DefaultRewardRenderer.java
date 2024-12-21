package brcomkassin.crates.rewards;

import brcomkassin.crates.rewards.cache.RewardCache;
import brcomkassin.crates.rewards.rarity.RewardsRarity;
import brcomkassin.utils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.PluginLogger;
import java.util.List;

public class DefaultRewardRenderer implements RewardRenderer {

    private final RewardCache rewardCache;

    public DefaultRewardRenderer(RewardCache rewardCache) {
        this.rewardCache = rewardCache;
    }

    @Override
    public void renderer(FileConfiguration itemRarityConfiguration, FileConfiguration rarityConfiguration) {
        loadItemsReward(itemRarityConfiguration, rarityConfiguration);
    }

    private void loadItemsReward(FileConfiguration itemRarityConfiguration, FileConfiguration rarityConfiguration) {
        if (!itemRarityConfiguration.contains("rewards")) {
            throw new IllegalArgumentException("Configuração inválida! Seção 'rewards' não encontrada.");
        }
        ConfigurationSection rewardsSection = itemRarityConfiguration.getConfigurationSection("rewards");

        if (rewardsSection == null) {
            throw new IllegalArgumentException("Configuração inválida! rewards 'crates' não encontrada."); // depois arrumar exceçoes
        }

        String PATH = "rewards.";

        for (String rewardsID : rewardsSection.getKeys(false)) {
            String itemType = itemRarityConfiguration.getString(PATH + rewardsID + ".item_type", "DIAMOND_SWORD");
            String displayName = itemRarityConfiguration.getString(PATH + rewardsID + ".display_name", "Item teste");
            List<String> lore = itemRarityConfiguration.getStringList(PATH + rewardsID + ".lore");
            String namespace = itemRarityConfiguration.getString(PATH + rewardsID + ".namespace", rewardsID);
            int customModelData = itemRarityConfiguration.getInt(PATH + rewardsID + ".custom_model_data", 0);

            ItemStack rewardItem = ItemBuilder.of(Material.getMaterial(itemType))
                    .setName(displayName)
                    .setLore(lore)
                    .setNameSpacedKey(namespace)
                    .setCustomModelData(customModelData)
                    .build();

            Reward reward = new Reward(rewardItem, displayName, lore, namespace, customModelData);
            rewardCache.add(rewardsID, reward);
            loadItemsRarity(rarityConfiguration);
        }
    }

    private void loadItemsRarity(FileConfiguration fileConfiguration) {
        if (!fileConfiguration.contains("rarity")) {
            throw new IllegalArgumentException("Configuração inválida! Seção 'rarity' não encontrada.");
        }

        ConfigurationSection raritySection = fileConfiguration.getConfigurationSection("rarity");

        if (raritySection == null) {
            throw new IllegalArgumentException("Configuração inválida! Seção 'rarity' não encontrada.");
        }

        String PATH = "rarity.";

        for (String crateID : raritySection.getKeys(false)) {
            String REWARD_PATH = PATH + crateID + ".rewards";
            ConfigurationSection rewardsSection = fileConfiguration.getConfigurationSection(REWARD_PATH);

            if (rewardsSection == null) continue;

            for (String rarityKey : rewardsSection.getKeys(false)) {
                RewardsRarity rewardsRarity;

                try {
                    rewardsRarity = RewardsRarity.getByString(rarityKey);
                } catch (IllegalArgumentException e) {
                    PluginLogger.getGlobal().info("Raridade inválida encontrada: " + rarityKey);
                    continue;
                }

                List<String> rewardIDs = fileConfiguration.getStringList(REWARD_PATH + "." + rarityKey);

                for (String rewardID : rewardIDs) {
                    Reward cachedReward = rewardCache.getReward(rewardID);

                    if (cachedReward == null) {
                        PluginLogger.getGlobal().info("Recompensa não encontrada no cache: " + rewardID);
                        return;
                    }
                    rewardCache.add(cachedReward, rewardsRarity);
                }
            }
        }
    }

}
