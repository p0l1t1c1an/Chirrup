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

import passwordtest.password.PasswordUtil;

/**
 * Provides the Definition/Structure for the users table
 *
 * @author Jacob Boicken, modified version Vivek Bengre's person class
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
    

	public User(){
        
    }

	public User(int id, String accName){
		this.id = id;
		this.accName = accName;
	}
	
	public User(int id, String accName, String passwd) throws Exception{
        this.id = id;
        this.accName = accName;
        this.hash = PasswordUtil.hash(passwd);
    }

    public User(int id, String accName, byte[] hash){
        this.id = id;
        this.accName = accName;
        this.hash = hash.clone();
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

	public void changePasswd(String passwd) throws Exception{
		this.hash = PasswordUtil.hash(passwd);
	}

    @Override
    public String toString() {
        return new ToStringCreator(this)

                .append("id", this.getId())
                .append("new", this.isNew())
                .append("accName", this.getAccName())
                .append("hash", this.getHash())
                .toString();
    }
}
