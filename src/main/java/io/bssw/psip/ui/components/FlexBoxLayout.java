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
package io.bssw.psip.ui.components;

import java.util.ArrayList;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.theme.lumo.Lumo;

import io.bssw.psip.ui.layout.size.Size;
import io.bssw.psip.ui.util.css.BorderRadius;
import io.bssw.psip.ui.util.css.BoxSizing;
import io.bssw.psip.ui.util.css.Display;
import io.bssw.psip.ui.util.css.Overflow;
import io.bssw.psip.ui.util.css.Position;
import io.bssw.psip.ui.util.css.Shadow;

public class FlexBoxLayout extends FlexLayout {

	public static final String BACKGROUND_COLOR = "background-color";
	public static final String BORDER_RADIUS = "border-radius";
	public static final String BOX_SHADOW = "box-shadow";
	public static final String BOX_SIZING = "box-sizing";
	public static final String DISPLAY = "display";
	public static final String FLEX_DIRECTION = "flex-direction";
	public static final String FLEX_WRAP = "flex-wrap";
	public static final String MAX_WIDTH = "max-width";
	public static final String OVERFLOW = "overflow";
	public static final String POSITION = "position";

	private ArrayList<Size> spacings;

	public FlexBoxLayout(Component... components) {
		super(components);
		spacings = new ArrayList<>();
	}

	public void setBackgroundColor(String value) {
		getStyle().set(BACKGROUND_COLOR, value);
	}

	public void setBackgroundColor(String value, String theme) {
		getStyle().set(BACKGROUND_COLOR, value);
		setTheme(theme);
	}

	public void removeBackgroundColor() {
		getStyle().remove(BACKGROUND_COLOR);
	}

	public void setBorderRadius(BorderRadius radius) {
		getStyle().set(BORDER_RADIUS, radius.getValue());
	}

	public void removeBorderRadius() {
		getStyle().remove(BORDER_RADIUS);
	}

	public void setBoxSizing(BoxSizing sizing) {
		getStyle().set(BOX_SIZING, sizing.getValue());
	}

	public void removeBoxSizing() {
		getStyle().remove(BOX_SIZING);
	}

	public void setDisplay(Display display) {
		getStyle().set(DISPLAY, display.getValue());
	}

	public void removeDisplay() {
		getStyle().remove(DISPLAY);
	}

	public void setFlex(String value, Component... components) {
		for (Component component : components) {
			component.getElement().getStyle().set("flex", value);
		}
	}

	public void setFlexBasis(String value, Component... components) {
		for (Component component : components) {
			component.getElement().getStyle().set("flex-basis", value);
		}
	}

	@Override
	public void setFlexDirection(FlexDirection direction) {
		getStyle().set(FLEX_DIRECTION, direction.name());
	}

	public void removeFlexDirection() {
		getStyle().remove(FLEX_DIRECTION);
	}

	public void setFlexShrink(String value, Component... components) {
		for (Component component : components) {
			component.getElement().getStyle().set("flex-shrink", value);
		}
	}

	@Override
	public void setFlexWrap(FlexWrap wrap) {
		getStyle().set(FLEX_WRAP, wrap.name());
	}

	public void removeFlexWrap() {
		getStyle().remove(FLEX_WRAP);
	}

	public void setMargin(Size... sizes) {
		for (Size size : sizes) {
			for (String attribute : size.getMarginAttributes()) {
				getStyle().set(attribute, size.getVariable());
			}
		}
	}

	public void removeMargin() {
		getStyle().remove("margin");
		getStyle().remove("margin-bottom");
		getStyle().remove("margin-left");
		getStyle().remove("margin-right");
		getStyle().remove("margin-top");
	}

	@Override
	public void setMaxWidth(String value) {
		getStyle().set(MAX_WIDTH, value);
	}

	public void removeMaxWidth() {
		getStyle().remove(MAX_WIDTH);
	}

	public void setOverflow(Overflow overflow) {
		getStyle().set(OVERFLOW, overflow.getValue());
	}

	public void removeOverflow() {
		getStyle().remove(OVERFLOW);
	}

	public void setPadding(Size... sizes) {
		removePadding();
		for (Size size : sizes) {
			for (String attribute : size.getPaddingAttributes()) {
				getStyle().set(attribute, size.getVariable());
			}
		}
	}

	public void removePadding() {
		getStyle().remove("padding");
		getStyle().remove("padding-bottom");
		getStyle().remove("padding-left");
		getStyle().remove("padding-right");
		getStyle().remove("padding-top");
	}

	public void setPosition(Position position) {
		getStyle().set(POSITION, position.getValue());
	}

	public void removePosition() {
		getStyle().remove(POSITION);
	}

	public void setShadow(Shadow shadow) {
		getStyle().set(BOX_SHADOW, shadow.getValue());
	}

	public void removeShadow() {
		getStyle().remove(BOX_SHADOW);
	}

	public void setSpacing(Size... sizes) {
		// Remove old styles (if applicable)
		for (Size spacing : spacings) {
			removeClassName(spacing.getSpacingClassName());
		}
		spacings.clear();

		// Add new
		for (Size size : sizes) {
			addClassName(size.getSpacingClassName());
			spacings.add(size);
		}
	}

	public void setTheme(String theme) {
		if (Lumo.DARK.equals(theme)) {
			getElement().setAttribute("theme", "dark");
		} else {
			getElement().removeAttribute("theme");
		}
	}
}
