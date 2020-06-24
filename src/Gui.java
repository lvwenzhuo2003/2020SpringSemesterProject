import javax.swing.*;
import java.io.*;
import java.util.ArrayList;

public class Gui {
    private JFrame            jFrame        = new JFrame("New Session");
    private JMenuBar          jMenuBar      = new JMenuBar();
    private JMenu             jMenu         = new JMenu();
    private JTextArea         amount        = new JTextArea();
    private JTextArea         productName   = new JTextArea();
    private JTextArea         customerName  = new JTextArea();
    private JTextField        usernameLogin = new JTextField();
    private JPasswordField    passwordLogin = new JPasswordField();
    private JButton           confirm       = new JButton();
    private JButton           clear         = new JButton();
    private JTable            tableOfRecord = new JTable();
    private ArrayList<String> record        = read();
    public static void main(String[] args){
        new Gui();
    }
    public Gui(){

        jFrame.pack();
    }
    public ArrayList<String> read(){
        ArrayList<String> record = new ArrayList<String>();
        try {
            ObjectInputStream in = new ObjectInputStream(new FileInputStream("./temp/record.ser"));
            record = (ArrayList<String>) in.readObject();
            in.close();
        } catch (FileNotFoundException ex){
            System.out.println("File not found");
        } catch (IOException ex){
            ex.printStackTrace();
        } catch (ClassNotFoundException ex){
            System.out.println("Records not found");
            ex.printStackTrace();
        }
        return record;
    }
    public ArrayList<String> write(){
        try {
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("./temp/record.ser"));
            out.writeObject(record);
            out.close();
            System.out.println("Serialized data write successfully!");
        } catch (IOException exp){
            exp.printStackTrace();
        }
    }
}
