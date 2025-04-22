
package tagebuch_logik;

import utility.DateiSchreibHelfer;
import utility.DateiLeseHelfer;
import utility.VerzeichnisHelfer;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Diese Klasse implementiert das {@link TagebuchRepository} und verwaltet das
 * Speichern, Lesen, Bearbeiten und Löschen von Tagebucheinträgen im Dateisystem.
 * Pro Tag wird eine separate Datei im Verzeichnis „Tagebuch/“ angelegt.
 */

public class DateiSpeicher implements TagebuchRepository {
    private final String ordner;
    private final DateiSchreibHelfer schreibHelfer;
    private final DateiLeseHelfer leseHelfer;

    /**
     * Konstruktor – stellt sicher, dass der Tagebuchordner existiert, und initialisiert die Datei-Helferklassen.
     */
    public DateiSpeicher() {
        this.ordner = "Tagebuch/";
        VerzeichnisHelfer verzeichnisHelfer = new VerzeichnisHelfer();
        verzeichnisHelfer.sicherstellen(ordner);
        this.schreibHelfer = new DateiSchreibHelfer();
        this.leseHelfer = new DateiLeseHelfer();
    }

    /** Injektion-Konstruktor */
    public DateiSpeicher(String ordner, DateiSchreibHelfer schreibHelfer, DateiLeseHelfer leseHelfer) {
        this.ordner = ordner.endsWith("/") || ordner.endsWith("\\") ? ordner : ordner + File.separator;
        new VerzeichnisHelfer().sicherstellen(this.ordner);
        this.schreibHelfer = schreibHelfer;
        this.leseHelfer = leseHelfer;
    }

    /**
     * Hilfsmethode zur Bildung des vollständigen Dateipfads für einen gegebenen Tag
     */
    private Path getPfad(String datum) {
        return Paths.get(ordner + datum + ".txt");
    }

    /**
     * Speichert einen Tagebucheintrag in der entsprechenden Tagesdatei.
     * Falls bereits Einträge vorhanden sind, wird der neue Eintrag mit einer Leerzeile abgetrennt.
     * @param eintrag der zu speichernde {@link TagebuchEintrag}
     */
    @Override
    public void speichern(TagebuchEintrag eintrag) {
        Path pfad = getPfad(eintrag.datum());
        schreibHelfer.anhaengenMitLeerzeile(pfad.toString(), eintrag.formatForFile());
        System.out.println("Eintrag gespeichert: " + pfad);
    }

    /**
     * Löscht die komplette Tagesdatei eines gegebenen Datums (inkl. aller Einträge darin).
     * @param datum das Datum der zu löschenden Datei (Format: yyyy-MM-dd)
     */
    @Override
    public void loeschen(String datum) {
        try {
            Files.deleteIfExists(getPfad(datum));
            System.out.println("Datei gelöscht: " + getPfad(datum));
        } catch (IOException e) {
            System.err.println("Fehler beim Löschen: " + e.getMessage());
        }
    }

    /**
     * Löscht einen bestimmten Eintrag innerhalb einer Tagesdatei basierend auf der Uhrzeit.
     * Wenn dies der letzte Eintrag ist, wird die Datei ggf. komplett entfernt.
     * @param datum das Datum der Datei
     * @param uhrzeit die Uhrzeit des zu löschenden Eintrags
     */
    @Override
    public void loeschenEintrag(String datum, String uhrzeit) {
        Path path = getPfad(datum);
        if (!Files.exists(path)) return;

        try {
            List<String> lines = Files.readAllLines(path);
            List<String> updated = new ArrayList<>();
            boolean loesche = false;

            for (String line : lines) {
                if (line.startsWith("Eingetragen um " + uhrzeit + ":")) {
                    loesche = true;
                    continue;
                }
                if (loesche && !line.startsWith("Eingetragen um ")) {
                    continue;
                }
                loesche = false;
                updated.add(line);
            }

            if (updated.isEmpty()) {
                Files.delete(path);
            } else {
                Files.write(path, updated);
            }

        } catch (IOException e) {
            System.err.println("Fehler beim Löschen des Eintrags: " + e.getMessage());
        }
    }

    /**
     * Liest den gesamten Inhalt einer Tagesdatei als zusammenhängenden Textblock.
     * @param datum das gewünschte Datum (Format: yyyy-MM-dd)
     * @return der Textinhalt oder eine Fehlermeldung
     */
    @Override
    public String lesen(String datum) {
        return leseHelfer.leseTextblock(getPfad(datum).toString());
    }

    /**
     * Gibt eine Liste aller verfügbaren Tagebuchdaten (Dateinamen ohne ".txt"-Endung) zurück.
     * @return Liste von Datums-Strings aller vorhandenen Tagebucheinträge
     */
    @Override
    public List<String> getVerfuegbareEintraege() {
        File verzeichnis = new File(ordner);
        File[] dateien = verzeichnis.listFiles((dir, name) -> name.endsWith(".txt"));
        List<String> eintraege = new ArrayList<>();

        if (dateien != null) {
            for (File datei : dateien) {
                eintraege.add(datei.getName().replace(".txt", ""));
            }
        }
        return eintraege;
    }

    /**
     * Ersetzt den Text eines bestimmten Eintrags in einer Tagesdatei anhand der Uhrzeit.
     * @param datum das Datum der Datei
     * @param uhrzeit die Uhrzeit des Eintrags, der ersetzt werden soll
     * @param neuerText der neue Textinhalt
     * @return true, wenn der Eintrag erfolgreich bearbeitet wurde
     */
    @Override
    public boolean bearbeiten(String datum, String uhrzeit, String neuerText) {
        Path path = getPfad(datum);
        if (!Files.exists(path)) return false;

        try {
            List<String> lines = Files.readAllLines(path);
            List<String> updated = new ArrayList<>();
            boolean ersetzen = false;

            for (String line : lines) {
                if (line.startsWith("Eingetragen um " + uhrzeit + ":")) {
                    ersetzen = true;
                    updated.add(line);
                    continue;
                }
                if (ersetzen && !line.startsWith("Eingetragen um ")) {
                    updated.add(neuerText);
                    ersetzen = false;
                    continue;
                }
                updated.add(line);
            }

            Files.write(path, updated);
            return true;
        } catch (IOException e) {
            return false;
        }
    }
}
