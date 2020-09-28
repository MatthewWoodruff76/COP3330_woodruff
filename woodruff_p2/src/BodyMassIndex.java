public class BodyMassIndex {

    public double index;       //Provides data accessible to other classes but stored here.
    public String category;     //Provides data accessible to other classes but stored here.
    public BodyMassIndex(double height, double weight) {
        index = FindIndex(height, weight);
        category = FindCategory(index);
    }
    public String FindCategory(double index){  //Assigns a category.
        if(index < 18.5)        return "underweight.";
        if(index < 24.9)        return "normal.";
        if(index < 29.9)        return "overweight.";
                                return "obese.";
    }
    public double FindIndex(double height, double weight){
         return 703 * weight / (height * height);       //Calculates BMI.
    }
}