package model;

/**
 * Created by Linus on 25.11.2017.
 */
public class LocationProperties {
    private boolean covered;
    private double history;
    private double activity;
    private double art;
    private double cost;
    private double averageTime;
    private double maxCapacity;

    public LocationProperties(boolean covered) {
        this.covered = covered;
    }

    public double getHistory() {
        return history;
    }

    public double getActivity() {
        return activity;
    }

    public double getArt() {
        return art;
    }

    public double getCost() {
        return cost;
    }

    public LocationProperties(boolean covered, double history, double activity, double art, double cost) {
        this.covered = covered;
        this.history = history;
        this.activity = activity;
        this.art = art;
        this.cost = cost;
    }

    public LocationProperties(boolean covered, double history, double activity, double art, double cost, double maxC) {
        this.covered = covered;
        this.history = history;
        this.activity = activity;
        this.art = art;
        this.cost = cost;
        this.maxCapacity = maxC;
    }

    public boolean isCovered() {
        return covered;
    }

	public double getAverageTime() {
		return averageTime;
	}

	public void setAverageTime(double averageTime) {
		this.averageTime = averageTime;
	}

	public double getMaxCapacity() {
		return maxCapacity;
	}

	public void setMaxCapacity(double maxCapacity) {
		this.maxCapacity = maxCapacity;
	}
}
