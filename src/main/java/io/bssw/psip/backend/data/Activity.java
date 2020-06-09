package io.bssw.psip.backend.data;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
public class Activity extends AbstractEntity {
	@NotBlank
	@Size(max = 255)
	private String name;
	
	@NotBlank
	@Size(max = 255)
	private String icon;
	
	@NotBlank
	@Size(max = 255)
	private String path;
	
	@Column(columnDefinition = "TEXT") // Variable length string
	private String description;
	
	@OneToMany(
			mappedBy = "activity",
			cascade = CascadeType.ALL,
			orphanRemoval = true,
			fetch = FetchType.EAGER) 
	// FIXME: EAGER only to avoid LazyInitializationException
	private List<Category> categories;
	
	public String getName() {
		return name;
	}

	public String getIcon() {
		return icon;
	}
	
	public String getPath() {
		return path;
	}
	
	public String getDescription() {
		return description;
	}

	public List<Category> getCategories() {
		return categories;
	}
}
