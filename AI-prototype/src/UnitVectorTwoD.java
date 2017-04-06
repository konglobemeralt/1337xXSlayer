/**
 * Created by Emil Jansson on 2017-03-30.
 */
public class UnitVectorTwoD {
    private final double xComposite;
    private final double yComposite;

    public double getxComposite() {
        return xComposite;
    }

    public double getyComposite() {
        return yComposite;
    }

    public UnitVectorTwoD(double x, double y){
        if (x==0 && y==0){
            this.xComposite=0;
            this.yComposite=0;
        }else{
            double scaleFactor = Math.pow(Math.sqrt( Math.pow(x,2) + Math.pow(y,2)), -1);
            this.xComposite = x*scaleFactor;
            this.yComposite = y*scaleFactor;
        }
    }
}
