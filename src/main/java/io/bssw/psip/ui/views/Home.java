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
package io.bssw.psip.ui.views;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.ListItem;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.html.UnorderedList;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.FlexLayout.FlexDirection;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;

import io.bssw.psip.ui.MainLayout;
import io.bssw.psip.ui.components.FlexBoxLayout;
import io.bssw.psip.ui.layout.size.Horizontal;
import io.bssw.psip.ui.layout.size.Uniform;
import io.bssw.psip.ui.util.LumoStyles;
import io.bssw.psip.ui.util.UIUtils;


@SuppressWarnings("serial")
@PageTitle("PSIP")
@Route(value = "", layout = MainLayout.class)
@AnonymousAllowed
public class Home extends ViewFrame {
	
	public Home() {
		setId("home");
		setViewContent(createContent());
	}

	private Component createContent() {
		Div intro = new Div();
		intro.add(new Image(UIUtils.IMG_PATH + "logos/ryp_logo.png", "Rate Your Project"));
		intro.add(new H2("A self assessment tool for improving software practices"));
		intro.add(new Paragraph("Software engineering is a systematic approach to the design, development, and maintenance of a software system. "
				+ "Teams seldom have the time to stop development and focus solely on improving productivity or sustainability. However, "
				+ "teams can incorporate improvements on the way to developing new science capabilities."));
		
		intro.add(new Paragraph("The tools on this site will help you:"));
		
		intro.add(new UnorderedList( 
				new ListItem("Assess your current practices"),
				new ListItem("Create progress tracking cards"),
				new ListItem("Integrate tracking cards with your workflow")));
		
		intro.add(new Paragraph("The self-assessment introduces software engineering practices that increase in maturity. "
				+ "Check the practices that your project already uses to rate your project."));
		
		intro.add(new Paragraph("Take the survey by clicking the button below or sign in to customize your survey"
				+ " and save the results directly to your project repository!"));




		VerticalLayout layout = new VerticalLayout();
		Button surveyButton = new Button("Take the Survey"); //commit this
		surveyButton.addClickListener(buttonClickEvent -> {
			surveyButton.getUI().ifPresent(ui -> ui.navigate("assessment"));
		});
		layout.add(surveyButton);
		layout.setHorizontalComponentAlignment(FlexComponent.Alignment.CENTER, surveyButton);
		surveyButton.setMaxWidth(15, Unit.REM);
		surveyButton.setWidthFull(); //keep this line, makes the button the full width of its max size
		surveyButton.setHeight(LumoStyles.Size.M);
		surveyButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY); //this worked, commit this




		FlexBoxLayout content = new FlexBoxLayout(intro, surveyButton);
		content.setFlexDirection(FlexDirection.COLUMN);
		content.setMargin(Horizontal.AUTO);
		content.setMaxWidth("840px");
		content.setPadding(Uniform.RESPONSIVE_L);
		return content;
	}

}
