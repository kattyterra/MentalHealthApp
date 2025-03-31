import menus.MainMenu;

/**
 * Hauptklasse zum Starten des gesamten Programms.
 * Sie enthält die main()-Methode und leitet den Einstieg in das Hauptmenü ein.
 */
public class App {

    /**
     * Einstiegspunkt des Programms.
     * Erstellt ein neues Main-Menü und zeigt es dem Benutzer an.
     *
     * @param args Kommandozeilenparameter (werden hier nicht verwendet)
     */
    public static void main(String[] args) {
        MainMenu programmStart = new MainMenu();
        programmStart.showMenu();
    }
}
