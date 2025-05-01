package gedanken_reflexion_logik;

import java.time.LocalDate;
import java.util.List;

import utility.*;

/**
 * Konkrete Implementierung des {@link GedankenReflexionRepository}.
 * Diese Klasse verwaltet das Speichern und Auslesen von Gedankenreflexionseinträgen
 * im Dateisystem. Jeder Eintrag wird in einer Textdatei abgelegt,
 * gruppiert nach dem jeweiligen Tagesdatum.
 */
public class GedankenReflexionSpeicher implements GedankenReflexionRepository {

    private final String ordner = "Reflexionen/";
    private final DateiSchreibHelfer schreiber;
    private final DateiLeseHelfer leser;

    /**
     * Konstruktor – prüft, ob der Speicherordner vorhanden ist,
     * und erstellt ihn bei Bedarf.
     */
    public GedankenReflexionSpeicher() {
        this.schreiber = new DateiSchreibHelfer();
        this.leser = new DateiLeseHelfer();
        initialisiereSpeicherort();
    }

    /** Injektion-Konstruktor */
    public GedankenReflexionSpeicher(DateiSchreibHelfer schreiber, DateiLeseHelfer leser){
        this.schreiber = schreiber;
        this.leser = leser;
        initialisiereSpeicherort();
    }

    /**
     * Speichert einen neuen Gedankenreflexionseintrag in einer Tagesdatei.
     * Falls bereits eine Datei für das heutige Datum existiert,
     * wird der Eintrag dort angehängt.
     * @param eintrag Der zu speichernde GedankenReflexionEintrag
     */
    @Override
    public void speichern(GedankenReflexionEintrag eintrag) {
        schreiber.anhaengen(ordner, LocalDate.now() + ".txt", List.of(eintrag.formatForFile()));
    }

    /**
     * Liest alle vorhandenen Gedankenreflexionseinträge aus dem Speicherordner.
     * Die Einträge werden nach Datum sortiert zurückgegeben und können direkt
     * für die Anzeige genutzt werden.
     * @return Liste aller gespeicherten Einträge als Textzeilen
     */
    @Override
    public List<String> lesenAlle() {
        return leser.leseAlleZeilen(ordner);
    }

    /**
     * Stellt sicher, dass der Speicherordner existiert.
     */
    private void initialisiereSpeicherort() {
        new VerzeichnisHelfer().sicherstellen(ordner);
    }
}
