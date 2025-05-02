package uebungen;

import java.io.*;
import java.util.*;

/**
 * Diese Klasse liest Übungen im bekannten Textformat aus einer Datei.
 * Jede Übung besteht aus einem Namen, Ziel und mehreren Anleitungsschritten.
 */
public class UebungLoader {

    /**
     * Lädt alle Übungen aus der angegebenen Datei im vordefinierten Format.
     * @param dateipfad Pfad zur Datei mit den Übungen
     * @return Liste eingelesener {@link Uebung}-Objekte
     */
    public List<Uebung> ladeUebungen(String dateipfad) {
        List<Uebung> uebungen = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(dateipfad))) {
            String zeile;
            String name = null;
            String ziel = null;
            List<String> anleitung = new ArrayList<>();
            boolean leseAnleitung = false;

            while ((zeile = reader.readLine()) != null) {
                if (istUebungsTitel(zeile)) {
                    fuegeUebungHinzuWennVollstaendig(uebungen, name, ziel, anleitung);
                    name = zeile.substring(3).trim();
                    ziel = null;
                    anleitung.clear();
                    leseAnleitung = false;
                } else if (zeile.startsWith("Ziel:")) {
                    ziel = zeile.substring(5).trim();
                    leseAnleitung = true;
                } else if (leseAnleitung && !zeile.isBlank()) {
                    anleitung.add(zeile);
                }
            }

            // letzte Übung nach Datei-Ende hinzufügen
            fuegeUebungHinzuWennVollstaendig(uebungen, name, ziel, anleitung);

        } catch (IOException e) {
            System.err.println("Fehler beim Laden der Übungen: " + e.getMessage());
        }

        return uebungen;
    }

    /** Prüft, ob die Zeile eine neue Übung einleitet (z.B. „1. …“) */
    private boolean istUebungsTitel(String zeile) {
        return zeile.matches("^\\d+\\.\\s.*");
    }

    /** Fügt eine vollständige Übung zur Liste hinzu, wenn der Name vorhanden ist. */
    private void fuegeUebungHinzuWennVollstaendig(List<Uebung> liste, String name, String ziel, List<String> anleitung) {
        if (name != null && ziel != null && !anleitung.isEmpty()) {
            liste.add(new Uebung(name, ziel, new ArrayList<>(anleitung)));
        }
    }
}
