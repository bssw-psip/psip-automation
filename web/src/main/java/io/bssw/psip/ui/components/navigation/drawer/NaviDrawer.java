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
package io.bssw.psip.ui.components.navigation.drawer;

import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.ClientCallable;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.AfterNavigationEvent;
import com.vaadin.flow.router.AfterNavigationObserver;

import elemental.json.JsonObject;
import io.bssw.psip.ui.util.UIUtils;

@SuppressWarnings("serial")
@CssImport("./styles/components/navi-drawer.css")
@JsModule("./swipe-away.js")
public class NaviDrawer extends Div
		implements AfterNavigationObserver {

	private String CLASS_NAME = "navi-drawer";
	private String RAIL = "rail";
	private String OPEN = "open";

	private Div scrim;

	private Div mainContent;
	private TextField search;

	private Button railButton;
	private NaviMenu menu;

	@Override
	protected void onAttach(AttachEvent attachEvent) {
		super.onAttach(attachEvent);
		UI ui = attachEvent.getUI();
		ui.getPage().executeJs("window.addSwipeAway($0,$1,$2,$3)",
				mainContent.getElement(), this, "onSwipeAway",
				scrim.getElement());
	}

	@ClientCallable
	public void onSwipeAway(JsonObject data) {
		close();
	}

	public NaviDrawer() {
		setClassName(CLASS_NAME);

		initScrim();
		initMainContent();

		initHeader();
//		initSearch();

		initMenu();

		initFooter();
	}

	private void initScrim() {
		// Backdrop on small viewports
		scrim = new Div();
		scrim.addClassName(CLASS_NAME + "__scrim");
		scrim.addClickListener(event -> close());
		add(scrim);
	}

	private void initMainContent() {
		mainContent = new Div();
		mainContent.addClassName(CLASS_NAME + "__content");
		add(mainContent);
	}

	private void initHeader() {
		mainContent.add(new BrandExpression("psip"));
	}

	private void initSearch() {
		search = new TextField();
		search.addValueChangeListener(e -> menu.filter(search.getValue()));
		search.setClearButtonVisible(true);
		search.setPlaceholder("Search");
		search.setPrefixComponent(new Icon(VaadinIcon.SEARCH));
		mainContent.add(search);
	}

	private void initMenu() {
		menu = new NaviMenu();
		mainContent.add(menu);
	}

	private void initFooter() {
		railButton = UIUtils.createSmallButton("Collapse", VaadinIcon.CHEVRON_LEFT_SMALL);
		railButton.addClassName(CLASS_NAME + "__footer");
		railButton.addClickListener(event -> toggleRailMode());
		railButton.getElement().setAttribute("aria-label", "Collapse menu");
		mainContent.add(railButton);
	}

	private void toggleRailMode() {
		if (getElement().hasAttribute(RAIL)) {
			getElement().setAttribute(RAIL, false);
			railButton.setIcon(new Icon(VaadinIcon.CHEVRON_LEFT_SMALL));
			railButton.setText("Collapse");
			UIUtils.setAriaLabel("Collapse menu", railButton);

		} else {
			getElement().setAttribute(RAIL, true);
			railButton.setIcon(new Icon(VaadinIcon.CHEVRON_RIGHT_SMALL));
			railButton.setText("Expand");
			UIUtils.setAriaLabel("Expand menu", railButton);
			getUI().get().getPage().executeJavaScript(
					"var originalStyle = getComputedStyle($0).pointerEvents;" //
							+ "$0.style.pointerEvents='none';" //
							+ "setTimeout(function() {$0.style.pointerEvents=originalStyle;}, 170);",
					getElement());
		}
	}

	public void toggle() {
		if (getElement().hasAttribute(OPEN)) {
			close();
		} else {
			open();
		}
	}

	private void open() {
		getElement().setAttribute(OPEN, true);
	}

	private void close() {
		getElement().setAttribute(OPEN, false);
		applyIOS122Workaround();
	}

	private void applyIOS122Workaround() {
		// iOS 12.2 sometimes fails to animate the menu away.
		// It should be gone after 240ms
		// This will make sure it disappears even when the browser fails.
		getUI().get().getPage().executeJavaScript(
				"var originalStyle = getComputedStyle($0).transitionProperty;" //
						+ "setTimeout(function() {$0.style.transitionProperty='padding'; requestAnimationFrame(function() {$0.style.transitionProperty=originalStyle})}, 250);",
				mainContent.getElement());
	}

	public NaviMenu getMenu() {
		return menu;
	}

	@Override
	public void afterNavigation(AfterNavigationEvent afterNavigationEvent) {
		close();
	}

}
