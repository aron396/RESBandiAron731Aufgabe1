import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

public class Aufgaben {

    public static List<Ninja> parseJson(String filePath) {
        List<Ninja> ninjas = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            StringBuilder jsonContent = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                jsonContent.append(line.trim());
            }

            // Remove the outer brackets
            String jsonString = jsonContent.toString();
            if (jsonString.startsWith("[")) {
                jsonString = jsonString.substring(1, jsonString.length() - 1);  // Removing '[' and ']'
            }

            // Split the objects in the array by "},"
            String[] ninjaStrings = jsonString.split("},\\s*\\{");

            // Iterate through each ninja string and parse
            for (String ninjaString : ninjaStrings) {
                // Remove curly braces at the start and end
                ninjaString = ninjaString.replace("{", "").replace("}", "");

                // Split by comma
                String[] attributes = ninjaString.split(",\\s*");

                // Parse individual attributes
                int id = 0;
                String charaktername = "";
                String stufeString = "";
                String beschreibung = "";
                String datumString = "";
                double kraftpunkte = 0.0;

                for (String attribute : attributes) {
                    String[] keyValue = attribute.split(":\\s*");
                    if (keyValue[0].contains("Id")) {
                        id = Integer.parseInt(keyValue[1].trim().replace("\"", ""));
                    } else if (keyValue[0].contains("Charaktername")) {
                        charaktername = keyValue[1].trim().replace("\"", "");
                    } else if (keyValue[0].contains("Stufe")) {
                        stufeString = keyValue[1].trim().replace("\"", "");
                    } else if (keyValue[0].contains("Beschreibung")) {
                        beschreibung = keyValue[1].trim().replace("\"", "");
                    } else if (keyValue[0].contains("Datum")) {
                        datumString = keyValue[1].trim().replace("\"", "");
                    } else if (keyValue[0].contains("Kraftpunkte")) {
                        kraftpunkte =Double.parseDouble(keyValue[1].trim().replace("\"", ""));
                    }

                }

                // Parse the Date
                LocalDate datum = LocalDate.parse(datumString);
                // Convert Stufe to enum
                Stufe stufe = Stufe.fromString(stufeString);

                // Create an Ninja object
                Ninja ninja = new Ninja(id, charaktername, stufe, beschreibung, datum, kraftpunkte);
                ninjas.add(ninja);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ninjas;
    }

    public void aufgabe_a() {
        String filePath = "ninja_events.json"; // Path to your JSON file
        List<Ninja> ninjas = parseJson(filePath);

        for (Ninja ninja : ninjas) {
            System.out.println(ninja.getId() + ".: " + ninja.getCharaktername() + ", " + ninja.getStufe() + ", " + ninja.getDatum() + ", " + ninja.getKraftpunkte());
        }
    }

    public void aufgabe_b() {
        List<Ninja> ninjas = parseJson("ninja_events.json");

        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter a number: ");
        double kraftpunkt = scanner.nextInt();
        scanner.close();

        for (Ninja ninja : ninjas) {
            double craftpunct = ninja.getKraftpunkte();
            if (kraftpunkt < craftpunct) {
                System.out.println(ninja.getCharaktername());
            }
        }
    }



}
