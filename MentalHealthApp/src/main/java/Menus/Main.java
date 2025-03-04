package Controllers;

import java.util.Scanner;

public class MainController {

    public MainController() {
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
                    Tagebuch tagebuch = new Tagebuch();
                    tagebuch.showMenu(scanner);
                    break;
                case 2:
                    Stimmungskalender stimmungskalender = new Stimmungskalender();
                    stimmungskalender.showMenu(scanner);
                    break;
                case 3:
                    Routine routine = new Routine();
                    routine.showMenu(scanner);
                    break;
                case 4:
                    AtemUndEnspannung.showMenu(scanner);
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

