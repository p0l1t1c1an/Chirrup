package coms309.people;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.springframework.core.style.ToStringCreator;

/**
 * Provides the Definition/Structure for the people table
 *
 * @author Vivek Bengre, edited further by Tyler Green
 */

@Entity
@Table(name = "people")
public class Person {

    // GenerationType.IDENTITY automatically generates ids i.e. you do not have to set this field
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @NotFound(action = NotFoundAction.IGNORE)
    private Integer id;

    @Column(name = "firstName")
    @NotFound(action = NotFoundAction.IGNORE)
    private String firstName;

    @Column(name = "lastName")
    @NotFound(action = NotFoundAction.IGNORE)
    private String lastName;

    @Column(name = "address")
    @NotFound(action = NotFoundAction.IGNORE)
    private String address;

    @Column(name = "telephone")
    @NotFound(action = NotFoundAction.IGNORE)
    private String telephone;

    public Person(){
        
    }

    public Person(int id, String firstName, String lastName, String address, String telephone){
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.telephone = telephone;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public boolean isNew() {
        return this.id == null;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTelephone() {
        return this.telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    @Override
    public String toString() {
        return new ToStringCreator(this)

                .append("id", this.getId())
                .append("new", this.isNew())
                .append("lastName", this.getLastName())
                .append("firstName", this.getFirstName())
                .append("address", this.address)
                .append("telephone", this.telephone).toString();
    }
}
