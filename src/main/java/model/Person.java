package model;

/**
 * Created by Linus on 18.11.2017.
 */
public class Person {
private int age;
private double activity;
private double art;
private double history;
private double rich;

    public Person(int age, double sport, double art, double rich) {
        this.age = age;
        this.activity = sport;
        this.art = art;
        this.rich = rich;
    }

    public double getHistory() {
        return history;
    }

    public void setHistory(double history) {
        this.history = history;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public double getActivity() {
        return activity;
    }

    public void setActivity(double activity) {
        this.activity = activity;
    }

    public double getArt() {
        return art;
    }

    public void setArt(double art) {
        this.art = art;
    }

    public double getRich() {
        return rich;
    }

    public void setRich(double rich) {
        this.rich = rich;
    }
}
