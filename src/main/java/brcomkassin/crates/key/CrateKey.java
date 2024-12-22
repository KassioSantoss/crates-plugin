package brcomkassin.crates.key;

import lombok.Data;
import org.bukkit.inventory.ItemStack;

@Data(staticConstructor = "of") public class CrateKey {

    private final String keyDisplayName;
    private final int keyCustomModelData;
    private final String namespace;
    private final ItemStack item;

}
