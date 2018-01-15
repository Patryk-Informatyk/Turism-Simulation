package function;
/**
 * Tutaj bedzie kozacki opis klasy
 *
 * @author      Patryk Zygmunt
 * @author      Grzegorz Puczkowski
 * @author      Hubert Rędzia
 * @version     1.0
 * @since       1.0
 */
public class Functions {

    /**
     * TODO
     *
     * @author      Patryk Zygmunt
     * @author      Grzegorz Puczkowski
     * @author      Hubert Rędzia
     * @return      Max queue
     */
    public static double queueToLocationFunctionMax(){return 100.0;}
    /**
     * TODO
     *
     * @author      Patryk Zygmunt
     * @author      Grzegorz Puczkowski
     * @author      Hubert Rędzia
     * @param   maxLocationSize Maximal amount of tourists in location
     * @param   queue   actual queue to location
     * @return  new queue to location
     */
    public static double queueToLocationFunction(int maxLocationSize,int queue){
        return Math.max(0,((1 - (double)queue / maxLocationSize)*100));
    }
    /**
     * Counts attractive for person without cost
     *
     * @author      Patryk Zygmunt
     * @author      Grzegorz Puczkowski
     * @author      Hubert Rędzia
     * @param activityPerson Activity of tourist
     * @param activityLocation Activity property of location
     * @param artPerson Art property of tourist
     * @param artLocation Art property of location
     * @param historyPerson History property of tourist
     * @param historyLocation   History property of location
     * @return average attractiveness
     */
    public static double attractiveForPersonWithoutCostFunction(double activityPerson, double activityLocation, double artPerson, double artLocation,
            double historyPerson, double historyLocation) {
        double activity = attractiveLocationIndicatorForPerson(activityPerson,activityLocation);
        double art = attractiveLocationIndicatorForPerson(artPerson,artLocation);
        double history = attractiveLocationIndicatorForPerson(historyPerson,historyLocation);

        return (activity + art + history)/3;
    }
    /**
     * TODO
     *
     * @author      Patryk Zygmunt
     * @author      Grzegorz Puczkowski
     * @author      Hubert Rędzia
     * @param personIndicator TODO
     * @param locationIndicator TODO
     * @return TODO
     */
    private static double attractiveLocationIndicatorForPerson(double personIndicator,double locationIndicator){
        return personIndicator * locationIndicator / 100;
    }
    /**
     * TODO
     *
     * @author      Patryk Zygmunt
     * @author      Grzegorz Puczkowski
     * @author      Hubert Rędzia
     * @param locationCost TODO
     * @param touristRich TODO
     * @return TODO
     */
    public static double expensiveIndicatorForPersonFunction(double locationCost, double touristRich) {
        return (100 -(locationCost * ((101-touristRich)/100)));
    }
    /**
     * Counts attractive for person with cost
     *
     * @author      Patryk Zygmunt
     * @author      Grzegorz Puczkowski
     * @author      Hubert Rędzia
     * @param attractiveWithoutCost counted attractivness without cost indicator
     * @param costIndicator  Cost indicator
     * @return average attractiveness with cost
     */
    public static double attractiveForPersonWithCostFunction(double attractiveWithoutCost, double costIndicator) {
        return  ((attractiveWithoutCost *1.5) + (costIndicator *0.5))/2;
    }
    /**
     * TODO
     *
     * @author      Patryk Zygmunt
     * @author      Grzegorz Puczkowski
     * @author      Hubert Rędzia
     * @param forPerson ?
     * @param weather Actual weather
     * @param overflow ?
     * @return Attractivness of location for tourist
     */
    public static double attractiveFunction(double forPerson, double weather, double overflow,double distance) {
        return (forPerson + (weather) + (overflow*150)+distance)/7;
    }
}
