package accessor.Document;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import accessor.supportClasses.rights.Rights;

@Entity(name="Document")
@Table(name="documents")
public class Document {
	//---------------------------------------------------------------------------------------------------------------------
	//											FIELDS DECLARATION
	//---------------------------------------------------------------------------------------------------------------------
	@Id
	@Column(name="document_id")
	private int id;
	
	@Column(name="name")
	private String name;
	
	@Transient
	private Rights accessRight;

	@Column(name="access_right")
	private String accessRightCoded;
	
	//---------------------------------------------------------------------------------------------------------------------
	//										#END OF FIELDS DECLARATION
	//---------------------------------------------------------------------------------------------------------------------
	
	//---------------------------------------------------------------------------------------------------------------------
	//											CONSTRUCTOR
	//---------------------------------------------------------------------------------------------------------------------	
	
	public Document(int id, String name, Rights right) {
		this.id = id;
		this.name = name;
		this.accessRight = right;
	}

	
	
	public Document(int id, String name, Rights accessRight, String accessRightCoded) {
		super();
		this.id = id;
		this.name = name;
		this.accessRight = accessRight;
		this.accessRightCoded = accessRightCoded;
	}



	public Document (String name, Rights right) {
		this.id=0;
		this.name = name;
		this.accessRight = right;
	}
	
	public Document () {
	}

		
	//---------------------------------------------------------------------------------------------------------------------
	//										#END OF CONSTRUCTOR
	//---------------------------------------------------------------------------------------------------------------------	
	
	//---------------------------------------------------------------------------------------------------------------------
	//										GETTERS AND SETTERS
	//---------------------------------------------------------------------------------------------------------------------

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	
	public Rights getAccessRight() {
		return accessRight;
	}

	public void setAccessRight(Rights accessRight) {
		this.accessRight = accessRight;
	}

	public String getAccessRightCoded() {
		return accessRightCoded;
	}



	public void setAccessRightCoded(String accessRightCoded) {
		this.accessRightCoded = accessRightCoded;
	}



	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
	//---------------------------------------------------------------------------------------------------------------------
	//									#END OF	GETTERS AND SETTERS
	//---------------------------------------------------------------------------------------------------------------------

	@Override
	public String toString() {
		return "Document [id=" + id + ", name=" + name + ", accessRight=" + accessRight + "]";
	}
}
