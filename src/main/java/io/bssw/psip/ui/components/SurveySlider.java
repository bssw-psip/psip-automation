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

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasComponents;
import com.vaadin.flow.component.HasSize;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import io.bssw.psip.backend.model.*;
import io.bssw.psip.backend.service.SurveyService;
import io.bssw.psip.ui.util.UIUtils;
import org.vaadin.pekkam.Canvas;
import org.vaadin.pekkam.CanvasRenderingContext2D;

import java.util.List;
import java.util.Optional;

@Tag("surveyHistory-group")
public class SurveySlider extends Component implements HasComponents, HasSize {
    private static final int BUTTON_RADIUS = 10;
    private static final int BUTTON_TRACK_WIDTH = 10;
    private static final int CANVAS_WIDTH = 320;
    private static final int CANVAS_HEIGHT = BUTTON_RADIUS * 2;
    private static final int SURVEY_LIMIT = 5;
    private static final String BACKGROUND_COLOR = "#E3E3E3";
    private static final String BUTTON_COLOR = "#007fff";
    private final List<SurveyScore> surveyScores;
    private final SurveyHistory history;
    private final SurveyService service;
    private final CanvasRenderingContext2D ctx;
    private final Label label;
    private final Button prev;
    private final Button next;
    private final int diff;
    private int currentIndex;
    private int bookMarkIndex;
    private int buttonPosition;
    private int lastButtonPos;
    private int startingPoint;
    private Integer zeroIndexPos;

