package brcomkassin.crates.register;

import org.bukkit.inventory.ItemStack;

public class GenericRegistry<T extends Enum<T> & RegistrableItem> implements Register {

    private final Class<T> enumClass;

    public GenericRegistry(Class<T> enumClass) {
        this.enumClass = enumClass;
    }

    @Override
    public void register() {
        for (T type : enumClass.getEnumConstants()) {
            ItemStack item = type.createItem();
            System.out.println("ID Registrado: " + type.getId() + " para o item " + item);
        }
    }
}
