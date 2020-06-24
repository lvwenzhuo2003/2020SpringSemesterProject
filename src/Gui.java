import javax.swing.*;

public class Gui {
    private JFrame jFrame = new JFrame("New Session");
    private JMenuBar jMenuBar = new JMenuBar();
    public static void main(String[] args){
        new Gui();
    }
    public Gui(){
        jFrame.pack();
    }
}
