package brcomkassin.crates.location;

import brcomkassin.crates.Crate;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CrateLocationEntry {
    private CrateLocation crateLocation;
    private Crate crate;
}
