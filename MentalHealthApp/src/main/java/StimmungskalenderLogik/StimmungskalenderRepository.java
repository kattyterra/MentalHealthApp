package StimmungskalenderLogik;

import java.util.List;

public interface StimmungskalenderRepository {
    void speichern(Stimmungseintrag eintrag);
    List<String> lesenAlle();
    void speichernEmotionen(List<Emotionseintrag> emotionen);
}
