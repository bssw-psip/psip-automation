package io.bssw.psip.backend.data;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
public class Score extends AbstractEntity {
	@NotBlank
	@Size(max = 255)
	private String name;
	
	@NotBlank
	@Size(max = 255)
	private String color;
	
	@ManyToOne(fetch = FetchType.LAZY)
	private Activity activity;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public Activity getActivity() {
		return activity;
	}

	public void setActivity(Activity activity) {
		this.activity = activity;
	}
}
