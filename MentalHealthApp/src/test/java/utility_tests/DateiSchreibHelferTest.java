package utility_tests;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import utility.DateiSchreibHelfer;

import java.io.IOException;
import java.nio.file.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DateiSchreibHelferTest {

    @Test
    void anhaengen_schreibtMehrereZeilenAnDatei(@TempDir Path tempDir) throws IOException {
        Path datei = tempDir.resolve("test.txt");
        DateiSchreibHelfer helfer = new DateiSchreibHelfer();

        helfer.anhaengen(tempDir.toString() + "/", "test.txt", List.of("Zeile 1", "Zeile 2"));

        List<String> zeilen = Files.readAllLines(datei);
        assertEquals(List.of("Zeile 1", "Zeile 2"), zeilen);
    }

    @Test
    void anhaengen_haengtAnBestehendeDateiAn(@TempDir Path tempDir) throws IOException {
        Path datei = tempDir.resolve("anhang.txt");
        Files.write(datei, List.of("Vorhanden"));

        DateiSchreibHelfer helfer = new DateiSchreibHelfer();
        helfer.anhaengen(tempDir.toString() + "/", "anhang.txt", List.of("Neu 1", "Neu 2"));

        List<String> zeilen = Files.readAllLines(datei);
        assertEquals(List.of("Vorhanden", "Neu 1", "Neu 2"), zeilen);
    }

    @Test
    void anhaengenMitLeerzeile_fuegtLeerzeileWennDateiNichtLeer(@TempDir Path tempDir) throws IOException {
        Path datei = tempDir.resolve("mitLeerzeile.txt");
        Files.writeString(datei, "Vorher");

        DateiSchreibHelfer helfer = new DateiSchreibHelfer();
        helfer.anhaengenMitLeerzeile(datei.toString(), "Nachher");

        List<String> zeilen = Files.readAllLines(datei);
        assertEquals(List.of("Vorher", "Nachher"), zeilen); // durch vorheriges "\n" zwei getrennte Zeilen
    }

    @Test
    void anhaengenMitLeerzeile_ohneLeerzeileWennDateiLeer(@TempDir Path tempDir) throws IOException {
        Path datei = tempDir.resolve("leer.txt");

        DateiSchreibHelfer helfer = new DateiSchreibHelfer();
        helfer.anhaengenMitLeerzeile(datei.toString(), "Erste Zeile");

        List<String> zeilen = Files.readAllLines(datei);
        assertEquals(List.of("Erste Zeile"), zeilen);
    }

    @Test
    void anhaengenMitLeerzeile_erstelltDateiWennNichtVorhanden(@TempDir Path tempDir) throws IOException {
        Path datei = tempDir.resolve("neu.txt");

        assertFalse(Files.exists(datei)); // Datei darf noch nicht da sein

        DateiSchreibHelfer helfer = new DateiSchreibHelfer();
        helfer.anhaengenMitLeerzeile(datei.toString(), "Startzeile");

        assertTrue(Files.exists(datei));
        assertEquals(List.of("Startzeile"), Files.readAllLines(datei));
    }
}
