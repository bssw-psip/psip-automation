package io.bssw.psip.backend.data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
public class Item extends AbstractEntity {
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

	@Column(columnDefinition = "TEXT") // Variable length string
	private String basicDescription;

	@Column(columnDefinition = "TEXT") // Variable length string
	private String intermediateDescription;

	@Column(columnDefinition = "TEXT") // Variable length string
	private String advancedDescription;
	
	@Column(columnDefinition = "INTEGER DEFAULT 0")
	@Enumerated(EnumType.ORDINAL)
	private ItemScore score;

	@ManyToOne(fetch = FetchType.LAZY)
	private Category category;

	public String getName() {
		return name;
	}

	public String getIcon() {
		return icon;
	}
	
	public String getPath() {
		return path;
	}
	
	public Category getCategory() {
		return category;
	}

	public String getDescription() {
		return description;
	}

	public String getBasicDescription() {
		return basicDescription;
	}

	public String getIntermediateDescription() {
		return intermediateDescription;
	}

	public String getAdvancedDescription() {
		return advancedDescription;
	}
	
	public ItemScore getScore() {
		return score;
	}
	
	public void setScore(ItemScore score) {
		this.score = score;
	}
}
