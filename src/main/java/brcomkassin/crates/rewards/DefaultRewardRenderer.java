package brcomkassin.crates.rewards;

import brcomkassin.crates.Crate;
import brcomkassin.crates.builder.CrateBuilder;
import brcomkassin.crates.cache.CrateCache;
import brcomkassin.crates.rewards.cache.RewardCache;
import brcomkassin.utils.ItemBuilder;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.PluginLogger;

import java.io.File;
import java.util.List;

public class DefaultRewardRenderer implements RewardRenderer {

    private final RewardCache rewardCache;

    public DefaultRewardRenderer(RewardCache rewardCache) {
        this.rewardCache = rewardCache;
    }

    @Override
    public void renderer(FileConfiguration fileConfiguration) {
        loadItemsReward(fileConfiguration);
    }

    private void loadItemsReward(FileConfiguration fileConfiguration) {
        if (!fileConfiguration.contains("rewards")) {
            throw new IllegalArgumentException("Configuração inválida! Seção 'rewards' não encontrada.");
        }
        ConfigurationSection rewardsSection = fileConfiguration.getConfigurationSection("rewards");

        if (rewardsSection == null) {
            throw new IllegalArgumentException("Configuração inválida! rewards 'crates' não encontrada."); // depois arrumar exceçoes
        }

        int cratesAmount = 0;
        String PATH = "rewards.";

        for (String rewardsID : rewardsSection.getKeys(false)) {
            String namespace = fileConfiguration.getString(PATH + rewardsID + ".namespace", rewardsID);
            String crateDisplayName = fileConfiguration.getString(PATH + rewardsID + ".display_name", "Caixa Sem Nome");
            int crateCustomModelData = fileConfiguration.getInt(PATH + rewardsID + ".crate_item_model.custom_model_data", 0);
            String keyDisplayName = fileConfiguration.getString(PATH + rewardsID + ".key_item.display_name", "Chave Sem Nome");
            int keyCustomModelData = fileConfiguration.getInt(PATH + rewardsID + ".key_item.custom_model_data", 0);
            String baseEntityModel = fileConfiguration.getString(PATH + rewardsID + ".base_entity_model.model_id", "crate_example");
            String animation = fileConfiguration.getString(PATH + rewardsID + ".base_entity_model.animation", "open");
            List<String> rewards = fileConfiguration.getStringList(PATH + rewardsID + ".rewards");

            ItemStack rewardItem = ItemBuilder.of().build();

            Reward reward = new Reward();

            cratesAmount++;
        }
    }

}
