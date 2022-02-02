package sample;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.Window;

public class EnvironnementUI extends Application {
    private final GridPane grid = new GridPane();
    private StackPane root;
    private StackPane root2;
    private Environnement environnement;
    private boolean startGame = false;

    @Override
    public void init() {

        root = new StackPane();
        root2 = new StackPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        grid.setAlignment(Pos.TOP_CENTER);
        root.getChildren().add(grid);

        Text scenetitle = new Text("Welcome to the Taquin game");
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(scenetitle, 0, 0, 2, 1);

        Label gameDimension = new Label("Enter dimension of the game:");
        grid.add(gameDimension, 0, 1);

        TextField gameDimensionText = new TextField();
        grid.add(gameDimensionText, 1, 1);

        Label nbAgents = new Label("Enter the number of agents:");
        grid.add(nbAgents, 0, 2);

        TextField nbAgentsText = new TextField();
        grid.add(nbAgentsText, 1, 2);


        Button btn = new Button("Validate");
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(btn);
        grid.add(hbBtn, 1, 4);

        final Text actiontarget = new Text();
        grid.add(actiontarget, 1, 6);

        btn.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e) {
                if (gameDimensionText.getText().isEmpty()) {
                    showAlert(Alert.AlertType.ERROR, grid.getScene().getWindow(),
                            "Form Error!", "Please enter dimension of the game");
                    return;
                }
                if (nbAgentsText.getText().isEmpty()) {
                    showAlert(Alert.AlertType.ERROR, grid.getScene().getWindow(),
                            "Form Error!", "Please enter the number of agents");
                    return;
                }
                int nbAgent = Integer.parseInt(nbAgentsText.getText());
                int board_size = Integer.parseInt(gameDimensionText.getText());  // Read user input
                startGame = true;
                environnement = new sample.Environnement(board_size, board_size, nbAgent);

                Stage secondStage = new Stage();

                Scene scene2 = new Scene(root2, 800, 800);

                secondStage.setTitle("Final");

                secondStage.setScene(scene2);
                secondStage.show();
                
            }
        });
    }

    public void textureInit() {

        if (startGame) {
            GridPane gameBoard = new GridPane();
            gameBoard.setPrefSize(755, 755);
            gameBoard.setAlignment(Pos.TOP_CENTER);
            root.getChildren().add(gameBoard);
            Object[][] map = environnement.getMap();
            for (int i = 0; i < map.length; i++) {
                for (int j = 0; j < map.length; j++) {

                    Rectangle tile = new Rectangle(160, 160);

                    tile.setStroke(Color.BLACK);
                    if (map[i][j] instanceof Agent) {
                        if (((Agent) map[i][j]).isWellPlaced())
                            tile.setFill(Color.BLUE);
                        else
                            tile.setFill(Color.BURLYWOOD);
                        Text text = new Text();
                        text.setFont(Font.font(40));
                        gameBoard.add(new StackPane(tile, text), j, i);

                        text.setText(Character.toString(((Agent) map[i][j]).getLettre()));
                        text.setFill(Color.BLACK);
                    } else {
                        tile.setFill(Color.BEIGE);

                        gameBoard.add(tile, j, i);
                    }
                }
            }


            GridPane gameBoard2 = new GridPane();
            gameBoard2.setPrefSize(755, 755);
            gameBoard2.setAlignment(Pos.TOP_CENTER);
            root2.getChildren().add(gameBoard2);
            Object[][] mapFinale = environnement.getMapFinale();
            for (int i = 0; i < mapFinale.length; i++) {
                for (int j = 0; j < mapFinale.length; j++) {

                    Rectangle tile = new Rectangle(160, 160);

                    tile.setStroke(Color.BLACK);
                    if (mapFinale[i][j] instanceof Agent) {
                        if (((Agent) mapFinale[i][j]).isWellPlaced())
                            tile.setFill(Color.BLUE);
                        else
                            tile.setFill(Color.BURLYWOOD);
                        Text text = new Text();
                        text.setFont(Font.font(40));
                        gameBoard2.add(new StackPane(tile, text), j, i);

                        text.setText(Character.toString(((Agent) mapFinale[i][j]).getLettre()));
                        text.setFill(Color.BLACK);
                    } else {
                        tile.setFill(Color.BEIGE);

                        gameBoard2.add(tile, j, i);
                    }
                }
            }
        }
    }

    private void showAlert(Alert.AlertType alertType, Window owner, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.initOwner(owner);
        alert.show();
    }

    private boolean isFinished() {
        if (environnement == null)
            return false;

        else return environnement.isFinished();
    }

    @Override
    public void start(Stage primaryStage) {


        Scene scene = new Scene(root, 800, 800);

        Thread thread = new Thread(() -> {
            Runnable updater = this::textureInit;

            while (!isFinished()) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                }

                Platform.runLater(updater);
            }
        });

        thread.setDaemon(true);
        thread.start();
        primaryStage.setTitle("Taquin Game");

        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