    public SurveySlider(SurveyService service) {
        this.service = service;
        this.history = service.getHistory();
        this.surveyScores = history.getScores();
        this.label = new Label();
        this.label.getElement().getStyle().set("font-style", "italic");
        this.prev = UIUtils.createLargeButton(VaadinIcon.CHEVRON_CIRCLE_LEFT);
        this.next = UIUtils.createLargeButton(VaadinIcon.CHEVRON_CIRCLE_RIGHT);
        this.buttonPosition = SURVEY_LIMIT - 1;
        this.lastButtonPos = buttonPosition;
        this.startingPoint = surveyScores.size();
        this.diff = surveyScores.size() - SURVEY_LIMIT;
        this.currentIndex = (SURVEY_LIMIT - 1) + diff;
        this.bookMarkIndex = currentIndex;
        this.zeroIndexPos = 0;


        VerticalLayout verticalLayout = new VerticalLayout();
        HorizontalLayout horizontalLayout = new HorizontalLayout();

        Canvas canvas = new Canvas(CANVAS_WIDTH, CANVAS_HEIGHT);

        if (surveyScores.size() != 0) {
            canvas.getElement().addEventListener("click", e ->
                    {
                        double x = e.getEventData().getNumber("event.offsetX");
                        buttonPosition = (int) Math.round(x) / (CANVAS_WIDTH / SURVEY_LIMIT);
                        if (surveyScores.size() > SURVEY_LIMIT) {

                            if (currentIndex < surveyScores.size() % SURVEY_LIMIT) {
                                zeroIndexPos = SURVEY_LIMIT - (surveyScores.size() % SURVEY_LIMIT);
                                if (buttonPosition < zeroIndexPos) {
                                    buttonPosition = lastButtonPos;
                                } else {
                                    currentIndex = currentIndex - (lastButtonPos - buttonPosition);
                                }
                            } else {
                                currentIndex = currentIndex - (lastButtonPos - buttonPosition);
                            }
                        }

                        if (surveyScores.size() <= SURVEY_LIMIT) {
                            if (buttonPosition < (SURVEY_LIMIT - surveyScores.size())) {
                                buttonPosition = lastButtonPos;
                            }
                            currentIndex = currentIndex - (lastButtonPos - buttonPosition);
                        }

//                        System.out.println("after clicking a button, current index from slider is: " + currentIndex);

                        if ((buttonPosition <= (SURVEY_LIMIT - (surveyScores.size() % SURVEY_LIMIT))) &&
                                (currentIndex < (surveyScores.size() % SURVEY_LIMIT)) && !(bookMarkIndex > 0)) {
                            prev.setEnabled(false);
                            //when size is divisible by 5
                        } else if (currentIndex == 0) {
                            prev.setEnabled(false);
                        } else {
                            prev.setEnabled(true);
                        }
                        if (currentIndex == surveyScores.size() - 1) {
                            next.setEnabled(false);
                        } else {
                            next.setEnabled(true);
                        }

//                        System.out.println("x: " + x);
                        System.out.println("buttonPosition: " + buttonPosition);
                        if (currentIndex >= 0 && currentIndex < surveyScores.size()) {
                            setDate(Optional.of(surveyScores.get(currentIndex).getTimestamp()));
                        }

                        int lowThreshold = buttonPosX(SURVEY_LIMIT - surveyScores.size() - 1);
//                        System.out.println("the low is: " + lowThreshold);
                        int highThreshold = buttonPosX(SURVEY_LIMIT - surveyScores.size());
//                        System.out.println("the high threshold is: " + highThreshold);
                        int errorThreshold = (lowThreshold + highThreshold) / 2;
//                        System.out.println("errorThreshold: " + errorThreshold);
                        if (surveyScores.size() < SURVEY_LIMIT && x < errorThreshold) {
                            if (surveyScores.size() == 1) {
                                Notification.show("There is only 1 survey to display",
                                                3000, Notification.Position.BOTTOM_START).
                                        addThemeVariants(NotificationVariant.LUMO_CONTRAST);
                            } else {
                                Notification.show("There are only " + surveyScores.size() +
                                                " surveys to display", 3000, Notification.Position.BOTTOM_START).
                                        addThemeVariants(NotificationVariant.LUMO_CONTRAST);
                            }
                        }
                        if (surveyScores.size() > SURVEY_LIMIT) {
                            if (currentIndex == 0) {
                                Notification.show("There are no more surveys left to display",
                                                3000, Notification.Position.BOTTOM_START).
                                        addThemeVariants(NotificationVariant.LUMO_CONTRAST);
                            }
                            if (currentIndex > 0 && buttonPosition == 0) {
                                Notification.show("Previous survey(s) available", 3000, Notification.Position.BOTTOM_START)
                                        .addThemeVariants(NotificationVariant.LUMO_CONTRAST);
                            }
                        }
                        if (currentIndex >= 0) {
                            bookMarkIndex = currentIndex;
                        }
                        System.out.println("bookmark is " + bookMarkIndex);
                        System.out.println("current index is " + currentIndex);
//                        if (currentIndex >= 0) {
//                            lastButtonPos = buttonPosition;
//                        }
                        lastButtonPos = buttonPosition;
                        //TODO: Update the apex chart right here when the user clicks
                    }
            ).addEventData("event.offsetX").addEventData("event.offsetY");
        }
        prev.addClickListener(e -> {
            next.setEnabled(true);
            if (surveyScores.size() > SURVEY_LIMIT &&
                    currentIndex > 0 && buttonPosition == 0) {
                buttonPosition = SURVEY_LIMIT;
            }
            if (buttonPosition != 0) {
                buttonPosition = buttonPosition - 1;

            } else {
                buttonPosition = SURVEY_LIMIT - 1;
            }
            if (currentIndex < 0) {
                buttonPosition = lastButtonPos;
            }
            if (currentIndex < 0) {
                currentIndex = bookMarkIndex;
            }
            if (currentIndex > 0) {
                currentIndex = currentIndex - 1;
            }
            System.out.println("after clicking prev, current index is: " + currentIndex);

            System.out.println("after hitting prev buttonPos is " + buttonPosition);
            if (surveyScores.size() <= SURVEY_LIMIT && currentIndex
                    >= 0 && currentIndex < surveyScores.size()) {
                setDate(Optional.of(surveyScores.get(currentIndex).getTimestamp()));
            }
            if (surveyScores.size() > SURVEY_LIMIT) {
                if (buttonPosition == SURVEY_LIMIT - 1) {
                    startingPoint = startingPoint - SURVEY_LIMIT;
                }
                if (currentIndex >= 0 && currentIndex < surveyScores.size()) {
                    setDate(Optional.of(surveyScores.get(currentIndex).getTimestamp()));
                }

            }
            if (buttonPosition == 0 && currentIndex > 0) {
                Notification.show("Previous survey(s) available", 3000, Notification.Position.BOTTOM_START)
                        .addThemeVariants(NotificationVariant.LUMO_CONTRAST);
            }
            if (currentIndex <= 0) {
                prev.setEnabled(false);
            }

            if (currentIndex == 0) {
                zeroIndexPos = buttonPosition;
                }
            bookMarkIndex = currentIndex;
            lastButtonPos = buttonPosition;
            //TODO: IMPLEMENT THE ERROR NOTIFICATIONS FOR WHEN THERE IS LESS THAN FIVE SURVEYS TO DISPLAY
        });

        next.addClickListener(e -> {
            prev.setEnabled(true);

            if (currentIndex == 0) {
                zeroIndexPos = 0;
            }
            if (currentIndex < 0) {
                currentIndex = bookMarkIndex;
            }
            if (currentIndex < surveyScores.size() - 1) {
                currentIndex = currentIndex + 1;
//                System.out.println("after clicking next, current index is: " + currentIndex);
            }
//            System.out.println("after hitting next buttonPos is " + buttonPosition);
//            System.out.println("after hitting next lastButtonPos is " + lastButtonPos);
            if (surveyScores.size() > SURVEY_LIMIT &&
                    currentIndex < surveyScores.size() - 1 && buttonPosition == SURVEY_LIMIT - 1) {
                //NOTE: is it problematic to hardcode 0?
                buttonPosition = 0;
            } else {
                buttonPosition = buttonPosition + 1;
            }

            if (surveyScores.size() <= SURVEY_LIMIT && currentIndex
                    >= 0 && currentIndex < surveyScores.size()) {
                setDate(Optional.of(surveyScores.get(currentIndex).getTimestamp()));
            }
            if (surveyScores.size() > SURVEY_LIMIT) {
					if (buttonPosition == 0 && lastButtonPos == SURVEY_LIMIT - 1) {
                        startingPoint = startingPoint + 5;;
					}
                setDate(Optional.of(surveyScores.get(currentIndex).getTimestamp()));
            }
            if (currentIndex >= (surveyScores.size() - 1)) {
                next.setEnabled(false);
            }
            lastButtonPos = buttonPosition;
        });

        ctx = canvas.getContext();
        ctx.setFillStyle(BACKGROUND_COLOR);
        /**
         * Builds the bar and fills the bar that the buttons are on (draws a rectangle)
         */
        ctx.fillRect(BUTTON_RADIUS, BUTTON_RADIUS - BUTTON_TRACK_WIDTH / 2, CANVAS_WIDTH - BUTTON_RADIUS * 2, BUTTON_TRACK_WIDTH);

        canvas.getElement().getStyle().set("align-self", "center"); // Avoid canvas scaling horizontally only
        horizontalLayout.add(prev, next);
        verticalLayout.add(canvas, horizontalLayout, label);
        verticalLayout.setHorizontalComponentAlignment(Alignment.CENTER, canvas, horizontalLayout, label);
        add(verticalLayout);

        prev.getElement().getStyle().set("background", "#F3F5F7");
        next.getElement().getStyle().set("background", "#F3F5F7");

        if (surveyScores.size() == 1 || surveyScores.size() == 0) {
            prev.setEnabled(false);
        }
        //Since the slider is constructed with the button all the way to the right, initially there is no next
        next.setEnabled(false);

        //TODO: add a click listener for the chevrons

        if (surveyScores.size() == 0) {
            for (int i = 0; i < SURVEY_LIMIT; i++) {
                drawButton(ctx, i, BACKGROUND_COLOR);
            }
            label.setText("There are no survey scores saved in the specified repository. Take a survey and save to view scores");
            label.getElement().getStyle().set("color", BUTTON_COLOR);
            canvas.getElement().addEventListener("click", event -> {
                Notification.show("There are no survey scores saved in the specified repository.",
                        5000, Notification.Position.BOTTOM_START).addThemeVariants(NotificationVariant.LUMO_ERROR);
            });
        } else {
            setDate(Optional.empty());
        }
        setWidth("100%");
    }

