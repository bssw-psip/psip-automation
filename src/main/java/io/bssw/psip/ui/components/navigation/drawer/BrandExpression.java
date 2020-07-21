package io.bssw.psip.ui.components.navigation.drawer;

import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Label;

import io.bssw.psip.ui.util.UIUtils;

@CssImport("./styles/components/brand-expression.css")
public class BrandExpression extends Div {

	private String CLASS_NAME = "brand-expression";

	private Image logo;
	private Label title;

	public BrandExpression(String text) {
		setClassName(CLASS_NAME);

		logo = new Image(UIUtils.IMG_PATH + "logos/psip.png", "");
		logo.setAlt(text + " logo");
		logo.setClassName(CLASS_NAME + "__logo");

		title = new Label(text);
		title.addClassName(CLASS_NAME + "__title");
		title.getElement().getStyle().set("color", "#2A285E").set("font-size", "30px").set("font-weight", "bold");

		add(logo, title);
	}

}
