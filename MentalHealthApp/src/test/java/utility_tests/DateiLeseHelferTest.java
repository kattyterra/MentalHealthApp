package utility_tests;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import utility.DateiLeseHelfer;

import java.io.IOException;
import java.nio.file.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DateiLeseHelferTest {

    @Test
    void leseAlleZeilen_liestTxtDateien(@TempDir Path tempDir) throws IOException {
        // Zwei .txt-Dateien mit je zwei Zeilen
        Path datei1 = tempDir.resolve("a.txt");
        Path datei2 = tempDir.resolve("b.txt");

        Files.write(datei1, List.of("Zeile A1", "Zeile A2"));
        Files.write(datei2, List.of("Zeile B1", "Zeile B2"));

        DateiLeseHelfer helfer = new DateiLeseHelfer();
        List<String> zeilen = helfer.leseAlleZeilen(tempDir.toString());

        assertEquals(4, zeilen.size());
        assertTrue(zeilen.contains("Zeile A1"));
        assertTrue(zeilen.contains("Zeile B2"));
    }

    @Test
    void leseAlleZeilen_leererOrdnerGibtLeereListe(@TempDir Path leererOrdner) {
        DateiLeseHelfer helfer = new DateiLeseHelfer();
        List<String> zeilen = helfer.leseAlleZeilen(leererOrdner.toString());

        assertTrue(zeilen.isEmpty());
    }

    @Test
    void leseAlleZeilen_unbekannterOrdnerGibtLeereListe() {
        DateiLeseHelfer helfer = new DateiLeseHelfer();
        // Verzeichnis existiert nicht
        List<String> zeilen = helfer.leseAlleZeilen("nicht_vorhanden_123456");

        assertTrue(zeilen.isEmpty());
    }

    @Test
    void leseTextblock_gibtTextblockZurueck(@TempDir Path tempDir) throws IOException {
        Path datei = tempDir.resolve("eintrag.txt");
        Files.write(datei, List.of("Erste Zeile", "Zweite Zeile"));

        DateiLeseHelfer helfer = new DateiLeseHelfer();
        String text = helfer.leseTextblock(datei.toString());

        assertTrue(text.contains("Erste Zeile"));
        assertTrue(text.contains("Zweite Zeile"));
    }

    @Test
    void leseTextblock_beiNichtExistenterDateiGibtFehlermeldung() {
        DateiLeseHelfer helfer = new DateiLeseHelfer();
        String text = helfer.leseTextblock("nicht_vorhanden.txt");

        assertEquals("Kein Eintrag für dieses Datum gefunden.", text);
    }
}