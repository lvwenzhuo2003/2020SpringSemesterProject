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
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

public class Gui implements Serializable, ActionListener {
    private final JFrame jFrame = new JFrame("New Session");
    private final JPanel comboPanel = new JPanel();
    private final JPanel northPanel = new JPanel();
    private final JPanel southPanel = new JPanel();
    private final JPanel eastPanel = new JPanel();
    private final JPanel westPanel = new JPanel();
    private final JLabel title = new JLabel("Welcome to use management system!");
    private final JTextArea amount = new JTextArea();
    private final JTextArea productName = new JTextArea();
    private final JTextArea customerName = new JTextArea();
    private final JButton confirm = new JButton();
    private final JButton records = new JButton();
    private final JLabel productNameLabel = new JLabel("Product Name: ");
    private final JLabel amountLabel = new JLabel("Amount: ");
    private final JLabel customerNameLabel = new JLabel("Customer Name: ");
    ArrayList<Order> record = read();

    public static void main(String[] args) {
        new Gui();
    }

    public Gui() {
        jFrame.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        jFrame.setSize(400,200);
        comboPanel.setLayout(new BorderLayout(10,10));
        int windowWidth = jFrame.getWidth();
        int windowHeight = jFrame.getHeight();
        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension screenSize = kit.getScreenSize();
        int screenWidth = screenSize.width;
        int screenHeight = screenSize.height;
        jFrame.setLocation(screenWidth / 2 - windowWidth / 2, screenHeight / 2 - windowHeight / 2);

        //set title(north)
        northPanel.setLayout(new BorderLayout(10,10));
        northPanel.add(title,BorderLayout.CENTER);

        //set textfields(west)
        westPanel.setLayout(new BorderLayout(10,10));
        productName.setLineWrap(false);
        amount.setLineWrap(false);

        westPanel.add(productName,BorderLayout.NORTH);
        westPanel.add(amount,BorderLayout.CENTER);
        westPanel.add(customerName,BorderLayout.SOUTH);

        //set textfield titles(east)
        eastPanel.setLayout(new BorderLayout(10,10));
        eastPanel.add(productNameLabel,BorderLayout.NORTH);
        eastPanel.add(amountLabel,BorderLayout.CENTER);
        eastPanel.add(customerNameLabel,BorderLayout.SOUTH);

        //set buttons(south)
        confirm.addActionListener(this);
        records.addActionListener(this);
        southPanel.setLayout(new BorderLayout(10,10));
        southPanel.add(confirm,BorderLayout.WEST);
        southPanel.add(records,BorderLayout.EAST);

        //pack up and show
        comboPanel.add(northPanel,BorderLayout.NORTH);
        comboPanel.add(westPanel,BorderLayout.WEST);
        comboPanel.add(eastPanel,BorderLayout.EAST);
        comboPanel.add(southPanel,BorderLayout.SOUTH);
        jFrame.add(comboPanel);
        jFrame.setVisible(true);
        jFrame.pack();
    }

    public ArrayList<Order> read() {
        ArrayList<Order> recording = new ArrayList<Order>();
        try {
            ObjectInputStream in = new ObjectInputStream(new FileInputStream("./temp/record.ser"));
            recording = (ArrayList<Order>) in.readObject();
            in.close();
        } catch (EOFException ex){
            recording = new ArrayList<Order>();
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

    public void write(ArrayList<Order> record) {
        try {
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("./temp/record.ser"));
            out.writeObject(record);
            out.close();
            JOptionPane.showMessageDialog(null, "Serialized data write successfully!");
        } catch (IOException exp) {
            JOptionPane.showMessageDialog(null, "Internal error. ");
        } catch (NumberFormatException exp){
            JOptionPane.showMessageDialog(null, "Incorrect input type! ");
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
            write(record);
            JOptionPane.showMessageDialog(null, "Successfully saved file. ");
        } else if (e.getSource() == records) {
            jFrame.setVisible(false);
        }
    }
}
