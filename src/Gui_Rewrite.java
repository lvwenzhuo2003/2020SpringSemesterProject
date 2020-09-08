import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.WindowConstants;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

@SuppressWarnings("all")
public class Gui_Rewrite implements ActionListener {
    private JFrame jFrame = new JFrame("New Session");
    private JFrame recordingFrame = new JFrame("Recording");
    private JPanel comboPanel = new JPanel();
    private JPanel northPanel = new JPanel();
    private JPanel southPanel = new JPanel();
    private JPanel eastPanel = new JPanel();
    private JPanel westPanel = new JPanel();
    private JPanel recordingNorthPanel = new JPanel();
    private JPanel recordingSouthPanel = new JPanel();
    private JPanel recordingCenterPanel = new JPanel();
    private JLabel recordingTitleLabel = new JLabel("Recordings");
    private JLabel titleLabel = new JLabel("Welcome to use storage management system! ");
    private JLabel productAmountLabel = new JLabel("Amount: ");
    private JLabel productNameLabel = new JLabel("Product name: ");
    private JLabel customerNameLabel = new JLabel("Customer name: ");
    private JTextArea recordingShowTextArea = new JTextArea();
    private JTextArea productAmountInput = new JTextArea(1,10);
    private JTextArea productNameInput = new JTextArea(1,10);
    private JTextArea customerNameInput = new JTextArea(1,10);
    private JButton backToMainButton = new JButton("Back");
    private JButton confirmAddRecordButton = new JButton("Confirm");
    private JButton showPreviousRecordButton = new JButton("Show Record");
    private ArrayList<Order> record;

    public static void main(String[] args){
        new Gui_Rewrite();
    }


    private Gui_Rewrite(){
        try {
            record = read();
        } catch (IOException | ClassNotFoundException | ClassCastException ex) {
            record = new ArrayList<Order>();
        }
        //set default frame with middle on the screen
        jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jFrame.setSize(400, 200);
        jFrame.setLayout(new BorderLayout());
        comboPanel.setLayout(new BorderLayout(10, 10));
        int windowWidth = jFrame.getWidth();
        int windowHeight = jFrame.getHeight();
        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension screenSize = kit.getScreenSize();
        int screenWidth = screenSize.width;
        int screenHeight = screenSize.height;
        jFrame.setLocation(screenWidth / 2 - windowWidth / 2, screenHeight / 2 - windowHeight / 2);

        //set north panel: the title
        setNorthPanel();

        //set south panel: the buttons
        setSouthPanel();

        //set west panel: the requirement titles
        setWestPanel();

        //set east panel: the input fields
        setEastPanel();

        //pack up and show
        jFrame.add(comboPanel, BorderLayout.CENTER);
        jFrame.setVisible(true);
        //jFrame.pack();

        //set recording frame with middle on the screen
        recordingFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        recordingFrame.setSize(400, 400);
        recordingFrame.setLayout(new BorderLayout());
        int recordingWindowWidth = jFrame.getWidth();
        int recordingWindowHeight = jFrame.getHeight();
        recordingFrame.setLocation(screenWidth / 2 - recordingWindowWidth / 2, screenHeight / 2 -
                recordingWindowHeight / 2);

        //set recording north panel: title
        setRecordingNorthPanel();

        //set recording center panel: recordings
        setRecordingCenterPanel();

        //set recording south panel: back button
        setRecordingSouthPanel();

        //pack up and show
        recordingFrame.setVisible(false);
        //recordingFrame.pack();
        return;
    }

    private void setRecordingSouthPanel(){
        backToMainButton.addActionListener(this);
        recordingSouthPanel.setLayout(new BorderLayout());
        recordingSouthPanel.add(backToMainButton,BorderLayout.CENTER);
        recordingFrame.add(recordingSouthPanel, BorderLayout.SOUTH);
        return;
    }

    private void setRecordingNorthPanel(){
        recordingNorthPanel.setLayout(new BorderLayout());
        recordingNorthPanel.add(recordingTitleLabel, BorderLayout.CENTER);
        recordingFrame.add(recordingNorthPanel, BorderLayout.NORTH);
        return;
    }

