import javax.swing.*;

/**
 * @author AshwinAnand868
 * @version 1.0
 * @email anandashwin868@gmail.com
 * @since 2024-05-23
 */
public class GameFrame extends JFrame {
    GameFrame() {
         // adding a panel to the frame
        this.add(new GamePanel());
        this.setTitle("Snake");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }
}
