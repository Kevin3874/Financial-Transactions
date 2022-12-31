import java.util.HashMap;
import java.util.Map;

public class User {
    private final String userID;
    private int minBalance;
    private int maxBalance;
    private int currentBalance;

    public User(String id) {
        userID = id;
        minBalance = Integer.MAX_VALUE;
        maxBalance = Integer.MIN_VALUE;
        currentBalance = 0;
    }

    public String getUserID() {
        return userID;
    }

    public int getMinBalance() {
        return minBalance;
    }

    public int getMaxBalance() {
        return maxBalance;
    }

    public int getCurrentBalance() {
        return currentBalance;
    }

    public void updateMinBalance(int newBalance) {
        minBalance = newBalance;
    }

    public void updateMaxBalance(int newBalance) {
        maxBalance = newBalance;
    }

    public void updateCurrentBalance(int amount) {
        currentBalance += amount;
    }

}
