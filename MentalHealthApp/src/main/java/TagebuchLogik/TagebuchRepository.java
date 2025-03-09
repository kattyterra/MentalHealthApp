package TagebuchLogik;

import java.util.List;

public interface TagebuchRepository {
    void speichern(TagebuchEintrag eintrag);
    void loeschen(String datum);
    void loeschenEintrag(String datum, String uhrzeit);
    String lesen(String datum);
    List<String> getVerfuegbareEintraege();
    boolean bearbeiten(String datum, String uhrzeit, String neuerText);
}
