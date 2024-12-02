package brcomkassin.crate;

import brcomkassin.utils.ItemBuilder;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;


@Getter(value = AccessLevel.PROTECTED)
@Setter(value = AccessLevel.PROTECTED)
public abstract class Crate {

    private String name;
    private String id;
    private final EntityType entityType = EntityType.ARMOR_STAND;
    private Material item;

    public abstract void renderer();

}