    public Button getPrev() {
        return prev;
    }
    public Button getNext() {
        return next;
    }
    public void setLabel(String text) {
        label.setText(text);
    }
    public int getDiff() {
        return diff;
    }
    /**
     * Set the date of the survey that was selected
     * @param timestamp the timestamp of the selected survey on the slider,
     * if no survey was selected, the slider will show the most recent survey.
     */
    public void setDate(Optional<String> timestamp) {
        int index = surveyScores.size() - 1;
        String value = surveyScores.get(index).getTimestamp();
        if (timestamp.isPresent()) {
            value = timestamp.get();
            for (SurveyScore score : surveyScores) {
                if (score.getTimestamp().equals(value)) {
                    index = surveyScores.indexOf(score);
                    break;
                }
            }
        }
        service.setDate(value, index);
        drawButtons(ctx, service, index);
    }

    private int buttonPosX(int idx) {
        int span = (CANVAS_WIDTH - (BUTTON_RADIUS * 2)) / (SURVEY_LIMIT - 1);
        return BUTTON_RADIUS + span * idx;
    }

    private void drawButton(CanvasRenderingContext2D ctx, int idx, String color) {
        ctx.beginPath();
        ctx.setFillStyle(color);
        ctx.arc(buttonPosX(idx), BUTTON_RADIUS, BUTTON_RADIUS, 0, 2 * Math.PI, false);
        ctx.fill();
    }

