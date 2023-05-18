package programs;

import models.Staff;
import utils.FileUtils;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.util.*;

public class StaffScheduler {
    private static File staffAvailabilityFile = new File("Hardware Store/src/resources/staff_availability_IN.txt");
    private static File shiftSchedulesFile = new File("Hardware Store/src/resources/shift_schedules_IN.txt");
    private static File storeScheduleFile = new File("Hardware Store/src/resources/store_schedule_OUT.txt");

    public static void scheduleStaff() throws IOException {
        List<String> strs= new ArrayList<>();
        Scanner l = new Scanner(Path.of(shiftSchedulesFile.toURI()));


        //Local class of staff that will hold proper strings of availability, hours worked, and name
        class staff {
            float hoursWorked;
            final List<String> availability;
            final String name;

            public staff(float hoursWorked, List<String> availability, String name) {
                this.hoursWorked = hoursWorked;
                this.availability = availability;
                this.name = name;
            }

            public float getHoursWorked() {
                return hoursWorked;
            }

            public void incHoursworked(float hoursW){
                hoursWorked = hoursWorked + hoursW;
            }

            public List<String> getAvailability() {
                return availability;
            }

            public String getName() {
                return name;
            }
        }
        //Local class of weeks that will hold the day and the shifts available that day
        class week{
            final String weekday;
            final float shifthours;


            public week(String weekday, float shifthours) {
                this.weekday = weekday;
                this.shifthours = shifthours;
            }

            public String getWeekday() {
                return weekday;
            }

            public float getShifthours() {
                return shifthours;
            }
        }

        List<staff> rec = new ArrayList<>();
        List<week> week = new ArrayList<>();
        List<Staff> st = FileUtils.readStaffFromFile();

        for(Staff element : st){
            String name = element.getFullName();
            List<String> av = AvailbToString(name);
            rec.add(new staff(0, av, name));
        }

        //Making array list of element
        while(l.hasNext()) {
            String input = l.next();
            strs.add(input);
        }


        float count;
        //Making array of week day instances
        for(int i = 0 ; i < strs.size() ; i = i + 3){
            count = (Float.parseFloat(strs.get(i + 2)) - Float.parseFloat(strs.get(i + 1))) / 100;
            String day = strs.get(i);
            week.add(new week(weekdayHelper(day, "full"), count));
        }


        strs.clear();

        //Two loops where a list of staff members will be looped through,
        //after each staff is looked through the weekday will change
        //from the beginning loop. Staff will be added if they are
        //available that day.
        for(week ele : week){
            String y = weekdayHelper(ele.getWeekday(), "single");
            rec.sort(Comparator.comparing(staff :: getHoursWorked));
            int counter = 0;
            for (staff person : rec) {
                if (person.getAvailability().contains(ele.getWeekday())) {
                    person.incHoursworked(ele.getShifthours());
                    y = y + " (" + person.getName() + ") ";
                    counter++;
                }
                if(counter == 3){
                    break;
                }
            }
            strs.add(y);
        }
        FileUtils.writeStoreScheduleToFile(strs);
    }

    //Returning String of staffs availability
    public static List<String> AvailbToString(String name) throws IOException {
        List<Staff> stff = FileUtils.readStaffFromFile();
        String[]  Availablity = new String[7];
        for(Staff element : stff){
            if(element.getFullName().equals(name)){
                Availablity = element.getAvailability().split("\\.");
            }
        }

        for (int i = 0 ; i < Availablity.length ; i++){
            String d = weekdayHelper(Availablity[i], "full");
            Availablity[i] = d;
        }
        return new ArrayList<>(Arrays.asList(Availablity));
    }

    //Helper function for processing weekdays
    public static String weekdayHelper(String day, String layout){
        if(layout.equals("single"))
            switch (day){
                case "monday" -> day = "M";
                case "tuesday" -> day = "T";
                case "wednesday" -> day = "W";
                case "thursday" -> day = "TR";
                case "friday" -> day = "F";
                case "saturday" -> day = "SAT";
                case "sunday" -> day = "SUN";
            }

        else {
            switch (day){
                case "M" -> day = "monday";
                case "T" -> day = "tuesday";
                case "W" -> day = "wednesday";
                case "TR" -> day = "thursday";
                case "F" -> day = "friday";
                case "SAT" -> day = "saturday";
                case "SUN" -> day = "sunday";
            }
        }
        return day;
    }



        public static void main(String[] args) throws IOException {
        StaffScheduler.scheduleStaff();

    }


}
