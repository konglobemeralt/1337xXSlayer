/**
 * Created by Emil Jansson on 2017-04-03.
 */
public interface ObserverOfWalkerModel {
    public abstract void reactOnUpdate();
    public abstract void setWalkerRadius(int i);
    public abstract void setDoubleToIntFactor(int i);
}
