package sample;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.Scanner;

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {


        Scanner myObj = new Scanner(System.in);  // Create a Scanner object
        System.out.println("Enter dimension of the game");

        int board_size = myObj.nextInt();  // Read user input

        System.out.println("Enter the number of agents");

        int nbAgent = myObj.nextInt();


        Environnement e = new sample.Environnement(board_size, board_size, nbAgent);

        System.out.println(e);

        GridPane gameBoard = new GridPane();
        gameBoard.setPrefSize(755, 755);


        Object[][] map = e.getMap();

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

            primaryStage.setTitle("Hello World!");

            StackPane root = new StackPane();
            root.getChildren().add(gameBoard);
            primaryStage.setScene(new Scene(root, 800, 800));
            primaryStage.show();
        }
    }


}
