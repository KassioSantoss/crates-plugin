package brcomkassin.crate;

import org.bukkit.Material;

public class PirateCrate extends Crate {

    @Override
    public void renderer() {
        CrateBuilder.of(new PirateCrate())
                .setName("Caixa Pirata")
                .setId("pirate-crate")
                .setItem(Material.COAL)
                .build();
    }

}
