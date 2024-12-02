package brcomkassin.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class CrateCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(commandSender instanceof Player player)) return true;

        if (args.length < 1) {
            return true;
        }

        switch (args[0]) {
            case "newCrate":
                if (args.length < 2) {
                    player.sendMessage("Uso correto: /crate newCrate <name>");
                    return true;
                }
                break;
            case "newKey":
                if (args.length < 2) {
                    player.sendMessage("Uso correto: /crate newKey <name>");
                    return true;
                }

                break;
        }
        return false;
    }
}
