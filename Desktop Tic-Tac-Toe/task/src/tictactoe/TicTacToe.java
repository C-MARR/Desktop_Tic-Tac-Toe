package tictactoe;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class TicTacToe extends JFrame {
    int count = 0;

    final String X = "X";
    final String O = "O";
    final String EMPTY = " ";
    final String GAME_NOT_STARTED = "Game is not started";
    final String HUMAN = "Human";
    final String CPU = "Robot";

    XOButton a1 = new XOButton("A", "1");
    XOButton a2 = new XOButton("A", "2");
    XOButton a3 = new XOButton("A", "3");
    XOButton b1 = new XOButton("B", "1");
    XOButton b2 = new XOButton("B", "2");
    XOButton b3 = new XOButton("B", "3");
    XOButton c1 = new XOButton("C", "1");
    XOButton c2 = new XOButton("C", "2");
    XOButton c3 = new XOButton("C", "3");
    XOButton[] XOButtons = {a1, a2, a3, b1, b2, b3, c1, c2, c3};
    JButton startResetButton = new JButton();
    JButton player1Button = new JButton(HUMAN);
    JButton player2Button = new JButton(HUMAN);
    JLabel labelStatus = new JLabel(GAME_NOT_STARTED);
    JPanel optionButtons = new JPanel(new GridLayout(1, 3));
    JPanel gameButtons = new JPanel();
    JPanel statusBar = new JPanel(new GridLayout(0, 1, 50, 50));
    JMenuBar menuBar = new JMenuBar();
    JMenu menuGame = new JMenu("Game");
    JMenuItem menuHumanHuman = new JMenuItem("Human vs Human");
    JMenuItem menuHumanRobot = new JMenuItem("Human vs Robot");
    JMenuItem menuRobotHuman = new JMenuItem("Robot vs Human");
    JMenuItem menuRobotRobot = new JMenuItem("Robot vs Robot");
    JMenuItem menuExit = new JMenuItem("Exit");


    public TicTacToe() {

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 650);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        gameButtons.setLayout(new GridLayout(3, 3));
        gameButtons.setSize(getWidth(), getHeight() - 50);
        statusBar.setSize(getWidth(), getHeight() / 8);
        statusBar.setMinimumSize(new Dimension(getWidth(), 25));
        statusBar.add(labelStatus);
        statusBar.setVisible(true);

        a1.setName("ButtonA1");
        a2.setName("ButtonA2");
        a3.setName("ButtonA3");
        b1.setName("ButtonB1");
        b2.setName("ButtonB2");
        b3.setName("ButtonB3");
        c1.setName("ButtonC1");
        c2.setName("ButtonC2");
        c3.setName("ButtonC3");
        menuGame.setName("MenuGame");
        menuGame.setMnemonic(KeyEvent.VK_G);
        menuHumanHuman.setName("MenuHumanHuman");
        menuHumanHuman.setMnemonic(KeyEvent.VK_H);
        menuHumanRobot.setName("MenuHumanRobot");
        menuHumanRobot.setMnemonic(KeyEvent.VK_R);
        menuRobotHuman.setName("MenuRobotHuman");
        menuRobotHuman.setMnemonic(KeyEvent.VK_U);
        menuRobotRobot.setName("MenuRobotRobot");
        menuRobotRobot.setMnemonic(KeyEvent.VK_O);
        menuExit.setName("MenuExit");
        menuExit.setMnemonic(KeyEvent.VK_X);
        startResetButton.setName("ButtonStartReset");
        startResetButton.setText("Start");
        startResetButton.setFocusable(false);
        player1Button.setName("ButtonPlayer1");
        player1Button.setFocusable(false);
        player1Button.addActionListener(selectHumanCpuPLayer());
        player2Button.addActionListener(selectHumanCpuPLayer());
        player2Button.setName("ButtonPlayer2");
        player2Button.setFocusable(false);
        labelStatus.setName("LabelStatus");
        labelStatus.setText(GAME_NOT_STARTED);

        optionButtons.add(player1Button);
        optionButtons.add(startResetButton);
        optionButtons.add(player2Button);

        gameButtons.add(a3);
        gameButtons.add(b3);
        gameButtons.add(c3);
        gameButtons.add(a2);
        gameButtons.add(b2);
        gameButtons.add(c2);
        gameButtons.add(a1);
        gameButtons.add(b1);
        gameButtons.add(c1);

        menuGame.add(menuHumanHuman);
        menuGame.add(menuHumanRobot);
        menuGame.add(menuRobotHuman);
        menuGame.add(menuRobotRobot);
        menuGame.addSeparator();
        menuGame.add(menuExit);
        menuBar.add(menuGame);
        setJMenuBar(menuBar);

        initMenuButtonListeners();

        add(optionButtons, BorderLayout.NORTH);
        add(gameButtons, BorderLayout.CENTER);
        add(statusBar, BorderLayout.SOUTH);

        setName("TicTacToe");
        setTitle("Tic Tac Toe");
        setVisible(true);
        resetGame();
        startResetButton.addActionListener(actionEvent -> {
            if (labelStatus.getText().equals(GAME_NOT_STARTED)) {
                labelStatus.setText(checkGameProgress());
                startGame();
            } else {
                resetGame();
            }
        });
    }

    private void initMenuButtonListeners() {

        menuHumanHuman.addActionListener(actionEvent -> {
            resetGame();
            player1Button.setText(HUMAN);
            player2Button.setText(HUMAN);
            startGame();
        });

        menuHumanRobot.addActionListener(actionEvent -> {
            resetGame();
            player1Button.setText(HUMAN);
            player2Button.setText(CPU);
            startGame();
        });

        menuRobotHuman.addActionListener(actionEvent -> {
            resetGame();
            player1Button.setText(CPU);
            player2Button.setText(HUMAN);
            startGame();
        });

        menuRobotRobot.addActionListener(actionEvent -> {
            resetGame();
            player1Button.setText(CPU);
            player2Button.setText(CPU);
            startGame();
        });

        menuExit.addActionListener(actionEvent -> System.exit(0));

    }


    public class XOButton extends JButton {
        String column;
        String row;

        XOButton(String column, String row) {
            this.column = column;
            this.row = row;
            setText(EMPTY);
            addActionListener(setupXOButtonListener());
            setFont(new Font("Arial", Font.BOLD, 60));
            setFocusable(false);
            setFocusPainted(false);
        }

        @Override
        public String toString() {
            return getText();
        }
    }

    private void playGame() {
        boolean xTurn = count % 2 == 0;
        if (player1Button.getText().equals(CPU) && xTurn) {
            cpuPlay();
        } else if (player2Button.getText().equals(CPU) && !xTurn) {
            cpuPlay();
        }

    }

    public ActionListener setupXOButtonListener() {
        return actionEvent -> {
            JButton xo = (JButton) actionEvent.getSource();
            if (xo.getText().matches("[XO]")) {
                return;
            }
            if (count % 2 == 0) {
                xo.setText(X);
            } else {
                xo.setText(O);
            }
            count++;
            labelStatus.setText(checkGameProgress());
            if (labelStatus.getText().startsWith("The turn")) {
                try {
                    Thread.currentThread().join(100L);
                    playGame();
                    Thread.currentThread().join(100L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
    }

    private ActionListener selectHumanCpuPLayer() {
        return actionEvent -> {
            JButton humanCpu = (JButton) actionEvent.getSource();
            if (humanCpu.getText().equals(HUMAN)) {
                humanCpu.setText(CPU);
            } else {
                humanCpu.setText(HUMAN);
            }
        };
    }

    private String checkGameProgress() {
        boolean xTurn = count % 2 == 0;
        String currentPlayer = xTurn ? player1Button.getText() : player2Button.getText();
        String currentXO = xTurn ? X : O;
        String winner = checkRowColumnWin();
        if (!EMPTY.equals(winner)) {
            disableXOButtons();
            return String.format("The %s Player (%s) wins", currentPlayer, winner);
        }
        winner = checkDiagonalWin();
        if (!EMPTY.equals(winner)) {
            disableXOButtons();
            return String.format("The %s Player (%s) wins", currentPlayer, winner);
        }
        if (count == 9) {
            disableXOButtons();
            return "Draw";
        }
        return String.format("The turn of %s Player (%s)", currentPlayer, currentXO);
    }


    private String checkRowColumnWin() {
        for (char row = '1'; row <= '3'; row++) {
            char finalRow = row;
            if (Arrays.stream(XOButtons).filter(button -> button.row.equals(String.valueOf(finalRow)))
                    .allMatch(button -> button.toString().matches(X))) {
                return X;
            } else if (Arrays.stream(XOButtons).filter(button -> button.row.equals(String.valueOf(finalRow)))
                    .allMatch(button -> button.toString().matches(O))) {
                return O;
            }
        }

        for (char column = 'A'; column <= 'C'; column++) {
            char finalColumn = column;
            if (Arrays.stream(XOButtons).filter(button -> button.column.equals(String.valueOf(finalColumn)))
                    .allMatch(button -> button.toString().matches(X))) {
                return X;
            } else if (Arrays.stream(XOButtons).filter(button -> button.column.equals(String.valueOf(finalColumn)))
                    .allMatch(button -> button.toString().matches(O))) {
                return O;
            }
        }
        return EMPTY;
    }


    private String checkDiagonalWin() {
        if ((!EMPTY.equals(b2.toString()) &&
                a1.toString().equals(b2.toString()) &&
                c3.toString().equals(b2.toString())) ||
                (!EMPTY.equals(b2.toString()) &&
                a3.toString().equals(b2.toString()) &&
                c1.toString().equals(b2.toString()))) {
            return b2.toString();
        }
        return EMPTY;
    }

    private void disableXOButtons() {
        for (XOButton xoButton : XOButtons) {
            xoButton.setEnabled(false);
        }
    }

    private void enableXOButtons() {
        for (XOButton xoButton : XOButtons) {
            xoButton.setEnabled(true);
        }
    }

    private void cpuPlay() {
        new Thread(() -> {
            Random random = new Random();
            while (true) {
                int randomSpot = random.nextInt(9);
                if (XOButtons[randomSpot].getText().equals(EMPTY)) {
                    try {
                        TimeUnit.MILLISECONDS.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    XOButtons[randomSpot].doClick();
                    break;
                }
            }
        }).start();

    }

    private void startGame() {
        enableXOButtons();
        startResetButton.setText("Reset");
        player1Button.setEnabled(false);
        player2Button.setEnabled(false);
        labelStatus.setText(checkGameProgress());
        playGame();
    }

    private void resetGame() {
        disableXOButtons();
        for (JButton xoButton : XOButtons) {
            xoButton.setText(EMPTY);
        }
        player1Button.setEnabled(true);
        player2Button.setEnabled(true);
        startResetButton.setText("Start");
        labelStatus.setText(GAME_NOT_STARTED);
        count = 0;
    }

}