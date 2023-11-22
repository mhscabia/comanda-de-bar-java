package FuckinAe;

import java.io.File;
import java.io.IOException;

public class Client {
    private String name;
    private int age;

    public Client(String name, int age) throws InvalidAgeException {
        if (age < 0 || age > 110) { // Updated age limit to 110 years
            throw new InvalidAgeException("Invalid age provided.");
        }
        this.name = name;
        this.age = age;
    }

    // Getters for name and age
    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public void createClientFile(String fileName) {
        try {
            File file = new File(fileName);
            if (file.createNewFile()) {
                System.out.println("File created: " + fileName);
            } else {
                System.out.println("File already exists.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
