/*
 * Military University of Technology
 * Engine house project
 * to pass concurrent programming
 *
 * kamil.tyszkiewicz47@gmail.com
 * WCY18IY4S1
 * 12.05.2020
 */

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class Main implements ActionListener {

    JFrame frame;
    JLabel MLabel, repairLabel1, repairLabel2, track1Label,
            track2Label, headOfRenovation, closeEngine, howLongRotary,
            div, rotarylogLabel, renovationLabel, trainLeftLabel,
            ProcesyLabel, coIlePociagLabel;
    JScrollPane sp1, sp2, sp3, sp4, sp5;
    public static JTextArea track1TextArea, track2TextArea, renovationTextArea, trainLeftTextArea, rotarylogTextArea, rotaryTextArea, MTextArea,
            repairTextArea, closeEngineTextArea, ProcesyTextArea, coIlePociagTextArea;
    private JButton onePlusButton, oneMinusButton, closeHouseButton, openHouseButton;
    volatile public static boolean flag = false;

    volatile public static boolean[] placeIsFree;

    private Random random = new Random();

    volatile public static int M=1;

    public Main(){
        {   //Ramka
            frame = new JFrame("Parowazownia");
            frame.setSize(1900, 1000);
            frame.setLayout(null);
            frame.setVisible(true);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setResizable(true);
        }
        {   //JLabel: "Liczba pociagow (Procesow):"
            ProcesyLabel = new JLabel("Liczba pociągów (Procesow):");
            ProcesyLabel.setBounds(10,10, 190, 20);
            frame.add(ProcesyLabel);
        }
        {   //JTextArea: (s)
            ProcesyTextArea = new JTextArea(1,5);
            ProcesyTextArea.setEditable(true);
            ProcesyTextArea.setBounds(10,35,75,20);
            ProcesyTextArea.setVisible(true);
            frame.add(ProcesyTextArea);
        }
        {   //JLabel: "Co ile sekund pojawia sie pociag:"
            coIlePociagLabel = new JLabel("Co ile sekund pojawia sie lokomotywa:");
            coIlePociagLabel.setBounds(10,55, 230, 20);
            frame.add(coIlePociagLabel);
        }
        {   //JTextArea: (s)ProcesyTextArea
            coIlePociagTextArea = new JTextArea(1,5);
            coIlePociagTextArea.setEditable(true);
            coIlePociagTextArea.setBounds(10,80,75,20);
            coIlePociagTextArea.setVisible(true);
            frame.add(coIlePociagTextArea);
        }
        {   //JLabel: "Liczba miejsc remontowych"
            MLabel = new JLabel("Liczba miejsc remontowych (M):");
            MLabel.setBounds(10,110, 190, 20);
            frame.add(MLabel);
        }
        {   //JTextArea: M
            MTextArea = new JTextArea(1,5);
            MTextArea.setEditable(false); //Mozliwosc odblokowania recznego pisania w okienku
            MTextArea.setBounds(200,111,25,20);
            MTextArea.setVisible(true);
            MTextArea.setText("1");
            frame.add(MTextArea);
        }
        {   //+1Button
            onePlusButton = new JButton("+ 1");
            onePlusButton.setBounds(10, 140, 70, 30);
            frame.add(onePlusButton);
            onePlusButton.addActionListener(this);
        }
        {   //-1Button
            oneMinusButton = new JButton("- 1");
            oneMinusButton.setBounds(90, 140, 70, 30);
            frame.add(oneMinusButton);
            oneMinusButton.addActionListener(this);
        }
        {   //JLabel: "Maksymalny czas naprawy lokomotywy (s)"
            repairLabel1 = new JLabel("Maksymalny czas naprawy ");
            repairLabel1.setBounds(10,180, 190, 20);
            frame.add(repairLabel1);
            repairLabel2 = new JLabel("lokomotywy (s): ");
            repairLabel2.setBounds(10,195, 190, 20);
            frame.add(repairLabel2);
        }
        {   //JTextArea: (s)
            repairTextArea = new JTextArea(1,5);
            repairTextArea.setEditable(true);
            repairTextArea.setBounds(10,220,75,20);
            repairTextArea.setVisible(true);
            frame.add(repairTextArea);
        }
        {   //JLabel: "Ile czasu obraca sie nastawnia: howLongRotary
            howLongRotary = new JLabel("Ile czasu obraca się nastawnia (s): ");
            howLongRotary.setBounds(10,240, 200, 20);
            frame.add(howLongRotary);
        }
        {   //JTextArea: (s)
            rotaryTextArea = new JTextArea(1,5);
            rotaryTextArea.setEditable(true);
            rotaryTextArea.setBounds(10,260,75,20);
            rotaryTextArea.setVisible(true);
            frame.add(rotaryTextArea);
        }
        {   //Button: "Rozpocznij prace parowazowni"
            openHouseButton = new JButton("Rozpocznij pracę parowazowni");
            openHouseButton.setBounds(10, 290, 220, 60);
            openHouseButton.addActionListener(this);
            frame.add(openHouseButton);
        }
        {   //JLabel: "_________________________"
            div = new JLabel("_______________________________");
            div.setBounds(10,345, 230, 20);
            frame.add(div);
        }

        {   //JLabel: "Szef robot remontowych: \n Zamknij parowazownie na (s):
            headOfRenovation = new JLabel("Szef robót remontowych:");
            headOfRenovation.setBounds(10,370, 190, 20);
            frame.add(headOfRenovation);
            closeEngine = new JLabel("Zamknij parowazownię na (s):");
            closeEngine.setBounds(11,390, 190, 20);
            frame.add(closeEngine);
        }
        {   //JTextArea: (s)
            closeEngineTextArea = new JTextArea(1,5);
            closeEngineTextArea.setEditable(true);
            closeEngineTextArea.setBounds(10,410,50,20);
            closeEngineTextArea.setVisible(true);
            closeEngineTextArea.setEnabled(false);
            frame.add(closeEngineTextArea);
        }
        {   //Button: "Zamknij parowazownię"
            closeHouseButton = new JButton("Zamknij parowazownię");
            closeHouseButton.setBounds(10, 440, 220, 60);
            closeHouseButton.addActionListener(this);
            closeHouseButton.setEnabled(false);
            frame.add(closeHouseButton);
        }
        //////////////////////////////////////////////////////////////////////////////////
        {   //JLabel: "Tor1:"
            track1Label = new JLabel("Tor 1:");
            track1Label.setBounds(255,10, 50, 20);
            frame.add(track1Label);
        }
        {   //JTextArea: trackTextArea
            track1TextArea = new JTextArea(25,25); //Tu moze byc problem potem
            track1TextArea.setEditable(false);
            track1TextArea.setVisible(true);
            frame.add(track1TextArea);

            sp1 = new JScrollPane(track1TextArea);
            sp1.setLocation(255, 35); //?
            sp1.setSize(300, 915);
            sp1.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
            frame.add(sp1);
            track1TextArea.repaint();
        }
        {   //JLabel: "Tor2:"
            track2Label = new JLabel("Tor 2:");
            track2Label.setBounds(570,10, 120, 20);
            frame.add(track2Label);
        }
        {   //JTextArea: trackTextArea
            track2TextArea = new JTextArea(25,25); //Tu moze byc problem potem
            track2TextArea.setEditable(false);
            track2TextArea.setVisible(true);
            frame.add(track2TextArea);

            sp2 = new JScrollPane(track2TextArea);
            sp2.setLocation(570, 35); //?
            sp2.setSize(300, 915);
            sp2.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
            frame.add(sp2);
            track2TextArea.repaint();
        }
        {   //JLabel: "Obrotowa nastawnia:"
            rotarylogLabel = new JLabel("Obrotowa nastawnia:");
            rotarylogLabel.setBounds(885,10, 150, 20);
            frame.add(rotarylogLabel);
        }
        {   //JTextArea: trackTextArea
            rotarylogTextArea = new JTextArea(25,25); //Tu moze byc problem potem
            rotarylogTextArea.setEditable(false);
            rotarylogTextArea.setVisible(true);
            frame.add(rotarylogTextArea);

            sp3 = new JScrollPane(rotarylogTextArea);
            sp3.setLocation(885, 35);
            sp3.setSize(300, 915);
            sp3.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
            frame.add(sp3);
            rotarylogTextArea.repaint();
        }
        {   //JLabel: "Miejsca remontowe:"
            renovationLabel = new JLabel("Miejsca remontowe:");
            renovationLabel.setBounds(1200,10, 120, 20);
            frame.add(renovationLabel);
        }
        {   //JTextArea: trackTextArea
            renovationTextArea = new JTextArea(25,25); //Tu moze byc problem potem
            renovationTextArea.setEditable(false);
            renovationTextArea.setVisible(true);
            frame.add(renovationTextArea);

            sp4 = new JScrollPane(renovationTextArea);
            sp4.setLocation(1200, 35);
            sp4.setSize(300, 915);
            sp4.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
            frame.add(sp4);
            renovationTextArea.repaint();
        }

        {   //JLabel: "Pociagi, ktore opuscily warsztat:"
            trainLeftLabel = new JLabel("Pociągi, które opuściły warsztat:");
            trainLeftLabel.setBounds(1515,10, 200, 20);
            frame.add(trainLeftLabel);
        }
        {   //JTextArea: trackTextArea
            trainLeftTextArea = new JTextArea(25,25); //Tu moze byc problem potem
            trainLeftTextArea.setEditable(false);
            trainLeftTextArea.setVisible(true);
            frame.add(trainLeftTextArea);

            sp5 = new JScrollPane(trainLeftTextArea);
            sp5.setLocation(1515, 35);
            sp5.setSize(300, 915);
            sp5.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
            frame.add(sp5);
            trainLeftTextArea.repaint();
        }
        frame.repaint();
    }

    public static void main(String[] args) {
        new Main();
    }
    @Override
    public void actionPerformed(ActionEvent event){
        if(event.getSource() == onePlusButton){
            M++;
            MTextArea.setText(Integer.toString(M));
        }
        if(event.getSource() == oneMinusButton){
            if(M>1) M--;
            else return;
            MTextArea.setText(Integer.toString(M));
        }
        if(event.getSource() == openHouseButton){
            repairTextArea.setEditable(false);
            onePlusButton.setEnabled(false);
            oneMinusButton.setEnabled(false);
            openHouseButton.setEnabled(false);
            rotaryTextArea.setEditable(false);
            closeEngineTextArea.setEnabled(true);
            closeHouseButton.setEnabled(true);
            ProcesyTextArea.setEnabled(false);
            coIlePociagTextArea.setEnabled(false);

            placeIsFree = new boolean[M];
            for(int i=0;i<M;i++){
                placeIsFree[i] = true;
            }
            Thread timerThread = new Thread(new Runnable() {
            @Override
            public void run() { //Co ile pojawia sie pociag
                int randomEntryNumber = random.nextInt(Integer.parseInt(coIlePociagTextArea.getText())*1000)+1000;
                Thread[] train = new Thread[Integer.parseInt(ProcesyTextArea.getText())];
                for(int i=0;i<train.length;i++){
                    try{
                        train[i] = new Train(i);
                        train[i].start();
                        Thread.sleep(randomEntryNumber);
                    } catch (InterruptedException e){
                        e.printStackTrace();
                    }
                }
            }});
        timerThread.start();
        }
        if(event.getSource() == closeHouseButton){
            closeHouseButton.setEnabled(false);
            closeEngineTextArea.setEnabled(false);
            flag = true;
            Thread timerThread2 = new Thread(new Runnable() {
                @Override
                public void run() { //Co ile pojawia sie pociag
                    try{    //Zamkniecie parowazowni na closeEngineTextArea sekund
                        addRenovationTextArea("Zarzadzono remont! Pozostale pojazdy" + "\n" + "opuszczaja parowazownie!");
                        addRenovationTextArea("Nikt nie moze wjechac przez " + closeEngineTextArea.getText() + " sekund!");
                        Thread.sleep((Integer.parseInt(closeEngineTextArea.getText()))*1000);
                        addRenovationTextArea("Parowazownia otwarta!");
                        closeHouseButton.setEnabled(true);
                        closeEngineTextArea.setEnabled(true);
                        flag = false;
                    } catch (InterruptedException e){ e.printStackTrace(); }
                }});
            timerThread2.start();
        }

    }
    public void addRenovationTextArea(String text){
        String currentText1 = Main.renovationTextArea.getText();
        String newText1 = text;
        String newTextToAppend = currentText1 + "\n" + newText1;
        Main.renovationTextArea.setText(newTextToAppend);
    }
}