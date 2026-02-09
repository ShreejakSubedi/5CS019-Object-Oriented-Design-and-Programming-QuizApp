package quizapp.model;

public class Name {
    private String firstName;
    private String middleName; // can be null/empty
    private String lastName;

    public Name(String firstName, String middleName, String lastName) {
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
    }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getMiddleName() { return middleName; }
    public void setMiddleName(String middleName) { this.middleName = middleName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getFullName() {
        String mid = (middleName == null || middleName.isBlank()) ? "" : (" " + middleName.trim());
        return firstName.trim() + mid + " " + lastName.trim();
    }

    public String getInitials() {
        StringBuilder sb = new StringBuilder();
        if (firstName != null && !firstName.isBlank()) sb.append(Character.toUpperCase(firstName.trim().charAt(0)));
        if (middleName != null && !middleName.isBlank()) sb.append(Character.toUpperCase(middleName.trim().charAt(0)));
        if (lastName != null && !lastName.isBlank()) sb.append(Character.toUpperCase(lastName.trim().charAt(0)));
        return sb.toString();
    }
}