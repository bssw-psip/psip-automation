package io.bssw.psip.ui.components;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.vaadin.flow.component.AbstractCompositeField;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import io.bssw.psip.backend.data.Item;
import io.bssw.psip.backend.data.Score;
import io.bssw.psip.ui.util.FontSize;
import io.bssw.psip.ui.util.TextColor;
import io.bssw.psip.ui.util.UIUtils;

@Tag("score-group")
public class ScoreItem extends AbstractCompositeField<VerticalLayout, ScoreItem, Item> {
	private Map<Integer, HorizontalLayout> scoreLayouts = new LinkedHashMap<>();	// Keep ordered by keys
	private Item item;

	public ScoreItem(Item item, List<Score> scores) {
		super(null);
		this.item = item;

		for (int i = 0; i < scores.size(); i++) {
			Score score = scores.get(i);
			Button button = UIUtils.createPrimaryButton(score.getName());
			button.setWidth("150px");
			button.getElement().getStyle().set("background-color", score.getColor());
			button.addClickListener(e -> setScore(Optional.of(score.getValue())));
			Label label = UIUtils.createLabel(FontSize.M, TextColor.BODY, item.getQuestions().get(i));
			label.setWidth("100%");
			HorizontalLayout scoreLayout = new HorizontalLayout(button, label);
			scoreLayout.setAlignItems(Alignment.CENTER);
			scoreLayout.setWidthFull();
			scoreLayout.setPadding(true);
			getContent().add(scoreLayout);
			scoreLayouts.put(score.getValue(), scoreLayout);
		}
		
		resetScores();
		setScore(item.getScore());
	}
	
	/**
	 * Set the border to 1px transparent. Note this may not work on all browsers
	 */
	private void resetScores() {
		for (HorizontalLayout score : scoreLayouts.values()) {
			score.getElement().getStyle().set("border", "1px solid transparent");
		}
	}
	
	/**
	 * Set the score to the supplied value
	 * 
	 * @param idx
	 */
	private void setScore(Optional<Integer> score) {
		resetScores();
		Integer value = scoreLayouts.keySet().iterator().next(); // First item
		if (score.isPresent()) { 
			value = score.get();
		}
		scoreLayouts.get(value).getElement().getStyle().set("border", "1px solid black");
		item.setScore(value);
	}


	@Override
	protected void setPresentationValue(Item item) {
		setScore(item.getScore());
	}
}
