package seedu.address.ui;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.NumberBinding;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Timetable-style window to display merged timeslot ranges as horizontal blocks.
 * The timeline spans from START_HOUR (inclusive) to END_HOUR (exclusive).
 *
 * Each LocalDateTime[] in mergedRanges is interpreted as {start, end}.
 */
public class TimeslotsWindow {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    private static final int START_HOUR = 8;
    private static final int END_HOUR = 24;
    private static final int HOURS = END_HOUR - START_HOUR;
    private static final double PIXELS_PER_MINUTE = 1.0; // 60 minutes => 60px per hour (adjust if needed)
    private static final double TIMELINE_WIDTH = HOURS * 60 * PIXELS_PER_MINUTE; // total px width for timeline
    private static final double ROW_HEIGHT = 64;

    // New shared layout constants to ensure exact alignment between header and rows
    private static final double DAY_LABEL_WIDTH = 90; // left column width used by day labels
    private static final double ROW_SPACING = 8;     // spacing used between day label and timeline
    private static final double BODY_PADDING = 8;   // must match VBox body padding used in renderWeek

    /**
     * Shows the merged timeslot ranges in a new window laid out as a timetable.
     * Each entry in {@code mergedRanges} should be a LocalDateTime[2] array: [start, end].
     */
    public static void showMerged(List<LocalDateTime[]> mergedRanges) {
        Stage stage = new Stage();
        stage.setTitle("Consultation Schedule");
        stage.initModality(Modality.NONE);

        BorderPane root = new BorderPane();
        root.setPadding(new Insets(10));

        Label header = new Label("Timetable");
        header.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        BorderPane.setAlignment(header, Pos.CENTER);

        // Navigation controls
        Button nextWeekBtn = new Button("Next Week");
        Button prevWeekBtn = new Button("Previous Week");

        // Determine initial week start: use earliest timeslot start (its Monday) if available,
        // otherwise fallback to current week's Monday.
        LocalDate[] weekStartRef = new LocalDate[1];
        weekStartRef[0] = Optional.ofNullable(mergedRanges)
                .filter(l -> !l.isEmpty())
                .flatMap(l -> l.stream()
                        .map(r -> r[0]) // start LocalDateTime
                        .filter(Objects::nonNull)
                        .map(LocalDateTime::toLocalDate)
                        .min(LocalDate::compareTo)
                )
                .orElse(LocalDate.now())
                .with(DayOfWeek.MONDAY);

        // top row: header + spacer + navigation buttons
        HBox topRow = new HBox();
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        topRow.setAlignment(Pos.CENTER_LEFT);
        topRow.setSpacing(ROW_SPACING);
        topRow.getChildren().addAll(header, spacer, prevWeekBtn, nextWeekBtn);

        // timeline width property shared by all timeline panes so they resize together
        DoubleProperty timelineWidth = new SimpleDoubleProperty(TIMELINE_WIDTH);

        // hours header (kept same as existing) - now takes timelineWidth
        HBox hoursHeader = buildHoursHeader(timelineWidth);
        // no padding here — buildHoursHeader now inserts a left placeholder matching the day label column
        // so ticks align exactly with the timelines.

        // render initial week
        renderWeek(root, weekStartRef[0], mergedRanges, topRow, hoursHeader, timelineWidth);

        // button actions: adjust weekStart and re-render
        nextWeekBtn.setOnAction(e -> {
            weekStartRef[0] = weekStartRef[0].plusWeeks(1);
            renderWeek(root, weekStartRef[0], mergedRanges, topRow, hoursHeader, timelineWidth);
        });
        prevWeekBtn.setOnAction(e -> {
            weekStartRef[0] = weekStartRef[0].minusWeeks(1);
            renderWeek(root, weekStartRef[0], mergedRanges, topRow, hoursHeader, timelineWidth);
        });

        Scene scene = new Scene(root);
        stage.setScene(scene);

        // allow user to resize the window
        stage.setResizable(true);
        // set sensible minimum size so layout remains usable
        stage.setMinWidth(600);
        stage.setMinHeight(400);

        stage.show();

        // Bind timelineWidth to scene width (minimum remains TIMELINE_WIDTH). Subtract left label area and padding.
        timelineWidth.bind(Bindings.createDoubleBinding(
                () -> Math.max(scene.getWidth() - 120, TIMELINE_WIDTH),
                scene.widthProperty()));

        // After showing, bind the ScrollPane viewport to the scene size so it grows/shrinks with the window.
        if (root.getCenter() instanceof ScrollPane) {
            ScrollPane sp = (ScrollPane) root.getCenter();
            sp.prefViewportWidthProperty().bind(scene.widthProperty().subtract(120));
            sp.prefViewportHeightProperty().bind(scene.heightProperty().subtract(140));
        }
    }

