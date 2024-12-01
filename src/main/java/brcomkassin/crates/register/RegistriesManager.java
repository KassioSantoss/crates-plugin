package brcomkassin.crates.register;

import brcomkassin.crates.item.CrateType;
import brcomkassin.crates.item.KeyType;

public class RegistriesManager {

    private final Register keyRegistries;
    private final Register cratesRegistries;

    public RegistriesManager() {
        this.keyRegistries = new GenericRegistry<>(KeyType.class);
        this.cratesRegistries = new GenericRegistry<>(CrateType.class);
    }

    public void registerAll() {
        keyRegistries.register();
        cratesRegistries.register();
    }

}
