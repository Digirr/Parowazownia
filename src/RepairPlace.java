import java.util.ArrayDeque;
        import java.util.Queue;
        import java.util.Random;

public class RepairPlace extends Thread{

    private int temporary = Integer.parseInt(Main.repairTextArea.getText());
    private int whichTrain;
    private boolean flag = false;
    private int whichPlace;
    private boolean help = false;
    volatile public static Queue<Integer> queueToReparyPlace = new ArrayDeque<>();
    private Random random = new Random();
    public RepairPlace(int whichTrain){
        this.whichTrain = whichTrain;
    }

    @Override
    public void run(){
        int randomEntryNumber = random.nextInt(temporary*1000);
        for(int i=0;i<Main.M;i++){
            if(Main.placeIsFree[i] == true){
                flag = true;
                whichPlace = i;
                break;
            }
        }
        if(flag == true){   //Znaleziono wolne miejsce
            synchronized (this){    //Zajecie miejsca
                addRenovationTextArea("Pociag nr. " + whichTrain + " wjezdza na " + (whichPlace+1) + " miejsce remontowe.");
                Main.placeIsFree[whichPlace] = false;
            }
            try {
                Thread.sleep(randomEntryNumber);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (this){
                addTrainLeftTextArea("Pociag nr. " + whichTrain + " wyjezdza z " + (whichPlace+1) + " miejsca.");
                Main.placeIsFree[whichPlace] = true;
            }

        }
        if(flag == false){  //Zadne miejsce nie jest wolne
            addRenovationTextArea("Pociag nr. " + whichTrain + " oczekuje na zwolnienie miejsca.");
            queueToReparyPlace.offer(whichTrain);   //Wrzucenie do kolejki
            while(true){
                for(int i=0;i<Main.M;i++){
                    if(Main.placeIsFree[i] == true){
                        int temp;
                        synchronized (this){    //Zdjecie z kolejki
                            temp = queueToReparyPlace.poll();
                            Main.placeIsFree[i] = false;
                            addRenovationTextArea("Pociag nr. " + temp + " wjezdza na " + (i+1) + " miejsce remontowe.");
                        }

                        try{
                            Thread.sleep(randomEntryNumber);
                        } catch (InterruptedException e) {e.printStackTrace();}
                        synchronized (this){ //Zwolnenie miejsca
                            Main.placeIsFree[i] = true;
                            addTrainLeftTextArea("Pociag nr. " + temp + " wyjezdza z " + (i+1) + " miejsca.");
                        }
                        help = true;
                        break;
                    }
                }
                if(help == true) break;
            }
        }
    }
    public void addRenovationTextArea(String text){
        String currentText1 = Main.renovationTextArea.getText();
        String newText1 = text;
        String newTextToAppend = currentText1 + "\n" + newText1;
        Main.renovationTextArea.setText(newTextToAppend);
    }
    public void addTrainLeftTextArea(String text){
        String currentText1 = Main.trainLeftTextArea.getText();
        String newText1 = text;
        String newTextToAppend = currentText1 + "\n" + newText1;
        Main.trainLeftTextArea.setText(newTextToAppend);
    }
}
