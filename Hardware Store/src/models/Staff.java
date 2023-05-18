package models;

public class Staff {
    private String fullName;
    private int age;
    private String role;
    private String availability;

    public Staff(String fullName, int age, String role, String availability){
        this.fullName = fullName;
        this.age = age;
        this.role = role;
        this.availability = availability;
    }


    public String getFullName() {
        return fullName;
    }

    public String getAvailability() {
        return availability;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public int getAge() {
        return age;
    }
}
