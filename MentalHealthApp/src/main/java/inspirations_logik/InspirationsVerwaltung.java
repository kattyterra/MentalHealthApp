package inspirations_logik;

import java.util.List;
import java.util.Random;

/**
 * Diese Klasse verwaltet den Zugriff auf Inspirationssätze.
 * Sie nutzt ein {@link InspirationsRepository}, um die Sätze zu laden,
 * und gibt einen zufälligen Satz zurück.
 */
public class InspirationsVerwaltung {
    private final InspirationsRepository repository;
    private final Random random;

    /**
     * Konstruktor – initialisiert die Inspirationsverwaltung mit einem Repository.
     *
     * @param repository Die konkrete Speicherquelle für Inspirationssätze (z. B. Datei)
     */
    public InspirationsVerwaltung(InspirationsRepository repository) {
        this.repository = repository;
        this.random = new Random();
    }

    /**
     * Gibt einen zufällig ausgewählten Inspirationssatz zurück.
     *
     * @return Ein zufälliger Satz oder eine Fehlermeldung, wenn keine Sätze vorhanden sind.
     */
    public String gibZufaelligenSatz() {
        List<String> saetze = repository.ladeSaetze();
        if (saetze.isEmpty()) {
            return "Keine Inspirationssätze verfügbar.";
        }
        return saetze.get(random.nextInt(saetze.size()));
    }
}
