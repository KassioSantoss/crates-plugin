package brcomkassin.crates.rewards.renderer;

import brcomkassin.cachedepency.CacheDependencyResolverService;
import brcomkassin.crates.rewards.Reward;
import brcomkassin.crates.rewards.rarity.RewardsRarity;
import brcomkassin.utils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Objects;

public class DefaultRewardRenderer implements RewardRendererService {

    private static DefaultRewardRenderer instance;

    private final CacheDependencyResolverService resolverService;

    public DefaultRewardRenderer(CacheDependencyResolverService resolverService) {
        this.resolverService = resolverService;
    }

    @Override
    public void renderer(FileConfiguration fileConfiguration) {

        ConfigurationSection rewardsSection = fileConfiguration.getConfigurationSection("rewards");
        Objects.requireNonNull(rewardsSection, "Invalid Configuration Section 'rewards' not found.");

        String PATH = "rewards.";
        for (String rewardsID : rewardsSection.getKeys(false)) {
            String itemType = fileConfiguration.getString(PATH + rewardsID + ".item_type", "DIAMOND_SWORD");
            String displayName = fileConfiguration.getString(PATH + rewardsID + ".display_name", "Item teste");
            List<String> lore = fileConfiguration.getStringList(PATH + rewardsID + ".lore");
            String namespace = fileConfiguration.getString(PATH + rewardsID + ".namespace", rewardsID);
            int customModelData = fileConfiguration.getInt(PATH + rewardsID + ".custom_model_data", 0);
            String rarityName = fileConfiguration.getString(PATH + rewardsID + ".rarity", RewardsRarity.COMMON.getRarityName());

            RewardsRarity rarity = RewardsRarity.getByString(rarityName);

            ItemStack rewardItem = ItemBuilder.of(Material.getMaterial(itemType))
                    .setName(displayName)
                    .setLore(lore)
                    .setNameSpacedKey(namespace)
                    .setCustomModelData(customModelData)
                    .build();

            Reward reward = Reward.of(rewardItem, displayName, lore, namespace, customModelData, rarity);
            resolverService.getRewardCache().addRewardById(rewardsID, reward);
            resolverService.getRewardCache().addRewardKey(rewardsID);

        }
    }

}

