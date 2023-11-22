package FuckinAe;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class Main {
    public static void main(String[] args) {
        DefaultItemsChecker.checkDefaultItemsFile();


        BarTab barTab = new BarTab();
        barTab.readFromTxtFile("default_items.txt"); // Load default items at startup

        JFrame frame = new JFrame("Caverna do Bin Laden");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 700);

        JLabel totalCostLabel = new JLabel("Total: $0.00");

        JComboBox<String> itemDropdown = new JComboBox<>();
        for (TabItem item : barTab.getTabItems()) {
            itemDropdown.addItem(item.getItemName());
        }

        JButton addItemButton = new JButton("Adicionar Item");

        JTextArea tabItemsTextArea = new JTextArea(10, 20);
        tabItemsTextArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(tabItemsTextArea);

        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());
        panel.add(itemDropdown);
        panel.add(addItemButton);
        panel.add(totalCostLabel);

        frame.add(panel, BorderLayout.NORTH);
        frame.add(scrollPane, BorderLayout.CENTER);

        // Dropdown menu for file selection
        JComboBox<String> fileDropdown = new JComboBox<>();

        // Populate dropdown with available .txt files in the folder
        File folder = new File(".");
        File[] files = folder.listFiles((dir, name) -> name.toLowerCase().endsWith(".txt") && !name.equals("default_items.txt"));
        if (files != null) {
            for (File file : files) {
                fileDropdown.addItem(file.getName());
            }
        }

        JPanel filePanel = new JPanel();
        filePanel.setLayout(new FlowLayout());
        filePanel.add(fileDropdown);
        frame.add(filePanel, BorderLayout.SOUTH);

        JButton createClientButton = new JButton("Criar Cliente");
        panel.add(createClientButton); // Add the button to an appropriate panel in your GUI layout

        JButton closeTabButton = new JButton("Fechar comanda");
        panel.add(closeTabButton); // Add the "Close Tab" button to an appropriate panel in your GUI layout

        closeTabButton.addActionListener(e -> {
            double totalCost = barTab.calculateTotalCost();
            String selectedFile = (String) fileDropdown.getSelectedItem();

            if (selectedFile != null && !selectedFile.isEmpty()) {
                File fileToDelete = new File(selectedFile);
                if (fileToDelete.delete()) {
                    JOptionPane.showMessageDialog(null, "Total gasto: $" + String.format("%.2f", totalCost) + ". Comanda fechada. Arquivio deletado: " + selectedFile);
                    fileDropdown.removeItem(selectedFile); // Remove the file from the dropdown
                    tabItemsTextArea.setText(""); // Clear the display
                    totalCostLabel.setText("Total Cost: $0.00"); // Reset total cost label
                } else {
                    JOptionPane.showMessageDialog(null, "Total gasto: $" + String.format("%.2f", totalCost) + ". Comanda fechada. Failed to delete the file: " + selectedFile);
                }
            } else {
                JOptionPane.showMessageDialog(null, "No file selected.");
            }
        });

        createClientButton.addActionListener(e -> {
            try {
                String name = JOptionPane.showInputDialog("Nome do cliente:");
                int age = 0;
                try {
                    String ageInput = JOptionPane.showInputDialog("Idade do cliente: ");
                    age = Integer.parseInt(ageInput);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Invalid age input. Age set to default (0).");
                }

                if (18 > age || age> 110) {
                    throw new InvalidAgeException("Idades acima de 18 ou inferiores a 110.");
                }

                Client newClient = new Client(name, age);

                String fileName = name + "_" + age + ".txt";
                newClient.createClientFile(fileName);

                // Add the new file name to the fileDropdown
                fileDropdown.addItem(fileName);

                JOptionPane.showMessageDialog(null, "Novo cliente criado: " + newClient.getName() + ", Idade: " + newClient.getAge());
            } catch (InvalidAgeException ex) {
                JOptionPane.showMessageDialog(null, "Idade invalida: " + ex.getMessage());
            }
        });

        addItemButton.addActionListener(e -> {
            String selectedItem = (String) itemDropdown.getSelectedItem();

            // Assuming 'default_items.txt' contains the list of available items
            BarTab defaultItems = new BarTab();
            defaultItems.readFromTxtFile("default_items.txt");

            boolean itemFound = false;
            for (TabItem item : defaultItems.getTabItems()) {
                if (item.getItemName().equals(selectedItem)) {
                    barTab.addItem(item); // Add the selected item to the current tab
                    itemFound = true;
                    break;
                }
            }

            if (!itemFound) {
                // Create a default item if not found in default_items.txt
                // For example, here, I'll create a Food item with a default price of 0
                TabItem newItem = new Food(selectedItem, 0); // Adjust based on your item structure
                barTab.addItem(newItem);
            }

            tabItemsTextArea.setText(barTab.getTabItemsAsString());

            double totalCost = barTab.calculateTotalCost();
            totalCostLabel.setText("Total: $" + String.format("%.2f", totalCost));
        });

        JButton removeItemButton = new JButton("Remover Item");
        panel.add(removeItemButton); // Add the button to an appropriate panel in your GUI layout

        removeItemButton.addActionListener(e -> {
            // Prompt the user for the item name to remove
            String itemNameToRemove = JOptionPane.showInputDialog("Entre com o nome do item a remover:");

            if (itemNameToRemove != null && !itemNameToRemove.isEmpty()) {
                boolean itemRemoved = barTab.removeItemByName(itemNameToRemove);

                if (itemRemoved) {
                    // Update the tabItemsTextArea to reflect the changes (refresh the display)
                    tabItemsTextArea.setText(barTab.getTabItemsAsString());
                    // Display a message indicating successful removal
                    JOptionPane.showMessageDialog(null, "Item '" + itemNameToRemove + "' removido com sucesso.");
                } else {
                    // Display a message if the item was not found
                    JOptionPane.showMessageDialog(null, "No item '" + itemNameToRemove + "' found.");
                }
            }
            double totalCost = barTab.calculateTotalCost();
            totalCostLabel.setText("Total: $" + String.format("%.2f", totalCost));
        });

        fileDropdown.addActionListener(e -> {
            String selectedFile = (String) fileDropdown.getSelectedItem();

            if (selectedFile != null && !selectedFile.isEmpty()) {
                // Clear existing tab items
                barTab.getTabItems().clear();

                // Load items from the selected file
                barTab.readFromTxtFile(selectedFile);

                // Update the total cost label with the newly loaded tab items' total cost
                double totalCost = barTab.calculateTotalCost();
                totalCostLabel.setText("Total Cost: $" + String.format("%.2f", totalCost));

                // Update the tabItemsTextArea to display the items from the selected file
                tabItemsTextArea.setText(barTab.getTabItemsAsString());
            }
        });

        JButton saveFileButton = new JButton("Salvar Arquivo");

        saveFileButton.addActionListener(e -> {
            String selectedFile = (String) fileDropdown.getSelectedItem();

            if (selectedFile != null && !selectedFile.isEmpty()) {
                // Save the alterations made to the selected file
                barTab.saveToTxtFile(selectedFile);
                // Optionally, show a confirmation message or perform other actions upon successful save
                JOptionPane.showMessageDialog(null, "Arquivo '" + selectedFile + "' salvo com sucesso.");
            } else {
                // Handle if no file is selected
                JOptionPane.showMessageDialog(null, "Please select a file to save.");
            }
        });
        filePanel.add(saveFileButton);
        frame.setVisible(true);

        WelcomePopup.WelcomePopup();
    }
}
