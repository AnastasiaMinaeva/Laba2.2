import java.util.Arrays;

public final class TaxDeclaration {
    private final int year;
    private final String fullName;
    private final double[] monthlyIncome;
    private final double[] cumulativeIncome;
    private final double[] monthlyTax;

    public TaxDeclaration(int year, String fullName, IncomeStatement... statements) {
        this.year = year;
        this.fullName = fullName;

        monthlyIncome = new double[12];
        for (IncomeStatement s : statements) {
            double[] incomes = s.getMonthlyIncomes();
            for (int i = 0; i < 12; i++) {
                monthlyIncome[i] += incomes[i];
            }
        }

        cumulativeIncome = new double[12];
        monthlyTax = new double[12];

        double cum = 0.0;
        for (int i = 0; i < 12; i++) {
            cum += monthlyIncome[i];
            cumulativeIncome[i] = cum;
            monthlyTax[i] = calculateTax(cum) - (i == 0 ? 0.0 : calculateTax(cumulativeIncome[i - 1]));
        }
    }

    private double calculateTax(double income) {
        if (income <= 24_000) return 0.0;
        if (income <= 240_000) return 0.13 * (income - 24_000);
        return 0.13 * (240_000 - 24_000) + 0.20 * (income - 240_000);
    }

    public int getYear() { return year; }
    public String getFullName() { return fullName; }
    public double[] getMonthlyIncome() { return Arrays.copyOf(monthlyIncome, 12); }
    public double[] getCumulativeIncome() { return Arrays.copyOf(cumulativeIncome, 12); }
    public double[] getMonthlyTax() { return Arrays.copyOf(monthlyTax, 12); }
    public double getTotalTax() {
        return Arrays.stream(monthlyTax).sum();
    }
}