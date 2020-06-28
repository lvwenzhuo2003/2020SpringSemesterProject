import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;

public class Gui implements Serializable, ActionListener {
    private final JFrame     jFrame            = new JFrame("New Session");
    private final JPanel     comboPanel        = new JPanel();
    private final JPanel     northPanel        = new JPanel();
    private final JPanel     southPanel        = new JPanel();
    private final JPanel     eastPanel         = new JPanel();
    private final JPanel     westPanel         = new JPanel();
    private final JPanel     centerPanel       = new JPanel();
    private final JLabel     title             = new JLabel("Welcome to use management system!");
    //private JMenuBar          jMenuBar      = new JMenuBar();
    private final JMenu      jMenu             = new JMenu();
    private final JTextField amount            = new JTextField();
    private final JTextField productName       = new JTextField();
    private final JTextField customerName      = new JTextField();
    private final JButton    confirm           = new JButton();
    private final JButton    records           = new JButton();
    private final JLabel     productNameLabel  = new JLabel("Product Name: ");
    private final JLabel     amountLabel       = new JLabel("Amount: ");
    private final JLabel     customerNameLabel = new JLabel("Customer Name: ");
    ArrayList<Order> record = read();

    public static void main(String[] args) {
        new Gui();
    }

    public Gui() {
        //init
        jFrame.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        comboPanel.setLayout(new BorderLayout());
        comboPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 0));


        //set window in the middle of the screen
        int windowWidth = jFrame.getWidth();
        int windowHeight = jFrame.getHeight();
        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension screenSize = kit.getScreenSize();
        int screenWidth = screenSize.width;
        int screenHeight = screenSize.height;
        jFrame.setLocation(screenWidth / 2 - windowWidth / 2, screenHeight / 2 - windowHeight / 2);

        //set title(north)
        northPanel.add(title);

        //set textfields(west)
        westPanel.add(productName);
        westPanel.add(amount);
        westPanel.add(customerName);

        //set textfield titles(east)
        eastPanel.add(productNameLabel);
        eastPanel.add(amountLabel);
        eastPanel.add(customerNameLabel);

        //set buttons(south)
        confirm.addActionListener(this);
        records.addActionListener(this);

        //pack up and show
        jFrame.setVisible(true);
        jFrame.add(northPanel);
        jFrame.add(comboPanel);
        jFrame.pack();
    }

    public ArrayList<Order> read() {
        ArrayList<Order> recording = new ArrayList<Order>();
        try {
            ObjectInputStream in = new ObjectInputStream(new FileInputStream("./temp/record.ser"));
            recording = (ArrayList<Order>) in.readObject();
            in.close();
        } catch (FileNotFoundException ex) {
            JOptionPane.showMessageDialog(null, "File not found");
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(null, "Records not found");
            ex.printStackTrace();
        }
        return recording;
    }

    public void write(ArrayList<String> record) {
        try {
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("./temp/record.ser"));
            out.writeObject(record);
            out.close();
            JOptionPane.showMessageDialog(null, "Serialized data write successfully!");
        } catch (IOException exp) {
            exp.printStackTrace();
        }
    }

    /**
     * Invoked when an action occurs.
     *
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == confirm) {
            record.add(new Order(productName.getText(), Integer.parseInt(amount.getText()), customerName.getText()));
        } else if (e.getSource() == records) {

        }
    }
}
