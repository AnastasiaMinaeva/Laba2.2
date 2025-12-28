import java.util.Objects;
//                              Задание 7
public class Payment {
    private String fullName;
    private int day;
    private int month;
    private int year;
    private int amount;

    public Payment(String fullName, int day, int month, int year, int amount) {
        this.fullName = fullName;
        this.day = day;
        this.month = month;
        this.year = year;
        this.amount = amount;
    }

    public String getFullName() {
        return fullName;
    }

    public int getDay() {
        return day;
    }

    public int getMonth() {
        return month;
    }

    public int getYear() {
        return year;
    }

    public int getAmount() {
        return amount;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "Payment{" + "fullName='" + fullName + '\'' + ", day=" + day + ", month=" + month + ", year=" + year + ", amount=" + amount + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Payment payment = (Payment) o;
        return day == payment.day &&
                month == payment.month &&
                year == payment.year &&
                amount == payment.amount &&
                Objects.equals(fullName, payment.fullName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fullName, day, month, year, amount);
    }
}