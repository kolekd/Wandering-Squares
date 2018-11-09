import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.TimerTask;

public class Board extends JComponent implements KeyListener {

    public static int boardWidth = 64*12;
    public static int boardHeight = 64*9;
    public static GameObject gameObject = new GameObject();
    HashMap<Integer, GameObject> gameObjectList = new HashMap<>();


    public Board(){
        setPreferredSize(new Dimension(boardWidth,boardHeight));
        setVisible(true);
    }

    public static void main(String[] args) {

        GameObject mrRectangle = new GameObject("img/mrRectangle.png", (boardHeight/64)/2,(boardWidth/64)/2 + 2);
        GameObject mrRectangle2 = new GameObject("img/mrRectangle2.png", (boardHeight/64)/2,(boardWidth/64)/2 - 2);

        JFrame frame = new JFrame("RPG Game");
        Board board = new Board();

        board.gameObjectList.put(board.gameObjectList.size(), mrRectangle);
        board.gameObjectList.put(board.gameObjectList.size(), mrRectangle2);

        frame.add(board);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.pack();
        frame.addKeyListener(board);

    }

    @Override
    public void paint (Graphics graphics){


//        System.out.println(gameObjectList);

        for (int i = 0; i < gameObjectList.size(); i++) {
            for (int j = 0; j < i; j++) {
                if (i!=j && gameObjectList.get(i).samePosition(gameObjectList.get(j)) && gameObjectList.size() < 3){
                    colourMerge(gameObjectList.get(j), gameObjectList.get(i));
                } else if (i!=j && gameObjectList.get(i).samePosition(gameObjectList.get(j)) && gameObjectList.size() == 3){
                    colourMerge2(gameObjectList.get(j), gameObjectList.get(i));
                }
            }
            gameObjectList.get(i).draw(graphics);

//            System.out.println(gameObjectList.get(i).filename + "  X: " + gameObjectList.get(i).posX + " Y: " + gameObjectList.get(i).posY);

            randMovement(gameObjectList.get(i));

        }

        try {
            Thread.sleep(50);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }


    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

        if (e.getKeyCode() == KeyEvent.VK_UP){
            gameObjectList.get(0).move("up", 1);
        } else if (e.getKeyCode() == KeyEvent.VK_DOWN){
            gameObjectList.get(0).move("down", 1);
        } else if (e.getKeyCode() == KeyEvent.VK_LEFT){
            gameObjectList.get(0).move("left", 1);
        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT){
            gameObjectList.get(0).move("right", 1 );
        }
        repaint();

    }

    public void randMovement(GameObject gameObject){
        gameObject.move(gameObject.randomDir(),1);
        repaint();
    }

    public void colourMerge (GameObject o1, GameObject o2){
        GameObject mrRectangleExtra = new GameObject(gameObject.getMergedColour(o1, o2), o2.posX/64, o2.posY/64);
        gameObjectList.put(gameObjectList.size(), mrRectangleExtra);
    }

    public void colourMerge2 (GameObject o1, GameObject o2){
        GameObject mrRectangleExtra = new GameObject("img/mrRectangleOverlap2.png", o2.posX/64, o2.posY/64);
        gameObjectList.put(gameObjectList.size(), mrRectangleExtra);
    }
}
