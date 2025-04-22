package tagebuch_logik_tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import tagebuch_logik.DateiSpeicher;
import tagebuch_logik.TagebuchEintrag;
import utility.DateiLeseHelfer;
import utility.DateiSchreibHelfer;

import java.io.IOException;
import java.nio.file.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DateiSpeicherTest {

    private MockDateiSchreibHelfer mockSchreiber;
    private MockDateiLeseHelfer mockLeser;
    private DateiSpeicher speicher;


    static class MockDateiLeseHelfer extends DateiLeseHelfer {
        public boolean wurdeAufgerufen = false;
        public String letzterPfad = null;

        @Override
        public String leseTextblock(String pfad) {
            wurdeAufgerufen = true;
            letzterPfad = pfad;
            return "Testinhalt";
        }
    }

    static class MockDateiSchreibHelfer extends DateiSchreibHelfer {
        public boolean wurdeAufgerufen = false;
        public String letzterPfad = null;
        public String letzterText = null;

        @Override
        public void anhaengenMitLeerzeile(String pfad, String text) {
            wurdeAufgerufen = true;
            letzterPfad = pfad;
            letzterText = text;
        }
    }

    @BeforeEach
    void setup() {
        mockSchreiber = new MockDateiSchreibHelfer();
        mockLeser = new MockDateiLeseHelfer();
        speicher = new DateiSpeicher("Tagebuch", mockSchreiber, mockLeser);
    }

    @Test
    void speichern_ruftAnhaengenMitLeerzeileMitKorrektFormatierterZeileAuf() {
        TagebuchEintrag eintrag = new TagebuchEintrag("2025-04-18", "08:00", "Testeintrag");

        speicher.speichern(eintrag);

        assertTrue(mockSchreiber.wurdeAufgerufen);
        assertEquals(Paths.get("Tagebuch", "2025-04-18.txt").toString(), mockSchreiber.letzterPfad);
        assertEquals(eintrag.formatForFile(), mockSchreiber.letzterText);
    }

    @Test
    void lesen_ruftLeseTextblockMitKorrektGebildetemPfadAuf() {
        speicher.lesen("2025-04-18");

        assertTrue(mockLeser.wurdeAufgerufen);
        assertEquals(Paths.get("Tagebuch", "2025-04-18.txt").toString(), mockLeser.letzterPfad);
    }

    @TempDir
    Path tempDir;

    DateiSpeicher createSpeicher(Path dir) {
        Path ordner = dir.resolve("Tagebuch");
        return new DateiSpeicher(
                ordner.toString(),
                new DateiSchreibHelfer() {
                    @Override
                    public void anhaengenMitLeerzeile(String pfad, String text) {
                        try {
                            Path path = Paths.get(pfad);
                            if (Files.exists(path) && Files.size(path) > 0) {
                                Files.writeString(path, System.lineSeparator(), StandardOpenOption.APPEND);
                            }
                            Files.writeString(path, text + System.lineSeparator(), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
                        } catch (IOException e) {
                            fail("Schreiben fehlgeschlagen: " + e.getMessage());
                        }
                    }
                },
                new DateiLeseHelfer() {
                    @Override
                    public String leseTextblock(String pfad) {
                        try {
                            return String.join(System.lineSeparator(), Files.readAllLines(Paths.get(pfad)));
                        } catch (IOException e) {
                            return "Fehler beim Lesen: " + e.getMessage();
                        }
                    }
                }
        );
    }

    @Test
    void loeschen_entferntDatei() throws IOException {
        Path datei = tempDir.resolve("Tagebuch/2025-04-18.txt");
        Files.createDirectories(datei.getParent());
        Files.writeString(datei, "Inhalt");

        DateiSpeicher speicher = createSpeicher(tempDir);
        speicher.loeschen("2025-04-18");

        assertFalse(Files.exists(datei));
    }

    @Test
    void loeschenEintrag_entferntGezieltenEintrag() throws IOException {
        Path datei = tempDir.resolve("Tagebuch/2025-04-18.txt");
        Files.createDirectories(datei.getParent());
        Files.write(datei, List.of(
                "Eingetragen um 08:00:",
                "Hallo",
                "Eingetragen um 18:00:",
                "Tschüss"
        ));

        DateiSpeicher speicher = createSpeicher(tempDir);
        speicher.loeschenEintrag("2025-04-18", "08:00");

        List<String> inhalt = Files.readAllLines(datei);
        assertEquals(List.of("Eingetragen um 18:00:", "Tschüss"), inhalt);
    }

    @Test
    void loeschenEintrag_loeschtKomplettWennLetzterEintrag() throws IOException {
        Path datei = tempDir.resolve("Tagebuch/2025-04-18.txt");
        Files.createDirectories(datei.getParent());
        Files.write(datei, List.of("Eingetragen um 08:00:", "Eintrag"));

        DateiSpeicher speicher = createSpeicher(tempDir);
        speicher.loeschenEintrag("2025-04-18", "08:00");

        assertFalse(Files.exists(datei));
    }

    @Test
    void bearbeiten_ersetztEintragText() throws IOException {
        Path datei = tempDir.resolve("Tagebuch/2025-04-18.txt");
        Files.createDirectories(datei.getParent());
        Files.write(datei, List.of("Eingetragen um 08:00:", "Original", "Eingetragen um 10:00:", "Anderer"));

        DateiSpeicher speicher = createSpeicher(tempDir);
        boolean result = speicher.bearbeiten("2025-04-18", "08:00", "Bearbeitet");

        List<String> lines = Files.readAllLines(datei);
        assertTrue(result);
        assertEquals(List.of("Eingetragen um 08:00:", "Bearbeitet", "Eingetragen um 10:00:", "Anderer"), lines);
    }

    @Test
    void getVerfuegbareEintraege_gibtDateinamenOhneEndungZurueck() throws IOException {
        Path ordner = tempDir.resolve("Tagebuch");
        Files.createDirectories(ordner);
        Files.createFile(ordner.resolve("2025-04-18.txt"));
        Files.createFile(ordner.resolve("2025-04-19.txt"));

        DateiSpeicher speicher = createSpeicher(tempDir);

        List<String> eintraege = speicher.getVerfuegbareEintraege();
        assertTrue(eintraege.contains("2025-04-18"));
        assertTrue(eintraege.contains("2025-04-19"));
    }
}
