package stimmungskalender_logik;

import utility.*;

import java.time.LocalDate;
import java.util.List;

/**
 * Diese Klasse implementiert das StimmungskalenderRepository.
 * Sie übernimmt die Datei-basierte Speicherung und das Einlesen
 * von Stimmungseinträgen und Emotionen – jeweils in eine Tagesdatei.
 */
public class StimmungskalenderSpeicher implements StimmungskalenderRepository {
    private final String ordner = "Stimmungskalender/";
    private final DateiSchreibHelfer schreiber;
    private final DateiLeseHelfer leser;

    /** Injektion-Konstruktor */
    public StimmungskalenderSpeicher(DateiSchreibHelfer schreiber, DateiLeseHelfer leser) {
        this.schreiber = schreiber;
        this.leser = leser;
        new VerzeichnisHelfer().sicherstellen(ordner);
    }

    /** Konstruktor: Erstellt den Ordner „Stimmungskalender“, falls er noch nicht existiert */
    public StimmungskalenderSpeicher() {
        VerzeichnisHelfer verzeichnisHelfer = new VerzeichnisHelfer();
        verzeichnisHelfer.sicherstellen(ordner);
        this.schreiber = new DateiSchreibHelfer();
        this.leser = new DateiLeseHelfer();
    }

    /**
     * Speichert einen einzelnen Stimmungseintrag in der heutigen Tagesdatei.
     * Falls die Datei bereits existiert, wird der Eintrag angehängt.
     * @param eintrag Der zu speichernde Stimmungseintrag
     */
    @Override
    public void speichern(Stimmungseintrag eintrag) {
        schreiber.anhaengen(ordner, heutigesDateiname(), List.of(eintrag.formatForFile()));
    }

    /**
     * Speichert eine Liste von Emotionseinträgen ebenfalls in der heutigen Tagesdatei.
     * Wird typischerweise direkt nach dem Stimmungseintrag gespeichert.
     * @param emotionen Liste von Emotionseinträgen
     */
    @Override
    public void speichernEmotionen(List<Emotionseintrag> emotionen) {
        List<String> zeilen = emotionen.stream()
                .map(Emotionseintrag::formatForFile)
                .toList();

        schreiber.anhaengen(ordner, heutigesDateiname(), zeilen);
    }

    /**
     * Liest alle Einträge aus allen Tagesdateien im Ordner „Stimmungskalender“ ein.
     * Rückgabe erfolgt als Liste aller Zeilen über alle Dateien hinweg.
     * @return Liste aller Einträge (Stimmungen + Emotionen) als Textzeilen
     */
    @Override
    public List<String> lesenAlle() {
        return leser.leseAlleZeilen(ordner);
    }

    /**
     * Hilfsmethode zur Erzeugung des aktuellen Tagesdateinamens.
     * @return Dateiname im Format yyyy-MM-dd.txt
     */
    private String heutigesDateiname() {
        return LocalDate.now() + ".txt";
    }
}
