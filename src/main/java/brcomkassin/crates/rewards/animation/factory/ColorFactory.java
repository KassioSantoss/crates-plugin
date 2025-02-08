package brcomkassin.crates.rewards.animation.factory;

import org.bukkit.Color;
import java.util.HashMap;
import java.util.Map;

public class ColorFactory {
    private static final Map<String, Color> COLORS = new HashMap<>();

    static {
        COLORS.put("WHITE", Color.WHITE);
        COLORS.put("SILVER", Color.SILVER);
        COLORS.put("GRAY", Color.GRAY);
        COLORS.put("BLACK", Color.BLACK);
        COLORS.put("RED", Color.RED);
        COLORS.put("MAROON", Color.MAROON);
        COLORS.put("YELLOW", Color.YELLOW);
        COLORS.put("OLIVE", Color.OLIVE);
        COLORS.put("LIME", Color.LIME);
        COLORS.put("GREEN", Color.GREEN);
        COLORS.put("AQUA", Color.AQUA);
        COLORS.put("TEAL", Color.TEAL);
        COLORS.put("BLUE", Color.BLUE);
        COLORS.put("NAVY", Color.NAVY);
        COLORS.put("FUCHSIA", Color.FUCHSIA);
        COLORS.put("PURPLE", Color.PURPLE);
        COLORS.put("ORANGE", Color.ORANGE);
    }

    public static Color fromString(String colorName) {
        if (colorName == null || !COLORS.containsKey(colorName.toUpperCase())) {
            throw new IllegalArgumentException("Unknown color name: " + colorName);
        }
        return COLORS.get(colorName.toUpperCase());
    }
}