    // Renders the given week starting at weekStart (Monday) into root using provided topRow and hoursHeader nodes.
    private static void renderWeek(BorderPane root, LocalDate weekStart, List<LocalDateTime[]> ranges,
            HBox topRow, HBox hoursHeader, DoubleProperty timelineWidth) {
        // Body: one row per day with timeline pane for the week starting at weekStart
        VBox body = new VBox(6);
        body.setPadding(new Insets(8));
        for (int i = 0; i < 7; i++) { // 7 days in a week
            LocalDate date = weekStart.plusDays(i);
            HBox row = buildDayRowForDate(date, ranges, timelineWidth);
            body.getChildren().add(row);
        }

        ScrollPane sp = new ScrollPane(body);
        sp.setFitToWidth(true);
        sp.setPrefViewportWidth(Math.min(TIMELINE_WIDTH + 120, 1000));
        sp.setPrefViewportHeight(ROW_HEIGHT * 5 + 200);

        root.setTop(new VBox(topRow, hoursHeader));
        root.setCenter(sp);
    }

    // build the top hours labels and vertical tick lines; timelineWidth drives the timeline Pane width
    private static HBox buildHoursHeader(DoubleProperty timelineWidth) {
        HBox header = new HBox();
        header.setAlignment(Pos.CENTER_LEFT);
        header.setSpacing(0);

        // left placeholder to align with dayLabel (minWidth = DAY_LABEL_WIDTH + ROW_SPACING + BODY_PADDING)
        Region leftGap = new Region();
        leftGap.setMinWidth(DAY_LABEL_WIDTH + ROW_SPACING + BODY_PADDING);
        leftGap.setPrefWidth(DAY_LABEL_WIDTH + ROW_SPACING + BODY_PADDING);
        header.getChildren().add(leftGap);

        Pane timeline = new Pane();
        // bind timeline width to the shared property so it grows/shrinks with the window
        timeline.prefWidthProperty().bind(timelineWidth);
        timeline.setPrefHeight(24);

        for (int h = 0; h <= HOURS; h++) {
            // position ratio relative to base TIMELINE_WIDTH so positions scale
            double baseX = h * 60 * PIXELS_PER_MINUTE;
            double ratio = baseX / TIMELINE_WIDTH; // baseX / TIMELINE_WIDTH in [0,1]
            NumberBinding xBind = timelineWidth.multiply(ratio);

            Line tick = new Line();
            tick.startXProperty().bind(xBind);
            tick.endXProperty().bind(xBind);
            tick.setStartY(0);
            tick.setEndY(24);
            // softer tick color
            tick.setStroke(Color.web("#c5d0da"));
            tick.setStrokeWidth(1);
            timeline.getChildren().add(tick);

            if (h < HOURS) {
                Label hourLabel = new Label(String.format("%02d:00", START_HOUR + h));
                hourLabel.setStyle("-fx-text-fill: #556070; -fx-font-size: 11px;");
                hourLabel.layoutXProperty().bind(xBind.add(4));
                hourLabel.setLayoutY(2);
                timeline.getChildren().add(hourLabel);
            }
        }

        header.getChildren().add(timeline);
        return header;
    }

