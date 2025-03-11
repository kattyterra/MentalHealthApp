package routinen_logik;

import java.io.*;
import java.util.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RoutineVorschlagsService {
    private final Map<RoutinenArt, List<String>> vorschlaege = new HashMap<>();

    public RoutineVorschlagsService(String dateipfad) {
        ladeVorschlaege(dateipfad);
    }

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

    public List<String> getVorschlaegeZuArt(RoutinenArt art) {
        return vorschlaege.getOrDefault(art, new ArrayList<>());
    }
}