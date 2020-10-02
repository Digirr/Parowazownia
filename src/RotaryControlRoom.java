public class RotaryControlRoom extends Thread{

    static int i;
    //Ile czasu obraca sie nastawnia
    private static int temp = Integer.parseInt(Main.rotaryTextArea.getText());

    public static synchronized int geti(){
        return i;
    }
    public static synchronized int getTemp(){
        return temp*1000;
    }
            public synchronized void run() { //Co ile pojawia sie pociag
                    if (Train.rotaryFree == false) {
                        try {
                            for (i = temp; i > 0; i--) {
                                Thread.sleep(1400);
                            }
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        Train.rotaryFree = true;
                    }
            }
}