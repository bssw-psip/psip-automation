package io.bssw.psip.ui.components;

import java.util.List;
import java.util.Optional;

import org.vaadin.pekkam.Canvas;
import org.vaadin.pekkam.CanvasRenderingContext2D;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasComponents;
import com.vaadin.flow.component.HasSize;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

import io.bssw.psip.backend.data.Item;
import io.bssw.psip.backend.data.Score;

@SuppressWarnings("serial")
@Tag("score-group")
public class ScoreSlider extends Component implements HasComponents, HasSize {
	private static final int BUTTON_RADIUS = 10;
	private static final int BUTTON_TRACK_WIDTH = 10;
	private static final int CANVAS_WIDTH = 320;
	private static final int CANVAS_HEIGHT = BUTTON_RADIUS * 2;
	private static final String BACKGROUND_COLOR = "#E3E3E3";

	private final Item item;
	private final List<Score> scores;
	private final CanvasRenderingContext2D ctx;
	private final Label label;

	public ScoreSlider(Item item) {
		this.item = item;
		this.scores = item.getCategory().getActivity().getScores();
		this.label = new Label();
		this.label.getElement().getStyle().set("font-style", "italic");
		
		HorizontalLayout layout = new HorizontalLayout();

		Canvas canvas = new Canvas(CANVAS_WIDTH, CANVAS_HEIGHT);
		canvas.getElement().addEventListener("click", e->
		{
			double x = e.getEventData().getNumber("event.offsetX");
			int val = (int)Math.round(x) / (CANVAS_WIDTH/scores.size());
			setScore(Optional.of(scores.get(val).getValue()));
		}
		).addEventData("event.offsetX").addEventData("event.offsetY");
		ctx = canvas.getContext();
		ctx.setFillStyle(BACKGROUND_COLOR);
		ctx.fillRect(BUTTON_RADIUS, BUTTON_RADIUS - BUTTON_TRACK_WIDTH / 2, CANVAS_WIDTH - BUTTON_RADIUS * 2, BUTTON_TRACK_WIDTH);

		canvas.getElement().getStyle().set("align-self", "center"); // Avoid canvas scaling horizontally only
		
		layout.add(canvas, label);
		add(layout);
		setScore(item.getScore());
		setWidth("100%");
	}
	
	
	/**
	 * Set the score to the supplied value
	 * 
	 * @param idx
	 */
	private void setScore(Optional<Integer> score) {
		Integer value = scores.get(0).getValue(); // Default
		if (score.isPresent()) { 
			value = score.get();
		}
		item.setScore(value);
		drawButtons(ctx, item);
	}
	
	private int buttonPosX(int idx) {
		int span = (CANVAS_WIDTH - (BUTTON_RADIUS * 2)) / (scores.size() - 1);
		return BUTTON_RADIUS + span * idx;
	}
	
	private void drawButton(CanvasRenderingContext2D ctx, int idx, String color) {
		ctx.beginPath();
		ctx.setFillStyle(color);
		ctx.arc(buttonPosX(idx), BUTTON_RADIUS, BUTTON_RADIUS, 0, 2 * Math.PI, false);
		ctx.fill();
	}
	
	/**
	 * Draw the buttons with the correct button selected.
	 * 
	 * @param ctx 2d drawing context
	 * @param item item used to select button. Must have a value.
	 */
	private void drawButtons(CanvasRenderingContext2D ctx, Item item) {
		int idx = 0;
		for (Score score : scores) {
			if (score.getValue() == item.getScore().get()) {
				drawButton(ctx, idx, score.getColor());
				label.setText(score.getBoost());
				label.getElement().getStyle().set("color", score.getColor());
			} else {
				drawButton(ctx, idx, BACKGROUND_COLOR);
			}
			idx++;
		}
	}
}
