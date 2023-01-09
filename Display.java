import java.awt.Color;

import javax.swing.*;
import javax.swing.border.LineBorder;

public class Display extends JFrame {
    
    public static JButton[][] display = new JButton[8][8];

    Display() {
        this.setTitle("Chess");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(null);
        this.setResizable(false);
        this.setSize(1200, 840);
        this.setVisible(true);

        for (int col = 0; col < 8; col++) {
            for (int row = 0; row < 8; row++) {
                display[col][row] = new JButton();
                display[col][row].setOpaque(true);
                display[col][row].setBorder(new LineBorder(Color.BLACK));
                this.add(display[col][row]);
                if ((col + row)%2 == 1)
                    display[col][row].setBackground(Color.white);
                else
                    display[col][row].setBackground(Color.green);
            }
        }
    }

    //Change to GUI soon
    public void draw(String turn) {
        for (int col = 0; col < 8; col++) {
            for (int row = 0; row < 8; row++) {
                if (turn.equals("white")) 
                    display[col][row].setBounds(col*64, (7-row)*64, 64, 64);
                else
                    display[col][row].setBounds((7-col)*64, row*64, 64, 64);
            }
        }
    }

}
