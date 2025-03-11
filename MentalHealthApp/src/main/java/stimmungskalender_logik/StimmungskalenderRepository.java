package stimmungskalender_logik;

import java.util.List;

/**
 * Dieses Interface definiert die Schnittstelle für das Speichern und Laden
 * von Stimmungseinträgen und Emotionen.
 * Es ermöglicht unterschiedliche Speicherstrategien (z. B. Datei, Datenbank).
 */
public interface StimmungskalenderRepository {

    /**
     * Speichert einen Stimmungseintrag (Datum, Uhrzeit, Stimmung).
     * @param eintrag Der zu speichernde Stimmungseintrag
     */
    void speichern(Stimmungseintrag eintrag);

    /**
     * Liest alle gespeicherten Einträge aus dem Speicher (Stimmungen + Emotionen).
     * @return Liste aller Textzeilen aus den gespeicherten Daten
     */
    List<String> lesenAlle();

    /**
     * Speichert eine Liste von Emotionseinträgen mit Intensität und Ursache.
     * @param emotionen Die zu speichernden Emotionen
     */
    void speichernEmotionen(List<Emotionseintrag> emotionen);
}
