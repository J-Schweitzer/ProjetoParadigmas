package gerenciapetshop;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import visao.LoginV;

/**
 *
 * @author Joao
 */
public class GerenciaPetShop {

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) { }

        SwingUtilities.invokeLater(() -> {
            LoginV login = new LoginV();
            login.setVisible(true);
        });
    }

}
