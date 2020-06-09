package io.bssw.psip.ui.components;

import com.vaadin.flow.component.AbstractCompositeField;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import io.bssw.psip.backend.data.Item;
import io.bssw.psip.backend.data.ItemScore;
import io.bssw.psip.ui.util.FontSize;
import io.bssw.psip.ui.util.TextColor;
import io.bssw.psip.ui.util.UIUtils;

@Tag("score-group")
public class ScoreItem extends AbstractCompositeField<VerticalLayout, ScoreItem, Item> {
	private String[] buttonText = {"Basic", "Intermediate", "Advanced"};
	private String[] buttonColor = {"#ff9933", "#99cc66", "#66999"};
	private HorizontalLayout[] scores = new HorizontalLayout[3];
	
	private Item item;

	public ScoreItem() {
		super(null);
		
		for (int i = 0; i < 3; i++) {
			final int idx = i;
			Button button = UIUtils.createPrimaryButton(buttonText[idx]);
			button.setWidth("130px");
			button.getElement().getStyle().set("background-color", buttonColor[idx]);
			button.addClickListener(e -> setScore(ItemScore.fromValue(idx + 1)));
			Label label = UIUtils.createLabel(FontSize.M, TextColor.BODY, "text for basic");
			scores[idx] = new HorizontalLayout(button, label);
			scores[idx].setAlignItems(Alignment.CENTER);
			scores[idx].setWidthFull();
			scores[idx].setPadding(true);
			getContent().add(scores[idx]);
		}
		resetScores();
	}
	
	/**
	 * Set the border to 1px transparent. Note this may not work on all browsers
	 */
	private void resetScores() {
		for (HorizontalLayout score : scores) {
			score.getElement().getStyle().set("border", "1px solid transparent");
		}
	}
	
	/**
	 * Set the score to the supplied value
	 * 
	 * @param idx
	 */
	private void setScore(ItemScore score) {
		resetScores();
		if (score != ItemScore.NONE) { 
			scores[score.ordinal() - 1].getElement().getStyle().set("border", "1px solid black");
		}
		item.setScore(score);
	}


	private void setDescription(String desc, int idx) {
		try {
			Component comp = scores[idx].getComponentAt(1);
			if (comp instanceof Label) {
				Label label = (Label)comp;
				label.setText(desc);
			}
		} catch (IllegalArgumentException e) {
			// Invalid component, skip
		}
	}

	@Override
	protected void setPresentationValue(Item item) {
		this.item = item;
		setDescription(item.getBasicDescription(), 0);
		setDescription(item.getIntermediateDescription(), 1);
		setDescription(item.getAdvancedDescription(), 2);
		setScore(item.getScore());
	}
}
