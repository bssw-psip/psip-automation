package io.bssw.psip.backend.data;

import java.util.List;
import java.util.Optional;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
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
	
	@ElementCollection
	private List<String> questions;

	@Column(columnDefinition = "TEXT") // Variable length string
	private String basicDescription;

	@Column(columnDefinition = "TEXT") // Variable length string
	private String intermediateDescription;

	@Column(columnDefinition = "TEXT") // Variable length string
	private String advancedDescription;
	
//	@Column(columnDefinition = "INTEGER DEFAULT 0")
//	@Enumerated(EnumType.ORDINAL)
//	private ItemScore score;
	private Integer score; // index into scores/questions

	@ManyToOne(fetch = FetchType.LAZY)
	private Category category;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<String> getQuestions() {
		return questions;
	}

	public void setQuestions(List<String> questions) {
		this.questions = questions;
	}

	public String getBasicDescription() {
		return basicDescription;
	}

	public void setBasicDescription(String basicDescription) {
		this.basicDescription = basicDescription;
	}

	public String getIntermediateDescription() {
		return intermediateDescription;
	}

	public void setIntermediateDescription(String intermediateDescription) {
		this.intermediateDescription = intermediateDescription;
	}

	public String getAdvancedDescription() {
		return advancedDescription;
	}

	public void setAdvancedDescription(String advancedDescription) {
		this.advancedDescription = advancedDescription;
	}

	public Optional<Integer> getScore() {
		return Optional.ofNullable(score);
	}

	public void setScore(Integer score) {
		this.score = score;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	@Override
	public String toString() {
		return "Item [name=" + name + ", icon=" + icon + ", path=" + path + ", description=" + description
				+ ", questions=" + questions + "]";
	}
}
