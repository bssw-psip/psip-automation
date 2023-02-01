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

import io.bssw.psip.backend.model.Item;
import io.bssw.psip.backend.model.Score;
import io.bssw.psip.ui.util.FontSize;
import io.bssw.psip.ui.util.TextColor;
import io.bssw.psip.ui.util.UIUtils;

@Tag("score-group")
public class ScoreItem extends AbstractCompositeField<VerticalLayout, ScoreItem, Item> {
	private Map<Integer, HorizontalLayout> scoreLayouts = new LinkedHashMap<>();	// Keep ordered by keys
	private Item item;

	public ScoreItem(Item item) {
		super(null);
		this.item = item;

		List<Score> scores = item.getCategory().getSurvey().getScores();
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
