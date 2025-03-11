package uebungen;

import java.io.*;
import java.util.*;

/**
 * Diese Klasse dient zum Einlesen von Übungstexten aus einer externen Datei.
 * Die Übungen enthalten jeweils:
 * - einen Namen (Beginn mit "1. ", "2. ", …),
 * - ein Ziel (beginnend mit "Ziel: …")
 * - und eine mehrzeilige Anleitung.
 * Das Format der Datei sollte sein:
 *  1. Übungsname
 *  Ziel: Zielbeschreibung
 *  Schritt 1 der Anleitung
 *  Schritt 2 der Anleitung
 *  ...
 */
public class UebungLoader {

    /**
     * Liest eine Liste von Übungen aus einer Textdatei ein.
     *
     * @param dateipfad Pfad zur Übungsvorlagendatei
     * @return Liste aller eingelesenen Übungen
     */
    public static List<Uebung> ladeUebungen(String dateipfad) {
        List<Uebung> uebungen = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(dateipfad))) {
            String zeile;
            String name = null;
            String ziel = null;
            List<String> anleitung = new ArrayList<>();
            boolean leseAnleitung = false;

            while ((zeile = reader.readLine()) != null) {
                // Neue Übung beginnt mit "1. ", "2. ", etc.
                if (zeile.matches("^\\d+\\.\\s.*")) {
                    // Falls vorherige Übung bereits gesammelt wurde, speichern
                    if (name != null) {
                        uebungen.add(new Uebung(name, ziel, new ArrayList<>(anleitung)));
                        anleitung.clear();
                    }
                    // Neuen Namen extrahieren
                    name = zeile.substring(3).trim();
                    ziel = null;
                    leseAnleitung = false;
                } else if (zeile.startsWith("Ziel:")) {
                    ziel = zeile.substring(5).trim();
                    leseAnleitung = true;
                } else if (leseAnleitung && !zeile.isBlank()) {
                    // Anleitungsschritte sammeln
                    anleitung.add(zeile);
                }
            }

            // Letzte Übung nach Dateiende hinzufügen
            if (name != null) {
                uebungen.add(new Uebung(name, ziel, anleitung));
            }

        } catch (IOException e) {
            System.out.println("Fehler beim Laden der Übungen: " + e.getMessage());
        }

        return uebungen;
    }
}
