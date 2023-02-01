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

import java.util.List;
import java.util.stream.Collectors;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Nav;
import com.vaadin.flow.component.html.UnorderedList;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.router.HasUrlParameter;

@CssImport("./styles/components/navi-menu.css")
public class NaviMenu extends Nav {

	private String CLASS_NAME = "navi-menu";
	private UnorderedList list;

	public NaviMenu() {
		setClassName(CLASS_NAME);
		list = new UnorderedList();
		add(list);
	}

	protected void addNaviItem(NaviItem item) {
		list.add(item);
	}

	protected void addNaviItem(NaviItem parent, NaviItem item) {
		parent.addSubItem(item);
		item.addParentItem(parent);
		addNaviItem(item);
	}

	public void filter(String filter) {
		getNaviItems().forEach(naviItem -> {
			boolean matches = naviItem.getText().toLowerCase()
					.contains(filter.toLowerCase());
			naviItem.setVisible(matches);
		});
	}

	public NaviItem addNaviItem(String text,
	                            Class<? extends Component> navigationTarget) {
		NaviItem item = new NaviItem(text, navigationTarget);
		addNaviItem(item);
		return item;
	}

	public NaviItem addNaviItem(VaadinIcon icon, String text,
	                            Class<? extends Component> navigationTarget) {
		NaviItem item = new NaviItem(icon, text, navigationTarget);
		addNaviItem(item);
		return item;
	}

	public NaviItem addNaviItem(Image image, String text,
	                            Class<? extends Component> navigationTarget) {
		NaviItem item = new NaviItem(image, text, navigationTarget);
		addNaviItem(item);
		return item;
	}

	public NaviItem addNaviItem(NaviItem parent, VaadinIcon icon, String text,
	                            Class<? extends Component> navigationTarget) {
		NaviItem item = new NaviItem(icon, text, navigationTarget);
		addNaviItem(parent, item);
		return item;
	}
	
	public NaviItem addNaviItem(NaviItem parent, String text,
            Class<? extends Component> navigationTarget) {
		NaviItem item = new NaviItem(text, navigationTarget);
		addNaviItem(parent, item);
		return item;
	}
	
	public <T, C extends Component & HasUrlParameter<T>> NaviItem addNaviItem(NaviItem parent, String text,
			Class<C> navigationTarget, T parameter) {
		NaviItem item = new NaviItem(text, navigationTarget, parameter);
		addNaviItem(parent, item);
		return item;
	}

	public List<NaviItem> getNaviItems() {
		List<NaviItem> items = list.getChildren()
			.filter(c -> c instanceof NaviItem)
			.map(c -> (NaviItem)c)
			.collect(Collectors.toList());
		return items;
	}

}
