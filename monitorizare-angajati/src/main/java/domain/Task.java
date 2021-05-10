package domain;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

@javax.persistence.Entity
@Table(name = "Task")
public class Task extends Entity<Integer> {
    private String description;
    private String date;
    private Status status;
    private Integer idEmployee;

    public Task( String description, String date, Status status,Integer idEmployee ) {
        this.description = description;
        this.date = date;
        this.status = status;
        this.idEmployee=idEmployee;
    }

    public Task() {
    }

    @Column(name = "description")
    public String getDescription() {
        return description;
    }

    public void setDescription( String description ) {
        this.description = description;
    }

    @Column(name = "date")
    public String getDate() {
        return date;
    }

    public void setDate( String date ) {
        this.date = date;
    }

    @Column(name="status")
    @Enumerated(EnumType.STRING)
    public Status getStatus() {
        return status;
    }

    public void setStatus( Status status ) {
        this.status = status;
    }

    @Column(name = "idEmployee")
    public Integer getIdEmployee() {
        return idEmployee;
    }

    public void setIdEmployee( Integer idEmployee ) {
        this.idEmployee = idEmployee;
    }

    @Override
    public String toString() {
        return "Task{" +
                "description='" + description + '\'' +
                ", date='" + date + '\'' +
                ", status=" + status +
                '}';
    }
}
