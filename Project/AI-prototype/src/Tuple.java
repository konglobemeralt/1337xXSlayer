/**
 * Created by Emil Jansson on 2017-03-30.
 */
public class Tuple<A, B> {
    private A a;
    private B b;

    public A getA() {
        return a;
    }

    public void setA(A a) {
        this.a = a;
    }

    public B getB() {
        return b;
    }

    public void setB(B b) {
        this.b = b;
    }

    public Tuple(A a, B b) {
        this.a = a;
        this.b = b;
    }
}
