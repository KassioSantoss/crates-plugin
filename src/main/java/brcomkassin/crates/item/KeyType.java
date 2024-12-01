package brcomkassin.crates.item;

import brcomkassin.crates.register.RegistrableItem;
import brcomkassin.utils.ItemBuilder;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

@Getter
public enum KeyType implements RegistrableItem {
    BLACK_KEY(new ItemStack(Material.DIAMOND), "Key Test", "Lore da Key", "black-crate");

    private final ItemStack item;
    private final String name;
    private final String lore;
    private final String id;

    KeyType(ItemStack item, String name, String lore, String id) {
        this.item = item;
        this.name = name;
        this.lore = lore;
        this.id = id;
    }

    @Override
    public ItemStack createItem() {
        return ItemBuilder.of(new ItemStack(item))
                .setName(name)
                .setLore(lore)
                .setItemMetaData(id)
                .build();
    }
}