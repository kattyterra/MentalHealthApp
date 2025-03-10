package Menus;

import java.util.Scanner;

public class Main {

    public Main() {
    }

    public void showMenu() {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("\nWähle bitte aus, was du tun möchtest:");
            System.out.println("1 - Dein Tagebuch");
            System.out.println("2 - Dein Stimmungskalender");
            System.out.println("3 - Deine Routinen");
            System.out.println("4 - Atem- und Entspannungsübungen");
            System.out.println("5 - Deine kleine Inspirationsecke");
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
                    AtemUndEnspannung atemUndEnspannung = new AtemUndEnspannung();
                    atemUndEnspannung.showMenu(scanner);
                    break;
                case 5:
                    Inspirationssaetze inspirationssaetze = new Inspirationssaetze();
                    inspirationssaetze.showMenu(scanner);
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

