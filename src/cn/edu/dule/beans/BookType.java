package cn.edu.dule.beans;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class BookType {
	private int id;
	private String name;
	private String note;
	private Set<BookType> childTypes = new HashSet<BookType>();
	private BookType parentType;
	
	public BookType() {
	}
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	@Column(length=36,nullable=false)
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	@Column(length=256)
	public String getNote() {
		return note;
	}
	
	public void setNote(String note) {
		this.note = note;
	}
	@OneToMany(cascade={CascadeType.REFRESH,CascadeType.REMOVE},mappedBy="parentType",fetch=FetchType.EAGER)
	public Set<BookType> getChildTypes() {
		return childTypes;
	}
	
	public void setChildTypes(Set<BookType> childTypes) {
		this.childTypes = childTypes;
	}
	
	@ManyToOne(cascade=CascadeType.REFRESH,fetch=FetchType.EAGER)
	@JoinColumn(name="parentid")
	public BookType getParentType() {
		return parentType;
	}
	
	public void setParentType(BookType parentType) {
		this.parentType = parentType;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((childTypes == null) ? 0 : childTypes.hashCode());
		result = prime * result + id;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((note == null) ? 0 : note.hashCode());
		result = prime * result
				+ ((parentType == null) ? 0 : parentType.hashCode());
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
		BookType other = (BookType) obj;
		if (childTypes == null) {
			if (other.childTypes != null)
				return false;
		} else if (!childTypes.equals(other.childTypes))
			return false;
		if (id != other.id)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (note == null) {
			if (other.note != null)
				return false;
		} else if (!note.equals(other.note))
			return false;
		if (parentType == null) {
			if (other.parentType != null)
				return false;
		} else if (!parentType.equals(other.parentType))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return String.format("bookType :id=%d,name=%s,note=%s,parentid=%d\n",id,name,note,parentType==null?0:parentType.getId());
	}
	
}	
