package TagebuchLogik;

public interface TagebuchRepository {
    void speichern(TagebuchEintrag eintrag);
    void loeschen(String datum);
    void loeschenEintrag(String datum, String uhrzeit);
}
