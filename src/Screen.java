import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import javax.swing.*;
import java.io.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;


public class Screen extends JFrame {
    private JPanel panelMain;
    private JPanel panelTop;
    private JPanel panelLeft;
    private JPanel panelRight;
    private JList listPeople;
    private JTextField textName;
    private JTextField textEmail;
    private JTextField textPhoneNumber;
    private JTextField textAddress;
    private JTextField textDOB;
    private JButton buttonNew;
    private JButton buttonSave;
    private JLabel labelAge;
    private JButton buttonClear;
    private JPanel Picture;

    private ArrayList<Person> people;
    private DefaultListModel listPeopleModel;

    Screen() {
        super("My Contacts Project");
        this.setContentPane(this.panelMain);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        people = new ArrayList<Person>();
        listPeopleModel = new DefaultListModel();
        listPeople.setModel(listPeopleModel);
        buttonSave.setEnabled(false);

        buttonNew.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buttonNewClick(e);
            }
        });
        buttonSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buttonSavedClick(e);
            }
        });
        listPeople.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                listPeopleSelection(e);
            }
        });


        buttonClear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buttonClearClick(e);
            }
        });
    }

    public void buttonClearClick(ActionEvent e){
        textName.setText(" ");
        textEmail.setText(" ");
        textPhoneNumber.setText(" ");
        textAddress.setText(" ");
        textDOB.setText(" ");

    }

    public void buttonNewClick(ActionEvent e) {
        String DOB = " ";
        if (DOB.equals(textDOB.getText()))
            DOB = "01/01/2000";
        else
            DOB = textDOB.getText();

        Person p = new Person(
                textName.getText(),
                textEmail.getText(),
                textPhoneNumber.getText(),
                textAddress.getText(),
                DOB
        );
        people.add(p);
        refreshPeopleList();
    }

    public void buttonSavedClick(ActionEvent e) {
        int personNumber = listPeople.getSelectedIndex();
        String empty = " ";
        if (personNumber >= 0) {
            Person p = people.get(personNumber);
            p.setName(textName.getText());
            p.setEmail(textEmail.getText());
            p.setPhoneNumber(textPhoneNumber.getText());
            p.setAddress(textAddress.getText());
            if (empty.equals(textDOB.getText()))
                p.setDateOfBirth("01/01/2000");
            else
                p.setDateOfBirth(textDOB.getText());
            refreshPeopleList();
        }
    }

    public void listPeopleSelection(ListSelectionEvent e) {
        int personNumber = listPeople.getSelectedIndex();
        if ( personNumber >= 0){
            Person p = people.get(personNumber);
            textName.setText(p.getName());
            textEmail.setText(p.getEmail());
            textPhoneNumber.setText(p.getPhoneNumber());
            textAddress.setText(p.getAddress());
            textDOB.setText(p.getDateOfBirth().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
            labelAge.setText((Integer.toString(p.getAge()) + " years"));
            buttonSave.setEnabled(true);
        }
        else
            buttonSave.setEnabled(false);
    }

    public void refreshPeopleList() {
        listPeopleModel.removeAllElements();
        System.out.println("Removing all people from list");
        for (Person p : people){
            System.out.println("Adding person to list: " + p.getName() );
            listPeopleModel.addElement(p.getName());
        }

    }

    public void addPerson(Person p){
        people.add(p);
        refreshPeopleList();
    }




    public static void main(String[] args) throws IOException{
        Screen screen = new Screen();
        screen.setSize(750,500);
        screen.setVisible(true);

        screen.Picture.setLayout(new FlowLayout());
        BufferedImage myPicture = ImageIO.read(new File("Addressbook.png"));
        Image dmyPicture = myPicture.getScaledInstance(212, 124, Image.SCALE_SMOOTH);
        JLabel picLabel = new JLabel(new ImageIcon(dmyPicture));
        screen.Picture.add(picLabel);


        FileWriter fw = new FileWriter("Contacts.txt",true);
        fw.write("Mr Infantozzi, r.infantozzi@isturin.it, 3919999999, Via Roma 999, 22/09/1979\n");
        fw.close();

        Person infantozzi = new Person("Mr Infantozzi", "r.infantozzi@isturin.it", "391 999 9999", "Via Roma 999", "22/09/1979" );
        screen.addPerson(infantozzi);

    }
}
