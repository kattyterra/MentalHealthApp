package zielverwaltung_logik;

import utility.VerzeichnisHelfer;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Datei-basierte Implementierung des {@link ZielRepository}-Interfaces.
 * Alle Ziele werden zeilenweise in einer zentralen Textdatei gespeichert („ziele.txt“ im Verzeichnis „Ziele/“).
 * Jedes Ziel besteht aus 7 aufeinanderfolgenden Zeilen, die bei Bedarf wieder eingelesen und geparst werden.
 * Für die Verzeichnisstruktur wird {@link VerzeichnisHelfer} verwendet.
 */
public class ZielSpeicher implements ZielRepository {
    private final String ordner = "Ziele/";
    private final String pfad = ordner + "ziele.txt";

    /**
     * Konstruktor – stellt sicher, dass das Verzeichnis „Ziele/“ existiert.
     * Falls nicht vorhanden, wird es automatisch erstellt.
     */
    public ZielSpeicher() {
        VerzeichnisHelfer verzeichnisHelfer = new VerzeichnisHelfer();
        verzeichnisHelfer.sicherstellen(ordner);
    }

    /**
     * Überschreibt die bestehende Zieldatei mit einer neuen Liste von Zielen.
     * Jedes Ziel wird in 7 aufeinanderfolgenden Zeilen gespeichert:
     * Kategorie, Beschreibung, Priorität, Erledigt (true/false), Fälligkeitsdatum, Wiederholungstyp, Motivationsnotiz.
     *
     * @param ziele Liste der zu speichernden {@link Ziel}-Objekte
     */
    @Override
    public void speichern(List<Ziel> ziele) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(pfad))) {
            for (Ziel ziel : ziele) {
                writer.write(ziel.getKategorie().name()); writer.newLine();
                writer.write(ziel.getBeschreibung()); writer.newLine();
                writer.write(String.valueOf(ziel.getPrioritaet())); writer.newLine();
                writer.write(String.valueOf(ziel.isErledigt())); writer.newLine();
                writer.write(ziel.getFaelligkeit().toString()); writer.newLine();
                writer.write(ziel.getWiederholung().name()); writer.newLine();
                writer.write(ziel.getMotivationsnotiz()); writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Fehler beim Speichern der Ziele: " + e.getMessage());
        }
    }

    /**
     * Lädt alle in der Datei gespeicherten Ziele und gibt sie als Liste zurück.
     * Falls die Datei nicht existiert, wird eine leere Liste zurückgegeben.
     *
     * @return Liste aller gespeicherten {@link Ziel}-Objekte
     */
    @Override
    public List<Ziel> laden() {
        List<Ziel> ziele = new ArrayList<>();
        File file = new File(pfad);

        if (!file.exists()) {
            System.out.println("Noch keine Ziele vorhanden");
            return ziele;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            while (reader.ready()) {
                ziele.add(parseZielAusReader(reader));
            }
        } catch (IOException | NumberFormatException e) {
            System.err.println("Fehler beim Laden der Ziele: " + e.getMessage());
        }

        return ziele;
    }

    /**
     * Parst ein vollständiges Ziel aus sieben aufeinanderfolgenden Zeilen im Reader.
     * Diese Methode wird während des Ladevorgangs verwendet.
     *
     * @param reader BufferedReader, positioniert auf der ersten Zeile eines Ziels
     * @return das rekonstruierte {@link Ziel}-Objekt
     * @throws IOException bei Problemen mit dem Datei- oder Lesefluss
     */
    private Ziel parseZielAusReader(BufferedReader reader) throws IOException {
        ZielKategorie kategorie = ZielKategorie.valueOf(reader.readLine());
        String beschreibung = reader.readLine();
        int prioritaet = Integer.parseInt(reader.readLine());
        boolean erledigt = Boolean.parseBoolean(reader.readLine());
        LocalDate faelligkeit = LocalDate.parse(reader.readLine());
        Wiederholungstyp wiederholung = Wiederholungstyp.valueOf(reader.readLine());
        String motivationsnotiz = reader.readLine();

        Ziel ziel = new Ziel(kategorie, beschreibung, prioritaet, faelligkeit, wiederholung);
        ziel.setErledigt(erledigt);
        ziel.setMotivationsnotiz(motivationsnotiz);
        return ziel;
    }
}