package routinen_logik;

import utility.VerzeichnisHelfer;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Konkrete Implementierung des {@link RoutineRepository} zur Verwaltung von Routinen über Textdateien.
 * Die Routinen werden in zwei Dateien gespeichert:
 *     -stammliste.txt – langfristige Stammliste aller Routinen
 *     -yyyy-MM-dd.txt – Tagesdatei mit aktuellem Erfüllungsstatus
 * Diese Klasse sorgt für das Laden, Speichern, Hinzufügen, Bearbeiten und Löschen der Routinen.
 */
public class FileBasedRoutineRepository implements RoutineRepository {

    /** Verzeichnis für Routinen-Dateien */
    private final String ordner = "Routinen";

    /** Stammliste-Datei mit Basisinformationen zu allen Routinen */
    private final String stammliste = ordner + "/stammliste.txt";

    /** Tagesdatei für den heutigen Tag (Datum-basiert) */
    private final String tagesdatei;

    /** Interner Zwischenspeicher der geladenen Routinen */
    private final List<Routine> routinen = new ArrayList<>();

    /**
     * Konstruktor – initialisiert das Repository:
     *     -legt Speicherverzeichnis und Dateien an (falls nicht vorhanden)
     *     -lädt entweder Tagesdatei oder Stammliste
     * @throws RoutineException bei Fehlern während der Initialisierung
     */
    public FileBasedRoutineRepository() throws RoutineException {
        try {
            VerzeichnisHelfer verzeichnisHelfer = new VerzeichnisHelfer();
            verzeichnisHelfer.sicherstellen(ordner);

            File stamm = new File(stammliste);
            if (!stamm.exists()) {
                if (stamm.createNewFile()) {
                    System.out.println("✅ Stammliste wurde neu erstellt.");
                } else {
                    System.out.println("⚠ Stammliste konnte nicht erstellt werden.");
                }
            }

            String datum = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
            tagesdatei = ordner + "/" + datum + ".txt";

            if (new File(tagesdatei).exists()) {
                ladeTagesdatei();
            } else {
                ladeStammliste();
                speichern();
            }

        } catch (IOException e) {
            throw new RoutineException("Fehler bei Initialisierung: " + e.getMessage());
        }
    }

    /**
     * Lädt alle Routinen aus der Stammliste (ohne Erledigt-Status).
     * @throws RoutineException bei Ladefehlern
     */
    private void ladeStammliste() throws RoutineException {
        routinen.clear();
        try (BufferedReader r = new BufferedReader(new FileReader(stammliste))) {
            String zeile;
            while ((zeile = r.readLine()) != null) {
                RoutinenArt art = RoutinenArt.fromText(zeile.trim());
                String beschreibung = r.readLine();
                routinen.add(new Routine(art, beschreibung));
            }
        } catch (IOException e) {
            throw new RoutineException("Fehler beim Laden der Stammliste: " + e.getMessage());
        }
    }

    /**
     * Lädt alle Routinen aus der Tagesdatei inklusive Erledigt-Status.
     * @throws RoutineException bei Ladefehlern
     */
    private void ladeTagesdatei() throws RoutineException {
        routinen.clear();
        try (BufferedReader r = new BufferedReader(new FileReader(tagesdatei))) {
            String zeile;
            while ((zeile = r.readLine()) != null) {
                RoutinenArt art = RoutinenArt.fromText(zeile.trim());
                String beschreibung = r.readLine();
                boolean erledigt = Boolean.parseBoolean(r.readLine());
                Routine routine = new Routine(art, beschreibung);
                routine.setErledigt(erledigt);
                routinen.add(routine);
            }
        } catch (IOException e) {
            throw new RoutineException("Fehler beim Laden der Tagesdatei: " + e.getMessage());
        }
    }

    /**
     * Gibt die aktuell verwalteten Routinen zurück.
     * @return Liste der Routinen
     */
    @Override
    public List<Routine> getRoutinen() {
        return routinen;
    }

    /**
     * Fügt eine neue Routine hinzu und speichert sie sowohl in der Tagesdatei als auch in der Stammliste.
     * @param routine die neue Routine
     * @throws RoutineException bei Speicherfehlern
     */
    @Override
    public void hinzufuegen(Routine routine) throws RoutineException {
        routinen.add(routine);
        speichern();
        try (BufferedWriter w = new BufferedWriter(new FileWriter(stammliste, true))) {
            w.write(routine.getArt().name());
            w.newLine();
            w.write(routine.getBeschreibung());
            w.newLine();
        } catch (IOException e) {
            throw new RoutineException("Fehler beim Speichern in Stammliste: " + e.getMessage());
        }
    }

    /**
     * Bearbeitet eine bestehende Routine anhand ihres Indexes.
     * @param index Index der zu bearbeitenden Routine
     * @param neueArt neue Routinenart
     * @param neueBeschreibung neue Beschreibung
     * @throws RoutineException bei ungültigem Index oder Speicherfehler
     */
    @Override
    public void bearbeiten(int index, RoutinenArt neueArt, String neueBeschreibung) throws RoutineException {
        if (index < 0 || index >= routinen.size()) throw new RoutineException("Ungültiger Index.");
        Routine r = routinen.get(index);
        r.setArt(neueArt);
        r.setBeschreibung(neueBeschreibung);
        speichern();
    }

    /**
     * Löscht eine Routine anhand ihres Indexes.
     * @param index Index der zu löschenden Routine
     * @throws RoutineException bei ungültigem Index oder Speicherfehler
     */
    @Override
    public void loeschen(int index) throws RoutineException {
        if (index < 0 || index >= routinen.size()) throw new RoutineException("Ungültiger Index.");
        routinen.remove(index);
        speichern();
    }

    /**
     * Speichert den aktuellen Zustand aller Routinen in der Tagesdatei.
     * @throws RoutineException bei Schreibfehlern
     */
    @Override
    public void speichern() throws RoutineException {
        try (BufferedWriter w = new BufferedWriter(new FileWriter(tagesdatei))) {
            for (Routine r : routinen) {
                w.write(r.getArt().name());
                w.newLine();
                w.write(r.getBeschreibung());
                w.newLine();
                w.write(Boolean.toString(r.isErledigt()));
                w.newLine();
            }
        } catch (IOException e) {
            throw new RoutineException("Fehler beim Speichern der Tagesdatei: " + e.getMessage());
        }
    }
}
