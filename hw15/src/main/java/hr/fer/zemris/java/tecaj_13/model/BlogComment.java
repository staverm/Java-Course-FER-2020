package hr.fer.zemris.java.tecaj_13.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Class that models a blog comment.
 * 
 * @author staver
 *
 */
@Entity
@Table(name="blog_comments")
public class BlogComment {

	private Long id; //comment id
	private BlogEntry blogEntry; //entry that this comment belongs to
	private String usersEMail; //email of the user who posted this comment
	private String message; //comment message
	private Date postedOn; //date when this comment was posted
	
	/**
	 * Comment id getter.
	 * @return comment id
	 */
	@Id @GeneratedValue
	public Long getId() {
		return id;
	}
	
	/**
	 * Comment id setter.
	 * @param id comment id to be set.
	 */
	public void setId(Long id) {
		this.id = id;
	}
	
	/**
	 * Blog entry that this comment belongs to getter.
	 * @return blog entry
	 */
	@ManyToOne
	@JoinColumn(nullable=false)
	public BlogEntry getBlogEntry() {
		return blogEntry;
	}
	
	/**
	 * Blog entry that this comment belongs to setter.
	 * @param blogEntry blog entry to be set
	 */
	public void setBlogEntry(BlogEntry blogEntry) {
		this.blogEntry = blogEntry;
	}
	
	/**
	 * User's email  getter.
	 * @return user's email
	 */
	@Column(length=100,nullable=false)
	public String getUsersEMail() {
		return usersEMail;
	}
	
	/**
	 * User's Email setter.
	 * @param usersEMail email to be set
	 */
	public void setUsersEMail(String usersEMail) {
		this.usersEMail = usersEMail;
	}
	
	/**
	 * Comment message getter.
	 * @return comment message
	 */
	@Column(length=4096,nullable=false)
	public String getMessage() {
		return message;
	}
	
	/**
	 * Comment message setter.
	 * @param message message to be set
	 */
	public void setMessage(String message) {
		this.message = message;
	}
	
	/**
	 * Posted on field getter.
	 * @return date when this comment was posted
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable=false)
	public Date getPostedOn() {
		return postedOn;
	}
	
	/**
	 * Posted on field setter.
	 * @param postedOn date this comment has been posted to be set
	 */
	public void setPostedOn(Date postedOn) {
		this.postedOn = postedOn;
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
		BlogComment other = (BlogComment) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}