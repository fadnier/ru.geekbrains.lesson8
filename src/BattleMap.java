import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class BattleMap extends JPanel {
    private GameWindow gameWindow;


    public static final int MODE_H_V_A = 0;
    public static final int MODE_H_V_H = 1;

    private int fieldSizeX;
    private int fieldSizeY;

    private int cellHeight;
    private int cellWidth;

    private boolean isInit = false;


    public BattleMap(GameWindow gameWindow) {
        this.gameWindow = gameWindow;
        setBackground(Color.WHITE);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                update(e);
            }
        });

    }

    private void update(MouseEvent e) {
        int cellX = e.getX() / cellWidth;
        int cellY = e.getY() / cellHeight;

        if(!Logic.gameFinished){
            Logic.setHumanXY(cellX, cellY);
        }


        System.out.println(cellX + " " + cellY);
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        render(g);
    }

    private void render(Graphics g) {
        if (!isInit) {
            return;
        }

        int panelWidth = getWidth();
        int panelHeight = getHeight();

        cellHeight = panelHeight / fieldSizeY;
        cellWidth = panelWidth / fieldSizeX;

        for (int i = 0; i < fieldSizeY; i++) {
            int y = i * cellHeight;
            g.drawLine(0, y, panelWidth, y);
        }

        for (int i = 0; i < fieldSizeX; i++) {
            int x = i * cellWidth;
            g.drawLine(x, 0, x, panelHeight);
        }

        for (int i = 0; i < Logic.SIZE ; i++) {
            for (int j = 0; j < Logic.SIZE; j++) {
                if(Logic.map[i][j]==Logic.DOT_O){
                    drawO(g,j,i);
                }
                if(Logic.map[i][j]==Logic.DOT_X){
                    drawX(g,j,i);
                }
            }
        }

        if(Logic.gameFinished==true) {
            drawWin(g);
        }
    }

    private void drawWin(Graphics g){
        Graphics2D g2D = (Graphics2D)g;

        g2D.setColor(new Color(0, 255, 0));
        g2D.setStroke(new BasicStroke(15.0f));

        System.out.println(Logic.gameFinishedLine[0]+" "+Logic.gameFinishedLine[1]+" "+Logic.gameFinishedLine[2]+" "+Logic.gameFinishedLine[3]+" ");
        int x = Logic.gameFinishedLine[1];
        int y = Logic.gameFinishedLine[0];
        int x2 = Logic.gameFinishedLine[3];
        int y2 = Logic.gameFinishedLine[2];
        g2D.drawLine(x*cellWidth+(cellWidth/2) , y*cellHeight+(cellHeight/2),
                x2*cellWidth+(cellWidth/2), y2*cellHeight+(cellHeight/2));
        g2D.setColor(new Color(255, 255, 255));
        g2D.fillRect(gameWindow.getWidth()/2-100, gameWindow.getHeight()/2-50, 200, 80);
        g2D.setColor(new Color(0, 0, 0));
        Font font = new Font("Serif", Font.PLAIN, 36);
        g2D.setFont(font);
        g2D.drawString("Конец игры ",gameWindow.getWidth()/2-100,gameWindow.getHeight()/2);
    }

    private void drawO(Graphics g, int cellX, int cellY){
        Graphics2D g2D = (Graphics2D)g;
        int margin = (int)(cellWidth*0.1);

        g2D.setStroke(new BasicStroke(10.0f));
        g2D.setColor(new Color(0,0,255));
        g2D.drawOval(cellX*cellWidth+margin , cellY*cellHeight+margin, cellWidth-(margin*2),cellHeight-(margin*2) );

    }
    private void drawX(Graphics g, int cellX, int cellY){
        Graphics2D g2D = (Graphics2D)g;
        int margin = (int)(cellWidth*0.1);

        g2D.setColor(new Color(255, 3, 0));
        g2D.setStroke(new BasicStroke(10.0f));

        g2D.drawLine(cellX*cellWidth+margin , cellY*cellHeight+margin,
                (cellX+1)*cellWidth-margin, (cellY+1)*cellHeight-margin);
        g2D.drawLine((cellX+1)*cellWidth-margin , cellY*cellHeight+margin,
                cellX*cellWidth+margin, (cellY+1)*cellHeight-margin);
    }


    void startNewGame(int gameMode, int fieldSizeX, int fieldSizeY, int winLength) {
        this.fieldSizeX = fieldSizeX;
        this.fieldSizeY = fieldSizeY;
        isInit = true;
        repaint();
    }
}
