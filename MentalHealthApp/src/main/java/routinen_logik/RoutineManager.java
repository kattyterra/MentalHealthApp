package routinen_logik;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Diese Klasse verwaltet alle Routinen:
 * - Laden der Stammliste
 * - tägliche Routinenverwaltung (Checkliste pro Tag)
 * - Speichern/Löschen/Bearbeiten von Routinen
 * - Erstellung täglicher Routinen-Checklisten
 */
public class RoutineManager {
    private final String routinenOrdner = "Routinen";
    private final String stammlistePfad = routinenOrdner + "/stammliste.txt";
    private final String tagesdateiPfad;
    private final List<Routine> routinen = new ArrayList<>();

    /**
     * Konstruktor: Initialisiert die Verzeichnisse und lädt die Tagesroutine oder Stammliste.
     */
    public RoutineManager() {
        // Routinen-Ordner erstellen, falls nicht vorhanden
        File ordner = new File(routinenOrdner);
        if (!ordner.exists()) {
            if (!ordner.mkdirs()) {
                System.out.println("Ordner 'Routinen' konnte nicht erstellt werden!");
            }
        }

        // Stammliste erstellen, falls nicht vorhanden
        File stammliste = new File(stammlistePfad);
        if (!stammliste.exists()) {
            try {
                if (stammliste.createNewFile()) {
                    System.out.println("\n");
                }
            } catch (IOException e) {
                System.out.println("⚠ Fehler beim Erstellen von stammliste.txt: " + e.getMessage());
            }
        }

        // Pfad zur Tagesdatei (nach aktuellem Datum)
        String datum = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        tagesdateiPfad = routinenOrdner + "/" + datum + ".txt";

        // Falls Tagesdatei existiert, diese laden – sonst Stammliste übernehmen
        if (new File(tagesdateiPfad).exists()) {
            ladeTagesdatei();
        } else {
            ladeStammliste();
            speichereTagesdatei();
        }
    }

    /**
     * Lädt alle Routinen aus der Stammliste (Standardroutinen ohne Tagesstatus).
     */
    public void ladeStammliste() {
        routinen.clear();
        try (BufferedReader reader = new BufferedReader(new FileReader(stammlistePfad))) {
            String art;
            while ((art = reader.readLine()) != null) {
                String beschreibung = reader.readLine();
                if (beschreibung != null) {
                    System.out.println("Du hast noch keine Routinen hinzugefügt. Zeit deine 1. Routine zu erstellen!");
                    routinen.add(new Routine(art.trim(), beschreibung.trim()));
                }
            }
        } catch (IOException e) {
            System.out.println("⚠ Fehler beim Laden der Stammliste: " + e.getMessage());
        }
    }

    /**
     * Lädt die Routinen mit Erledigt-Status aus der aktuellen Tagesdatei.
     */
    public void ladeTagesdatei() {
        routinen.clear();
        try (BufferedReader reader = new BufferedReader(new FileReader(tagesdateiPfad))) {
            String art;
            while ((art = reader.readLine()) != null) {
                String beschreibung = reader.readLine();
                boolean erledigt = Boolean.parseBoolean(reader.readLine());
                Routine r = new Routine(art.trim(), beschreibung.trim());
                r.setErledigt(erledigt);
                routinen.add(r);
            }
        } catch (IOException e) {
            System.out.println("⚠ Fehler beim Laden der Tagesdatei: " + e.getMessage());
        }
    }

    /**
     * Speichert den aktuellen Stand aller Routinen (inkl. Erledigt-Status) in die Tagesdatei.
     */
    public void speichereTagesdatei() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(tagesdateiPfad))) {
            for (Routine r : routinen) {
                writer.write(r.getRoutinenArt());
                writer.newLine();
                writer.write(r.getBeschreibung());
                writer.newLine();
                writer.write(Boolean.toString(r.isErledigt()));
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Fehler beim Speichern der Tagesdatei: " + e.getMessage());
        }
    }

    /**
     * Fügt eine neue Routine hinzu – sowohl zur Tagesliste als auch zur Stammliste.
     *
     * @param routine Neue Routine, die ergänzt werden soll
     */
    public void routineHinzufuegen(Routine routine) {
        routinen.add(routine);
        speichereTagesdatei();

        // Zusätzlich in die Stammliste speichern (dauerhaft)
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(stammlistePfad, true))) {
            writer.write(routine.getRoutinenArt());
            writer.newLine();
            writer.write(routine.getBeschreibung());
            writer.newLine();
        } catch (IOException e) {
            System.out.println("Fehler beim Speichern in Stammliste: " + e.getMessage());
        }
    }

    /**
     * Bearbeitet eine bestehende Routine anhand des Index (Routinenart und Beschreibung).
     *
     * @param index Index der zu bearbeitenden Routine
     * @param neueArt Neue Routinenkategorie
     * @param neueBeschreibung Neue Aufgabenbeschreibung
     */
    public void routineBearbeiten(int index, String neueArt, String neueBeschreibung) {
        Routine r = routinen.get(index);
        r.setRoutinenArt(neueArt);
        r.setBeschreibung(neueBeschreibung);
        speichereTagesdatei();
    }

    /**
     * Löscht eine Routine aus der Liste (Tagesliste).
     *
     * @param index Index der zu löschenden Routine
     */
    public void routineLoeschen(int index) {
        routinen.remove(index);
        speichereTagesdatei();
    }

    /**
     * Gibt die aktuelle Liste aller Tagesroutinen zurück.
     * @return Liste aller Routinen
     */
    public List<Routine> getRoutinen() {
        return routinen;
    }
}
