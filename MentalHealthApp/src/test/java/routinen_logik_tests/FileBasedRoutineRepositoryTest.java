package routinen_logik_tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import routinen_logik.*;

import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

public class FileBasedRoutineRepositoryTest {

    @TempDir
    Path tempDir;

    private File stammlisteDatei;
    private FileBasedRoutineRepository repository;

    @BeforeEach
    void setup() throws Exception {
        // Erstelle das Routinen-Verzeichnis und stammliste.txt
        Path routinenOrdner = tempDir.resolve("Routinen");
        File ordnerFile = new File(routinenOrdner.toUri());
        if (!ordnerFile.exists() && !ordnerFile.mkdirs()) {
            throw new IOException("❌ Verzeichnis konnte nicht erstellt werden: " + ordnerFile.getPath());
        }
        stammlisteDatei = routinenOrdner.resolve("stammliste.txt").toFile();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(stammlisteDatei))) {
            writer.write("MORGEN");
            writer.newLine();
            writer.write("Wasser trinken");
            writer.newLine();
        }

        // Kopie der Klasse mit Pfad-Injektion (angepasst oder erweitern)
        repository = new FileBasedRoutineRepository() {
            {
                try {
                    // Zugriff auf Superklasse (da die Felder dort deklariert sind)
                    Class<?> superClass = this.getClass().getSuperclass();

                    Field ordnerFeld = superClass.getDeclaredField("ordner");
                    ordnerFeld.setAccessible(true);
                    ordnerFeld.set(this, routinenOrdner.toString());

                    Field stammlisteFeld = superClass.getDeclaredField("stammliste");
                    stammlisteFeld.setAccessible(true);
                    stammlisteFeld.set(this, stammlisteDatei.getPath());

                    Field tagesdateiFeld = superClass.getDeclaredField("tagesdatei");
                    tagesdateiFeld.setAccessible(true);
                    tagesdateiFeld.set(this, routinenOrdner.resolve("2025-04-25.txt").toString());

                    // Routinen laden und speichern
                    Method ladeStammlisteMethode = superClass.getDeclaredMethod("ladeStammliste");
                    ladeStammlisteMethode.setAccessible(true);
                    ladeStammlisteMethode.invoke(this);
                    speichern();

                } catch (Exception e) {
                    throw new RuntimeException("Fehler beim Setzen der Felder per Reflection", e);
                }
            }
        };
    }

    @Test
    void hinzufuegen_fuegtRoutineZurListeHinzu() throws RoutineException {
        int vorher = repository.getRoutinen().size();
        repository.hinzufuegen(new Routine(RoutinenArt.ABEND, "Spazierengehen"));
        int nachher = repository.getRoutinen().size();

        assertEquals(vorher + 1, nachher);
    }

    @Test
    void bearbeiten_aendertRoutineDaten() throws RoutineException {
        repository.bearbeiten(0, RoutinenArt.NACHT, "Lesen");
        Routine r = repository.getRoutinen().getFirst();

        assertEquals(RoutinenArt.NACHT, r.getArt());
        assertEquals("Lesen", r.getBeschreibung());
    }

    @Test
    void loeschen_entferntRoutine() throws RoutineException {
        repository.hinzufuegen(new Routine(RoutinenArt.MITTAG, "Meditieren"));
        int sizeBefore = repository.getRoutinen().size();
        repository.loeschen(sizeBefore - 1);
        int sizeAfter = repository.getRoutinen().size();

        assertEquals(sizeBefore - 1, sizeAfter);
    }

    @Test
    void speichern_schreibtTagesdatei() throws RoutineException, IOException {
        repository.speichern();

        File routinenVerzeichnis = tempDir.resolve("Routinen").toFile();
        File[] files = routinenVerzeichnis.listFiles();

        assertNotNull(files, "Das Verzeichnis 'Routinen' konnte nicht gelesen werden oder existiert nicht.");

        boolean found = false;
        for (File file : files) {
            if (file.getName().endsWith(".txt") && !file.getName().equals("stammliste.txt")) {
                found = true;
                break;
            }
        }

        assertTrue(found, "Es wurde keine Tagesdatei im Format '.txt' (außer 'stammliste.txt') gefunden.");
    }
}