package gedanken_reflexion_logik;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GedankenReflexionSpeicher implements GedankenReflexionRepository {
    private final String ordner = "Reflexionen/";

    public GedankenReflexionSpeicher() {
        File dir = new File(ordner);
        if (!dir.exists()) {
            boolean success = dir.mkdirs();
            if (!success) {
                System.err.println("Ordner '" + ordner + "' konnte nicht erstellt werden!");
            }
        }
    }

    @Override
    public void speichern(GedankenReflexionEintrag eintrag) {
        String pfad = ordner + LocalDate.now() + ".txt";
        try (FileWriter w = new FileWriter(pfad, true)) {
            w.write(eintrag.formatForFile());
        } catch (IOException e) {
            System.err.println("Fehler beim Speichern: " + e.getMessage());
        }
    }

    @Override
    public List<String> lesenAlle() {
        List<String> zeilen = new ArrayList<>();
        File[] dateien = new File(ordner).listFiles((d, name) -> name.endsWith(".txt"));
        if (dateien != null) {
            Arrays.sort(dateien);
            for (File f : dateien) {
                try {
                    zeilen.add("\n📅 " + f.getName().replace(".txt", "") + ":\n");
                    zeilen.addAll(java.nio.file.Files.readAllLines(f.toPath()));
                } catch (IOException e) {
                    zeilen.add("Fehler beim Lesen: " + f.getName());
                }
            }
        }
        return zeilen;
    }
}