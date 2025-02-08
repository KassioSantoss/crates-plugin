package brcomkassin.commands;

import brcomkassin.cachedepency.CacheDependencyResolverService;
import brcomkassin.crates.Crate;
import brcomkassin.crates.cache.CrateCache;
import brcomkassin.crates.cache.CrateCacheService;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class CrateCommand implements CommandExecutor, TabExecutor {

    private final CacheDependencyResolverService resolverService;

    public CrateCommand(CacheDependencyResolverService resolverService) {
        this.resolverService = resolverService;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (!(commandSender instanceof Player player)) return false;

        if (args.length < 1) {
            return true;
        }

        String argument = args[0];
        Crate crate = resolverService.getCrateCache().getCrateById(argument);

        if (crate == null) {
            player.sendMessage("Esse kit de caixa não existe");
            return true;
        }
        player.getInventory().addItem(crate.getCrateItem());
        player.getInventory().addItem(crate.getKey().getItem());
        return false;
    }


    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        List<String> arguments = resolverService.getCrateCache().getCratesByIdList();
        if (args.length == 1) {
            return arguments;
        }
        return List.of();
    }
}
