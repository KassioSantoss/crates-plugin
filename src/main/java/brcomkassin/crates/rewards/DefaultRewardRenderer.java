package brcomkassin.crates.rewards;

import brcomkassin.crates.Crate;
import brcomkassin.crates.cache.CrateCache;
import brcomkassin.crates.rewards.cache.RewardCache;
import brcomkassin.crates.rewards.rarity.RewardsRarity;
import brcomkassin.utils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.PluginLogger;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.logging.Logger;

public class DefaultRewardRenderer implements RewardRenderer {

    private final RewardCache rewardCache;
    private final CrateCache crateCache;

    public DefaultRewardRenderer(RewardCache rewardCache, CrateCache crateCache) {
        this.rewardCache = rewardCache;
        this.crateCache = crateCache;
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
            throw new IllegalArgumentException("Configuração inválida! rewards 'crates' não encontrada.");
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

            Reward reward = new Reward(rewardItem, displayName, lore, namespace, customModelData, RewardsRarity.COMMON);
            rewardCache.add(rewardsID, reward);
            rewardCache.addKeys(rewardsID);

            Logger.getGlobal().info(rewardCache.getReward(rewardsID).getDisplayName());
            Logger.getGlobal().info(rewardCache.getKeys().toString());
        }
        loadItemsRarity(rarityConfiguration);
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
            Crate crate = crateCache.getCrateById(crateID);
            Logger.getGlobal().info(crate.nameSpace());
            Logger.getGlobal().info(crate.nameSpace());
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
                List<Reward> rewardList = new ArrayList<>();
                List<ItemRarity> itemRarities = new ArrayList<>();

                for (String rewardID : rewardIDs) {
                    Reward cachedReward = rewardCache.getReward(rewardID);
                    if (cachedReward == null) {
                        PluginLogger.getGlobal().info("Recompensa não encontrada no cache: " + rewardID);
                        continue;
                    }
                    cachedReward.setRarity(rewardsRarity);
                    rewardList.add(cachedReward);
                }
                itemRarities.add(new ItemRarity(rewardsRarity, rewardList));
                rewardCache.addItemRarity(crate.id(), itemRarities);
                Logger.getGlobal().info("========================================");
                Logger.getGlobal().info("getItemRarities -> " + rewardCache.getItemRarities(crate.id()).toString());
                Logger.getGlobal().info("========================================");
            }
        }
    }
}

