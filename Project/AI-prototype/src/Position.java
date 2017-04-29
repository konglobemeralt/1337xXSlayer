/**
 * Created by Emil Jansson on 2017-03-30.
 */
public class Position {

    private final double x;

    private final double y;

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public Position(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getDistance(Position pos){
        return Math.sqrt(Math.pow(this.getX() - pos.getX(), 2) + Math.pow(this.getY() - pos.getY(), 2));
    }

    public boolean isInProximityOf(Position pos, double maxDistance){
        if (this.getDistance(pos) < maxDistance){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Position position = (Position) o;

        if (Double.compare(position.x, x) != 0) return false;
        return Double.compare(position.y, y) == 0;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        temp = Double.doubleToLongBits(x);
        result = (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(y);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }
}
