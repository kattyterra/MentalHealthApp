package StimmungskalenderLogik;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class StimmungskalenderSpeicher implements StimmungskalenderRepository {
    private final String ordner = "Stimmungskalender/";
    private final String dateiname = "stimmungskalender.txt";
    private final String pfad = ordner + dateiname;

    public StimmungskalenderSpeicher(){
        File dir = new File(ordner);
        if (!dir.exists()) {
            boolean success = dir.mkdirs();
            if (!success) {
                System.err.println("Ordner '" + ordner + "' konnte nicht erstellt werden!");
            }
        }
    }

    @Override
    public void speichern(Stimmungseintrag eintrag) {
        try (FileWriter writer = new FileWriter(pfad, true)) {
            writer.write(eintrag.formatForFile() + System.lineSeparator());
        } catch (IOException e) {
            System.err.println("Fehler beim Speichern: " + e.getMessage());
        }
    }

    @Override
    public List<String> lesenAlle() {
        try {
            return Files.readAllLines(Paths.get(pfad));
        } catch (IOException e) {
            System.err.println("Fehler beim Lesen: " + e.getMessage());
            return List.of();
        }
    }


}
