package io.bssw.psip.ui.views;

import org.springframework.beans.factory.annotation.Autowired;

import com.github.appreciated.apexcharts.ApexCharts;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.formlayout.FormLayout.FormItem;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent.JustifyContentMode;
import com.vaadin.flow.component.orderedlayout.FlexLayout.FlexDirection;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.progressbar.ProgressBar;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.WildcardParameter;

import io.bssw.psip.backend.data.Activity;
import io.bssw.psip.backend.data.Category;
import io.bssw.psip.backend.data.Item;
import io.bssw.psip.backend.service.ActivityService;
import io.bssw.psip.ui.MainLayout;
import io.bssw.psip.ui.components.FlexBoxLayout;
import io.bssw.psip.ui.components.RadarChart;
import io.bssw.psip.ui.components.ScoreItem;
import io.bssw.psip.ui.components.ScoreSlider;
import io.bssw.psip.ui.layout.size.Horizontal;
import io.bssw.psip.ui.layout.size.Uniform;
import io.bssw.psip.ui.util.UIUtils;

@SuppressWarnings("serial")
@PageTitle("Assessment")
@Route(value = "assessment", layout = MainLayout.class)
public class Assessment extends ViewFrame implements HasUrlParameter<String> {
	private Label description;
	private VerticalLayout mainLayout;
	private ActivityService activityService;
	
	public Assessment(@Autowired ActivityService activityService) {
		this.activityService = activityService;
		setViewContent(createContent());
	}

	private Component createContent() {
		description = new Label();
		mainLayout = new VerticalLayout();
		mainLayout.setMargin(false);
		mainLayout.setHeightFull();
		FlexBoxLayout content = new FlexBoxLayout(new VerticalLayout(description), mainLayout);
		content.setFlexDirection(FlexDirection.COLUMN);
		content.setMargin(Horizontal.AUTO);
		content.setMaxWidth("840px");
		content.setPadding(Uniform.RESPONSIVE_L);
		content.setHeightFull();
		return content;
	}
	
	private void createItemLayout(Item item) {
		mainLayout.removeAll();
		ScoreItem scoreItem = new ScoreItem(item);
		String path = item.getCategory().getActivity().getPath() + "/" + item.getCategory().getPath() + "/" + item.getPath();
		Item prevItem =  activityService.getPrevItem(path);
		Button button1 = UIUtils.createLargeButton(VaadinIcon.CHEVRON_CIRCLE_LEFT);
		button1.getElement().addEventListener("click", e -> {
			MainLayout.navigate(Assessment.class, prevItem.getCategory().getPath() + "/" + prevItem.getPath());
		});
		if (prevItem == null) {
			button1.getElement().setEnabled(false);
		}
		Item nextItem = activityService.getNextItem(path);
		Button button2 =  UIUtils.createLargeButton(VaadinIcon.CHEVRON_CIRCLE_RIGHT);
		button2.getElement().addEventListener("click", e -> {
			MainLayout.navigate(Assessment.class, nextItem.getCategory().getPath() + "/" + nextItem.getPath());
		});
		if (nextItem == null) {
			button2.getElement().setEnabled(false);
		}
		HorizontalLayout hz = new HorizontalLayout(button1, button2);
		hz.setJustifyContentMode(JustifyContentMode.CENTER);
		hz.setWidthFull();
		mainLayout.addAndExpand(scoreItem, hz);
	}
	
	private void createCategoryLayout(Category category) {
		mainLayout.removeAll();
		FormLayout form = new FormLayout();
		for (Item item : category.getItems()) {
			ScoreSlider slider = new ScoreSlider(item);
			FormItem formItem = form.addFormItem(slider, item.getName());
			formItem.getElement().getStyle().set("--vaadin-form-item-label-width", "15em"); // Set width of label otherwise it will wrap
			formItem.getElement().getStyle().set("align-self", "flex-start");
			form.setColspan(formItem, 2); // FormLayout defaults to 2 columns so span both
		}
		
		Label label = new Label("The rows below show how well your team is doing for each practice. Click on the button below to assess "
				+ "individual practices, or you can update them directly on this page.");
		label.getElement().getStyle().set("font-style", "italic");
		
		Button button = new Button("Assess Practices");
		button.getElement().addEventListener("click", e -> {
			MainLayout.navigate(Assessment.class, category.getPath() + "/" + category.getItems().get(0).getPath());
		});
		button.setWidth("200px");
		button.setHeight("50px");
		HorizontalLayout hz = new HorizontalLayout(button);
		hz.setJustifyContentMode(JustifyContentMode.CENTER);
		hz.setWidthFull();
		mainLayout.add(label, form, hz);
	}
	
	private void createActivityLayout(Activity activity) {
		mainLayout.removeAll();
		Component summary = createActivitySummary(activity);
		Button button = new Button("Begin Assessment");
		button.getElement().addEventListener("click", e -> MainLayout.navigate(Assessment.class, activity.getCategories().get(0).getPath()));
		button.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
		button.setWidth("200px");
		button.setHeight("50px");
		HorizontalLayout hz = new HorizontalLayout(button);
		hz.setJustifyContentMode(JustifyContentMode.CENTER);
		hz.setWidthFull();
		mainLayout.addAndExpand(hz, summary);
	}
	
	private Component createActivitySummary(Activity activity) {
		ApexCharts chart = new RadarChart(activity).build();
		chart.setWidth("100%");
		return chart;
	}
	
	@SuppressWarnings("unused")
	private Component createActivitySummaryAsProgress(Activity activity) {
		FormLayout form = new FormLayout();
		for (Category category : activity.getCategories()) {
			int score = 0;
			for (Item item : category.getItems()) {
				if (item.getScore().isPresent()) {
					score += item.getScore().get();
				}
			}
			ProgressBar bar = new ProgressBar(0, category.getItems().size() > 0 ? 100 * category.getItems().size() : 1);
			bar.getElement().getStyle().set("height", "10px");
			bar.setValue(score);
			if (category.getItems().isEmpty()) {
				bar.getElement().setEnabled(false);
			}
			FormItem formItem = form.addFormItem(bar, category.getName());
			formItem.getElement().getStyle().set("--vaadin-form-item-label-width", "15em"); // Set width of label otherwise it will wrap
			form.setColspan(formItem, 2); // FormLayout defaults to 2 columns so span both
		}
		return form;
	}

	@Override
	public void setParameter(BeforeEvent event, @WildcardParameter String parameter) {
		String desc = "";
		if (parameter.isEmpty()) {
			Activity activity = activityService.getActivity("Assessment");
			desc = activity.getDescription();
			createActivityLayout(activity);
		} else if (parameter.contains("/")) {
			// Item
			Item item = activityService.getItem("assessment/" + parameter);
			if (item != null) {
				desc = item.getDescription();
				createItemLayout(item);
			} else {
				System.out.println("invalid parameter="+parameter);
			}
		} else {
			// Category
			Category category = activityService.getCategory("assessment/" + parameter);
			desc = category.getDescription();
			createCategoryLayout(category);
		}
		description.getElement().setText(desc);
	}

}
