import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.awt.event.*;
import java.util.ArrayList;

public class Gui implements Serializable{
    private JFrame            jFrame        = new JFrame("New Session");
    private JPanel comboPanel = new JPanel();
    private JPanel northPanel = new JPanel();
    private JPanel southPanel = new JPanel();
    private JPanel eastPanel = new JPanel();
    private JPanel westPanel = new JPanel();
    private JPanel centerPanel = new JPanel();
    private JLabel title = new JLabel("Welcome to use management system!");
   //private JMenuBar          jMenuBar      = new JMenuBar();
    private JMenu             jMenu         = new JMenu();
    private JTextField         amount        = new JTextField();
    private JTextField productName   = new JTextField();
    private JTextField         customerName  = new JTextField();
    private JButton           confirm       = new JButton();
    private JButton           clear         = new JButton();
    public static void main(String[] args){
        new Gui();
    }
    public Gui() {
        //init
        ArrayList<String> record = read();
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
        jFrame.setLocation(screenWidth/2-windowWidth/2, screenHeight/2-windowHeight/2);

        //set title
        northPanel.add(title);


        //pack up and show
        jFrame.setVisible(true);
        jFrame.add(northPanel);
        jFrame.add(comboPanel);
        jFrame.pack();
    }
    public ArrayList<String> read(){
        ArrayList<String> record = new ArrayList<String>();
        try {
            ObjectInputStream in = new ObjectInputStream(new FileInputStream("./temp/record.ser"));
            record = (ArrayList<String>) in.readObject();
            in.close();
        } catch (FileNotFoundException ex){
            JOptionPane.showMessageDialog(null,"File not found");
        } catch (IOException ex){
            ex.printStackTrace();
        } catch (ClassNotFoundException ex){
            JOptionPane.showMessageDialog(null,"Records not found");
            ex.printStackTrace();
        }
        return record;
    }
    public void write(ArrayList<String> record){
        try {
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("./temp/record.ser"));
            out.writeObject(record);
            out.close();
            JOptionPane.showMessageDialog(null,"Serialized data write successfully!");
        } catch (IOException exp){
            exp.printStackTrace();
        }
    }
}
