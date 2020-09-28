public class BodyMassIndex {

    public double index;       //Provides data accessible to other classes but stored here.
    public String category;     //Provides data accessible to other classes but stored here.
    public BodyMassIndex(double height, double weight) {
        index = FindIndex(height, weight);
        category = FindCategory(index);
    }
    public String FindCategory(double index){  //Assigns a category.
        if(index < 18.5)        return "underweight.";
        if(index < 25)          return "normal.";
        if(index < 30)          return "overweight.";
                                return "obese.";
    }
    public double FindIndex(double height, double weight){
         return Math.round(7030.0 * weight / (height * height))/10.0;       //Calculates BMI to the same precision as the official calculator.
    }
}