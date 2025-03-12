package gedanken_reflexion_logik;

import java.util.List;

public interface GedankenReflexionRepository {
    void speichern(GedankenReflexionEintrag eintrag);
    List<String> lesenAlle();
}
