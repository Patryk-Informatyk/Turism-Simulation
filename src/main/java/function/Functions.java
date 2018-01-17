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
        if(queue==0)return 100.0;
        else
        return Math.max(0,(Math.pow((1 - (double)queue / maxLocationSize),7)*100));
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
        return (forPerson + (weather) + (overflow*4)+distance)/7;
    }

    /**
     * Ja wiem, ze troche po ang i troche po pl to troche lipa, ale co tam.
     * W 2016r w Krakowie było ~11.000.000 turystów w ciągu roku. Funkcja zwraca mnożnik
     * ilości turystów w danym dniu w mieście. Baza 5000 turystów będzie przemnożona przez zwrócony licznik
     * żeby nie wpływając na szybkość symulacji, przybliżyć faktyczną liczbę turystów
     * Działam na intach, żeby uniknąć ew. bugów z ułamkami przy stanie turystów
     * Od kwietnia do początku października ilość turystów jest niemal dwukrotnie większa
     * niż w innych miesiącach, dlatego mnożnik w tych miesiącach musi byc odpowiednio wyższy
     *
     *
     * @author      Patryk Zygmunt
     * @author      Grzegorz Puczkowski
     * @author      Hubert Rędzia
     * @param month Month in year
     * @param tourists Sample of tourists in simulation
     * @return Indicator to multiply number of tourist in location that day
     */
    public static double touristsPerMonthIndicator(int month, int hour, int tourists){
        /*
        Próba turystów : tourists
        liczba turystów na I-III ~2200000
        liczba turystów na IV-VI ~3300000
        liczba turystów na VI-IX ~3300000
        liczba turystów na X-XII ~2200000
         */
        // probowalem na switchu, ale zwracalo zawsze jedna wartosc
        double x = 1;

        if      ((hour <= 3) || (hour > 18))  x = 0.4;
        else if ((hour > 3) && (hour <= 8))   x = 0.1;
        else if ((hour > 8) && (hour <= 10))  x = 0.7;
        else if ((hour > 10) && (hour <= 16)) x = 1.0;
        else if ((hour > 16) && (hour <= 18)) x = 0.8;


        if     (month == 1) return x*(2200000/tourists)/31;   // ~7
        else if(month == 2) return x*(2200000/tourists)/28;   // ~7 (8)?
        else if(month == 3) return x*(2200000/tourists)/31;   // ~7
        else if(month == 4) return x*(3300000/tourists)/30;   // ~11
        else if(month == 5) return x*(3300000/tourists)/31;   // ~10
        else if(month == 6) return x*(3300000/tourists)/30;   // ~11
        else if(month == 7) return x*(3300000/tourists)/31;   // ~10
        else if(month == 8) return x*(3300000/tourists)/31;   // ~10
        else if(month == 9) return x*(3300000/tourists)/30;   // ~11
        else if(month == 10) return x*(2200000/tourists)/31;  // ~7
        else if(month == 11) return x*(2200000/tourists)/30;  // ~7
        else if(month == 12) return x*(2200000/tourists)/31;  // ~7
        return 1;   // el. neutralny
    }
}
