package inspirations_logik_tests;

import inspirations_logik.InspirationsSpeicher;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InspirationsSpeicherTest {

    @Test
    void ladeSaetze_liefertInhalteAusDatei(@TempDir Path tempDir) throws IOException {
        Path inspoFile = tempDir.resolve("Inspo.txt");
        Files.write(inspoFile, List.of("Sei du selbst.", "Glaub an dich."));

        InspirationsSpeicher speicher = new InspirationsSpeicher(inspoFile.toString());
        List<String> saetze = speicher.ladeSaetze();

        assertEquals(2, saetze.size());
        assertTrue(saetze.contains("Sei du selbst."));
    }

    @Test
    void ladeSaetze_leereDateiGibtLeereListe(@TempDir Path tempDir) throws IOException {
        Path leereDatei = tempDir.resolve("Inspo.txt");
        Files.createFile(leereDatei);

        InspirationsSpeicher speicher = new InspirationsSpeicher(leereDatei.toString());
        List<String> saetze = speicher.ladeSaetze();

        assertTrue(saetze.isEmpty());
    }

    @Test
    void ladeSaetze_dateiFehltGibtLeereListe() {
        InspirationsSpeicher speicher = new InspirationsSpeicher("nicht_existent.txt");
        List<String> saetze = speicher.ladeSaetze();

        assertNotNull(saetze); // darf nicht null sein
        assertTrue(saetze.isEmpty());
    }
}