package uebungen;

import java.io.*;
import java.util.*;

/**
 * Diese Klasse dient zum Einlesen von Übungstexten aus einer externen Textdatei.
 * <p>
 * Das Dateiformat folgt einer klaren Struktur pro Übung:
 * <pre>
 * 1. Name der Übung
 * Ziel: Zielbeschreibung
 * Schritt 1 der Anleitung
 * Schritt 2 der Anleitung
 * ...
 * </pre>
 * <p>
 * Jede Übung besteht aus:
 * <ul>
 *     <li>einem Namen (Beginn mit „1. “, „2. “, …)</li>
 *     <li>einem Ziel (beginnend mit „Ziel: “)</li>
 *     <li>mehreren Anleitungsschritten</li>
 * </ul>
 */
public class UebungLoader {

    /**
     * Liest eine Liste von Übungen aus einer gegebenen Textdatei ein.
     * Die Datei muss im beschriebenen Format aufgebaut sein.
     *
     * @param dateipfad Pfad zur Datei mit Übungsvorlagen
     * @return Liste aller eingelesenen {@link Uebung}-Objekte
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
                    // Vorherige Übung hinzufügen (falls vorhanden)
                    if (name != null) {
                        uebungen.add(new Uebung(name, ziel, new ArrayList<>(anleitung)));
                        anleitung.clear();
                    }
                    name = zeile.substring(3).trim(); // Name extrahieren
                    ziel = null;
                    leseAnleitung = false;
                } else if (zeile.startsWith("Ziel:")) {
                    ziel = zeile.substring(5).trim(); // Ziel extrahieren
                    leseAnleitung = true;
                } else if (leseAnleitung && !zeile.isBlank()) {
                    // Anleitungsschritte sammeln
                    anleitung.add(zeile);
                }
            }

            // Letzte Übung hinzufügen (nach Dateiende)
            if (name != null) {
                uebungen.add(new Uebung(name, ziel, anleitung));
            }

        } catch (IOException e) {
            System.out.println("Fehler beim Laden der Übungen: " + e.getMessage());
        }

        return uebungen;
    }
}
