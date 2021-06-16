/******************************************************************************
*
* Copyright 2020-, UT-Battelle, LLC. All rights reserved.
* 
* Redistribution and use in source and binary forms, with or without
* modification, are permitted provided that the following conditions are met:
*
*  o Redistributions of source code must retain the above copyright notice, this
*    list of conditions and the following disclaimer.
*    
*  o Redistributions in binary form must reproduce the above copyright notice,
*    this list of conditions and the following disclaimer in the documentation
*    and/or other materials provided with the distribution.
*    
*  o Neither the name of the copyright holder nor the names of its
*    contributors may be used to endorse or promote products derived from
*    this software without specific prior written permission.
*
* THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
* AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
* IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
* DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE
* FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
* DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
* SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
* CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
* OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
* OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*
*******************************************************************************/
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
