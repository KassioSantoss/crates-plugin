package brcomkassin.crate.renderer;

import brcomkassin.crate.Crate;
import brcomkassin.crate.PirateCrate;

public class RendererCrate implements RendererCrateService {

    private final Crate pirateCrate;

    public RendererCrate() {
        this.pirateCrate = new PirateCrate();
    }

    @Override
    public void renderer() {
        pirateCrate.renderer();
    }
}
