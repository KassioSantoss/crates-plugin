package brcomkassin.crates.register;

import org.bukkit.inventory.ItemStack;

public interface RegistrableItem {
    ItemStack createItem();
    String getId();
}

