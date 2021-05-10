package domain;

import javax.persistence.Column;
import javax.persistence.Table;
import java.io.Serializable;

@javax.persistence.Entity
@Table(name = "User")
public class User extends Entity<Integer> implements Serializable {
    private String username;
    private String password;
    private String cnp;
    private String name;
    private String address;
    private Double salary;

    private String department;
    private String job;

    private Boolean present;
    private String hour;

    public User() {
    }

    public User( String username, String password, String cnp, String name, String address,
                 Double salary, String department, String job, Boolean present, String hour ) {
        this.username = username;
        this.password = password;
        this.cnp = cnp;
        this.name = name;
        this.address = address;
        this.salary = salary;
        this.department = department;
        this.job = job;
        this.present = present;
        this.hour = hour;
    }

    @Column(name = "username")
    public String getUsername() {
        return username;
    }

    public void setUsername( String username ) {
        this.username = username;
    }

    @Column(name = "password")
    public String getPassword() {
        return password;
    }

    public void setPassword( String password ) {
        this.password = password;
    }

    @Column(name = "cnp")
    public String getCnp() {
        return cnp;
    }

    public void setCnp( String cnp ) {
        this.cnp = cnp;
    }

    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName( String name ) {
        this.name = name;
    }

    @Column(name = "address")
    public String getAddress() {
        return address;
    }

    public void setAddress( String address ) {
        this.address = address;
    }

    @Column(name = "salary")
    public Double getSalary() {
        return salary;
    }

    public void setSalary( Double salary ) {
        this.salary = salary;
    }

    @Column(name = "department")
    public String getDepartment() {
        return department;
    }

    public void setDepartment( String department ) {
        this.department = department;
    }

    @Column(name = "job")
    public String getJob() {
        return job;
    }

    public void setJob( String job ) {
        this.job = job;
    }

    @Column(name = "present")
    public Boolean getPresent() {
        return present;
    }

    public void setPresent( Boolean present ) {
        this.present = present;
    }

    @Column(name = "hour")
    public String getHour() {
        return hour;
    }

    public void setHour( String hour ) {
        this.hour = hour;
    }
}
