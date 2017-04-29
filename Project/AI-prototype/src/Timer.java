/**
 * Created by Emil Jansson on 2017-04-04.
 */
public class Timer implements Runnable{
    private boolean running;
    private int time;

    public boolean getRunning(){
        return running;
    }

    public void startTimer(int milis){
        this.running = true;
        this.run();
    }

    @Override
    public void run(){
        try {
            Thread.sleep(2000);
        }catch (Exception e){}
    }
}
