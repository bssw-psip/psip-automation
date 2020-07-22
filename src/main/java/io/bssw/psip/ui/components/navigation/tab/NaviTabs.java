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
package io.bssw.psip.ui.components.navigation.tab;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;

import io.bssw.psip.ui.util.UIUtils;
import io.bssw.psip.ui.util.css.Overflow;
import io.bssw.psip.ui.views.Home;

/**
 * NaviTabs supports tabs that can be closed, and that can navigate to a
 * specific target when clicked.
 */
@SuppressWarnings("serial")
public class NaviTabs extends Tabs {

	private ComponentEventListener<SelectedChangeEvent> listener = (ComponentEventListener<SelectedChangeEvent>) selectedChangeEvent -> navigateToSelectedTab();

	public NaviTabs() {
		addSelectedChangeListener(listener);
		getElement().setAttribute("overflow", "end");
		UIUtils.setOverflow(Overflow.HIDDEN, this);
	}

	/**
	 * When adding the first tab, the selection change event is triggered. This
	 * will cause the app to navigate to that tab's navigation target (if any).
	 * This constructor allows you to add the tabs before the event listener is
	 * set.
	 */
	public NaviTabs(NaviTab... naviTabs) {
		this();
		add(naviTabs);
	}

	/**
	 * Creates a regular tab without any click listeners.
	 */
	public Tab addTab(String text) {
		Tab tab = new Tab(text);
		add(tab);
		return tab;
	}

	/**
	 * Creates a tab that when clicked navigates to the specified target.
	 */
	public Tab addTab(String text,
	                  Class<? extends Component> navigationTarget) {
		Tab tab = new NaviTab(text, navigationTarget);
		add(tab);
		return tab;
	}

	/**
	 * Creates a (closable) tab that when clicked navigates to the specified
	 * target.
	 */
	public Tab addClosableTab(String text,
	                          Class<? extends Component> navigationTarget) {
		ClosableNaviTab tab = new ClosableNaviTab(text, navigationTarget);
		add(tab);

		tab.getCloseButton().addClickListener(event -> {
			remove(tab);
			navigateToSelectedTab();
		});

		return tab;
	}

	/**
	 * Navigates to the selected tab's navigation target if available.
	 */
	public void navigateToSelectedTab() {
		if (getSelectedTab() instanceof NaviTab) {
			try {
				UI.getCurrent().navigate(
						((NaviTab) getSelectedTab()).getNavigationTarget());
			} catch (Exception e) {
				// @todo this is an code flow by exception anti-pattern. Either
				// handle the case without the exception, or
				// @todo at least document meticulously why this can't be done
				// any other way and what kind of exceptions are we catching
				// @todo and when they can occur.
				// @todo this block consumes all exceptions, even
				// backend-originated, and may result in exceptions disappearing
				// mysteriously.

				// If the right-most tab is closed, the Tabs component does not
				// auto-select tabs on the left.
				if (getTabCount() > 0) {
					setSelectedIndex(getTabCount() - 1);
				} else {
					UI.getCurrent().navigate(Home.class);
				}
			}
		}
	}

	/**
	 * Updates the current tab's name and navigation target.
	 */
	public void updateSelectedTab(String text,
	                              Class<? extends Component> navigationTarget) {
		Tab tab = getSelectedTab();
		tab.setLabel(text);

		if (tab instanceof NaviTab) {
			((NaviTab) tab).setNavigationTarget(navigationTarget);
		}

		if (tab instanceof ClosableNaviTab) {
			tab.add(((ClosableNaviTab) tab).getCloseButton());
		}

		navigateToSelectedTab();
	}

	/**
	 * Returns the number of tabs.
	 */
	public int getTabCount() {
		return Math.toIntExact(getChildren()
				.filter(component -> component instanceof Tab).count());
	}

}
