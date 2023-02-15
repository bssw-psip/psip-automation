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

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.ListItem;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.html.UnorderedList;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.FlexLayout.FlexDirection;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.server.StreamResource;
import com.vaadin.flow.server.auth.AnonymousAllowed;

import io.bssw.psip.backend.service.RepositoryProvider;
import io.bssw.psip.backend.service.RepositoryProviderManager;
import io.bssw.psip.ui.HomeLayout;
import io.bssw.psip.ui.components.FlexBoxLayout;
import io.bssw.psip.ui.layout.size.Horizontal;
import io.bssw.psip.ui.layout.size.Uniform;
import io.bssw.psip.ui.util.LumoStyles;
import io.bssw.psip.ui.util.UIUtils;

@PageTitle("PSIP")
@Route(value = "", layout = HomeLayout.class)
@RouteAlias(value = "home", layout = HomeLayout.class)
@AnonymousAllowed
public class Home extends ViewFrame {
	private static final String DEFAULT_BRANCH = "main";

	private final RepositoryProviderManager repositoryManager;

	@Autowired
	public Home(RepositoryProviderManager repositoryManager) {
		this.repositoryManager = repositoryManager;
		setId("home");
		setViewContent(createContent());
	}

	private Component createContent() {
		Div intro = new Div();
		StreamResource imageResource = new StreamResource("ryp_logo.png",
			() -> getClass().getResourceAsStream(UIUtils.IMG_PATH + "logos/ryp_logo.png"));
		intro.add(new Image(imageResource, "Rate Your Project"));
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
		
		intro.add(new Paragraph("Take the survey by clicking the button below or sign in to customize your survey"
				+ " and save the results directly to your project repository!"));

		VerticalLayout layout = new VerticalLayout();
		HorizontalLayout surveyLayout = new HorizontalLayout();
		surveyLayout.setAlignItems(Alignment.BASELINE);
		
		layout.add(surveyLayout);
		layout.setHorizontalComponentAlignment(FlexComponent.Alignment.CENTER, surveyLayout);

		Button surveyButton = new Button("Take the Survey"); //commit this
		surveyButton.setMaxWidth(15, Unit.REM);
		surveyButton.setWidthFull(); //keep this line, makes the button the full width of its max size
		surveyButton.setHeight(LumoStyles.Size.M);
		surveyButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY); //this worked, commit this

		if (repositoryManager.isLoggedIn()) {
			RepositoryProvider provider = repositoryManager.getRepositoryProvider();
			if (provider.canProvideRepositories()) {
				createComboBoxControls(surveyLayout, surveyButton, provider);
			} else {
				createTextFieldControls(surveyLayout, surveyButton, provider);
			}

		} else {
			surveyButton.addClickListener(buttonClickEvent -> {
				surveyButton.getUI().ifPresent(ui -> ui.navigate("assessment"));
			});
		}

		surveyLayout.add(surveyButton);

		FlexBoxLayout content = new FlexBoxLayout(intro, layout);
		content.setFlexDirection(FlexDirection.COLUMN);
		content.setMargin(Horizontal.AUTO);
		content.setMaxWidth("840px");
		content.setPadding(Uniform.RESPONSIVE_L);
		return content;
	}

	private void createComboBoxControls(HorizontalLayout layout, Button button, RepositoryProvider provider) {
		ComboBox<String> repoField = new ComboBox<>("Repository:");
		repoField.setWidth("400px");
		repoField.setItems(provider.getRepositories());
		repoField.setPlaceholder("Select repository");
		ComboBox<String> branchField = new ComboBox<>("Branch:");
		layout.add(repoField, branchField);

		repoField.addValueChangeListener(e -> {
			String defaultBranch = provider.getDefaultBranch(repoField.getValue());
			List<String> branches = provider.getBranches(repoField.getValue());
			branchField.setItems(branches);
			branchField.setValue(defaultBranch);
		});
		button.addClickListener(buttonClickEvent -> {
			try {
				if (!repoField.isEmpty()) {
					repositoryManager.getRepositoryProvider().connect(repoField.getValue(), branchField.getValue());
				}
				button.getUI().ifPresent(ui -> ui.navigate("assessment"));
			} catch (Exception e) {
				displayError(e.getLocalizedMessage());
			}
		});
	}

	private void createTextFieldControls(HorizontalLayout layout,Button button, RepositoryProvider provider) {
		TextField repoField = new TextField("Repository:");
		repoField.setWidth("400px");
		repoField.setPlaceholder("Repository name");
		TextField branchField = new TextField("Branch:");
		branchField.setPlaceholder("Branch name");
		layout.add(repoField, branchField);
		button.addClickListener(buttonClickEvent -> {
			try {
				if (!repoField.isEmpty()) {
					String branch = DEFAULT_BRANCH;
					if (!branchField.isEmpty()) {
						branch = branchField.getValue();
					}
					repositoryManager.getRepositoryProvider().connect(repoField.getValue(), branch);
				}
				button.getUI().ifPresent(ui -> ui.navigate("assessment"));
			} catch (Exception e) {
				displayError(e.getLocalizedMessage());
			}
		});
	}

	private void displayError(String message) {
		Notification notification = new Notification();
		notification.addThemeVariants(NotificationVariant.LUMO_ERROR);

		Div text = new Div(new Text(message));

		Button closeButton = new Button(new Icon("lumo", "cross"));
		closeButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY_INLINE);
		closeButton.getElement().setAttribute("aria-label", "Close");
		closeButton.addClickListener(event -> {
			notification.close();
		});

		HorizontalLayout noteLayout = new HorizontalLayout(text, closeButton);
		noteLayout.setAlignItems(Alignment.CENTER);

		notification.add(noteLayout);
		notification.open();
	}
}
