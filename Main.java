import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        //read in user input for where the .csv file is
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter .csv location (ex: C:\\Users\\user\\Desktop\\Java\\Financials.csv) : ");
        String inputFilePath = scanner.nextLine();
        scanner.close();

        //create a reader object based on user input
        BufferedReader reader = new BufferedReader(new FileReader(inputFilePath));

        //make a HashSet to make sure only one unique userID exists at a time
        //Map<UserID, Map<Date, User>>
        Map<String, Map<String, User>> userBalances = new LinkedHashMap<>();

        //start reading
        String line;
        while ((line = reader.readLine()) != null) {
            if (line.equals(",,")) {
                continue;
            }
            //get the user ID, Date, and transaction
            String[] fields = line.split(",");
            //get userID
            String userID = fields[0];
            // Parse the date field
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
            LocalDate fullDate = LocalDate.parse(fields[1], formatter);
            String date = fullDate.format(DateTimeFormatter.ofPattern("MM/yyyy"));

            //get transaction amount
            int amount = Integer.parseInt(fields[2]);

            //check if the user exists
            if (!userBalances.containsKey(userID)) {
                Map<String, User> monthlyTransaction = new LinkedHashMap<>();
                userBalances.put(userID, monthlyTransaction);
            }

            //check if monthly transaction has occurred
            if (!userBalances.get(userID).containsKey(date)) {
                User currentUser = new User(userID);
                userBalances.get(userID).put(date, currentUser);
            }

            //update all transactions
            User currentUser = userBalances.get(userID).get(date);
            currentUser.updateCurrentBalance(amount);
            currentUser.updateMinBalance(Math.min(currentUser.getMinBalance(), currentUser.getCurrentBalance()));
            currentUser.updateMaxBalance(Math.max(currentUser.getMaxBalance(), currentUser.getCurrentBalance()));
        }
        //close file
        reader.close();

        //create new .csv to write to
        BufferedWriter writer = new BufferedWriter(new FileWriter("transactions.csv"));

        //go through each entry for user and date
        for (String user : userBalances.keySet()) {
            //get the monthly transactions for the user
            Map<String, User> transactionsByMonth = userBalances.get(user);
            //go through the user's transactions by month
            for (Map.Entry<String, User> userMonthly : transactionsByMonth.entrySet()) {
                //get specific date
                String monthYear = userMonthly.getKey();
                //get transaction amount
                User transactionsData = userMonthly.getValue();
                //write to the new .csv file
                writer.write(user+ "," + monthYear + "," + transactionsData.getMinBalance()+ "," +
                        transactionsData.getMaxBalance() + "," + transactionsData.getCurrentBalance() + "\n");
            }
        }
        //close .csv
        writer.close();
    }
}