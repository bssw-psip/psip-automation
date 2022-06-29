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
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Emphasis;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.ListItem;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.html.UnorderedList;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexLayout.FlexDirection;
import com.vaadin.flow.component.orderedlayout.FlexLayout.FlexWrap;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import io.bssw.psip.ui.MainLayout;
import io.bssw.psip.ui.components.FlexBoxLayout;
import io.bssw.psip.ui.layout.size.Horizontal;
import io.bssw.psip.ui.layout.size.Right;
import io.bssw.psip.ui.layout.size.Uniform;
import io.bssw.psip.ui.util.UIUtils;

@SuppressWarnings("serial")
@PageTitle("PSIP")
@Route(value = "", layout = MainLayout.class)
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
		
		intro.add(new Paragraph(new Emphasis("Click the \"Git\" button below to sign into GitHub and manage"
		+ " your tracking cards.")));

		Button gitButton = new Button("Git");
		gitButton.addThemeVariants(ButtonVariant.LUMO_SMALL);
		FlexBoxLayout authenticateGit = new FlexBoxLayout(gitButton);
		authenticateGit.setFlexWrap(FlexWrap.WRAP);
		authenticateGit.setSpacing(Right.S);

		Anchor assessment = new Anchor("https://rateyourproject.org/assessment", UIUtils.createButton("Take Assessment", VaadinIcon.EXTERNAL_LINK));
		FlexBoxLayout link2 = new FlexBoxLayout(assessment);
		link2.setFlexWrap(FlexWrap.WRAP);
		link2.setSpacing(Right.S);


		intro.add(new Paragraph("Good luck!"));
	
		Anchor documentation = new Anchor("https://bssw-psip.github.io/ptc-catalog/", UIUtils.createButton("Read the documentation", VaadinIcon.EXTERNAL_LINK));

		FlexBoxLayout link1 = new FlexBoxLayout(documentation);



		link1.setFlexWrap(FlexWrap.WRAP);
		link1.setSpacing(Right.S);



		FlexBoxLayout content = new FlexBoxLayout(intro, gitButton, link1, link2);
		content.setFlexDirection(FlexDirection.COLUMN);
		content.setMargin(Horizontal.AUTO);
		content.setMaxWidth("840px");
		content.setPadding(Uniform.RESPONSIVE_L);
		return content;
	}

}
