package uebungen;

import java.util.List;

/**
 * Implementierung des {@link UebungRepository}, die alle Übungen aus einer Textdatei lädt.
 * Diese Klasse kapselt den Pfad zur Datei und delegiert das Laden
 * der Übungen an den {@link UebungLoader}.
 */
public class TextdateiUebungRepository implements UebungRepository {

    /** Pfad zur Datei mit den Übungstexten */
    private final String dateipfad;

    /**
     * Konstruktor – initialisiert das Repository mit einem Pfad zur Übungsdatei.
     * @param dateipfad Pfad zur Datei mit Atem- oder Achtsamkeitsübungen
     */
    public TextdateiUebungRepository(String dateipfad) {
        this.dateipfad = dateipfad;
    }

    /**
     * Lädt alle Übungen aus der definierten Textdatei.
     * Die Datei wird mithilfe des {@link UebungLoader} eingelesen.
     * @return Liste aller geladenen {@link Uebung}-Objekte
     */
    @Override
    public List<Uebung> ladeAlle() {
        UebungLoader loader = new UebungLoader();
        return loader.ladeUebungen(dateipfad);
    }
}
