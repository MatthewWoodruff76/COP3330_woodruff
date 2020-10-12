abstract class Shape {
    abstract String getName();
    abstract double getArea();
}
abstract class Shape2D extends Shape{ //Empty per rubic
}
abstract class Shape3D extends Shape{
    abstract double getVolume();
}

class Square extends Shape2D {
    public static double side = 0;
    public Square(double input) {
        side = input;
    }
    @Override
    public String getName() {
        return "square";
    }
    @Override
    public double getArea() {
        return side*side;
    }
}

class Triangle extends Shape2D {
    public static double length = 0;
    public static double width = 0;
    public Triangle(double input1, double input2) {
        length = input1;
        width = input2;
    }
    @Override
    public String getName() {
        return "triangle";
    }
    @Override
    public double getArea() { //Returns area
        return 0.5*width*length;
    }
}

class Circle extends Shape2D {
    public static double radius = 0;
    public Circle(double input) {
        radius = input;
    }
    @Override
    public String getName() {
        return "circle";
    }
    @Override
    public double getArea() { //Returns the area
        return Math.PI*radius*radius;
    }
}

class Cube extends Shape3D {
    public static double side = 0;
    public Cube(double input) {
        side = input;
    }
    @Override
    public String getName() {
        return "cube";
    }
    @Override
    public double getArea() { //Returns the area
        return 6*side*side;
    }
    @Override
    public double getVolume() { //Returns the volume
        return side*side*side;
    }
}

class Pyramid extends Shape3D {
    public static double height = 0;
    public static double width = 0;
    public static double length = 0;
    public Pyramid(double input1, double input2, double input3) {
        length = input1;
        width = input2;
        height = input3;
    }
    @Override
    public String getName() {
        return "pyramid";
    }
    @Override
    public double getArea() { //Returns the area
        double sidesArea = sideArea1(length, width, height)+sideArea2(length, width, height);
        return base(width,length)+ sidesArea;
    }

    @Override
    public double getVolume() { //Returns the volume
        return height*width*length/3.0;
    }
    private double base(double width, double length){
        return width*length;
    }
    private double sideArea1(double length, double width, double height){
        return width*Math.sqrt(height*height+0.25*length*length);
    }
    private double sideArea2(double length, double width, double height){
        return length*Math.sqrt(height*height+0.25*width*width);
    }
}

class Sphere extends Shape3D {
    public static double radius = 0;
    public Sphere(double input) {
        radius = input;
    }
    @Override
    public String getName() {
        return "sphere";
    }
    @Override
    public double getArea() { //Returns the area
        return 4.0*Math.PI*radius*radius;
    }
    @Override
    public double getVolume() { //Returns the volume
        return 4.0/3.0*Math.PI*radius*radius*radius;
    }
}