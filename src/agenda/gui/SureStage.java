/**
 * The SureStage is a pop-up in the GUI that comes up when pressing a "done" button in the delete pop-up, giving you an extra chance to cancel if you selected the wrong show to delete
 * @param index the index is the position of the selected show in the arrayList
 */
package agenda.gui;

import agenda.data.DataStore;
import agenda.data.Deserializer;
import agenda.data.Serializer;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import javax.xml.crypto.Data;

public class SureStage {
    Deserializer deserializer = new Deserializer();
    Serializer serializer = new Serializer();

    SureStage(int artistIndex, int stageIndex){
        //Setup for the sureStage with buttons, labels, text fields, etc.
        Stage delStage = new Stage();
        delStage.setTitle("Delete show");

        FlowPane root = new FlowPane();
        root.setAlignment(Pos.CENTER);
        Scene scene = new Scene(root, 300, 200);

        Label delPopupLabel = new Label("Are you sure?");
        Button yesButton = new Button("Yes");
        Button noButton = new Button("No");

        yesButton.setOnAction(e -> {
            //Since we don't need an index when we want to delete all elements, we replace it with -1 or DELETE_ALL
            if (artistIndex == DeleteStage.DELETE_ALL && stageIndex == DeleteStage.DELETE_ALL){    serializer.Clear();
                DataStore.setShowsA(this.deserializer.Read(Serializer.SHOWS));
            //if nothing is filled in the stage field, we know we need to delete an artist
            }else if(stageIndex == 0) {
                //If the dataStore file is not already empty, we remove the show that was selected in DeleteStage
                if (!deserializer.Read(Serializer.ARTISTS).isEmpty()){
                    DataStore.setShowsA(this.deserializer.Read(Serializer.SHOWS));
                }
                DataStore.getShowsA().remove(artistIndex-1);
                serializer.Write(DataStore.getShowsA(), Serializer.SHOWS);
            }
            //If nothing is filled in the artist field, we know we need to delete a stage
            else if(artistIndex == 0) {
                if(!this.deserializer.ReadStages().isEmpty()) {
                    DataStore.setStages(this.deserializer.Read(Serializer.STAGES));
                }
                DataStore.getStages().remove(stageIndex-1);
                this.serializer.Write(DataStore.getStages(), Serializer.STAGES);
            }
            //When confirmed, close the deleteStage and the show will be deleted
            delStage.close();
        });

        //When pressed on the "no" button, the desired show or stage will not be deleted from the existing shows
        noButton.setOnAction(e -> {
            delStage.close();
        });

        VBox delBox = new VBox();
        HBox delBoxButton = new HBox();
        delBoxButton.getChildren().addAll(yesButton, noButton);
        delBoxButton.setSpacing(10);
        delBox.getChildren().addAll(delPopupLabel, delBoxButton);
        delBox.setSpacing(20);

        root.getChildren().addAll(delBox);
        delStage.setScene(scene);
        delStage.show();
    }
}
