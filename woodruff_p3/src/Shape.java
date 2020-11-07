abstract class Shape {
    abstract String getName();              //Returns a pre-scripted shape name.
    abstract double getArea();              //Returns the area, calculated in the return statement.
}
abstract class Shape2D extends Shape{
}
abstract class Shape3D extends Shape{
    abstract double getVolume();            //Returns the volume, calculated in the return statement.
}

class Square extends Shape2D {              //The shape subclasses extend their respective dimensions.
    private static double side = 0;
    protected Square(double input) {        //The shape methods assign the input to corresponding variables.
        side = input;
    }
    @Override
    protected String getName() {
        return "square";
    }
    @Override
    protected double getArea() {
        return side*side;
    }
}

class Triangle extends Shape2D {
    private static double length = 0;
    private static double width = 0;
    protected Triangle(double input1, double input2) {
        length = input1;
        width = input2;
    }
    @Override
    protected String getName() {
        return "triangle";
    }
    @Override
    protected double getArea() {
        return 0.5*width*length;
    }
}

class Circle extends Shape2D {
    private static double radius = 0;
    protected Circle(double input) {
        radius = input;
    }
    @Override
    protected String getName() {
        return "circle";
    }
    @Override
    protected double getArea() {
        return Math.PI*radius*radius;
    }
}

class Cube extends Shape3D {
    private static double side = 0;
    protected Cube(double input) {
        side = input;
    }
    @Override
    protected String getName() {
        return "cube";
    }
    @Override
    protected double getArea() {
        return 6*side*side;
    }
    @Override
    protected double getVolume() {
        return side*side*side;
    }
}

class Pyramid extends Shape3D {
    private static double height = 0;
    private static double width = 0;
    private static double length = 0;
    protected Pyramid(double input1, double input2, double input3) {
        length = input1;
        width = input2;
        height = input3;
    }
    @Override
    protected String getName() {
        return "pyramid";
    }
    @Override
    protected double getArea() {
        return baseArea(width,length)+ sideArea1(length, width, height)+sideArea2(length, width, height);
    }

    @Override
    protected double getVolume() {
        return height*width*length/3.0;
    }
    private double baseArea(double width, double length){                   //Finds the area of the pyramid base
        return width*length;
    }
    private double sideArea1(double length, double width, double height){   //Finds the first term of the side area
        return width*Math.sqrt(height*height+0.25*length*length);
    }
    private double sideArea2(double length, double width, double height){   //Finds the second term of the side area
        return length*Math.sqrt(height*height+0.25*width*width);
    }
}

class Sphere extends Shape3D {
    private static double radius = 0;
    protected Sphere(double input) {
        radius = input;
    }
    @Override
    protected String getName() {
        return "sphere";
    }
    @Override
    protected double getArea() {
        return 4.0*Math.PI*radius*radius;
    }
    @Override
    protected double getVolume() {
        return 4.0/3.0*Math.PI*radius*radius*radius;
    }
}