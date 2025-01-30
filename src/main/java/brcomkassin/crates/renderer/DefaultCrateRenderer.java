package brcomkassin.crates.renderer;

import brcomkassin.crates.Crate;
import brcomkassin.crates.builder.CrateBuilder;
import brcomkassin.crates.cache.CrateCacheService;
import brcomkassin.crates.rewards.Reward;
import brcomkassin.crates.rewards.cache.RewardCache;
import brcomkassin.utils.ColoredLogger;
import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class DefaultCrateRenderer implements CrateRendererService {

    private final CrateCacheService crateCacheService;
    private final RewardCache rewardCache;

    public DefaultCrateRenderer(CrateCacheService crateCacheService, RewardCache rewardCache) {
        this.crateCacheService = crateCacheService;
        this.rewardCache = rewardCache;
    }

    @Override
    public void load(FileConfiguration config) {
        if (!config.contains("crates")) {
            throw new IllegalArgumentException("Configuração inválida! Seção 'crates' não encontrada.");
        }

        ConfigurationSection crates = config.getConfigurationSection("crates");

        Objects.requireNonNull(crates, "Configuração inválida! Seção 'crates' não encontrada.");

        int cratesAmount = 0;
        String PATH = "crates.";

        for (String crateID : crates.getKeys(false)) {
            String namespace = config.getString(PATH + crateID + ".namespace", "example_id");
            String crateDisplayName = config.getString(PATH + crateID + ".display_name", ChatColor.GREEN + "Caixa de Teste");
            int crateCustomModelData = config.getInt(PATH + crateID + ".crate_item_model.custom_model_data", 0);
            int keyCustomModelData = config.getInt(PATH + crateID + ".key_item.custom_model_data", 0);
            String keyDisplayName = config.getString(PATH + crateID + "key_item.display_name", ChatColor.GREEN + " Chave de Teste");
            String baseEntityModel = config.getString(PATH + crateID + ".base_entity_model.model_id", "crate_example");

            String animation = config.getString(PATH + crateID + ".base_entity_model.animation", "open");
            List<String> rewardsName = config.getStringList(PATH + crateID + ".rewards");

            showRenderedCrates("&a",
                    "&9==============================================================================&a",
                    "&a" + crateID + "&b -> namespace: &a" + namespace,
                    "&a" + crateID + "&b -> crateDisplayName: &a" + crateDisplayName,
                    "&a" + crateID + "&b -> crateCustomModelData: &a" + crateCustomModelData,
                    "&a" + crateID + "&b -> keyDisplayName: &a" + keyDisplayName,
                    "&a" + crateID + "&b -> keyCustomModelData: &a" + keyCustomModelData,
                    "&a" + crateID + "&b -> baseEntityModel: &a" + baseEntityModel,
                    "&a" + crateID + "&b -> animation: &a" + animation,
                    "&a" + crateID + "&b -> rewards: &a" + rewardsName,
                    "&9==============================================================================&a"
            );

            List<Reward> rewardList = new ArrayList<>();

            for (String rewardName : rewardsName) {
                Reward reward = rewardCache.findRewardById(rewardName);
                rewardList.add(reward);
            }

            Crate crate = CrateBuilder.builder()
                    .setCrateKey(keyDisplayName, keyCustomModelData, namespace)
                    .setId(crateID)
                    .setNameSpace(namespace)
                    .setCrateDisplayName(crateDisplayName)
                    .setCrateCustomModelData(crateCustomModelData)
                    .setBaseEntityModel(baseEntityModel)
                    .setAnimation(animation)
                    .addRewards(rewardList)
                    .build();

            crateCacheService.add(crate.getNameSpace(), crate);
            crateCacheService.addNameSpacedToList(crate.getNameSpace());
            crateCacheService.addCrateById(crate.getId(), crate);
            rewardCache.addRewardsForCrate(crate, rewardList);
            cratesAmount++;
        }
        ColoredLogger.info("&aQuantidade de caixas e keys carregadas: &5" + cratesAmount);
    }

    public void showRenderedCrates(String... strings) {
        for (String string : strings) {
            ColoredLogger.info(string);
        }
    }

}
