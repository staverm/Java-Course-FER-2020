package hr.fer.zemris.java.tecaj_13.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * Class that models a blog user.
 * 
 * @author staver
 *
 */
@NamedQueries({
	@NamedQuery(name="BlogUser.find",query="select b from BlogUser as b where b.nick=:nick")
})
@Entity
@Table(name="blog_users", uniqueConstraints={
	@UniqueConstraint(columnNames={"nick"})
})
public class BlogUser {
	
	private Long id; //user's id
	private String firstName; //user's first name
	private String lastName; //user's last name
	private String nick; //user's nickname
	private String email; //user's email
	private String passwordHash; //user's hashed password
	
	/**
	 * Id getter.
	 * @return id
	 */
	@Id @GeneratedValue
	public Long getId() {
		return id;
	}
	
	/**
	 * First name getter.
	 * @return first name
	 */
	@Column(length=20,nullable=false)
	public String getFirstName() {
		return firstName;
	}
	
	/**
	 * Last name getter.
	 * @return last name
	 */
	@Column(length=20,nullable=false)
	public String getLastName() {
		return lastName;
	}
	
	/**
	 * Nickname getter.
	 * @return nickname
	 */
	@Column(length=20,nullable=false)
	public String getNick() {
		return nick;
	}
	
	/**
	 * Email getter.
	 * @return email
	 */
	@Column(length=50,nullable=false)
	public String getEmail() {
		return email;
	}
	
	/**
	 * Password hash getter.
	 * @return password hash
	 */
	@Column(length=64,nullable=false)
	public String getPasswordHash() {
		return passwordHash;
	}
	
	/**
	 * Id setter.
	 * @param id id to be set
	 */
	public void setId(Long id) {
		this.id = id;
	}
	
	/**
	 * First name setter.
	 * @param firstName first name to be set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	/**
	 * Last name setter.
	 * @param lastName last name to be set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	/**
	 * Nickname setter.
	 * @param nick nickname to be set
	 */
	public void setNick(String nick) {
		this.nick = nick;
	}
	
	/**
	 * Email setter.
	 * @param email email to be set
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	
	/**
	 * Password hash setter.
	 * 
	 * @param passwordHash string to be set
	 */
	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BlogEntry other = (BlogEntry) obj;
		if (id == null) {
			if (other.getId() != null)
				return false;
		} else if (!id.equals(other.getId()))
			return false;
		return true;
	}
	
}
