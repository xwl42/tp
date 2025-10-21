package seedu.address.ui;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Simple window to display merged timeslot ranges in a readable timetable/list format.
 */
public class TimeslotsWindow {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    /**
     * Shows the merged timeslot ranges in a new window.
     * Each entry in {@code mergedRanges} should be a LocalDateTime[2] array: [start, end].
     */
    public static void showMerged(List<LocalDateTime[]> mergedRanges) {
        Stage stage = new Stage();
        stage.setTitle("Merged Timeslots");
        stage.initModality(Modality.NONE);

        BorderPane root = new BorderPane();
        root.setPadding(new Insets(10));

        Label header = new Label("Merged Timeslot Ranges");
        header.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        VBox contentBox = new VBox(8);
        contentBox.setPadding(new Insets(8));

        if (mergedRanges == null || mergedRanges.isEmpty()) {
            contentBox.getChildren().add(new Label("No timeslots found."));
        } else {
            ListView<String> listView = new ListView<>();
            for (LocalDateTime[] range : mergedRanges) {
                String line = String.format("%s  →  %s",
                        range[0].format(FORMATTER),
                        range[1].format(FORMATTER));
                listView.getItems().add(line);
            }
            listView.setPrefWidth(500);
            listView.setPrefHeight(Math.min(400, 24 * listView.getItems().size() + 20));
            ScrollPane sp = new ScrollPane(listView);
            sp.setFitToWidth(true);
            root.setCenter(sp);
        }

        VBox top = new VBox(header);
        top.setPadding(new Insets(0, 0, 8, 0));
        root.setTop(top);

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
