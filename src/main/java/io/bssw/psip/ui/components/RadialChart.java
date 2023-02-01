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

import java.util.Arrays;

import com.github.appreciated.apexcharts.ApexChartsBuilder;
import com.github.appreciated.apexcharts.config.builder.ChartBuilder;
import com.github.appreciated.apexcharts.config.builder.FillBuilder;
import com.github.appreciated.apexcharts.config.builder.PlotOptionsBuilder;
import com.github.appreciated.apexcharts.config.builder.StrokeBuilder;
import com.github.appreciated.apexcharts.config.chart.Type;
import com.github.appreciated.apexcharts.config.chart.builder.DropShadowBuilder;
import com.github.appreciated.apexcharts.config.plotoptions.builder.RadialBarBuilder;
import com.github.appreciated.apexcharts.config.plotoptions.hollow.HollowPosition;
import com.github.appreciated.apexcharts.config.plotoptions.radialbar.builder.HollowBuilder;
import com.github.appreciated.apexcharts.config.plotoptions.radialbar.builder.NameBuilder;
import com.github.appreciated.apexcharts.config.plotoptions.radialbar.builder.RadialBarDataLabelsBuilder;
import com.github.appreciated.apexcharts.config.plotoptions.radialbar.builder.TrackBuilder;
import com.github.appreciated.apexcharts.config.plotoptions.radialbar.builder.ValueBuilder;
import com.github.appreciated.apexcharts.config.stroke.LineCap;

public class RadialChart extends ApexChartsBuilder {

	public RadialChart(String color) {
        withChart(ChartBuilder.get()
        		.withBackground("#f3f5f7") // Background of chart area
                .withType(Type.RADIALBAR)
                .build())
                .withPlotOptions(PlotOptionsBuilder.get()
                	.withRadialBar(RadialBarBuilder.get()
                        .withStartAngle(-90.0)
                        .withEndAngle(90.0)
                        .withHollow(HollowBuilder.get()
                                .withMargin(0.0)
                                .withSize("30%")
                                .withBackground("#f3f5f7")
                                .withPosition(HollowPosition.FRONT)
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
                        .withTrack(TrackBuilder.get()
                                .withBackground("#fff")
                                .withStrokeWidth("80%")
                                .withDropShadow(DropShadowBuilder.get()
                                        .withTop(-3.0)
                                        .withLeft(0.0)
                                        .withBlur(4.0)
                                        .withOpacity(0.35)
                                        .build())
                                .build())
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
                        .withType("solid")
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
                		
//                      .withType("gradient")
//                      .withGradient(GradientBuilder.get()
//                              .withShade("dark")
////                              .withType("horizontal")
//                              .withShadeIntensity(0.15)
////                              .withGradientToColors("#000000", "#ff9933", "#99cc66", "#66999")
//                              .withInverseColors(false)
//                              .withOpacityFrom(1.0)
//                              .withOpacityTo(1.0)
//                              .withStops(0.0, 25.0, 50.0, 75.0)
//                              .build())
                        .build())
                .withSeries(0.0)
                .withLabels(Arrays.asList(color).toArray(new String[0]))
                .withStroke(StrokeBuilder.get()
                        .withLineCap(LineCap.BUTT)
                		.withColors(Arrays.asList(color).toArray(new String[0]))
//                        .withDashArray(Collections.singletonList(4.0))
                        .build());	
	}

}
