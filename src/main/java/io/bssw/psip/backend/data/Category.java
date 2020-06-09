package io.bssw.psip.backend.data;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
public class Category extends AbstractEntity {
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

	@ManyToOne(fetch = FetchType.LAZY)
	private Activity activity;
	
	@OneToMany(
			mappedBy = "category",
			cascade = CascadeType.ALL,
			orphanRemoval = true,
			fetch = FetchType.EAGER) 
	private List<Item> items;

	public String getName() {
		return name;
	}
	
	public String getIcon() {
		return icon;
	}
	
	public String getPath() {
		return path;
	}
	
	public Activity getActivity() {
		return activity;
	}
	
	public String getDescription() {
		return description;
	}

	public List<Item> getItems() {
		return items;
	}

}
