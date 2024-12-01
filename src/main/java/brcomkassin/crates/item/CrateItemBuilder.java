package brcomkassin.crates.item;

import brcomkassin.utils.ItemBuilder;
import org.bukkit.inventory.ItemStack;

public class CrateItemBuilder {

    public static ItemStack build(CrateType crateType) {
        return ItemBuilder.of(crateType.getItem())
                .setName(crateType.getName())
                .setLore(crateType.getLore())
                .setItemMetaData(crateType.getId())
                .build();
    }

}
