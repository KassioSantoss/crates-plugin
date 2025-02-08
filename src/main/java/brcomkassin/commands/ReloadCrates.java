package brcomkassin.commands;

import brcomkassin.CratesPlugin;
import brcomkassin.cachedepency.CacheDependencyResolverService;
import brcomkassin.reload.ReloadCratesPlugin;
import brcomkassin.utils.Message;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class ReloadCrates implements CommandExecutor {

    private final ReloadCratesPlugin reloadCrates;

    public ReloadCrates(CratesPlugin plugin, CacheDependencyResolverService resolverService) {
        this.reloadCrates = new ReloadCratesPlugin(resolverService, plugin);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (!(commandSender instanceof Player player)) return true;

        reloadCrates.reload();

        Message.Chat.send(player, "&6Plugin Crates Reiniciado com Sucesso!");
        return false;
    }
}
