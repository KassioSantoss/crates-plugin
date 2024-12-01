package brcomkassin.command;

import brcomkassin.crates.item.CrateItem;
import brcomkassin.crates.item.CrateType;
import brcomkassin.crates.item.KeyItem;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class CrateCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (!(commandSender instanceof Player player)) return true;

        player.getInventory().addItem(KeyItem.BLACK_KEY);
        player.getInventory().addItem(CrateType.BLACK_CRATE.getItem());
        player.sendMessage("Voce recebeu um kit de caixas!");
        return false;
    }
}
