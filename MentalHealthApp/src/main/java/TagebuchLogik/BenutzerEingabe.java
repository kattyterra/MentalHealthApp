package TagebuchLogik;

import java.util.Scanner;

public class BenutzerEingabe {

    public String leseEintrag(Scanner scanner) {
        System.out.println("Gib den Text für deinen Eintrag ein:");
        return scanner.nextLine();
    }

    public String leseDatum(Scanner scanner) {
        System.out.println("Gib das Datum im Format yyyy-MM-dd ein:");
        return scanner.nextLine();
    }
}
