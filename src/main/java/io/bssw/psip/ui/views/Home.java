package io.bssw.psip.ui.views;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Emphasis;
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
		
		intro.add(new Paragraph(new Emphasis("Click on the assessment to the left "
				+ "tab to get started. You can also click the arrow icon on the assessment tab to view the practice categories.")));

		intro.add(new Paragraph("Good luck!"));
	
		Anchor documentation = new Anchor("https://bssw-psip.github.io/ptc-catalog/", UIUtils.createButton("Read the documentation", VaadinIcon.EXTERNAL_LINK));

		FlexBoxLayout links = new FlexBoxLayout(documentation);
		links.setFlexWrap(FlexWrap.WRAP);
		links.setSpacing(Right.S);

		FlexBoxLayout content = new FlexBoxLayout(intro, links);
		content.setFlexDirection(FlexDirection.COLUMN);
		content.setMargin(Horizontal.AUTO);
		content.setMaxWidth("840px");
		content.setPadding(Uniform.RESPONSIVE_L);
		return content;
	}

}
