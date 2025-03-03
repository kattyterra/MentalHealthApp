package Controllers;

import java.util.Scanner;

public class InterfaceController {

    public InterfaceController() {
    }

    public void startProgramm() {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("\nWähle bitte aus, was du tun möchtest:");
            System.out.println("1 - Tagebuch verwalten");
            System.out.println("2 - Stimmungskalender");
            System.out.println("3 - Routinen verwalten");
            System.out.println("4 - Atem- und Entspannungsübungen");
            System.out.println("5 - Inspirationssatz bekommen");
            System.out.println("6 - Programm beenden");
            System.out.print("Deine Wahl: ");

            int choice;
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Ungültige Eingabe. Bitte eine Zahl eingeben.");
                continue;
            }

            switch (choice) {
                case 1:
                    TagebuchController tg = new TagebuchController();
                    tg.TagebuchMenu(scanner);
                    break;
                case 2:
                    System.out.println("Stimmungskalender wurde gewählt.");
                    break;
                case 3:
                    System.out.println("Routinen verwalten wurde gewählt.");
                    break;
                case 4:
                    System.out.println("Atem- und Entspannungsübungen wurde gewählt.");
                    break;
                case 5:
                    System.out.println("Hier ist dein Inspirationssatz!");
                    break;
                case 6:
                    System.out.println("Programm wird beendet...");
                    running = false;
                    break;
                default:
                    System.out.println("Ungültige Auswahl. Bitte erneut versuchen.");
            }
        }

        scanner.close();
    }
}

