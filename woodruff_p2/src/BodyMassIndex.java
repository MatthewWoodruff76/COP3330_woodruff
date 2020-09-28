public class BodyMassIndex {

    public double stored;
    //A class called `BodyMassIndex` will be used to calculate the BMI scores and categories.
    public <BodyMassIndex> BodyMassIndex(double height, double weight) {
       stored = 703 * weight / (height * height);
    }
}