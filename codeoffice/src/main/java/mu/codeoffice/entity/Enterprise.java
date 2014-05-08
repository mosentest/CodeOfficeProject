package mu.codeoffice.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "enterprise")
public class Enterprise implements Serializable {

	private static final long serialVersionUID = 782680675742454535L;

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

    @Column(name = "code", nullable = false, unique = true, length = 3)
	private String code;

    @Column(name = "name", nullable = false, unique = true)
	private String name;

    @Column(name = "description")
	private String description;	

    public Enterprise() {}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}    
    
}