    /**
     * Draw the buttons with the correct button selected.
     *
     * @param ctx 2d drawing context
     * @param service the SurveyService instance
     * @param index the value on the slider to draw the button
     */
    /**
     * Here, find the number of histories instead of the number of scores and iterate
     * through the number of histories. Condition to fill in the button should be
     * whether the current history is equal to the new history.
     * current history needs to be configured to trigger a change in the spider branch
     */
    private void drawButtons(CanvasRenderingContext2D ctx, SurveyService service, int index) {

        if (surveyScores.size() >= SURVEY_LIMIT) {
            iterateHistoryOverLimit(ctx, service, index, SURVEY_LIMIT);
        } else {
            iterateHistoryUnderLimit(ctx, service, index, SURVEY_LIMIT);
        }
    }

    private void iterateHistoryOverLimit(CanvasRenderingContext2D ctx, SurveyService service, int index, int limit) {
        /*
           NOTE: Going backwards, so we can display the most recent surveys.
            The surveys are read and added to the list from earliest to most
            recent, so we must loop through the list backwards in order to
            access the most recent surveys first. For instance, if the list length is
            greater than the survey max, the most recent surveys will not be displayed.
         */
        for (int i = startingPoint - 1; startingPoint - i <= limit; i--) {
            //TODO: see if a locale can be passed into getTimestamp
//            System.out.println("starting point is: " + startingPoint);
//            System.out.println("i is: " + i);
            if (i >= 0 && surveyScores.get(i).getTimestamp().equals(service.getTimestamp(index))) {
//                System.out.println("index is: " + index);
//                System.out.println(currentIndex);
                drawButton(ctx, (SURVEY_LIMIT - (startingPoint - i)), BUTTON_COLOR);
                label.setText(surveyScores.get(i).getFriendlyTimestamp());
                label.getElement().getStyle().set("color", BUTTON_COLOR);
                //TODO: worry about apex chart implementation after PR
                //survey = scoreList.get(i).getSurvey();
                //setCurrentSurvey(survey);
            } else {
                drawButton(ctx, (SURVEY_LIMIT - (startingPoint - i)), BACKGROUND_COLOR);
            }
        }
    }

    private void iterateHistoryUnderLimit(CanvasRenderingContext2D ctx, SurveyService service, int index, int limit) {
        for (int i = surveyScores.size() - 1; i >= 0; i--) {
            if (surveyScores.get(i).getTimestamp().equals(service.getTimestamp(index))) {
                drawButton(ctx, ((SURVEY_LIMIT - surveyScores.size()) + i), BUTTON_COLOR);
                label.setText(surveyScores.get(i).getFriendlyTimestamp());
                label.getElement().getStyle().set("color", BUTTON_COLOR);
                //TODO: worry about apex chart implementation after PR
            } else {
                drawButton(ctx, ((SURVEY_LIMIT - surveyScores.size()) + i), BACKGROUND_COLOR);
            }
        }
        for (int i = SURVEY_LIMIT - surveyScores.size() - 1; i >= 0; i--) {
            drawButton(ctx, i, BACKGROUND_COLOR);
        }
    }
}
