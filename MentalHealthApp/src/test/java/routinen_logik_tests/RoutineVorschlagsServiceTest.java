package routinen_logik_tests;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import routinen_logik.RoutineVorschlagsService;
import routinen_logik.RoutinenArt;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RoutineVorschlagsServiceTest {

    @Test
    void ladeVorschlaege_liefertKorrekteZuordnung(@TempDir Path tempDir) throws IOException {
        Path datei = tempDir.resolve("vorschlaege.txt");
        Files.write(datei, List.of(
                "MORGEN",
                "Trinken eines Glases Wasser",
                "ABEND",
                "Tagebucheintrag schreiben"
        ));

        RoutineVorschlagsService service = new RoutineVorschlagsService(datei.toString());

        List<String> morgen = service.getVorschlaegeZuArt(RoutinenArt.MORGEN);
        List<String> abend = service.getVorschlaegeZuArt(RoutinenArt.ABEND);

        assertEquals(List.of("Trinken eines Glases Wasser"), morgen);
        assertEquals(List.of("Tagebucheintrag schreiben"), abend);
    }

    @Test
    void getVorschlaegeZuArt_gibtLeereListeZurueckWennNichtGefunden(@TempDir Path tempDir) throws IOException {
        Path datei = tempDir.resolve("leer.txt");
        Files.write(datei, List.of(
                "MORGEN",
                "Dehnen"
        ));

        RoutineVorschlagsService service = new RoutineVorschlagsService(datei.toString());

        List<String> nacht = service.getVorschlaegeZuArt(RoutinenArt.NACHT);
        assertTrue(nacht.isEmpty());
    }

    @Test
    void ladeVorschlaege_mitUngueltigerArt_ignoriertDiese(@TempDir Path tempDir) throws IOException {
        Path datei = tempDir.resolve("ungueltig.txt");
        Files.write(datei, List.of(
                "XYZNONSENSE",
                "Komischer Eintrag",
                "MITTAG",
                "Spaziergang"
        ));

        RoutineVorschlagsService service = new RoutineVorschlagsService(datei.toString());

        assertTrue(service.getVorschlaegeZuArt(RoutinenArt.MITTAG).contains("Spaziergang"));
        assertTrue(service.getVorschlaegeZuArt(RoutinenArt.MORGEN).isEmpty());
    }

    @Test
    void ladeVorschlaege_dateiFehlt_fuehrtZuLeeremErgebnis() {
        // absichtlich einen Pfad geben, der nicht existiert
        RoutineVorschlagsService service = new RoutineVorschlagsService("nicht_existiert.txt");

        assertTrue(service.getVorschlaegeZuArt(RoutinenArt.MORGEN).isEmpty());
    }
}
