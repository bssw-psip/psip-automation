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

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.github.appreciated.apexcharts.ApexChartsBuilder;
import com.github.appreciated.apexcharts.config.builder.ChartBuilder;
import com.github.appreciated.apexcharts.config.builder.GridBuilder;
import com.github.appreciated.apexcharts.config.builder.XAxisBuilder;
import com.github.appreciated.apexcharts.config.builder.YAxisBuilder;
import com.github.appreciated.apexcharts.config.chart.Type;
import com.github.appreciated.apexcharts.config.chart.builder.ToolbarBuilder;
import com.github.appreciated.apexcharts.config.grid.builder.PaddingBuilder;
import com.github.appreciated.apexcharts.config.xaxis.builder.LabelsBuilder;
import com.github.appreciated.apexcharts.config.xaxis.labels.builder.StyleBuilder;
import com.github.appreciated.apexcharts.helper.Series;

import io.bssw.psip.backend.model.*;

public class RadarChart extends ApexChartsBuilder {
	
	List<Double> scores = new ArrayList<>();
	List<String> labels = new ArrayList<>();

	//Took in Survey at first
	public RadarChart(SurveyScore surveyScore) {
		for (CategoryScore score: surveyScore.getCategoryScores()) {
			scores.add(getCategorySummaryScore(score));
			//TODO: need implementation of a get name here for the
			// label instead of getPath
			labels.add(score.getPath());
		}
		buildChart();
	}
	//Keep this constructor for times when the user is not logged in
	public RadarChart(Survey survey) {
		survey.getCategories().forEach(c -> {
			scores.add(getCategorySummaryScore(c));
			labels.add(c.getName());
		});
		buildChart();
	}

	private void buildChart() {
		withChart(ChartBuilder.get()
				.withToolbar(ToolbarBuilder.get().withShow(false).build())
				.withBackground("#f3f5f7") // Background of chart area
				.withType(Type.RADAR)
				.build())
				.withSeries(new Series<>(scores.toArray()))
				.withXaxis(XAxisBuilder.get()
						.withLabels(LabelsBuilder.get()
								.withStyle(StyleBuilder.get()
										.withColors(labels.stream().map(l -> "black").collect(Collectors.toList()))
										.withFontSize("12px")
										.build())
								.build())
						.build())
				.withLabels(labels.toArray(new String[0]))
				.withYaxis(YAxisBuilder.get()
						.withMin(0.0)
						.withMax(100.0)
						.withTickAmount(5.0)
						.build())
				// try to reduce the padding around the chart
				.withGrid(GridBuilder.get()
						.withPadding(PaddingBuilder.get()
								.withTop(-50.0)
								.withBottom(-50.0)
								.build())
						.build())
				.build();
	}

	
	/**
	 * Calculate scaled summary score for the category. The score is the sum
	 * of the item scores divided by the number of scores. This should always
	 * give a value between 0 and 100.
	 * 
	 * @param category
	 * @return scaled summary score
	 */
	private Double getCategorySummaryScore(CategoryScore score) {
		int value = 0;
		for (ItemScore itemScore: score.getItemScores()) {
			value += Integer.parseInt(itemScore.getValue());
		}
		return (double) (value / score.getItemScores().size());
	}
	private Double getCategorySummaryScore(Category category) {
		if (!category.getItems().isEmpty()) {
			int score = 0;
			for (Item item : category.getItems()) {
				if (item.getScore().isPresent()) {
					score += item.getScore().get();
				}
			}
			return (double) (score / category.getItems().size());
		}

		return 0.0;
	}
}
