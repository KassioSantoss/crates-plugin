package brcomkassin.crates.renderer;

import brcomkassin.crates.Crate;
import lombok.Data;
import org.bukkit.plugin.PluginLogger;

@Data(staticConstructor = "of") public class CrateRendererDetailsDisplayer {

    private final Crate crate;

    public void display() {
        PluginLogger.getGlobal().info("\u001B[32m======================================================================================");
        PluginLogger.getGlobal().info("\u001B[36mID: " + this.crate.id() + "\u001B[37m -> namespace: " + this.crate.nameSpace());
        PluginLogger.getGlobal().info("\u001B[36mID: " + this.crate.id() + "\u001B[37m -> crateDisplayName: " + this.crate.crateDisplayName());
        PluginLogger.getGlobal().info("\u001B[36mID: " + this.crate.id() + "\u001B[37m -> crateCustomModelData: " + this.crate.crateCustomModelData());
        PluginLogger.getGlobal().info("\u001B[36mID: " + this.crate.id() + "\u001B[37m -> keyDisplayName: " + this.crate.key().getKeyDisplayName());
        PluginLogger.getGlobal().info("\u001B[36mID: " + this.crate.id() + "\u001B[37m -> keyCustomModelData: " + this.crate.key().getKeyCustomModelData());
        PluginLogger.getGlobal().info("\u001B[36mID: " + this.crate.id() + "\u001B[37m -> baseEntityModel: " + this.crate.baseEntityModel());
        PluginLogger.getGlobal().info("\u001B[36mID: " + this.crate.id() + "\u001B[37m -> animation: " + this.crate.animation());
        PluginLogger.getGlobal().info("\u001B[32m======================================================================================\u001B[0m");

    }
}
