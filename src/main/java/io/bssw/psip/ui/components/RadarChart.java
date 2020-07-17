package io.bssw.psip.ui.components;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.github.appreciated.apexcharts.ApexChartsBuilder;
import com.github.appreciated.apexcharts.config.builder.ChartBuilder;
import com.github.appreciated.apexcharts.config.builder.XAxisBuilder;
import com.github.appreciated.apexcharts.config.builder.YAxisBuilder;
import com.github.appreciated.apexcharts.config.chart.Type;
import com.github.appreciated.apexcharts.config.chart.builder.ToolbarBuilder;
import com.github.appreciated.apexcharts.config.xaxis.builder.LabelsBuilder;
import com.github.appreciated.apexcharts.config.xaxis.labels.builder.StyleBuilder;
import com.github.appreciated.apexcharts.helper.Series;

import io.bssw.psip.backend.data.Activity;
import io.bssw.psip.backend.data.Category;
import io.bssw.psip.backend.data.Item;

public class RadarChart extends ApexChartsBuilder {
	
	List<Double> scores = new ArrayList<>();
	List<String> labels = new ArrayList<>();

	public RadarChart(Activity activity) {
		activity.getCategories().forEach(c -> {
			scores.add(getCategorySummaryScore(c));
			labels.add(c.getName());
		});
        withChart(ChartBuilder.get()
        		.withToolbar(ToolbarBuilder.get().withShow(false).build())
	        		.withBackground("#f3f5f7") // Background of chart area
	                .withType(Type.radar)
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
