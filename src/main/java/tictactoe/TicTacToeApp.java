package tictactoe;

import java.util.Arrays;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class TicTacToeApp extends Application {

    private final Button[][] cells = new Button[3][3];
    private enum CellAction { DISABLE, RESET, SETSTYLE };
    private boolean xTurn = true;
    private boolean singlePlayer = true;
    private final char[][] board = new char[3][3];
    private final Label statusLabel = new Label("Choose mode and start a game!");
    private boolean darkMode = false;
    private BorderPane root;
    private GridPane grid;

    @Override
    public void start(Stage stage) {
        stage.setTitle("Tic Tac Toe");

        root = new BorderPane();
        root.setPadding(new Insets(15));

        VBox topPane = new VBox(10);
        topPane.setAlignment(Pos.CENTER);

        HBox modePane = new HBox(10);
        modePane.setAlignment(Pos.CENTER);

        ToggleGroup modeGroup = new ToggleGroup();
        RadioButton singlePlayerButton = new RadioButton("Single Player");
        singlePlayerButton.setToggleGroup(modeGroup);
        singlePlayerButton.setSelected(true);
        RadioButton twoPlayerButton = new RadioButton("Two Player");
        twoPlayerButton.setToggleGroup(modeGroup);

        CheckBox darkModeToggle = new CheckBox("Dark Mode");
        darkModeToggle.setOnAction(e -> {
            darkMode = darkModeToggle.isSelected();
            applyTheme(root);
        });

        modePane.getChildren().addAll(singlePlayerButton, twoPlayerButton, darkModeToggle);

        Button restartButton = new Button("Start / Restart");
        restartButton.setOnAction(e -> resetBoard());

        statusLabel.setFont(Font.font(18));
        statusLabel.setTextFill(Color.DARKBLUE);

        topPane.getChildren().addAll(modePane, restartButton, statusLabel);
        root.setTop(topPane);
        Arrays.stream(board).forEach(row -> Arrays.fill(row, ' '));

        grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setAlignment(Pos.CENTER);
        grid.setPadding(new Insets(20));

        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                Button cell = new Button(" ");
                cell.setMinSize(110, 110);
                cell.setFont(Font.font(36));
                cell.setFocusTraversable(false);
                int r = row;
                int c = col;
                cell.setOnAction(e -> handleMove(r, c));
                cells[row][col] = cell;
                grid.add(cell, col, row);
            }
        }

        root.setCenter(grid);

        singlePlayerButton.setOnAction(e -> {
            singlePlayer = true;
            statusLabel.setText("Single Player mode selected. X goes first.");
            resetBoard();
        });

        twoPlayerButton.setOnAction(e -> {
            singlePlayer = false;
            statusLabel.setText("Two Player mode selected. X goes first.");
            resetBoard();
        });

        applyTheme(root);

        Scene scene = new Scene(root, 420, 520);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    private void handleMove(int row, int col) {
        if (board[row][col] != ' ') {
            return;
        }
        if (checkWinner() != ' ') {
            return;
        }

        board[row][col] = xTurn ? 'X' : 'O';
        cells[row][col].setText(String.valueOf(board[row][col]));
        cells[row][col].setDisable(true);

        char winner = checkWinner();
        if (winner != ' ') {
            statusLabel.setText((winner == 'T' ? "It's a tie!" : winner + " wins!") + " Click restart to play again.");
            disableBoard();
            return;
        }

        xTurn = !xTurn;
        statusLabel.setText((xTurn ? "X" : "O") + "'s turn.");

        if (singlePlayer && !xTurn && checkWinner() == ' ') {
            computerMove();
        }
    }

    private void computerMove() {
        int[] bestMove = findBestMove();
        int row = bestMove[0];
        int col = bestMove[1];
        board[row][col] = 'O';
        cells[row][col].setText("O");
        cells[row][col].setDisable(true);

        char winner = checkWinner();
        if (winner != ' ') {
            statusLabel.setText((winner == 'T' ? "It's a tie!" : winner + " wins!") + " Click restart to play again.");
            disableBoard();
            return;
        }

        xTurn = true;
        statusLabel.setText("X's turn.");
    }

    private int[] findBestMove() {
        int bestScore = Integer.MIN_VALUE;
        int[] bestMove = {0, 0};

        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                if (board[row][col] == ' ') {
                    board[row][col] = 'O';
                    int score = minimax(0, false);
                    board[row][col] = ' ';
                    if (score > bestScore) {
                        bestScore = score;
                        bestMove[0] = row;
                        bestMove[1] = col;
                    }
                }
            }
        }
        return bestMove;
    }

    private int minimax(int depth, boolean isMaximizing) {
        char winner = checkWinner();
        if (winner != ' ') {
            if (winner == 'O') return 10 - depth;
            if (winner == 'X') return depth - 10;
            return 0;
        }

        if (isMaximizing) {
            int bestScore = Integer.MIN_VALUE;
            for (int row = 0; row < 3; row++) {
                for (int col = 0; col < 3; col++) {
                    if (board[row][col] == ' ') {
                        board[row][col] = 'O';
                        int score = minimax(depth + 1, false);
                        board[row][col] = ' ';
                        bestScore = Math.max(score, bestScore);
                    }
                }
            }
            return bestScore;
        } else {
            int bestScore = Integer.MAX_VALUE;
            for (int row = 0; row < 3; row++) {
                for (int col = 0; col < 3; col++) {
                    if (board[row][col] == ' ') {
                        board[row][col] = 'X';
                        int score = minimax(depth + 1, true);
                        board[row][col] = ' ';
                        bestScore = Math.min(score, bestScore);
                    }
                }
            }
            return bestScore;
        }
    }

    private char checkWinner() {
        for (int i = 0; i < 3; i++) {
            if (board[i][0] != ' ' && board[i][0] == board[i][1] && board[i][0] == board[i][2]) {
                return board[i][0];
            }
            if (board[0][i] != ' ' && board[0][i] == board[1][i] && board[0][i] == board[2][i]) {
                return board[0][i];
            }
        }

        if (board[0][0] != ' ' && board[0][0] == board[1][1] && board[0][0] == board[2][2]) {
            return board[0][0];
        }
        if (board[0][2] != ' ' && board[0][2] == board[1][1] && board[0][2] == board[2][0]) {
            return board[0][2];
        }

        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                if (board[row][col] == ' ') {
                    return ' ';
                }
            }
        }

        return 'T';
    }

    private void resetBoard() {
        xTurn = true;
        statusLabel.setText((singlePlayer ? "Single Player" : "Two Player") + " mode. X starts.");
        Arrays.stream(board).forEach(row -> Arrays.fill(row, ' '));
        updateCellsBasedOnAction(CellAction.RESET, null);
    }

    private void disableBoard() {
       updateCellsBasedOnAction(CellAction.DISABLE, null);
    }

    private void applyTheme(BorderPane root) {
        if (darkMode) {
            root.setStyle("-fx-base: #1e1e1e;");
            grid.setStyle("-fx-base: #2d2d2d;");
            updateCellsBasedOnAction(CellAction.SETSTYLE, "-fx-base: #3d3d3d; -fx-text-fill: #ffffff;");
        } else {
            root.setStyle("");
            grid.setStyle("");
            updateCellsBasedOnAction(CellAction.SETSTYLE, "");
        }
    }

    /**
     * performs action for all cells in the tic tac toe grid. Action can be either of: sets CSS style, disable or reset cells. 
     * @param cellAction
     * @param cssStyling
     */
    private void updateCellsBasedOnAction(CellAction cellAction, String cssStyling) {
        Arrays.stream(cells)
            .flatMap(Arrays::stream)
            .forEach(cell -> { 
                switch (cellAction) {
                    case DISABLE -> cell.setDisable(true);
                    case RESET -> {
                        cell.setText(" ");
                        cell.setDisable(false);
                    }
                    case SETSTYLE -> cell.setStyle(cssStyling == null ? "" : cssStyling);
                }
            });
    }

    public static void main(String[] args) {
        launch();
    }
}