    private void setRecordingCenterPanel(){
        recordingCenterPanel.setLayout(new BorderLayout());
        recordingShowTextArea.append("Product Type\tAmount\tCustomer Name\n");
        for (Order order : record) {
            for (int j = 0; j < 3; j++) {
                if (j == 0) {
                    recordingShowTextArea.append(order.getProductType() + "\t");
                } else if (j == 1) {
                    recordingShowTextArea.append(order.getAmount() + "\t");
                } else if (j == 2) {
                    recordingShowTextArea.append(order.getCustomerName() + "\n");
                }
            }
        }
        recordingShowTextArea.setFocusable(false);
        recordingCenterPanel.add(recordingShowTextArea, BorderLayout.CENTER);
        recordingFrame.add(recordingCenterPanel, BorderLayout.CENTER);
        return;
    }

    private void setNorthPanel(){
        northPanel.setLayout(new BorderLayout(10, 10));
        northPanel.add(titleLabel, BorderLayout.CENTER);
        comboPanel.add(northPanel,BorderLayout.NORTH);
        return;
    }

    private void setSouthPanel(){
        confirmAddRecordButton.addActionListener(this);
        showPreviousRecordButton.addActionListener(this);
        southPanel.setLayout(new BorderLayout(10, 10));
        southPanel.add(confirmAddRecordButton, BorderLayout.WEST);
        southPanel.add(showPreviousRecordButton, BorderLayout.EAST);
        comboPanel.add(southPanel, BorderLayout.SOUTH);
        return;
    }

    private void setWestPanel(){
        westPanel.setLayout(new BorderLayout(10, 10));
        westPanel.add(productNameLabel, BorderLayout.NORTH);
        westPanel.add(productAmountLabel, BorderLayout.CENTER);
        westPanel.add(customerNameLabel, BorderLayout.SOUTH);
        comboPanel.add(westPanel, BorderLayout.WEST);
        return;
    }

    private void setEastPanel(){
        eastPanel.setLayout(new BorderLayout(10, 10));
        productNameInput.setLineWrap(false);
        productNameInput.setMinimumSize(new Dimension(20, 14));
        productAmountInput.setLineWrap(false);
        productAmountInput.setMinimumSize(new Dimension(20, 14));
        customerNameInput.setLineWrap(false);
        customerNameInput.setMinimumSize(new Dimension(20, 14));
        eastPanel.add(productNameInput, BorderLayout.NORTH);
        eastPanel.add(productAmountInput, BorderLayout.CENTER);
        eastPanel.add(customerNameInput, BorderLayout.SOUTH);
        comboPanel.add(eastPanel, BorderLayout.EAST);
        return;
    }

    private static ArrayList<Order> read() throws IOException, ClassNotFoundException, ClassCastException {
        ArrayList<Order> recording;
        ObjectInputStream in = new ObjectInputStream(new FileInputStream("./temp/record.ser"));
        recording = (ArrayList<Order>) in.readObject();
            in.close();
        return recording;
    }

    public static void write(ArrayList<Order> record) {
        try {
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("./temp/record.ser"));
            out.writeObject(record);
            out.close();
        } catch (IOException exp) {
            JOptionPane.showMessageDialog(null, "Internal error. ");
            exp.printStackTrace();
            System.exit(1);
        }
        return;
    }

    /**
     * Invoked when an action occurs.
     *
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == confirmAddRecordButton) {
            try {
                record.add(new Order(productNameInput.getText(), Integer.parseInt(productAmountInput.getText()),
                        customerNameInput.getText()));
                write(record);
                JOptionPane.showMessageDialog(null, "Successfully saved file. ");
                productAmountInput.setText("");
                productNameInput.setText("");
                customerNameInput.setText("");
                recordingShowTextArea.setText("");
                setRecordingCenterPanel();
            } catch (NumberFormatException exo) {
                JOptionPane.showMessageDialog(null,"Incorrect data type!");
            }
        } else if (e.getSource() == showPreviousRecordButton) {
            jFrame.setVisible(false);
            recordingFrame.setVisible(true);
        } else if (e.getSource() == backToMainButton) {
            recordingFrame.setVisible(false);
            productAmountInput.setText("");
            productNameInput.setText("");
            productAmountInput.setText("");
            jFrame.setVisible(true);
        }
        return;
    }
}
