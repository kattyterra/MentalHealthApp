package utility;

import java.io.File;

public class VerzeichnisHelfer {

    /**
     * Erstellt ein Verzeichnis, wenn es nicht existiert.
     * Gibt true zurück, wenn es erfolgreich erstellt oder schon vorhanden ist.
     *
     * @param pfad Pfad zum Verzeichnis
     */
    public void sicherstellen(String pfad) {
        File dir = new File(pfad);
        if (!dir.exists()) {
            boolean success = dir.mkdirs();
            if (!success) {
                System.err.println("Ordner '" + pfad + "' konnte nicht erstellt werden!");
            }
        }
    }
}