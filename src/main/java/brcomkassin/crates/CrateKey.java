package brcomkassin.crates;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.inventory.ItemStack;

@Getter
@Setter
@AllArgsConstructor
public class CrateKey {
    private String keyDisplayName;
    private int keyCustomModelData;
    private String namespace;
    private ItemStack item;
}
