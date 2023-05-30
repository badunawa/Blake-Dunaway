package utils;


import models.Item;
import models.Staff;

import java.io.*;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class FileUtils {
    String userHome = System.getProperty("user.home");

    private static File inputFile = new File("Hardware Store/src/resources/input.txt");
    private static File outputFile = new File("Hardware Store/src/resources/output.txt");
    private static File inventoryFile = new File("Hardware Store/src/resources/inventory.txt");
    private static File staffAvailabilityFile = new File("Hardware Store/src/resources/staff_availability_IN.txt");
    private static File shiftSchedulesFile = new File("Hardware Store/src/resources/shift_schedules_IN.txt");
    private static File storeScheduleFile = new File("Hardware Store/src/resources/store_schedule_OUT.txt");

    public static List<Item> readInventoryFromFile() throws IOException {
        List<Item> items = new ArrayList<>();
        List<String> strings = new ArrayList<>();
        Scanner in = new Scanner(Path.of(inventoryFile.toURI()));
        while(in.hasNext()){
            String input = in.nextLine();
            strings.add(input);
        }
        for(int i = 0; i < strings.size();i++){
            String[] splitted = strings.get(i).split(",");
            String itemName = splitted[0].substring(1, splitted[0].length()-1);
            double itemCost = Double.parseDouble(splitted[1]);
            int itemQuantity = Integer.parseInt(splitted[2]);
            int itemAisle = Integer.parseInt(splitted[3]);
            Item x = new Item(itemName,itemCost,itemQuantity,itemAisle);
            items.add(x);
        }
        in.close();
        return items;

    }

    public static List<Staff> readStaffFromFile() throws IOException {

        List<Staff> staff= new ArrayList<>();
        ArrayList<String> strs = new ArrayList<>();

        Scanner l = new Scanner(Path.of(staffAvailabilityFile.toURI()));

        //Making array list of element
        while(l.hasNext()) {
            String input = l.next();
            strs.add(input);
        }
        //Add

        for(int i = 0 ; i < strs.size() - 1; i = i + 5){
            String fullname = strs.get(i) + " " + strs.get(i + 1);
            int age = Integer.parseInt(strs.get(i + 2));
            String role = strs.get(i + 3);
            String availability = strs.get(i + 4);
            Staff s = new Staff(fullname, age, roleHelper(role, "Full"), availability);
            staff.add(s);

        }
        return staff; //Does not need to return anything
    }

    public static String roleHelper(String role, String format){
        if(format.equals("single")) {
            return switch (role) {
                case "Gardening Expert" -> "G";
                case "Cashier" -> "C";
                case "Manager" -> "M";
                default -> null;
            };
        }
        else {
            return switch (role) {
                case "G" -> "Gardening Expert";
                case "C" -> "Cashier";
                case "M" -> "Manager";
                default -> null;
            };
        }
    }

    public static void writeInventoryToFile(List<Item> items) throws IOException {
        PrintWriter out = new PrintWriter(inventoryFile);
        for(Item x : items){
            out.println("'" + x.getName() + "'," + x.getPrice() + "," + x.getQuantity() + ","+ x.getAsileNum());
        }

        out.close();
    }

    public static void writeStaffToFile(List<Staff> employees) throws FileNotFoundException { //Static?
        PrintWriter out = new PrintWriter(staffAvailabilityFile);
        for (Staff element : employees) {
            out.println(element.getFullName() + " " + element.getAge() + " " + roleHelper(element.getRole(), "single") + " " + element.getAvailability());
        }
        out.close();
    }


    public static List<String> readCommandsFromFile() throws IOException {
        List<String> strs = new ArrayList<>();
        Scanner l = new Scanner(FileUtils.inputFile);

        //Making array list of element
        while(l.hasNext()) {
            String input = l.nextLine();
            strs.add(input);
        }
        return strs;
    }

    public static void writeStoreScheduleToFile(List<String> lines) throws FileNotFoundException {
        PrintWriter out = new PrintWriter(FileUtils.storeScheduleFile);
        for(String element : lines){
            out.println(element);
        }
        out.close();
    }

    public static void writeLineToOutputFile(String line) throws IOException {
        PrintWriter tes = new PrintWriter(new FileWriter(FileUtils.outputFile,true));
        tes.println(line);
        tes.close();
    }
}
