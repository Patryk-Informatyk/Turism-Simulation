package function;

public class Functions {


    public static double queueToLocationFunctionMax(){return 100.0;}
    public static double queueToLocationFunction(int maxLocationSize,int queue){
        return Math.max(0,((1 - (double)queue / maxLocationSize)*100));
    }

    public static double attractiveForPersonWithoutCostFunction(double activityPerson, double activityLocation, double artPerson, double artLocation,
            double historyPerson, double historyLocation) {
        double activity = attractiveLocationIndicatorForPerson(activityPerson,activityLocation);
        double art = attractiveLocationIndicatorForPerson(artPerson,artLocation);
        double history = attractiveLocationIndicatorForPerson(historyPerson,historyLocation);

        return (activity + art + history)/3;
    }

    private static double attractiveLocationIndicatorForPerson(double personIndicator,double locationIndicator){
        return personIndicator * locationIndicator / 100;
    }

    public static double expensiveIndicatorForPersonFunction(double locationCost, double touristRich) {
        return (100 -(locationCost * ((101-touristRich)/100)));
    }

    public static double attractiveForPersonWithCostFunction(double attractiveWithoutCost, double costIndicator) {
        return  ((attractiveWithoutCost *1.5) + (costIndicator *0.5))/2;
    }

    public static double attractiveFunction(double forPerson, double weather, double overflow) {
        return (forPerson + (weather) + (overflow*150))/7;
    }
}
