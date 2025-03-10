package AtemuebungenLogik;

import java.io.*;
import java.util.*;

public class AtemuebungLoader {

    public static List<Atemuebung> ladeUebungen(String dateipfad) {
        List<Atemuebung> uebungen = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(dateipfad))) {
            String zeile;
            String name = null;
            String ziel = null;
            List<String> anleitung = new ArrayList<>();

            while ((zeile = reader.readLine()) != null) {
                if (zeile.matches("^\\d+\\.\\s.*")) {
                    if (name != null) {
                        uebungen.add(new Atemuebung(name, ziel, new ArrayList<>(anleitung)));
                        anleitung.clear();
                    }
                    name = zeile.substring(3).trim();
                    ziel = null;
                } else if (zeile.startsWith("Ziel:")) {
                    ziel = zeile.substring(5).trim();
                } else if (!zeile.isBlank()) {
                    anleitung.add(zeile);
                }
            }

            // Letzte Übung speichern
            if (name != null) {
                uebungen.add(new Atemuebung(name, ziel, anleitung));
            }

        } catch (IOException e) {
            System.out.println("Fehler beim Laden der Atemübungen: " + e.getMessage());
        }

        return uebungen;
    }
}