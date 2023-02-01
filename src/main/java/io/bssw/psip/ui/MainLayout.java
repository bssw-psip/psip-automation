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
package io.bssw.psip.ui;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasElement;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Main;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.router.AfterNavigationEvent;
import com.vaadin.flow.router.AfterNavigationObserver;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.RouteConfiguration;
import com.vaadin.flow.router.RouterLayout;
import com.vaadin.flow.server.ErrorHandler;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.theme.lumo.Lumo;

import io.bssw.psip.backend.model.Activity;
import io.bssw.psip.backend.model.Category;
import io.bssw.psip.backend.model.Item;
import io.bssw.psip.backend.model.Survey;
import io.bssw.psip.backend.service.ActivityService;
import io.bssw.psip.backend.service.RepositoryProviderManager;
import io.bssw.psip.backend.service.SurveyService;
import io.bssw.psip.ui.components.FlexBoxLayout;
import io.bssw.psip.ui.components.navigation.bar.AppBar;
import io.bssw.psip.ui.components.navigation.bar.TabBar;
import io.bssw.psip.ui.components.navigation.drawer.NaviDrawer;
import io.bssw.psip.ui.components.navigation.drawer.NaviItem;
import io.bssw.psip.ui.components.navigation.drawer.NaviMenu;
import io.bssw.psip.ui.util.UIUtils;
import io.bssw.psip.ui.util.css.Display;
import io.bssw.psip.ui.util.css.Overflow;
import io.bssw.psip.ui.views.Home;

