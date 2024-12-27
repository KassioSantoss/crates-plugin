package brcomkassin.crates.renderer;

import brcomkassin.crates.Crate;
import lombok.Data;
import org.bukkit.plugin.PluginLogger;

@Data(staticConstructor = "of") public class CrateRendererDetailsDisplayer {

    private final Crate crate;

    public void display() {
        PluginLogger.getGlobal().info("\u001B[32m======================================================================================");
        PluginLogger.getGlobal().info("\u001B[36mID: " + this.crate.getId() + "\u001B[37m -> namespace: " + this.crate.getNameSpace());
        PluginLogger.getGlobal().info("\u001B[36mID: " + this.crate.getId() + "\u001B[37m -> crateDisplayName: " + this.crate.getCrateDisplayName());
        PluginLogger.getGlobal().info("\u001B[36mID: " + this.crate.getId() + "\u001B[37m -> crateCustomModelData: " + this.crate.getCrateCustomModelData());
        PluginLogger.getGlobal().info("\u001B[36mID: " + this.crate.getId() + "\u001B[37m -> keyDisplayName: " + this.crate.getKey().getKeyDisplayName());
        PluginLogger.getGlobal().info("\u001B[36mID: " + this.crate.getId() + "\u001B[37m -> keyCustomModelData: " + this.crate.getKey().getKeyCustomModelData());
        PluginLogger.getGlobal().info("\u001B[36mID: " + this.crate.getId() + "\u001B[37m -> baseEntityModel: " + this.crate.getBaseEntityModel());
        PluginLogger.getGlobal().info("\u001B[36mID: " + this.crate.getId() + "\u001B[37m -> animation: " + this.crate.getAnimation());
        PluginLogger.getGlobal().info("\u001B[32m======================================================================================\u001B[0m");

    }
}
