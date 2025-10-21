package seedu.address.ui;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
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
    private static final String[] DAY_LABELS = {"MON", "TUE", "WED", "THU", "FRI", "SAT", "SUN"};

    /**
     * Shows the merged timeslot ranges in a new window laid out as a timetable.
     * Each entry in {@code mergedRanges} should be a LocalDateTime[2] array: [start, end].
     */
    public static void showMerged(List<LocalDateTime[]> mergedRanges) {
        Stage stage = new Stage();
        stage.setTitle("Next Week Schedule");
        stage.initModality(Modality.NONE);

        BorderPane root = new BorderPane();
        root.setPadding(new Insets(10));

        Label header = new Label("Timetable");
        header.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        BorderPane.setAlignment(header, Pos.CENTER);
        root.setTop(header);

        // Hours header (top row)
        HBox hoursHeader = buildHoursHeader();
        hoursHeader.setPadding(new Insets(8, 0, 8, 80)); // leave space for day labels
        root.setTop(new VBox(header, hoursHeader));

        // Body: one row per day with timeline pane
        VBox body = new VBox(6);
        body.setPadding(new Insets(8));
        for (int i = 0; i < DAY_LABELS.length; i++) {
            DayOfWeek dow = DayOfWeek.of(((i + 1) % 7 == 0) ? 7 : (i + 1)); // 1=MON ... 7=SUN
            HBox row = buildDayRow(DAY_LABELS[i], dow, mergedRanges);
            body.getChildren().add(row);
        }

        ScrollPane sp = new ScrollPane(body);
        sp.setFitToWidth(true);
        sp.setPrefViewportWidth(Math.min(TIMELINE_WIDTH + 120, 1000));
        sp.setPrefViewportHeight(ROW_HEIGHT * 5 + 200);

        root.setCenter(sp);

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    // build the top hours labels and vertical tick lines
    private static HBox buildHoursHeader() {
        HBox header = new HBox();
        header.setAlignment(Pos.CENTER_LEFT);
        header.setSpacing(0);

        Pane timeline = new Pane();
        timeline.setPrefSize(TIMELINE_WIDTH, 24);

        for (int h = 0; h <= HOURS; h++) {
            double x = h * 60 * PIXELS_PER_MINUTE;
            Line tick = new Line(x, 0, x, 24);
            // softer tick color
            tick.setStroke(Color.web("#c5d0da"));
            tick.setStrokeWidth(1);
            timeline.getChildren().add(tick);

            if (h < HOURS) {
                Label hourLabel = new Label(String.format("%02d:00", START_HOUR + h));
                hourLabel.setStyle("-fx-text-fill: #556070; -fx-font-size: 11px;");
                hourLabel.setLayoutX(x + 4);
                hourLabel.setLayoutY(2);
                timeline.getChildren().add(hourLabel);
            }
        }

        header.getChildren().add(timeline);
        return header;
    }

    // build one row: day label + timeline pane with colored blocks
    private static HBox buildDayRow(String dayLabelText, DayOfWeek dow, List<LocalDateTime[]> ranges) {
        HBox row = new HBox();
        row.setSpacing(8);
        row.setAlignment(Pos.CENTER_LEFT);

        Label dayLabel = new Label(dayLabelText);
        dayLabel.setMinWidth(60);
        dayLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: #333333;");

        Pane timeline = new Pane();
        // light background and subtle border for low contrast
        timeline.setStyle("-fx-background-color: #fbfcfd; -fx-border-color: #e6eef6; -fx-border-radius: 4;");
        timeline.setPrefSize(TIMELINE_WIDTH, ROW_HEIGHT);
        timeline.setOpacity(0.98);

        // add hour divider lines lightly
        for (int h = 0; h <= HOURS; h++) {
            double x = h * 60 * PIXELS_PER_MINUTE;
            Line divider = new Line(x, 0, x, ROW_HEIGHT);
            divider.setStroke(Color.web("#e6eef6"));
            divider.setStrokeWidth(0.8);
            timeline.getChildren().add(divider);
        }

        // Place a block for each visible portion of each timeslot that intersects this day and the displayed window
        if (ranges != null) {
            for (LocalDateTime[] range : ranges) {
                LocalDateTime start = range[0];
                LocalDateTime end = range[1];
                if (start == null || end == null) {
                    continue;
                }

                // iterate each date the range spans and render the portion that falls on that date & within START_HOUR..END_HOUR
                LocalDate current = start.toLocalDate();
                LocalDate endDate = end.toLocalDate();
                while (!current.isAfter(endDate)) {
                    if (current.getDayOfWeek() == dow) {
                        LocalDateTime dayWindowStart = LocalDateTime.of(current, LocalTime.of(START_HOUR, 0));
                        // Use midnight of the next day as the exclusive end to avoid LocalTime.of(24,0) error
                        LocalDateTime dayWindowEnd = LocalDateTime.of(current.plusDays(1), LocalTime.MIDNIGHT);

                        LocalDateTime renderStart = start.isAfter(dayWindowStart) ? start : dayWindowStart;
                        LocalDateTime renderEnd = end.isBefore(dayWindowEnd) ? end : dayWindowEnd;

                        if (renderStart.isBefore(renderEnd)) {
                            double minutesFromStart = (renderStart.getHour() * 60 + renderStart.getMinute()) - START_HOUR * 60;
                            double durationMinutes = (renderEnd.getHour() * 60 + renderEnd.getMinute())
                                    - (renderStart.getHour() * 60 + renderStart.getMinute());

                            double x = Math.max(0, minutesFromStart * PIXELS_PER_MINUTE);
                            double w = Math.max(4, durationMinutes * PIXELS_PER_MINUTE);

                            Rectangle block = new Rectangle(x, 8, w, ROW_HEIGHT - 16);
                            block.setArcWidth(8);
                            block.setArcHeight(8);
                            block.setFill(pickColorForDay(dow));
                            // softer stroke for blocks
                            block.setStroke(Color.web("#d0d7de"));
                            block.setStrokeWidth(1);

                            String labelText = String.format("%s - %s",
                                    renderStart.format(DateTimeFormatter.ofPattern("HH:mm")),
                                    renderEnd.format(DateTimeFormatter.ofPattern("HH:mm")));
                            Label bl = new Label(labelText);
                            bl.setStyle("-fx-text-fill: #1f2a2f; -fx-font-size: 11px;");
                            bl.setLayoutX(x + 6);
                            bl.setLayoutY(12);

                            Tooltip tooltip = new Tooltip(start.format(FORMATTER) + "\n" + end.format(FORMATTER));
                            Tooltip.install(block, tooltip);
                            Tooltip.install(bl, tooltip);

                            timeline.getChildren().addAll(block, bl);
                        }
                    }
                    current = current.plusDays(1);
                }
            }
        }

        row.getChildren().addAll(dayLabel, timeline);
        return row;
    }

    // crude check for ranges that span the circular week (rare) - keep conservative
    private static boolean spansDay(DayOfWeek dow, LocalDateTime start, LocalDateTime end) {
        // if start is before this dow and end is after this dow (in week order)
        int s = start.getDayOfWeek().getValue();
        int e = end.getDayOfWeek().getValue();
        int d = dow.getValue();
        if (s <= e) {
            return s <= d && d <= e;
        } else {
            // wraps week boundary
            return d >= s || d <= e;
        }
    }

    // pick a pleasant color for the day (cycled)
    private static Color pickColorForDay(DayOfWeek dow) {
        // Pastel/soft palette for reduced contrast and easier viewing
        return switch (dow) {
        case MONDAY -> Color.web("#9ec5ff");    // soft blue
        case TUESDAY -> Color.web("#b8e6d8");   // mint
        case WEDNESDAY -> Color.web("#ffd9a8"); // peach
        case THURSDAY -> Color.web("#d6d5ff");  // lavender
        case FRIDAY -> Color.web("#f6cfe6");    // light pink
        case SATURDAY -> Color.web("#d9f5d7");  // light green
        case SUNDAY -> Color.web("#f7eddc");    // light cream
        default -> Color.web("#cbd5df");
        };
    }
}
