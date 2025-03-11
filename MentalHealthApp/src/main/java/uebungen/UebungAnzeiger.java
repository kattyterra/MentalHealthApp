package uebungen;

import java.util.Scanner;

public class UebungAnzeiger {
    public void zeige(Uebung uebung, Scanner scanner) {
        System.out.println("\n🌿 " + uebung.name());
        System.out.println("🎯 Ziel: " + uebung.ziel());
        System.out.println("\n📖 Anleitung:");
        for (String s : uebung.anleitung()) {
            System.out.println("• " + s);
        }
        System.out.println("\nDrücke Enter, um zurückzukehren.");
        scanner.nextLine();
    }
}
