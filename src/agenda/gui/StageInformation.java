package agenda.gui;

import agenda.data.Artist;
import agenda.data.Show;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class StageInformation {
    public StageInformation(Show show) {
        //Setup for the sureStage with buttons, labels, text fields, etc.
        Stage informationStage = new Stage();
        informationStage.setTitle("Show information");

        Label artistLabel = new Label("Artist: ");
        Label genreLabel = new Label("Genre: ");
        Label popularityLabel = new Label("Popularity: ");
        Label stageLabel = new Label("Stage:");
        Label beginTimeLabel = new Label("BeginTime: ");
        Label endTimeLabel = new Label("EndTime: ");

        //Gets all the artist in 1 string, so it can be displayed in 1 Label
        String artists = "";
        for(Artist artist : show.getArtistA()) {
            artists += artist.getName() + ", ";
        }
        //The , for the last artist is removed
        artists = artists.substring(0, artists.length()-2);

        Label artistInformationLabel = new Label(artists);
        Label genreInformationLabel = new Label(show.getGenre());
        Label popularityInformationLabel = new Label(show.getPopularity() + "");
        Label stageInformationLabel = new Label(show.getStage().getName() + "");
        Label beginTimeInformationLabel = new Label(show.getStartTime() + "");
        Label endTimeInformationLabel = new Label(show.getEndTime() + "");

        Button doneButton = new Button("Done");

        VBox labelBox = new VBox();
        labelBox.getChildren().addAll(artistLabel, genreLabel, popularityLabel, stageLabel, beginTimeLabel, endTimeLabel);
        labelBox.setSpacing(20);
        VBox informationBox = new VBox();
        informationBox.getChildren().addAll(artistInformationLabel, genreInformationLabel, popularityInformationLabel, stageInformationLabel, beginTimeInformationLabel, endTimeInformationLabel);
        informationBox.setSpacing(20);

        HBox hBox = new HBox();
        hBox.getChildren().addAll(labelBox, informationBox);
        hBox.setSpacing(20);

        FlowPane root = new FlowPane();
        root.setAlignment(Pos.CENTER);
        Scene scene = new Scene(root, 280, 250);

        root.getChildren().addAll(hBox);

        informationStage.setScene(scene);
        informationStage.show();
    }
}