    // New: buildDayRowForDate renders a row for a given LocalDate (shows label with date)
    private static HBox buildDayRowForDate(LocalDate date, List<LocalDateTime[]> ranges,
            DoubleProperty timelineWidth) {
        HBox row = new HBox();
        row.setSpacing(ROW_SPACING);
        row.setAlignment(Pos.CENTER_LEFT);
        // set the same padding here as used when creating the body so layout offsets match
        row.setPadding(new Insets(0, 0, 0, 0));

        String dayLabelText = String.format("%s %s", date.getDayOfWeek().toString().substring(0, 3),
                date.format(DateTimeFormatter.ofPattern("MM/dd")));
        Label dayLabel = new Label(dayLabelText);
        dayLabel.setMinWidth(DAY_LABEL_WIDTH);
        dayLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: #333333;");

        Pane timeline = new Pane();
        // light background and subtle border for low contrast
        timeline.setStyle("-fx-background-color: #fbfcfd; -fx-border-color: #e6eef6; -fx-border-radius: 4;");
        // bind timeline width to the shared property so it scales with the window width
        timeline.prefWidthProperty().bind(timelineWidth);
        timeline.setPrefHeight(ROW_HEIGHT);
        timeline.setOpacity(0.98);

        // add hour divider lines lightly (positions scale with timelineWidth)
        for (int h = 0; h <= HOURS; h++) {
            double baseX = h * 60 * PIXELS_PER_MINUTE;
            double ratio = baseX / TIMELINE_WIDTH;
            NumberBinding xBind = timelineWidth.multiply(ratio);

            Line divider = new Line();
            divider.startXProperty().bind(xBind);
            divider.endXProperty().bind(xBind);
            divider.setStartY(0);
            divider.setEndY(ROW_HEIGHT);
            divider.setStroke(Color.web("#e6eef6"));
            divider.setStrokeWidth(0.8);
            timeline.getChildren().add(divider);
        }

        // Place a block for each visible portion of each timeslot that intersects this date and the displayed window
        if (ranges != null) {
            for (LocalDateTime[] range : ranges) {
                LocalDateTime start = range[0];
                LocalDateTime end = range[1];
                if (start == null || end == null) {
                    continue;
                }

                // Compute the portion of [start,end) that falls on 'date'
                LocalDateTime dayWindowStart = LocalDateTime.of(date, LocalTime.of(START_HOUR, 0));
                LocalDateTime dayWindowEnd = LocalDateTime.of(date.plusDays(1), LocalTime.MIDNIGHT);

                LocalDateTime renderStart = start.isAfter(dayWindowStart) ? start : dayWindowStart;
                LocalDateTime renderEnd = end.isBefore(dayWindowEnd) ? end : dayWindowEnd;

                if (renderStart.isBefore(renderEnd)) {
                    double minutesFromStart = (renderStart.getHour() * 60 + renderStart.getMinute()) - START_HOUR * 60;
                    double durationMinutes = (renderEnd.getHour() * 60 + renderEnd.getMinute())
                            - (renderStart.getHour() * 60 + renderStart.getMinute());

                    // Compute ratios relative to base timeline so they scale with timelineWidth
                    double xRatio = (minutesFromStart * PIXELS_PER_MINUTE) / TIMELINE_WIDTH;
                    double wRatio = (durationMinutes * PIXELS_PER_MINUTE) / TIMELINE_WIDTH;

                    NumberBinding xBind = timelineWidth.multiply(xRatio);
                    NumberBinding wBind = timelineWidth.multiply(wRatio);

                    Rectangle block = new Rectangle();
                    // bind position and size so block scales with timelineWidth
                    block.xProperty().bind(xBind);
                    block.yProperty().set(8);
                    block.widthProperty().bind(wBind);
                    block.setHeight(ROW_HEIGHT - 16);
                    block.setArcWidth(8);
                    block.setArcHeight(8);
                    block.setFill(pickColorForDay(date.getDayOfWeek()));
                    // softer stroke for blocks
                    block.setStroke(Color.web("#d0d7de"));
                    block.setStrokeWidth(1);

                    String labelText = String.format("%s - %s",
                            renderStart.format(DateTimeFormatter.ofPattern("HH:mm")),
                            renderEnd.format(DateTimeFormatter.ofPattern("HH:mm")));
                    Label bl = new Label(labelText);
                    bl.setStyle("-fx-text-fill: #1f2a2f; -fx-font-size: 11px;");
                    // bind label position to block.x + 6 so it follows scaling
                    bl.layoutXProperty().bind(xBind.add(6));
                    bl.setLayoutY(12);

                    Tooltip tooltip = new Tooltip(start.format(FORMATTER) + "\n" + end.format(FORMATTER));
                    Tooltip.install(block, tooltip);
                    Tooltip.install(bl, tooltip);

                    timeline.getChildren().addAll(block, bl);
                }
            }
        }

        row.getChildren().addAll(dayLabel, timeline);
        return row;
    }

    // pick a pleasant color for the day (cycled)
    private static Color pickColorForDay(DayOfWeek dow) {
        // Pastel/soft palette for reduced contrast and easier viewing
        return switch (dow) {
        case MONDAY -> Color.web("#9ec5ff"); // soft blue
        case TUESDAY -> Color.web("#b8e6d8"); // mint
        case WEDNESDAY -> Color.web("#ffd9a8"); // peach
        case THURSDAY -> Color.web("#d6d5ff"); // lavender
        case FRIDAY -> Color.web("#f6cfe6"); // light pink
        case SATURDAY -> Color.web("#d9f5d7"); // light green
        case SUNDAY -> Color.web("#f7eddc"); // light cream
        default -> Color.web("#cbd5df");
        };
    }
}
