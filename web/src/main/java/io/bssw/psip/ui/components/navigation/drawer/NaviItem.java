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

import java.util.ArrayList;
import java.util.List;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.ListItem;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.router.AfterNavigationEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.HighlightConditions;
import com.vaadin.flow.router.RouterLink;

import io.bssw.psip.ui.util.UIUtils;

@SuppressWarnings("serial")
@CssImport("./styles/components/navi-item.css")
public class NaviItem extends ListItem {

	private String CLASS_NAME = "navi-item";

	private int level = 0;

	private Component link;
	private Class<? extends Component> navigationTarget;
	private String text;

	protected Button expandCollapse;

	private NaviItem parent;
	private List<NaviItem> subItems;
	private boolean expanded;

	public NaviItem(VaadinIcon icon, String text, Class<? extends Component> navigationTarget) {
		this(text, navigationTarget);
		link.getElement().insertChild(0, new Icon(icon).getElement());
	}

	public NaviItem(Image image, String text, Class<? extends Component> navigationTarget) {
		this(text, navigationTarget);
		link.getElement().insertChild(0, image.getElement());
	}
	
	public NaviItem(String text, Class<? extends Component> navigationTarget) {
		this.text = text;
		this.navigationTarget = navigationTarget;

		if (navigationTarget != null) {
			RouterLink routerLink = new RouterLink(null, navigationTarget);
			routerLink.add(new Span(text));
			routerLink.setClassName(CLASS_NAME + "__link");
			routerLink.setHighlightCondition(HighlightConditions.sameLocation());
			this.link = routerLink;
		} else {
			Div div = new Div(new Span(text));
			div.addClickListener(e -> expandCollapse.click());
			div.setClassName(CLASS_NAME + "__link");
			this.link = div;
		}

		init();
	}

	public <T, C extends Component & HasUrlParameter<T>> NaviItem(String text, Class<C> navigationTarget, T parameter) {
		this.text = text;
		this.navigationTarget = navigationTarget;

		RouterLink routerLink = new RouterLink(null, navigationTarget, parameter);
		routerLink.add(new Span(text));
		routerLink.setClassName(CLASS_NAME + "__link");
		routerLink.setHighlightCondition(HighlightConditions.sameLocation());
		this.link = routerLink;
		
		init();
	}
	
	private void init() {
		setClassName(CLASS_NAME);
		setLevel(0);
		
		expandCollapse = UIUtils.createButton(VaadinIcon.CARET_UP, ButtonVariant.LUMO_SMALL, ButtonVariant.LUMO_TERTIARY);
		expandCollapse.addClickListener(event -> setExpanded(!expanded));
		expandCollapse.setVisible(false);

		subItems = new ArrayList<>();
		expanded = true;
		updateAriaLabel();

		add(link, expandCollapse);
	}

	private void updateAriaLabel() {
		String action = (expanded ? "Collapse " : "Expand ") + text;
		UIUtils.setAriaLabel(action, expandCollapse);
	}

	public boolean isHighlighted(AfterNavigationEvent e) {
		return link instanceof RouterLink && ((RouterLink) link)
				.getHighlightCondition().shouldHighlight((RouterLink) link, e);
	}

	public void setLevel(int level) {
		this.level = level;
		if (level > 0) {
			getElement().setAttribute("level", Integer.toString(level));
		}
	}

	public int getLevel() {
		return level;
	}

	public Class<? extends Component> getNavigationTarget() {
		return navigationTarget;
	}
	
	public void addParentItem(NaviItem parent) {
		this.parent = parent;
	}

	public void addSubItem(NaviItem item) {
		if (!expandCollapse.isVisible()) {
			expandCollapse.setVisible(true);
		}
		item.setLevel(getLevel() + 1);
		subItems.add(item);
	}

	public void setExpanded(boolean visible) {
		expandCollapse.setIcon(new Icon(visible ? VaadinIcon.CARET_UP : VaadinIcon.CARET_DOWN));
		expanded = visible; // must be set before calling setVisible on sub-items
		subItems.forEach(item -> item.setVisible(visible));
		updateAriaLabel();
	}
	
	public boolean isExpanded() {
		return expanded;
	}

	@Override
	public String getText() {
		return text;
	}

	@Override
	public void setVisible(boolean visible) {
		super.setVisible(visible);

		// if true, we need to show and expand all parent menus, otherwise hide our sub-menus
		if (visible) {
			if (parent != null && !parent.isExpanded()) {
				parent.setVisible(visible);
				parent.setExpanded(visible);
			}
		} else {
			setExpanded(visible);
		}
	}
}
