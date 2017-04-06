import javax.swing.*;

/**
 * Created by Emil Jansson on 2017-04-03.
 */
public class WalkerMain {
    public static void main(String[] args){
        WalkerModel model = new WalkerModel();
        JFrame frame = new JFrame();
        WalkerView view = new WalkerView(model, frame);
        WalkerController controller = new WalkerController(10, model);

        model.addListener(view);
        WalkerUtil.setupThree(model);

        controller.runGameLoop();
    }
}
