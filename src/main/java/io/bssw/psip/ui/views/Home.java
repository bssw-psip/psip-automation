package io.bssw.psip.ui.views;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Html;
import com.vaadin.flow.component.html.Anchor;
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

@PageTitle("Welcome")
@Route(value = "", layout = MainLayout.class)
public class Home extends ViewFrame {
	
	public Home() {
		setId("home");
		setViewContent(createContent());
	}

	private Component createContent() {
		Html intro = new Html("<p>The tools on this site will help you:</p>\n");
		
		Html intro1 = new Html("<ul>\n" + 
				"<li>Assess your current practices\n" + 
				"<li>Create progress tracking cards\n" + 
				"<li>Integrate tracking cards with your workflow \n" + 
				"</ul>\n");

		Anchor documentation = new Anchor("https://bssw-psip.github.io/ptc-catalog/", UIUtils.createButton("Read the documentation", VaadinIcon.EXTERNAL_LINK));

		FlexBoxLayout links = new FlexBoxLayout(documentation);
		links.setFlexWrap(FlexWrap.WRAP);
		links.setSpacing(Right.S);

		FlexBoxLayout content = new FlexBoxLayout(intro, intro1, links);
		content.setFlexDirection(FlexDirection.COLUMN);
		content.setMargin(Horizontal.AUTO);
		content.setMaxWidth("840px");
		content.setPadding(Uniform.RESPONSIVE_L);
		return content;
	}

}
