public class BodyMassIndex {

    public double index;       //Provides data accessible to other classes but stored here.
    public String category;     //Provides data accessible to other classes but stored here.
    public BodyMassIndex(double height, double weight) {
        index = FindIndex(height, weight);
        category = FindCategory(index, null);
    }
    public String FindCategory(double index, String category){  //Assigns a category.
        if(index < 18.5)       category = "underweight.";
        else if(index < 24.9)  category = "normal.";
        else if(index < 29.9)  category = "overweight.";
        else if(index >=30.0)  category = "obese.";
        return category;
    }
    public double FindIndex(double height, double weight){
         return Math.round(703 * weight / (height * height) * 10.0) / 10.0;//Calculates BMI to one decimal point precision.
    }
}