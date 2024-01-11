public class MyPoint {
    private Object x;
    private Object y;
    
    public MyPoint(Object s1, Object s2){
        this.x = s1;
        this.y = s2;
    }

    public Object getX() {
        return x;
    }

    public Object getY() {
        return y;
    }

    public void setX(Object x) {
        this.x = x;
    }

    public void setY(Object y) {
        this.y = y;
    }

    @Override
    public String toString() {
        return "MyPoint [x=" + x + ", y=" + y + "]";
    } 

}
