package passwordtest.users;

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
@Table(name = "users")
public class User {

    // GenerationType.IDENTITY automatically generates ids i.e. you do not have to set this field
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @NotFound(action = NotFoundAction.IGNORE)
    private Integer id;

	@Column(name = "accName")
    @NotFound(action = NotFoundAction.IGNORE)
    private String accName;

    @Column(name = "hash")
    @NotFound(action = NotFoundAction.IGNORE)
    private byte[] hash;

    @Column(name = "salt")
    @NotFound(action = NotFoundAction.IGNORE)
    private byte[] salt;

    public Person(){
        
    }

    public Person(int id, String accName, byte[] hash, byte[] salt){
        this.id = id;
        this.accName = accName;
        this.hash = hash.clone();
        this.salt = salt.clone();
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

    public String getAccName() {
        return this.accName;
    }

    public void setAccName(String accName) {
        this.accName = accName;
    }

    public byte[] getHash() {
        return this.hash.clone();
    }

    public byte[] getSalt() {
        return this.salt.clone();
    }

	public void changePasswd(String passwd){

	}

    @Override
    public String toString() {
        return new ToStringCreator(this)

                .append("id", this.getId())
                .append("new", this.isNew())
                .append("lastName", this.getAccName())
                .append("firstName", this.getHash())
                .append("address", this.getSalt())
                .toString();
    }
}