@CssImport(value = "./styles/components/charts.css", themeFor = "vaadin-chart", include = "vaadin-chart-default-theme")
@CssImport(value = "./styles/components/floating-action-button.css", themeFor = "vaadin-button")
@CssImport(value = "./styles/components/grid.css", themeFor = "vaadin-grid")
@CssImport("./styles/lumo/border-radius.css")
@CssImport("./styles/lumo/icon-size.css")
@CssImport("./styles/lumo/margin.css")
@CssImport("./styles/lumo/padding.css")
@CssImport("./styles/lumo/shadow.css")
@CssImport("./styles/lumo/spacing.css")
@CssImport("./styles/lumo/typography.css")
@CssImport("./styles/misc/box-shadow-borders.css")
@CssImport(value = "./styles/styles.css", include = "lumo-badge")
@JsModule("@vaadin/vaadin-lumo-styles/badge")
public class MainLayout extends FlexBoxLayout
		implements RouterLayout, AfterNavigationObserver {

	private static final Logger log = LoggerFactory.getLogger(MainLayout.class);
	private static final String CLASS_NAME = "root";

	private Div appHeaderOuter;

	private FlexBoxLayout row;
	private NaviDrawer naviDrawer;
	private FlexBoxLayout column;

	private Div appHeaderInner;
	private Main viewContainer;
	private Div appFooterInner;

	private Div appFooterOuter;

	private TabBar tabBar;
	private boolean navigationTabs = false;
	private AppBar appBar;
	
	private static Map<String, NaviItem> naviItems = new HashMap<String, NaviItem>();

	private final ActivityService activityService;
	private final SurveyService surveyService;
	private final RepositoryProviderManager repositoryManager;

	public MainLayout(ActivityService activityService, SurveyService surveyService, RepositoryProviderManager manager) {
		this.activityService = activityService;
		this.surveyService = surveyService;
		this.repositoryManager = manager;

		VaadinSession.getCurrent()
				.setErrorHandler((ErrorHandler) errorEvent -> {
					log.error("Uncaught UI exception",
							errorEvent.getThrowable());
					Notification.show(
							"We are sorry, but an internal error occurred");
				});

		addClassName(CLASS_NAME);
		setFlexDirection(FlexDirection.COLUMN);
		setSizeFull();

		// Initialise the UI building blocks
		initStructure();

		// Populate the navigation drawer
		initNaviItems();

		// Configure the headers and footers (optional)
		initHeadersAndFooters();
	}

	/**
	 * Initialise the required components and containers.
	 */
	private void initStructure() {
		naviDrawer = new NaviDrawer();

		viewContainer = new Main();
		viewContainer.addClassName(CLASS_NAME + "__view-container");
		UIUtils.setDisplay(Display.FLEX, viewContainer);
		UIUtils.setFlexGrow(1, viewContainer);
		UIUtils.setOverflow(Overflow.HIDDEN, viewContainer);

		column = new FlexBoxLayout(viewContainer);
		column.addClassName(CLASS_NAME + "__column");
		column.setFlexDirection(FlexDirection.COLUMN);
		column.setFlexGrow(1, viewContainer);
		column.setOverflow(Overflow.HIDDEN);

		row = new FlexBoxLayout(naviDrawer, column);
		row.addClassName(CLASS_NAME + "__row");
		row.setFlexGrow(1, column);
		row.setOverflow(Overflow.HIDDEN);
		add(row);
		setFlexGrow(1, row);
	}

	/**
	 * Initialize the navigation items.
	 */
	private <C extends Component & HasUrlParameter<String>> void initNaviItems() {
		NaviMenu menu = naviDrawer.getMenu();
        menu.addNaviItem(VaadinIcon.HOME, "Home", Home.class);
		for (Activity activity : activityService.getActivities()) {
			Optional<Class<? extends Component>> optRoute = RouteConfiguration.forSessionScope().getRoute(activity.getPath(), Collections.singletonList(""));
			if (optRoute.isPresent()) {
				activityService.setActivity(activity.getName(), activity);
				Class<? extends Component> route = optRoute.get();
				NaviItem actNav = menu.addNaviItem(VaadinIcon.valueOf(activity.getIcon()), activity.getName(), optRoute.get());
				naviItems.put(activity.getPath(),  actNav);
				if (activity.hasCategories()) {
					Survey survey = surveyService.getSurvey();
					List<Category> categories = survey.getCategories();
					ListIterator<Category> categoryIter = categories.listIterator();
					Item prev = null;
					Item next = null;
					while (categoryIter.hasNext()) {
						Category category = categoryIter.next();
						if (HasUrlParameter.class.isAssignableFrom(route)) {
							@SuppressWarnings("unchecked") NaviItem catNav = menu.addNaviItem(actNav, category.getName(), (Class<? extends C>)route, category.getPath()); 
							String catPath = activity.getPath() + "/assessment/" + category.getPath();
							naviItems.put(catPath,  catNav);
							ListIterator<Item> itemIter = category.getItems().listIterator();
							while(itemIter.hasNext()) {
								Item item = itemIter.next();
								next = findNextItem(itemIter, categoryIter, category, categories);
								String itemNavPath = category.getPath() + "/" + item.getPath();
								@SuppressWarnings("unchecked") NaviItem naviItem = menu.addNaviItem(catNav, item.getName(), (Class<? extends C>)route, itemNavPath); 
								String itemPath = activity.getPath() + "/" + itemNavPath;
								surveyService.setItem(itemNavPath, item);
								surveyService.setPrevItem(itemNavPath, prev);
								surveyService.setNextItem(itemNavPath, next);
								naviItems.put(itemPath,  naviItem);
								prev = item;
							}
							catNav.setExpanded(false);
						}
					}
				}
				actNav.setExpanded(false);
			}
		}
	}
	
	// Next item of the last element is the first item in the next category
	private Item findNextItem(ListIterator<Item> itemIter, ListIterator<Category> categoryIter, Category category, List<Category> categories) {
		if (itemIter.hasNext()) {
			return category.getItems().get(itemIter.nextIndex());
		}
		if (categoryIter.hasNext()) {
			List<Item> items = categories.get(categoryIter.nextIndex()).getItems();
			return !items.isEmpty() ? items.get(0) : null;
		}
		return null;
	}

	/**
	 * Configure the app's inner and outer headers and footers.
	 */
	private void initHeadersAndFooters() {
		 setAppHeaderOuter();
		 setAppFooterInner();
		 setAppFooterOuter();

		// Default inner header setup:
		// - When using tabbed navigation the view title, user avatar and main menu button will appear in the TabBar.
		// - When tabbed navigation is turned off they appear in the AppBar.

		appBar = new AppBar(repositoryManager, "");

		// Tabbed navigation
		if (navigationTabs) {
			tabBar = new TabBar();
			UIUtils.setTheme(Lumo.DARK, tabBar);

			// Shift-click to add a new tab
			for (NaviItem item : naviDrawer.getMenu().getNaviItems()) {
				item.addClickListener(e -> {
					if (e.getButton() == 0 && e.isShiftKey()) {
						tabBar.setSelectedTab(tabBar.addClosableTab(item.getText(), item.getNavigationTarget()));
					}
				});
			}
			setAppHeaderInner(tabBar, appBar);

			// Default navigation
		} else {
			UIUtils.setTheme(Lumo.DARK, appBar);
			setAppHeaderInner(appBar);
		}
	}

	private void setAppHeaderOuter(Component... components) {
		if (appHeaderOuter == null) {
			appHeaderOuter = new Div();
			appHeaderOuter.addClassName("app-header-outer");
			getElement().insertChild(0, appHeaderOuter.getElement());
		}
		appHeaderOuter.removeAll();
		appHeaderOuter.add(components);
	}

	private void setAppHeaderInner(Component... components) {
		if (appHeaderInner == null) {
			appHeaderInner = new Div();
			appHeaderInner.addClassName("app-header-inner");
			column.getElement().insertChild(0, appHeaderInner.getElement());
		}
		appHeaderInner.removeAll();
		appHeaderInner.add(components);
	}

	private void setAppFooterInner(Component... components) {
		if (appFooterInner == null) {
			appFooterInner = new Div();
			appFooterInner.addClassName("app-footer-inner");
			column.getElement().insertChild(column.getElement().getChildCount(),
					appFooterInner.getElement());
		}
		appFooterInner.removeAll();
		appFooterInner.add(components);
	}

	private void setAppFooterOuter(Component... components) {
		if (appFooterOuter == null) {
			appFooterOuter = new Div();
			appFooterOuter.addClassName("app-footer-outer");
			getElement().insertChild(getElement().getChildCount(),
					appFooterOuter.getElement());
		}
		appFooterOuter.removeAll();
		appFooterOuter.add(components);
	}

	@Override
	public void showRouterLayoutContent(HasElement content) {
		this.viewContainer.getElement().appendChild(content.getElement());
	}

	public NaviDrawer getNaviDrawer() {
		return naviDrawer;
	}

	public static MainLayout get() {
		return (MainLayout) UI.getCurrent().getChildren()
				.filter(component -> component.getClass() == MainLayout.class)
				.findFirst().orElse(null);
	}

	public static <T, C extends Component & HasUrlParameter<T>> void navigate(
            Class<? extends C> navigationTarget, T parameter) {
		UI.getCurrent().navigate(navigationTarget, parameter);
		String url = RouteConfiguration.forSessionScope().getUrl(navigationTarget, parameter);
		if (naviItems.containsKey(url)) {
			NaviItem item = naviItems.get(url);
			UI.getCurrent().access(() -> item.setVisible(true));
		}
	}

	public AppBar getAppBar() {
		return appBar;
	}

	@Override
	public void afterNavigation(AfterNavigationEvent event) {
		if (navigationTabs) {
			afterNavigationWithTabs(event);
		} else {
			afterNavigationWithoutTabs(event);
		}
	}

	private void afterNavigationWithTabs(AfterNavigationEvent e) {
		NaviItem active = getActiveItem(e);
		if (active == null) {
			if (tabBar.getTabCount() == 0) {
				tabBar.addClosableTab("", Home.class);
			}
		} else {
			if (tabBar.getTabCount() > 0) {
				tabBar.updateSelectedTab(active.getText(),
						active.getNavigationTarget());
			} else {
				tabBar.addClosableTab(active.getText(),
						active.getNavigationTarget());
			}
		}
		appBar.getMenuIcon().setVisible(false);
	}

	private NaviItem getActiveItem(AfterNavigationEvent e) {
		for (NaviItem item : naviDrawer.getMenu().getNaviItems()) {
			if (item.isHighlighted(e)) {
				return item;
			}
		}
		return null;
	}

	private void afterNavigationWithoutTabs(AfterNavigationEvent e) {
		NaviItem active = getActiveItem(e);
		if (active != null) {
			if (active.getText() == "Home") {
				getNaviDrawer().setVisible(false);
				getAppBar().setTitle("  ");
				getAppBar().setVisible(true);
			} else {
				getNaviDrawer().setVisible(true);
				getAppBar().setTitle(active.getText());
				getAppBar().setVisible(true);
			}
		}
	}
}
