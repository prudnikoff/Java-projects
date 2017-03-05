/**
 * Created by Vlad on 03.10.2016.
 */
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Game {
    JFrame frame;
    MyDrawPanel panel;
    int[][] matrix;
    JButton startButton;
    int buttonStatement = 0;
    boolean isExitFound = false;
    JTextArea hello;
    public static void main(String[] args) {
        Game starter = new Game();
        starter.go();
    }

    public void go() {
        setUpGui();
        matrix = new int[16][21];
    }

    public void setUpGui() {
        frame = new JFrame("Prudnikoff game");
        hello = new JTextArea("   Приветствуем Вас на борту корабля Alien2! Ваша миссия: построить корабль, на котором \n " +
                "                                    Вам предстоит похитить" + " студента БГУИР. Удачи!");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        startButton = new JButton("Начать игру");
        startButton.addActionListener(new startGameButton());
        panel = new MyDrawPanel();
        panel.addMouseListener(new MyDrawPanel());
        panel.setBackground(Color.GREEN);
        frame.getContentPane().add(BorderLayout.NORTH, hello);
        frame.getContentPane().add(BorderLayout.CENTER, panel);
        frame.getContentPane().add(BorderLayout.SOUTH, startButton);
        frame.setBounds(30, 30, 630, 536);
        frame.setVisible(true);
    }

    int statement = 0;
    int x = 0;
    int y = 0;
    int xBegin = 0;
    int yBegin = 0;
    boolean isItLine = false;

    class startGameButton implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            switch (buttonStatement) {
                case 0: {
                    hello.setText("          Ваш корабль - это космический 2D-лабиринт, который Вам предстоит построить. \n " +
                            "                  Коснитесь любых двух квадратов - и вы поймете, как это работает");
                    startButton.setText("Закончить стройку корабля");
                    buttonStatement = 1;
                    statement = 1;
                    panel.repaint();
                    break;
                }
                case 1: {
                    hello.setText("                                    Теперь нужно выбрать место, куда посадить героя. \n " +
                            "                                                       Заточите его поглубже!");
                    isItLine = false;
                    statement = 3;
                    buttonStatement = 2;
                    startButton.setText("Выбрать местоположение");
                    panel.repaint();
                    break;
                }
                case 2: {
                    hello.setText("О нет! Наш студент оказался хорошим программистом, знающим алгоритм поиска в глубину. \n " +
                            "  Он распустил свой свитшот на нитки и с помощью них стал помечать места, где уже был.");
                    startButton.setText("Посмотреть, что из этого вышло");
                    buttonStatement = 3;
                    break;
                }
                case 3: {
                    statement = 5;
                    buttonStatement = 4;
                    startButton.setText("Закончить игру");
                    panel.repaint();
                    break;
                }
                case 4: {
                    frame.dispose();
                    break;
                }
            }
        }
    }

    public class MyDrawPanel extends JPanel implements MouseListener, MouseMotionListener {

        public void mouseEntered(MouseEvent mEvent) {}
        public void mouseExited(MouseEvent me) {}
        public void mouseClicked(MouseEvent me) {}
        public void mouseReleased(MouseEvent me) {}
        public void mouseDragged(MouseEvent me) {}
        public void mouseMoved(MouseEvent me) {}

        public void mousePressed(MouseEvent me) {
            if (statement == 1 || statement == 2) {
                statement = 2;
            }
            x = me.getX();
            y = me.getY();
            panel.repaint();
        }

        public void paintComponent(Graphics gr) {
            System.out.println("St = " + statement);
            switch (statement) {
                case 0: {
                    try {
                        Image img = ImageIO.read(new File("background.jpg"));
                        gr.drawImage(img, 5, 5, null);
                    }
                    catch(IOException ex) {
                        Logger.getLogger(JFrame.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    break;
                }
                case 1: drawField(gr); break;
                case 2: {
                    gr.setColor(Color.orange);
                    dotNormalize();
                    drawPoint(gr, x, y);
                    break;
                }
                case 3: {
                    statement++;
                    break;
                }
                case 4: {
                    gr.setColor(Color.red);
                    dotNormalize();
                    drawPoint(gr, x, y);
                    statement = 0;
                    break;
                }
                case 5: {
                    act(gr);
                    break;
                }
                case 6: {
                    if (isExitFound) {
                        hello.setText("      Студент выбрался! Зелёным цветом обозначен выход, красным - место заточения, \n " +
                                "                                 бирюзовым - места, где ходил студент. Конец игры =)");
                    } else {
                        hello.setText("                А вы коварный человек! Построили такой корабль, что даже студент БГУИР \n " +
                                "                                            не смог выбраться. Поздравляем! =)");
                    }
                    buttonStatement = 4;
                    break;
                }
            }
        }

        public void drawField(Graphics fGr) {
            clean(fGr);
            fGr.setColor(new Color(192, 192, 192));
            for (int i = 0; i < 640; i += 30) {
                fGr.drawLine(i, 0, i, 491);
            }
            for (int i = 0; i < 491; i += 30) {
                fGr.drawLine(0, i, 640, i);
            }
        }

        public void clean(Graphics cGr) {
            cGr.setColor(new Color(	240, 255, 255));
            cGr.fillRect(0, 0, this.getWidth(), this.getHeight());
        }

        public void dotNormalize() {
            x = x/30 * 30;
            y = y/30 * 30;
        }

        public void drawPoint(Graphics rGr, int x, int y) {
            if (isItLine & (xBegin == x|| yBegin == y)) {
                isItLine = false;
                drawLine(rGr, xBegin, yBegin, x, y);
            } else {
                xBegin = x;
                yBegin = y;
                fillMatrix(x, y);
                rGr.fillRect(x, y, 30, 30);
                isItLine = true;
            }
        }

        public void drawLine(Graphics lGr, int xBegin, int yBegin, int x, int y) {
            if (xBegin == x) {
                for (int i = Math.min(y, yBegin) + 1; i <= Math.max(y, yBegin); i++) {
                    drawPoint(lGr, x, i);
                }
            } else {
                for (int i = Math.min(x, xBegin) + 1; i <= Math.max(x, xBegin); i++) {
                    drawPoint(lGr, i, y);
                }
            }
            isItLine = false;
        }

        public void fillMatrix(int x, int y) {
            int xMatrixCoordinate = x/30;
            int yMatrixCoordinate = y/30;
            matrix[yMatrixCoordinate][xMatrixCoordinate] = 1;
            //printMatrix();
        }

        public void printMatrix() {
            for (int i = 0; i < 16; i++) {
                for (int j = 0; j < 21; j++) {
                    System.out.print(matrix[i][j] + " ");
                }
                System.out.println();
            }
        }

        public void act(Graphics aGr) {
            DFS(aGr, y/30, x/30);
            drawWay(aGr);
        }

        ArrayList<Integer> xQueue = new ArrayList<>();
        ArrayList<Integer> yQueue = new ArrayList<>();

        public boolean DFS (Graphics dGr, int y_c, int x_c) {
            //System.out.println(x_c + " " + y_c);
            xQueue.add(x_c * 30);
            yQueue.add(y_c * 30);
            byte[] towardX = new byte[4];
            byte[] towardY = new byte[4];
            towardX[0] = 1;
            towardX[1] = -1;
            towardX[2] = 0;
            towardX[3] = 0;
            towardY[0] = 0;
            towardY[1] = 0;
            towardY[2] = 1;
            towardY[3] = -1;
            matrix[y_c][x_c] = 2;
            //printMatrix();
            if (y_c == 0 || y_c == 15 || x_c == 0 || x_c == 20) {
                isExitFound = true;
                System.out.println("Exit has been found");
                return false;
            } else {
                for (int i = 0; i < 4; i++) {
                    if (matrix[y_c + towardY[i]][x_c + towardX[i]] == 0 && !isExitFound) {
                        DFS(dGr, y_c + towardY[i], x_c + towardX[i]);
                    }
                }
                return true;
            }
        }

        public void drawWay(Graphics wGr) {
            statement = 6;
            wGr.setColor(new Color(135, 206, 235));
            for (int i = 1; i < xQueue.size() - 1; i++) {
                x = xQueue.get(i);
                y = yQueue.get(i);
                wGr.fillRect(x, y, 30, 30);
                panel.repaint();
            }
            int checkX = xQueue.get(xQueue.size() - 1)/30;
            int checkY = yQueue.get(yQueue.size() - 1)/30;
            if (checkY == 0 || checkY == 15 || checkX == 0 || checkX == 20) {
                isExitFound = true;
                wGr.setColor(Color.green);
                wGr.fillRect(xQueue.get(xQueue.size() - 1), yQueue.get(yQueue.size() - 1), 30, 30);
            } else isExitFound = false;
        }

        public void slow (int n) {
            long t0, t1;
            t0 = System.currentTimeMillis();
            do {
                t1=System.currentTimeMillis();
            } while (t1-t0 < n);
        }
    }
}
