package utility_tests;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import utility.VerzeichnisHelfer;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class VerzeichnisHelferTest {

    @Test
    void erstelltOrdnerWennNichtVorhanden(@TempDir Path tempDir) {
        Path neuerOrdner = tempDir.resolve("neu");

        assertFalse(Files.exists(neuerOrdner));

        VerzeichnisHelfer helfer = new VerzeichnisHelfer();
        helfer.sicherstellen(neuerOrdner.toString());

        assertTrue(Files.isDirectory(neuerOrdner));
    }

    @Test
    void machtNichtsWennOrdnerSchonExistiert(@TempDir Path tempDir) {
        VerzeichnisHelfer helfer = new VerzeichnisHelfer();

        // Ordner schon da
        helfer.sicherstellen(tempDir.toString());

        // Sollte nichts verändern oder Fehler werfen
        assertTrue(Files.isDirectory(tempDir));
    }

    @Test
    void erstelltVerschachtelteVerzeichnisse(@TempDir Path tempDir) {
        Path tief = tempDir.resolve("eins/zwei/drei");

        VerzeichnisHelfer helfer = new VerzeichnisHelfer();
        helfer.sicherstellen(tief.toString());

        assertTrue(Files.isDirectory(tief));
    }
}

