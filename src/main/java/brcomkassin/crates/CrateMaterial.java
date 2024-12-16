package brcomkassin.crates;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.Material;

@Getter
@AllArgsConstructor
public enum CrateMaterial {
    DEFAULT_CRATE_MATERIAL(Material.GOLD_INGOT),
    DEFAULT_KEY_MATERIAL(Material.STICK);

    private final Material material;
}
