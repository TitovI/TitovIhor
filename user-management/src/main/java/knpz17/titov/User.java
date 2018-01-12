package knpz17.titov;

import java.util.Calendar;
import java.util.Date;

public class User {
    private Long id;
    private String firstName;
    private String lastName;
    private Date dateOfBirth;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getFullName() {
        return getLastName().concat(", ").concat(getFirstName());
    }

    public int getAge() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        int currYear = cal.get(Calendar.YEAR);
        cal.setTime(getDateOfBirth());
        int yearOfBirth = cal.get(Calendar.YEAR);
        return currYear - yearOfBirth;
    }
}
