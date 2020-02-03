package GUI;


import javax.swing.*;

public class Main extends JFrame {
    public Main() {
        GridTest xyz = new GridTest(200, 200, 20, 20);
        add(xyz);
        pack();
    }

    public static void main(String[] a) {
        new Main().setVisible(true);
    }
}