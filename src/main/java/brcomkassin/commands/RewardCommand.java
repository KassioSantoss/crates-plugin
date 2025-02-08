package brcomkassin.commands;

import brcomkassin.cachedepency.CacheDependencyResolverService;
import brcomkassin.crates.rewards.Reward;
import brcomkassin.crates.rewards.cache.RewardCache;
import brcomkassin.crates.rewards.cache.RewardCacheService;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class RewardCommand implements CommandExecutor, TabExecutor {

    private final CacheDependencyResolverService resolverService;

    public RewardCommand(CacheDependencyResolverService resolverService) {
        this.resolverService = resolverService;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (!(commandSender instanceof Player player)) return false;

        if (args.length < 1) {
            return true;
        }
        Reward reward = resolverService.getRewardCache().findRewardById(args[0]);
        player.getInventory().addItem(reward.getItem());
        return false;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        final List<String> keys = resolverService.getRewardCache().getAllRewardKeys();
        final List<String> arguments = new ArrayList<>(keys);
        return args.length == 1 ? arguments : List.of();
    }
}
