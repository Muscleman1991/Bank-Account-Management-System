
package transactionhistory;


public class History {
    
    private String name;
    private String amount;
    private String generic;
    private String date;
    private String time;

    public History(String name, String amount, String generic, String date, String time) {
        this.name = name;
        this.amount = amount;
        this.generic = generic;
        this.date = date;
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public String getAmount() {
        return amount;
    }

    public String getGeneric() {
        return generic;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }
    
    
}
