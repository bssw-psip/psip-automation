package io.bssw.psip.ui.components;

import java.util.Arrays;
import java.util.Collections;

import com.github.appreciated.apexcharts.ApexChartsBuilder;
import com.github.appreciated.apexcharts.config.builder.ChartBuilder;
import com.github.appreciated.apexcharts.config.builder.FillBuilder;
import com.github.appreciated.apexcharts.config.builder.PlotOptionsBuilder;
import com.github.appreciated.apexcharts.config.builder.StrokeBuilder;
import com.github.appreciated.apexcharts.config.chart.Type;
import com.github.appreciated.apexcharts.config.fill.builder.GradientBuilder;
import com.github.appreciated.apexcharts.config.plotoptions.builder.RadialBarBuilder;
import com.github.appreciated.apexcharts.config.plotoptions.hollow.HollowPosition;
import com.github.appreciated.apexcharts.config.plotoptions.radialbar.builder.HollowBuilder;
import com.github.appreciated.apexcharts.config.plotoptions.radialbar.builder.NameBuilder;
import com.github.appreciated.apexcharts.config.plotoptions.radialbar.builder.RadialBarDataLabelsBuilder;
import com.github.appreciated.apexcharts.config.plotoptions.radialbar.builder.ValueBuilder;

public class RadialChart extends ApexChartsBuilder {

	public RadialChart(String color) {
        withChart(ChartBuilder.get()
        		.withBackground("#f3f5f7") // Background of chart area
                .withType(Type.radialBar)
                .build())
                .withPlotOptions(PlotOptionsBuilder.get()
                	.withRadialBar(RadialBarBuilder.get()
                        .withStartAngle(-135.0)
                        .withEndAngle(135.0)
                        .withHollow(HollowBuilder.get()
                                .withMargin(0.0)
                                .withSize("10%")
                                .withBackground("#f3f5f7")
                                .withPosition(HollowPosition.front)
                                .build())
//                        .withHollow(HollowBuilder.get()
//                                .withMargin(0.0)
//                                .withSize("60%")
//                                .withBackground("#f3f5f7")
//                                .withPosition(HollowPosition.front)
//                                .withDropShadow(DropShadowBuilder.get()
//                                        .withEnabled(true)
//                                        .withTop(3.0)
//                                        .withBlur(4.0)
//                                        .withOpacity(0.24)
//                                        .build())
//                                .build())
//                        .withTrack(TrackBuilder.get()
//                                .withBackground("#f3f5f7")
//                                .withStrokeWidth("80%")
//                                .withDropShadow(DropShadowBuilder.get()
//                                        .withTop(-3.0)
//                                        .withLeft(0.0)
//                                        .withBlur(4.0)
//                                        .withOpacity(0.35)
//                                        .build())
//                                .build())
//
//                        .withHollow(HollowBuilder.get()
//                                .withMargin(0.0)
//                                .withSize("40%")
//                                .withBackground("#f3f5f7")
//                                .withPosition(HollowPosition.front)
//                                .build())
//                        .withTrack(TrackBuilder.get()
//                                .withBackground("#f3f5f7")
//                                .withStrokeWidth("80%")
//                                .build())
                        
                        .withDataLabels(RadialBarDataLabelsBuilder.get()
                                .withShow(false)
                                .withName(NameBuilder.get().withShow(false).build())
                                .withValue(ValueBuilder
                                        .get()
                                        .withOffsetY(10.0)
//                                        .withFormatter("function(val) {return parseInt(val) + 'foo';}")
                                        .withColor("#111")
                                        .withFontSize("15px")
                                        .withShow(true)
                                        .build())
                                .build())
                        .build())
                	.build())
                .withFill(FillBuilder.get()
//                        .withType("solid")
                        .withColors(Arrays.asList(color))
                		
//                        .withType("gradient")
//                        .withColors(Arrays.asList("#000000", "#ff9933", "#99cc66", "#66999"))
//                        .withGradient(GradientBuilder.get()
//                                .withShade("dark")
//                                .withType("horizontal")
//                                .withShadeIntensity(0.5)
//                                .withGradientToColors("#000000", "#ff9933", "#99cc66", "#66999")
//                                .withInverseColors(true)
//                                .withOpacityFrom(1.0)
//                                .withOpacityTo(1.0)
//                                .withStops(0.0, 25.0, 50.0, 75.0)
//                                .build())
                		
                      .withType("gradient")
                      .withGradient(GradientBuilder.get()
                              .withShade("dark")
//                              .withType("horizontal")
                              .withShadeIntensity(0.15)
//                              .withGradientToColors("#000000", "#ff9933", "#99cc66", "#66999")
                              .withInverseColors(false)
                              .withOpacityFrom(1.0)
                              .withOpacityTo(1.0)
                              .withStops(0.0, 25.0, 50.0, 75.0)
                              .build())
                        .build())
                .withSeries(0.0)
                .withLabels(Arrays.asList(color).toArray(new String[0]))
                .withStroke(StrokeBuilder.get()
//                        .withLineCap(LineCap.round)
                		.withColors(Arrays.asList(color).toArray(new String[0]))
                        .withDashArray(Collections.singletonList(4.0))
                        .build());	
	}

}
