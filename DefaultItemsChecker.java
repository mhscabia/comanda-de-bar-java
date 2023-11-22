package FuckinAe;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class DefaultItemsChecker {
    public static void checkDefaultItemsFile() {
        String fileName = "default_items.txt";

        File file = new File(fileName);

        if (file.exists()) {
            // File exists, do nothing
            System.out.println("File 'default_items.txt' already exists. Skipping creation.");
        } else {
            // File doesn't exist, create a new file and populate it
            try (FileWriter writer = new FileWriter(fileName)) {
                writer.write("Food, Pizza, 12\nFood, Salad, 8\nFood, Burger, 10\nFood, Pasta, 15\nFood, Sushi, 20\nFood, Tacos, 9\nFood, Sandwich, 7\nDrink, Soda, 2\nDrink, Coffee, 4\nDrink, Tea, 3\nDrink, Smoothie, 6\nDrink, Lemonade, 3.5\nDrink, Milkshake, 5\nDrink, Juice, 3\nDrink, Beer, 5\nDrink, Wine, 12\nDrink, Whiskey, 8");
                System.out.println("File 'default_items.txt' created with default content.");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}q