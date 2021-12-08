package sample;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.Scanner;

public class EnvironnementUI extends Application {
    private GridPane gameBoard = new GridPane();
    private StackPane root;
    private Environnement environnement;


    public EnvironnementUI() {
        gameBoard.setPrefSize(755, 755);
    }

    @Override
    public void init() {

        Scanner myObj = new Scanner(System.in);  // Create a Scanner object
        System.out.println("Enter dimension of the game");

        int board_size = myObj.nextInt();  // Read user input

        System.out.println("Enter the number of agents");

        int nbAgent = myObj.nextInt();

        environnement = new sample.Environnement(board_size, board_size, nbAgent);


        root = new StackPane();
        gameBoard.setAlignment(Pos.TOP_CENTER);
        root.getChildren().add(gameBoard);


        textureInit();

    }

    public void textureInit() {
        Object[][] map = environnement.getMap();
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map.length; j++) {

                Rectangle tile = new Rectangle(160, 160);

                tile.setStroke(Color.BLACK);
                if (map[i][j] instanceof Agent) {
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
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        Scene scene = new Scene(root, 800, 800);

        Thread thread = new Thread(() -> {
            Runnable updater = this::textureInit;

            while (!environnement.keepGoing()) {
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
