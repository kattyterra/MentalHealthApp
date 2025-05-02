package inspirations_logik;

import java.util.List;
import java.util.Random;

/**
 * Diese Klasse verwaltet den Zugriff auf Inspirationssätze.
 * Sie nutzt ein {@link InspirationsRepository}, um die Sätze zu laden,
 * und gibt einen zufälligen Satz zurück.
 */
public class InspirationsVerwaltung {
    private static final String STANDARD_PFAD = "Textvorlagen(nicht_ändern!)/Inspo.txt";
    private final InspirationsRepository repository;
    private final Random random = new Random();

    // Produktivkonstruktor
    public InspirationsVerwaltung() {
        this.repository = new InspirationsSpeicher(STANDARD_PFAD);
    }

    // Injection-Konstruktor
    public InspirationsVerwaltung(InspirationsRepository repository) {
        this.repository = repository;
    }
    /**
     * Gibt einen zufällig ausgewählten Inspirationssatz zurück.
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
