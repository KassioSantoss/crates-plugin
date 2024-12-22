package brcomkassin.crates.rewards.renderer;

import brcomkassin.crates.Crate;
import brcomkassin.crates.cache.CrateCache;
import brcomkassin.crates.rewards.rarity.ItemRarity;
import brcomkassin.crates.rewards.Reward;
import brcomkassin.crates.rewards.cache.RewardCache;
import brcomkassin.crates.rewards.rarity.RewardsRarity;
import brcomkassin.utils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
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

            Reward reward = new Reward(rewardItem, displayName, lore, namespace, customModelData);
            rewardCache.add(rewardsID, reward);
            rewardCache.addKeys(rewardsID);
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

        for (String crateID : raritySection.getKeys(false)) {
            Crate crate = crateCache.getCrateById(crateID);
            Logger.getGlobal().info(crate.nameSpace());

            String REWARD_PATH = "rarity." + crateID + ".rewards";
            ConfigurationSection rewardsSection = fileConfiguration.getConfigurationSection(REWARD_PATH);

            if (rewardsSection == null) continue;

            List<ItemRarity> itemRarities = new ArrayList<>();

            for (String rarityKey : rewardsSection.getKeys(false)) {
                RewardsRarity rewardsRarity;
                try {
                    rewardsRarity = RewardsRarity.getByString(rarityKey);
                } catch (IllegalArgumentException e) {
                    Logger.getGlobal().info("Raridade inválida encontrada: " + rarityKey);
                    continue;
                }

                String rewardPath = REWARD_PATH + "." + rarityKey;
                List<String> rewardIDs = fileConfiguration.getStringList(rewardPath);
                List<Reward> rewardList = new ArrayList<>();

                for (String rewardID : rewardIDs) {
                    Reward cachedReward = rewardCache.getReward(rewardID);
                    Objects.requireNonNull(cachedReward, "Recompensa não encontrada no cache: " + rewardID);
                    rewardList.add(cachedReward);
                }
                ItemRarity itemRarity = new ItemRarity(rewardsRarity, rewardList);
                itemRarities.add(itemRarity);
            }
            rewardCache.addItemRarity(crate.id(), itemRarities);
        }
    }
}

