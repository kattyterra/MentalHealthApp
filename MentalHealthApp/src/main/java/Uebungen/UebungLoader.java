package Uebungen;

import java.io.*;
import java.util.*;

public class UebungLoader {

    public static List<Uebung> ladeUebungen(String dateipfad) {
        List<Uebung> uebungen = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(dateipfad))) {
            String zeile;
            String name = null;
            String ziel = null;
            List<String> anleitung = new ArrayList<>();
            boolean leseAnleitung = false;

            while ((zeile = reader.readLine()) != null) {
                if (zeile.matches("^\\d+\\.\\s.*")) {
                    if (name != null) {
                        uebungen.add(new Uebung(name, ziel, new ArrayList<>(anleitung)));
                        anleitung.clear();
                    }
                    name = zeile.substring(3).trim();
                    ziel = null;
                    leseAnleitung = false;
                } else if (zeile.startsWith("Ziel:")) {
                    ziel = zeile.substring(5).trim();
                    leseAnleitung = true;
                } else if (leseAnleitung && !zeile.isBlank()) {
                    anleitung.add(zeile);
                }
            }

            if (name != null) {
                uebungen.add(new Uebung(name, ziel, anleitung));
            }

        } catch (IOException e) {
            System.out.println("Fehler beim Laden der Übungen: " + e.getMessage());
        }

        return uebungen;
    }
}
