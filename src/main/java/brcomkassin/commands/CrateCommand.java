package brcomkassin.commands;

import brcomkassin.crates.Crate;
import brcomkassin.crates.cache.CrateCache;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import java.util.ArrayList;
import java.util.List;

public class CrateCommand implements CommandExecutor, TabExecutor {

    private final CrateCache crateCache;

    public CrateCommand(CrateCache crateCache) {
        this.crateCache = crateCache;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (!(commandSender instanceof Player player)) return false;

        if (args.length < 1) {
            return true;
        }

        String argument = args[0];
        Crate crate = crateCache.getCrate(argument);

        if (crate == null) {
            player.sendMessage("Esse kit de caixa não existe");
            return true;
        }
        player.getInventory().addItem(crate.crateItem());
        player.getInventory().addItem(crate.keyItem());
        return false;
    }


    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        List<String> arguments = new ArrayList<>(crateCache.getKeys());
        if (args.length == 1) {
            return arguments;
        }
        return List.of();
    }
}
