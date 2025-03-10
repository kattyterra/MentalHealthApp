package InspirationsLogik;

import java.util.List;
import java.util.Random;

public class InspirationsVerwaltung {
    private final InspirationsRepository repository;
    private final Random random;

    public InspirationsVerwaltung(InspirationsRepository repository) {
        this.repository = repository;
        this.random = new Random();
    }

    public String gibZufaelligenSatz() {
        List<String> saetze = repository.ladeSaetze();
        if (saetze.isEmpty()) {
            return "Keine Inspirationssätze verfügbar.";
        }
        return saetze.get(random.nextInt(saetze.size()));
    }
}