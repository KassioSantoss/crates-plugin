package brcomkassin.crates.item;

import brcomkassin.crates.register.RegistrableItem;
import brcomkassin.utils.ItemBuilder;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

@Getter
public enum CrateType implements RegistrableItem {
    BLACK_CRATE(new ItemStack(Material.COAL), "Crate Test", "Lore da Crate", "black-crate");

    private final ItemStack item;
    private final String name;
    private final String lore;
    private final String id;

    CrateType(ItemStack item, String name, String lore, String id) {
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