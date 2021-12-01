package com.example;

import java.util.Random;
import java.util.Vector;
import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

public class HelloFx extends Application {

    public static FlowPane flow;
    public static FlowPane boardback;
    public static Scene scene;
    public static VBox diffculty;
    public static VBox root;
    public static VBox dsp;
    protected static Checkerboard checkerboard;
    // public Boolean playerControl;
    public Boolean playerControl = true;

    // Parameter for Minmax searching
    public static int limit = 0;

    // Interface initiate
    public void start(Stage stage) {

        Label board_digit = new Label("");
        diffculty = new VBox(20, new Label("Diffculty: Random"));
        root = new VBox(20, board_digit);
        dsp = new VBox(20, new Label(""));

        boardback = new FlowPane();
        Image img = new Image(HelloFx.class.getResourceAsStream("8x8_checkered_board.png"));

        // Background setting(Checkerboard as the background)
        BackgroundSize size = new BackgroundSize(500, 500, false, false, true, false);
        BackgroundImage bimg = new BackgroundImage(img, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT, size);
        Background bground = new Background(bimg);
        boardback.setBackground(bground);
        boardback.setMinWidth(400);

        // Adding block and pawns according to checkerboard
        checkerboard = new Checkerboard(false, playerControl);
        boardUpdate(checkerboard);

        Menu menu = new Menu("Menu");
        MenuItem Reset = new MenuItem("Reset");
        MenuItem HintMode = new MenuItem("HintMode");

        menu.getItems().add(Reset);
        menu.getItems().add(HintMode);
        EventHandler<ActionEvent> setReset = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                playerControl = true;
                checkerboard = new Checkerboard(false, playerControl);
                boardUpdate(checkerboard);
            }
        };

        Reset.setOnAction(setReset);

        Menu diff = new Menu("Difficulty");
        MenuItem Random = new MenuItem("Random");
        MenuItem Normal = new MenuItem("Normal");
        MenuItem Slightly_Hard = new MenuItem("Slightly_Hard");
        MenuItem Hard = new MenuItem("Hard");
        MenuItem ExTraHard = new MenuItem("ExTraHard");

        diff.getItems().add(Random);
        diff.getItems().add(Normal);
        diff.getItems().add(Slightly_Hard);
        diff.getItems().add(Hard);
        diff.getItems().add(ExTraHard);
        EventHandler<ActionEvent> setRandom = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {

                diffculty.getChildren().clear();
                diffculty.getChildren().add(new Label("Diffculty: Random"));
                limit = 0;
            }
        };
        EventHandler<ActionEvent> setNormal = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                diffculty.getChildren().clear();
                diffculty.getChildren().add(new Label("Diffculty: Normal"));
                limit = 10 * 10;
            }
        };
        EventHandler<ActionEvent> setSlightyHard = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                diffculty.getChildren().clear();
                diffculty.getChildren().add(new Label("Diffculty: Hard"));
                limit = 100 * 100;
            }
        };
        EventHandler<ActionEvent> setHard = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                diffculty.getChildren().clear();
                diffculty.getChildren().add(new Label("Diffculty: Hard"));
                limit = 1000 * 1000;
            }
        };
        EventHandler<ActionEvent> setExtraHard = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                diffculty.getChildren().clear();
                diffculty.getChildren().add(new Label("Diffculty: Hard"));
                limit = 10000 * 10000;
            }
        };

        Random.setOnAction(setRandom);
        Normal.setOnAction(setNormal);
        Slightly_Hard.setOnAction(setSlightyHard);
        Hard.setOnAction(setHard);
        ExTraHard.setOnAction(setExtraHard);

        MenuBar menuBar = new MenuBar();
        menuBar.getMenus().add(menu);
        menuBar.getMenus().add(diff);

        VBox main = new VBox(menuBar);

        VBox logAndButton = new VBox(20, diffculty, root, dsp);

        // Version and digit
        flow = new FlowPane();
        // flow.getChildren().add(menuBar);
        flow.getChildren().add(boardback);
        // flow.getChildren().add(reset);
        flow.getChildren().add(logAndButton);
        flow.setHgap(10);
        flow.setVgap(10);
        main.getChildren().add(flow);

        scene = new Scene(main, 550, 430);
        // scene.getStylesheets().add(HelloFx.class.getResource("styles.css").toExternalForm());
        stage.setScene(scene);
        stage.setTitle("MinMaxAB Checkersgame");
        stage.show();
    }

    // Boardback update part
    public void boardUpdate(Checkerboard checkerboard) {
        int[][] list = checkerboard.toList();

        boardback.getChildren().clear();
        for (int y = 0; y < 8; ++y) {
            for (int x = 0; x < 8; ++x) {
                // Define the container of pawns
                HBox block = new HBox(0);
                block.setPrefWidth(50);
                block.setPrefHeight(50);
                block.getStyleClass().add("" + x + y);
                if (list[x][y] == -1) {
                    block.getStyleClass().add("unused");
                }

                if (list[x][y] == 0) {
                    makeBlockOnDrag(block);
                    block.getStyleClass().add("block");
                }
                if (list[x][y] == 1) {
                    // if value equals to 1, add a white pawn into the container
                    ImageView pawn = new ImageView(
                            new Image(HelloFx.class.getResourceAsStream("icons8-pawn-white.png")));
                    // makePawnDragged(pawn);
                    pawn.setPreserveRatio(true);
                    pawn.setFitWidth(50);
                    pawn.getStyleClass().add("White");
                    pawn.getStyleClass().add("pawn");
                    block.getChildren().add(pawn);
                    block.getStyleClass().add("block");
                    makeBlockOnDrag(block);
                }
                if (list[x][y] == 2) {
                    // if value equals to 2, add a black pawn into the container
                    ImageView pawn = new ImageView(
                            new Image(HelloFx.class.getResourceAsStream("icons8-pawn-black.png")));
                    // makePawnDragged(pawn);
                    pawn.setPreserveRatio(true);
                    pawn.setFitWidth(50);
                    pawn.getStyleClass().add("Black");
                    pawn.getStyleClass().add("pawn");
                    block.getChildren().add(pawn);
                    block.getStyleClass().add("block");
                    makeBlockOnDrag(block);
                }
                if (list[x][y] == 3) {
                    // if value equals to 1, add a white pawn into the container
                    ImageView pawn = new ImageView(
                            new Image(HelloFx.class.getResourceAsStream("icons8-king-white.png")));
                    // makePawnDragged(pawn);
                    pawn.setPreserveRatio(true);
                    pawn.setFitWidth(50);
                    pawn.getStyleClass().add("WhiteK");
                    pawn.getStyleClass().add("pawn");
                    block.getChildren().add(pawn);
                    block.getStyleClass().add("block");
                    makeBlockOnDrag(block);
                }
                if (list[x][y] == 4) {
                    // if value equals to 1, add a white pawn into the container
                    ImageView pawn = new ImageView(
                            new Image(HelloFx.class.getResourceAsStream("icons8-king-black.png")));
                    // makePawnDragged(pawn);
                    pawn.setPreserveRatio(true);
                    pawn.setFitWidth(50);
                    pawn.getStyleClass().add("BlackK");
                    pawn.getStyleClass().add("pawn");
                    block.getChildren().add(pawn);
                    block.getStyleClass().add("block");
                    makeBlockOnDrag(block);
                }
                boardback.getChildren().add(block);
            }
        }
    }

    // Drag&drop function part
    Boolean couldBeDrop = false;
    Boolean Dropped = false;
    // Boolean captureMode = checkerboard.captureMode();
    int target_x;
    int target_y;
    int source_x;
    int source_y;

    private void makeBlockOnDrag(HBox box) {

        box.setOnMouseEntered(e -> {
            box.setBackground(new Background(new BackgroundFill(Color.rgb(200, 204, 200, 0.8), null, null)));
        });

        box.setOnMouseExited(e -> {
            box.setBackground(null);
        });

        box.setOnDragDetected(e -> {
            Dragboard dragboard = box.startDragAndDrop(TransferMode.ANY);
            ClipboardContent clipboardContent = new ClipboardContent();
            Object children = box.getChildren().get(0);
            clipboardContent.putImage(((ImageView) children).getImage());
            clipboardContent.putString(((ImageView) children).getStyleClass().get(1));
            dragboard.setContent(clipboardContent);
            box.getChildren().remove(0);
        });

        // Return the pawn to original block if it isn't dropped to another block
        // successfully
        box.setOnDragDone(e -> {
            if (Dropped) {
                Dropped = false;
                displayLog(0);
                if (!(new Checkerboard(toList(boardback), playerControl)).captureMode()) {
                    playerControl = !playerControl;
                }
                checkerboard = new Checkerboard(toList(boardback), playerControl);
                root.getChildren().clear();
                root.getChildren().add(new Label("Player complete"));
                displayLog(checkerboard.win());
                // Checkerboard cache = checkerboard;
                // switch (checkerboard.win()) {
                // case 1:
                // root.getChildren().add(new Label("Player won!"));
                // break;
                // case -1:
                // root.getChildren().add(new Label("AI won!"));
                // break;
                // case 0:
                // root.getChildren().add(new Label("Draw"));
                // break;
                // }

                // System.out.println(checkerboard);
                if (checkerboard.win() != 0) {
                    playerControl = null;
                }
                // boardUpdate(checkerboard);

                if (playerControl == false) {
                    root.getChildren().clear();
                    root.getChildren().add(new Label("AI Moving"));

                    int[] newmove;
                    // MinMax will be here
                    if (limit != 0) {
                        dsp.getChildren().clear();
                        dsp.getChildren().add(new Label("Thinking..."));
                        // System.out.println(checkerboard);
                        newmove = checkerboard.MMABEval(limit);
                        // System.out.println(cache);
                        // int[][] cache = checkerboard.toList();
                        // MMAB_cls mmab = new MMAB_cls(limit, playerControl, cache);
                        // newmove = mmab.startEval();

                    } else {
                        Random rand = new Random();
                        // root.getChildren().remove(0);
                        newmove = ((int[]) checkerboard.next()
                                .elementAt(rand.nextInt(checkerboard.next().size())));
                    }
                    System.out.println("" + newmove[0] + newmove[1] + newmove[2] + newmove[3]);
                    // Animation of AI

                    AIMove(newmove);
                }
                e.consume();
            } else {
                ImageView insert = new ImageView(e.getDragboard().getImage());
                insert.setFitWidth(50);
                insert.setFitHeight(50);
                insert.getStyleClass().add(e.getDragboard().getString());
                insert.getStyleClass().add("pawn");
                box.getChildren().add(insert);
            }
        });

        box.setOnDragOver(e -> {
            if (e.getGestureSource() != box && e.getDragboard().hasImage()) {
                e.acceptTransferModes(TransferMode.COPY_OR_MOVE);
            }
            e.consume();
        });

        box.setOnDragEntered(e -> {
            String target_location = box.getStyleClass().get(0);
            Object source = e.getGestureSource();
            String source_location = ((HBox) source).getStyleClass().get(0);

            target_x = Character.getNumericValue(target_location.charAt(0));
            target_y = Character.getNumericValue(target_location.charAt(1));
            source_x = Character.getNumericValue(source_location.charAt(0));
            source_y = Character.getNumericValue(source_location.charAt(1));

            Vector<int[]> nextMoves = checkerboard.next();
            for (int i = 0; i < nextMoves.size(); i++) {
                int[] nextMove = nextMoves.elementAt(i);

                if (source_x == nextMove[0]
                        && source_y == nextMove[1]
                        && target_x == nextMove[2]
                        && target_y == nextMove[3]) {
                    box.setBackground(new Background(new BackgroundFill(Color.GREEN, null, null)));
                    couldBeDrop = true;
                    break;
                } else {
                    box.setBackground(new Background(new BackgroundFill(Color.RED, null, null)));
                }
            }
            e.consume();
        });

        box.setOnDragExited(e -> {
            box.setBackground(null);
            couldBeDrop = false;
        });

        box.setOnDragDropped(e -> {
            if (box.getChildren().isEmpty() && couldBeDrop) {
                ImageView insert = new ImageView(e.getDragboard().getImage());
                insert.setFitWidth(50);
                insert.setFitHeight(50);
                insert.getStyleClass().add(e.getDragboard().getString());
                insert.getStyleClass().add("pawn");
                box.getChildren().add(insert);
                Dropped = true;
                if (checkerboard.captureMode()) {
                    int capped = ((target_x + source_x) / 2) + ((target_y + source_y) / 2) * 8;
                    Object removed = boardback.getChildren().get(capped);
                    ((HBox) removed).getChildren().remove(0);
                }

                // King
                if (e.getDragboard().getString() == "Black" && target_y == 0) {
                    box.getChildren().remove(0);
                    ImageView coronation = new ImageView(
                            new Image(HelloFx.class.getResourceAsStream("icons8-king-black.png")));
                    coronation.setFitWidth(50);
                    coronation.setFitHeight(50);
                    coronation.getStyleClass().add("BlackK");
                    coronation.getStyleClass().add("pawn");
                    box.getChildren().add(coronation);
                }
                if (e.getDragboard().getString() == "White" && target_y == 7) {
                    box.getChildren().remove(0);
                    ImageView coronation = new ImageView(
                            new Image(HelloFx.class.getResourceAsStream("icons8-king-white.png")));
                    coronation.setFitWidth(50);
                    coronation.setFitHeight(50);
                    coronation.getStyleClass().add("WhiteK");
                    coronation.getStyleClass().add("pawn");
                    box.getChildren().add(coronation);
                }
                root.getChildren().clear();
                root.getChildren().add(new Label("Player complete"));
                // root.getChildren().add(new Label(checkerboard.win()));
                displayLog(checkerboard.win());
            }
        });
    }

    // Move statement part

    private int[][] toList(Object current_boardback) {
        int[][] list = new int[8][8];
        // int count = 0;
        for (int y = 0; y < 8; ++y) {
            for (int x = 0; x < 8; ++x) {
                Object pointed_block = ((FlowPane) current_boardback).getChildren().get((y * 8) + x);
                if (((HBox) pointed_block).getStyleClass().get(1) == "block") {
                    if (!((HBox) pointed_block).getChildren().isEmpty()) {
                        Object pointed_view = ((HBox) pointed_block).getChildren().get(0);
                        switch (((ImageView) pointed_view).getStyleClass().get(1)) {
                            case "White":
                                list[x][y] = 1;
                                break;
                            case "Black":
                                list[x][y] = 2;
                                break;
                            case "WhiteK":
                                list[x][y] = 3;
                                break;
                            case "BlackK":
                                list[x][y] = 4;
                                break;
                        }
                    } else {
                        list[x][y] = 0;
                    }
                } else {
                    list[x][y] = -1;
                }
                // count++;
            }
        }
        return list;
    }

    // HBox choosed_block;
    // ImageView choosed_pawn;

    public void AIMove(int[] newmove) {
        // Location of target
        int choosed_idx = newmove[1] * 8
                + newmove[0];
        HBox choosed_block = ((HBox) boardback.getChildren().get(choosed_idx));
        ImageView choosed_pawn = ((ImageView) choosed_block.getChildren().get(0));
        // HBox moveto_block = ((HBox) boardback.getChildren().get(moveto_idx));

        // Bounds choosed_location =
        // choosed_block.localToScene(choosed_block.getBoundsInLocal());
        // Bounds moveto_location =
        // moveto_block.localToScene(moveto_block.getBoundsInLocal());
        double[] choosed_location = { newmove[0] * 50,
                newmove[1] * 50 };
        double[] moveto_location = { newmove[2] * 50,
                newmove[3] * 50 };

        // Move Animation
        Duration duration = Duration.millis(1000);
        TranslateTransition transition = new TranslateTransition(duration, choosed_pawn);
        transition.setByX(moveto_location[0] - choosed_location[0]);
        transition.setByY(moveto_location[1] - choosed_location[1]);
        transition.setCycleCount(1);

        // Fadeout Animation for captured
        if (Math.abs(newmove[0] - newmove[2]) == 2 && Math.abs(newmove[1] - newmove[3]) == 2) {
            int captured_idx = (newmove[1]
                    + newmove[3]) / 2 * 8
                    + ((newmove[0] + newmove[2])
                            / 2);
            HBox captured_block = ((HBox) boardback.getChildren().get(captured_idx));
            ImageView captured_pawn = ((ImageView) captured_block.getChildren().get(0));
            FadeTransition fadeOutTransition = new FadeTransition(Duration.millis(1000), captured_pawn);
            fadeOutTransition.setFromValue(1.0);
            fadeOutTransition.setToValue(0.0);
            // New board from here
            // checkerboard.movement(newmove);
            // fadeOutTransition.setOnFinished(e -> boardUpdate(checkerboard));
            transition.setOnFinished(e -> fadeOutTransition.play());
            fadeOutTransition.setOnFinished(e -> AfterAIMove(newmove));
            transition.play();
            // if (!checkerboard.captureMode()) {
            // playerControl = !playerControl;
            // }
        } else {
            // playerControl = !playerControl;
            // New board from here
            // checkerboard.movement(newmove);
            transition.setOnFinished(e -> AfterAIMove(newmove));
            transition.play();
        }

        // transition.setOnFinished(e -> checkerboard.movement(newmove));
        // transition.setOnFinished(e -> boardUpdate(checkerboard));

        // Log
        // if (playerControl) {
        // root.getChildren().add(new Label("Your next move" + newmove));
        // } else {
        // root.getChildren().add(new Label("AI next move" + newmove));
        // }
    }

    public void AfterAIMove(int[] newmove) {
        // Get newboard as next step
        HBox choosed_block = ((HBox) boardback.getChildren().get(newmove[1] * 8
                + newmove[0]));
        HBox target_block = ((HBox) boardback.getChildren().get(newmove[3] * 8
                + newmove[2]));
        ImageView choosed_pawn = ((ImageView) choosed_block.getChildren().get(0));
        target_block.getChildren().add(choosed_pawn);
        choosed_block.getChildren().clear();

        // if (!(new Checkerboard(toList(boardback), playerControl)).captureMode()) {
        // playerControl = !playerControl;
        // }
        // checkerboard = new Checkerboard(toList(boardback), playerControl);

        if (Math.abs(newmove[0] - newmove[2]) == 2 && Math.abs(newmove[1] - newmove[3]) == 2) {
            HBox captured_block = ((HBox) boardback.getChildren().get((newmove[1]
                    + newmove[3]) / 2 * 8
                    + ((newmove[0] + newmove[2])
                            / 2)));
            captured_block.getChildren().clear();
        }

        // checkerboard.movement(newmove, false);
        // boardUpdate(checkerboard);
        // Coronation checking
        int[][] checking = new int[8][8];
        checking = toList(boardback);
        for (int x = 0; x < 8; x++) {
            if (checking[x][7] == 1) {
                checking[x][7] += 2;
            }
            if (checking[x][0] == 2) {
                checking[x][0] += 2;
            }

        }

        // If there is no capture move after
        if (!(new Checkerboard(checking, playerControl)).captureMode()) {
            playerControl = !playerControl;
        }
        checkerboard = new Checkerboard(checking, playerControl);
        boardUpdate(checkerboard);
        // Checkerboard cache = checkerboard;
        int[] nextnewmove;
        if (playerControl == false) {
            // MinMax will be here
            if (limit != 0) {
                // System.out.println(checkerboard);
                dsp.getChildren().clear();
                dsp.getChildren().add(new Label("Thinking..."));

                nextnewmove = checkerboard.MMABEval(limit);
                // System.out.println(cache);
                // MMAB_cls mmab = new MMAB_cls(limit, playerControl, cache);
                // nextnewmove = mmab.startEval();
                // System.out.println(checkerboard);
                // nextnewmove = startEval(limit, playerControl, checkerboard);
            } else {
                Random rand = new Random();
                nextnewmove = ((int[]) checkerboard.next().elementAt(rand.nextInt(checkerboard.next().size())));
            }
            // System.out.println("" + newmove[0] + newmove[1] + newmove[2] + newmove[3]);
            AIMove(nextnewmove);
        }
        root.getChildren().clear();
        root.getChildren().add(new Label("AI complete"));

        // root.getChildren().add(new Label(new Checkerboard(toList(boardback),
        // playerControl).win()));
        displayLog(new Checkerboard(toList(boardback), playerControl).win());

        // check if nextstep is capture mode

    }

    public void displayLog(int win) {
        switch (win) {
            case 1:
                root.getChildren().add(new Label("Player won!"));
                break;
            case -1:
                root.getChildren().add(new Label("AI won!"));
                break;
            case 2:
                root.getChildren().add(new Label("Draw"));
                break;
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

}