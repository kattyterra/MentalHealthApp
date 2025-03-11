package tagebuch_logik;

import java.util.List;

/**
 * Dieses Interface definiert die Schnittstelle für die grundlegenden Funktionen
 * des Tagebuchsystems. Es ermöglicht unterschiedliche Implementierungen
 * (z. B. Datei, Datenbank) für das Speichern, Löschen, Bearbeiten und Lesen von Einträgen.
 */
public interface TagebuchRepository {

    /**
     * Speichert einen neuen Tagebucheintrag.
     *
     * @param eintrag Der zu speichernde Eintrag
     */
    void speichern(TagebuchEintrag eintrag);

    /**
     * Löscht den kompletten Eintrag (bzw. die Datei) für ein bestimmtes Datum.
     *
     * @param datum Das Datum des zu löschenden Eintrags (Format: yyyy-MM-dd)
     */
    void loeschen(String datum);

    /**
     * Löscht nur einen bestimmten Eintrag innerhalb einer Datei (nach Uhrzeit).
     *
     * @param datum   Das Datum der Datei
     * @param uhrzeit Die Uhrzeit des zu löschenden Eintrags
     */
    void loeschenEintrag(String datum, String uhrzeit);

    /**
     * Liest alle Einträge eines bestimmten Tages.
     *
     * @param datum Das gewünschte Datum (Format: yyyy-MM-dd)
     * @return Der Textinhalt des Eintrags oder eine Fehlermeldung
     */
    String lesen(String datum);

    /**
     * Gibt eine Liste aller vorhandenen Tagebucheinträge (Datumsangaben) zurück.
     *
     * @return Liste aller verfügbaren Eintragsdaten (z. B. „2025-03-11“)
     */
    List<String> getVerfuegbareEintraege();

    /**
     * Bearbeitet den Text eines Eintrags zu einem bestimmten Zeitpunkt.
     *
     * @param datum     Das Datum der Datei
     * @param uhrzeit   Die Uhrzeit des Eintrags
     * @param neuerText Der neue Text, der den bisherigen ersetzen soll
     * @return true, wenn der Eintrag erfolgreich bearbeitet wurde, sonst false
     */
    boolean bearbeiten(String datum, String uhrzeit, String neuerText);
}
