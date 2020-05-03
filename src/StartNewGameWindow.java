import javax.swing.*;
import java.awt.*;

public class StartNewGameWindow extends JFrame {
    private static final int WIN_HEIGHT = 450;
    private static final int WIN_WIDTH  = 400;
    private static final int WIN_POS_X  = 650;
    private static final int WIN_POS_Y  = 450;
    private static final int MIN_FIELD_SIZE  = 3;
    private static final int MAX_FIELD_SIZE  = 10;
    private static final int MIN_WIN_LENGTH = 3;



    private JRadioButton jrbHumVsAi;
    private JRadioButton jrbHumVsHum;
    private ButtonGroup gameMode;

    private JSlider jsFieldSize;
    private JSlider jsWinLength;

    private GameWindow gameWindow;

    public StartNewGameWindow(GameWindow gameWindow) {
        this.gameWindow = gameWindow;
        setBounds(WIN_POS_X,WIN_POS_Y,WIN_WIDTH,WIN_HEIGHT);
        setTitle("TicTacToe");
        setResizable(false);
        setLayout(new GridLayout(8,1));

        //gameMode
        add(new JLabel("Choose gameMode:"));
        jrbHumVsAi = new JRadioButton("Hum vs Ai",true);
        jrbHumVsHum = new JRadioButton("Hum vs Hum");
        gameMode = new ButtonGroup();
        gameMode.add(jrbHumVsAi);
        gameMode.add(jrbHumVsHum);
        add(jrbHumVsAi);
        add(jrbHumVsHum);

        //size
        //dots to win
        add(new JLabel("Choose field size:"));
        jsFieldSize = new JSlider(MIN_FIELD_SIZE,MAX_FIELD_SIZE,MIN_FIELD_SIZE);
        add(jsFieldSize);
        jsFieldSize.setMajorTickSpacing(1);
        jsFieldSize.setPaintLabels(true);
        jsFieldSize.setPaintTicks(true);

        jsFieldSize.addChangeListener(e->{
            int fieldSize = jsFieldSize.getValue();
            jsWinLength.setMaximum(fieldSize);
        });

        add(new JLabel("Choose winning length:"));
        jsWinLength = new JSlider(MIN_WIN_LENGTH, MIN_FIELD_SIZE, MIN_WIN_LENGTH);
        add(jsWinLength);
        jsWinLength.setMajorTickSpacing(1);
        jsWinLength.setPaintLabels(true);
        jsWinLength.setPaintTicks(true);


        //buttonOk
        JButton btnStartGame = new JButton("Start a game");
        add(btnStartGame);
        btnStartGame.addActionListener(e->{
            btnStartGameClick();
        });

        setVisible(false);
    }

    private void btnStartGameClick(){
        setVisible(false);
        int gameMode;
        if(jrbHumVsAi.isSelected()){
            gameMode = BattleMap.MODE_H_V_A;
            Logic.aiMode = true;
        } else {
            gameMode = BattleMap.MODE_H_V_H;
            Logic.aiMode = false;
        }
        int fieldSize = jsFieldSize.getValue();
        int winLength = jsWinLength.getValue();

        Logic.SIZE = fieldSize;
        Logic.DOTS_TO_WIN = winLength;
        Logic.initMap();
        Logic.printMap();
        Logic.gameFinished = false;

        gameWindow.startNewGame(gameMode, fieldSize,fieldSize,winLength);

    }
}
