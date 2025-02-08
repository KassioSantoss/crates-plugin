package brcomkassin.crates.rewards.animation;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Map;
import java.util.HashMap;

@Getter
@AllArgsConstructor
public enum AnimationType {
    DEFAULT("0"),
    FIRST("1");

    private final String animation;
    private static final Map<String, AnimationType> MAP = new HashMap<>();

    static {
        for (AnimationType type : values()) {
            MAP.put(type.animation.toLowerCase(), type);
        }
    }

    public static AnimationType fromString(String animation) {
        return MAP.getOrDefault(animation.toLowerCase(), DEFAULT);
    }
}