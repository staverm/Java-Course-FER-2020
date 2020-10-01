package hr.fer.zemris.java.tecaj_13.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Class that models a blog entry.
 * 
 * @author staver
 *
 */
@NamedQueries({
	@NamedQuery(name="BlogEntry.upit1",query="select b from BlogComment as b where b.blogEntry=:be and b.postedOn>:when")
})
@Entity
@Table(name="blog_entries")
@Cacheable(true)
public class BlogEntry {

	private Long id; //entry id
	private List<BlogComment> comments = new ArrayList<>(); //list of entry comments
	private Date createdAt; //date of creation
	private Date lastModifiedAt; //date of last modification
	private String title; //entry title
	private String text; //entry text
	private BlogUser creator; //entry creator
	
	/**
	 * Entry creator getter.
	 * @return user that created this entry
	 */
	@ManyToOne
	public BlogUser getCreator() {
		return creator;
	}
	
	/**
	 * Entry creator setter.
	 * @param creator user that created this entry to be set
	 */
	public void setCreator(BlogUser creator) {
		this.creator = creator;
	}
	
	/**
	 * Entry Id getter.
	 * @return entry id
	 */
	@Id @GeneratedValue
	public Long getId() {
		return id;
	}
	
	/**
	 * Entry Id setter.
	 * @param id id to be set
	 */
	public void setId(Long id) {
		this.id = id;
	}
	
	/**
	 * Comments list getter.
	 * @return list of comments
	 */
	@OneToMany(mappedBy="blogEntry",fetch=FetchType.LAZY, cascade=CascadeType.PERSIST, orphanRemoval=true)
	@OrderBy("postedOn")
	public List<BlogComment> getComments() {
		return comments;
	}
	
	/**
	 * Comments list setter.
	 * @param comments list of comments to be set
	 */
	public void setComments(List<BlogComment> comments) {
		this.comments = comments;
	}
	
	/**
	 * Created at field getter.
	 * @return date of creation to be set
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable=false)
	public Date getCreatedAt() {
		return createdAt;
	}
	
	/**
	 * Created at field setter.
	 * @param createdAt date to be set
	 */
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
	
	/**
	 * Last modified at field getter.
	 * @return date of last modification
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable=true)
	public Date getLastModifiedAt() {
		return lastModifiedAt;
	}
	
	/**
	 * Last modified at field setter.
	 * @param lastModifiedAt date to be set
	 */
	public void setLastModifiedAt(Date lastModifiedAt) {
		this.lastModifiedAt = lastModifiedAt;
	}
	
	/**
	 * Entry title getter.
	 * @return entrie's title
	 */
	@Column(length=200,nullable=false)
	public String getTitle() {
		return title;
	}
	
	/**
	 * Entry title setter.
	 * @param title title to be set
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	
	/**
	 * Entry text getter.
	 * @return entry text
	 */
	@Column(length=4096,nullable=false)
	public String getText() {
		return text;
	}
	
	/**
	 * Entry text setter.
	 * @param text text to be set
	 */
	public void setText(String text) {
		this.text = text;
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
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}