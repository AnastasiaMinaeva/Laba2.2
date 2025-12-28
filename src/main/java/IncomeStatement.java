import java.util.Arrays;

public final class IncomeStatement {
    private final int year;
    private final String fullName;
    private final String organization;
    private final double[] monthlyIncomes;

    public IncomeStatement(int year, String fullName, String organization, double[] monthlyIncomes) {
        if (monthlyIncomes == null || monthlyIncomes.length != 12)
            throw new IllegalArgumentException("Месяцев не может быть null и не 12");
        this.year = year;
        this.fullName = fullName;
        this.organization = organization;
        this.monthlyIncomes = Arrays.copyOf(monthlyIncomes, 12);
    }

    public int getYear() { return year; }
    public String getFullName() { return fullName; }
    public String getOrganization() { return organization; }
    public double[] getMonthlyIncomes() { return Arrays.copyOf(monthlyIncomes, 12); }
}