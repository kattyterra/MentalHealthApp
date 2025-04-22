package routinen_logik;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * Der {@code RoutineVorschlagsService} lädt vordefinierte Routinen-Vorschläge
 * aus einer externen Textdatei und stellt sie nach RoutinenArt sortiert bereit.
 * Ziel ist es, dem Benutzer beim Erstellen oder Bearbeiten von Routinen passende
 * Vorschläge anzuzeigen, um die Auswahl zu erleichtern.
 */
public class RoutineVorschlagsService {

    /** Map mit RoutinenArt → Liste von Vorschlägen */
    private final Map<RoutinenArt, List<String>> vorschlaege = new HashMap<>();

    /**
     * Konstruktor – lädt Vorschläge aus der übergebenen Datei.
     * @param dateipfad Pfad zur Textdatei mit Vorschlägen
     */
    public RoutineVorschlagsService(String dateipfad) {
        ladeVorschlaege(dateipfad);
    }

    /**
     * Lädt alle Routinen-Vorschläge aus einer Textdatei.
     * Das Dateiformat muss zeilenweise aufgebaut sein:
     *   MORGEN
     *   Trinken eines Glases Wasser
     *   ABEND
     *   Tagebucheintrag schreiben
     *   ...
     * @param dateipfad Pfad zur Datei
     */
    private void ladeVorschlaege(String dateipfad) {
        try (BufferedReader reader = new BufferedReader(new FileReader(dateipfad))) {
            String artZeile;
            while ((artZeile = reader.readLine()) != null) {
                String beschreibung = reader.readLine();
                if (beschreibung != null) {
                    try {
                        RoutinenArt art = RoutinenArt.fromText(artZeile.trim());
                        vorschlaege.putIfAbsent(art, new ArrayList<>());
                        vorschlaege.get(art).add(beschreibung.trim());
                    } catch (IllegalArgumentException e) {
                        System.out.println("⚠ Ungültiger RoutinenArt-Eintrag in Datei: " + artZeile);
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("⚠ Fehler beim Laden der Routinenvorschläge: " + e.getMessage());
        }
    }

    /**
     * Gibt alle Vorschläge zurück, die zu einer bestimmten {@link RoutinenArt} passen.
     * @param art die Routinenart
     * @return Liste der Vorschläge (kann leer sein)
     */
    public List<String> getVorschlaegeZuArt(RoutinenArt art) {
        return vorschlaege.getOrDefault(art, new ArrayList<>());
    }
}
