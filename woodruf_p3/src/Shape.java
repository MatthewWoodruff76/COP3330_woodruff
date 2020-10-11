import static org.junit.jupiter.api.Assertions.assertEquals;

//Progenitor Class
abstract class Shape {
    protected static int side;
    public abstract String getName();
    public abstract Integer getArea();
}


//Subclass
class Square extends Shape {
    private int mySide;
    public Square(int side) {
        mySide = side;
    }
    public String getName() {
        return "square";
    }
    public Integer getArea(){
        return mySide*mySide;
    }
}