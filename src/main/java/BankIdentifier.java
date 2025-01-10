import java.io.*;
import java.net.*;
import java.util.Scanner;

public class BankIdentifier {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the first three digits of the account number: ");
        String userInput = scanner.nextLine();

        if (userInput.length() != 3 || !userInput.matches("\\d{3}")) {
            System.out.println("Invalid format. Please enter exactly three digits.");
            return;
        }

        try {
            URL url = new URL("https://ewib.nbp.pl/plewibnra?dokNazwa=plewibnra.txt");
            BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));

            String line;
            boolean found = false;

            // Search the file for matching data
            while ((line = reader.readLine()) != null) {
                if (line.startsWith(userInput)) {
                    String[] parts = line.split(";", -1);
                    if (parts.length >= 2) {
                        String bankCode = parts[0];
                        String bankName = parts[1];
                        System.out.println("Short bank number: " + bankCode);
                        System.out.println("Bank name: " + bankName);
                        found = true;
                        break;
                    }
                }
            }

            reader.close();

            if (!found) {
                System.out.println("No bank information found for the given digits.");
            }

        } catch (IOException e) {
            System.out.println("An error occurred while fetching data: " + e.getMessage());
        }
    }
}
