import java.util.ArrayDeque;
import java.util.Queue;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Train extends Thread {

    volatile static int whichTrain;
    private Lock blokada = new ReentrantLock();
    static Queue<Integer> queueToRotary = new ArrayDeque<>();   //FIFO
    private int modulo;
    volatile static boolean rotaryFree = true;
    private int k;
    //Zmienna lokalna watku
    ThreadLocal<Boolean> local = new ThreadLocal<Boolean>(){
        @Override protected Boolean initialValue(){
            return true;
        }
    };

    public Train(int whichTrain){ //RepairPlace[] repair
        this.whichTrain = whichTrain;
    }


    @Override
    public void run(){
        try{
            blokada.lock();
            whichTrain++;
            modulo = whichTrain % 2;
        } finally {
            blokada.unlock();
        }

        if (modulo == 0) {
            addTextTrack1TextArea("Pociąg nr. " + whichTrain + " stoi w kolejce.");
            synchronized (queueToRotary){
                queueToRotary.offer(whichTrain);    //Sekcja krytyczna
            }
        }
        if (modulo == 1) {
            addTextTrack2TextArea("Pociąg nr. " + whichTrain + " stoi w kolejce.");
            synchronized (queueToRotary){
                queueToRotary.offer(whichTrain);    //Sekcja krytyczna
            }
        }

        //Synchronizacja, czyli reszta watkow stoi, jeden sie wykonuje
        if(queueToRotary.isEmpty()==false){ //If quene is not empty
            while(k<2) {
                if (rotaryFree == true) {
                        int temp;
                        synchronized (queueToRotary){
                            temp = queueToRotary.poll();    //Zdjecie z kolejki
                        }
                        addRotarylogTextArea("Pociag nr. " + temp + " wjechal na nastawnie.");
                        try{
                            blokada.lock();
                            rotaryFree = false; //Synchronizacja
                        } finally { blokada.unlock(); }
                        //Ten licznik moze uruchomic sie tylko raz
                        //bo moze wjezdzac jednoczesnie jeden pociag
                        Thread counter = new RotaryControlRoom();
                        counter.start();
                        try{
                            Thread.sleep((RotaryControlRoom.getTemp()));
                        } catch (InterruptedException e) { e.printStackTrace(); }
                        addRotarylogTextArea("Pociag nr. " + temp + " wyjezdza z nastawni.");

                        //Jezeli flaga zostanie ustawiona na true, to uspij watek na iles sekund - zamkniecie parowazowni
                        //Tutaj tworzy sie watek powiazany z jednym jedynym pociagiem
                        if(Main.flag == true){
                            synchronized (this){
                                try{
                                    Thread.sleep((Integer.parseInt(Main.closeEngineTextArea.getText()))*1000);
                                } catch (InterruptedException e) {e.printStackTrace();}
                            }


                        }
                                Thread obj = new RepairPlace(temp);
                                obj.start();

                    break;
                }
                if(rotaryFree == false){
                        try{
                            blokada.lock();
                            if(local.get()==true) {
                                addRotarylogTextArea("Pociag " + whichTrain + " czeka w kolejce.");
                                local.set(false);
                            }
                            Thread.sleep((RotaryControlRoom.geti())*1100);
                        } catch (InterruptedException e) {e.printStackTrace();}
                        finally {
                           blokada.unlock();
                        }
                    continue;
                }
                synchronized (this){
                    k++;
                }
            }
        }
    }

    public void addTextTrack1TextArea(String text){
        String currentText1 = Main.track1TextArea.getText();
        String newText1 = text;
        String newTextToAppend = currentText1 + "\n" + newText1;
        Main.track1TextArea.setText(newTextToAppend);
    }
    public void addTextTrack2TextArea(String text){
        String currentText2 = Main.track2TextArea.getText();
        String newText2 = text;
        String newTextToAppend = currentText2 + "\n" + newText2;
        Main.track2TextArea.setText(newTextToAppend);
    }
    public void addRotarylogTextArea(String text){
        String currentText1 = Main.rotarylogTextArea.getText();
        String newText1 = text;
        String newTextToAppend = currentText1 + "\n" + newText1;
        Main.rotarylogTextArea.setText(newTextToAppend);
    }
}