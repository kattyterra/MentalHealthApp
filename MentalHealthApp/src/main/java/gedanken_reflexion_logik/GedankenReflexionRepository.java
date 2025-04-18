package gedanken_reflexion_logik;

import java.util.List;

/**
 * Schnittstelle für das Repository zur Gedankenreflexion.
 * Sie definiert die grundlegenden Operationen zum Speichern
 * und Lesen von GedankenReflexionEinträgen.
 * Das Ziel ist eine lose Kopplung zwischen Speichermechanismus
 * (z. B. Datei, Datenbank) und der Geschäftslogik.
 */
public interface GedankenReflexionRepository {

    /**
     * Speichert einen neuen Gedankenreflexionseintrag.
     *
     * @param eintrag der zu speichernde Eintrag
     */
    void speichern(GedankenReflexionEintrag eintrag);

    /**
     * Liest alle gespeicherten Reflexionseinträge als Textzeilen.
     * Die Ausgabe ist formatierungsfertig zur Anzeige.
     *
     * @return Liste aller gespeicherten Einträge (je nach Implementierung zeilenweise)
     */
    List<String> lesenAlle();
}
