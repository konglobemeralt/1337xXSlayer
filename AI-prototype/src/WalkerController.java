import java.util.ArrayList;
import java.util.List;

/**
 * Created by Emil Jansson on 2017-04-03.
 */
public class WalkerController {
    private int ticDelay; //Miliseconds
    private WalkerModel model;

    public WalkerController(int ticDelay, WalkerModel model){
        this.ticDelay = ticDelay;
        this.model = model;
    }

    public void runGameLoop(){
        while (true){
            model.preformMainLoop();
            try {
                Thread.sleep(ticDelay);
            }catch (Exception e){}
        }
    }
}
