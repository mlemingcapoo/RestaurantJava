package frame;

import javax.swing.*;

public class hmmm extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;

    public hmmm() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
    }

    public static void main(String[] args) {
        hmmm dialog = new hmmm();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }
}
