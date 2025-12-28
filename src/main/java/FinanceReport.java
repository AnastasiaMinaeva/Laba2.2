public class FinanceReport {
    private final Payment[] payments;
    private String fullName;
    private int reportDay;
    private int reportMonth;
    private int reportYear;

    public FinanceReport(Payment[] payments, String fullName, int reportDay, int reportMonth, int reportYear) {
        this.payments = payments;
        this.fullName = fullName;
        this.reportDay = reportDay;
        this.reportMonth = reportMonth;
        this.reportYear = reportYear;
    }

    public int getPaymentCount() {
        return payments.length;
    }

    public Payment getPaymentI(int index) {
        if (index < 0 || index >= payments.length) {
            throw new IndexOutOfBoundsException("Некорректный размер индекса: " + index);
        }
        return payments[index];
    }

    public void setPayment(int index, Payment payment) {
        if (index < 0 || index >= payments.length) {
            throw new IndexOutOfBoundsException("Некорректный размер индекса: " + index);
        }
        payments[index] = payment;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public int getReportDay() {
        return reportDay;
    }

    public void setReportDay(int reportDay) {
        this.reportDay = reportDay;
    }

    public int getReportMonth() {
        return reportMonth;
    }

    public void setReportMonth(int reportMonth) {
        this.reportMonth = reportMonth;
    }

    public int getReportYear() {
        return reportYear;
    }

    public void setReportYear(int reportYear) {
        this.reportYear = reportYear;
    }

    //9
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("[Автор: %s, Дата: %d.%d.%d Платежи: [\n", fullName, reportDay, reportMonth, reportYear));
        if (payments.length > 0) {
            int rubRus;
            int copeyka;
            Payment item;
            for (int i = 0; i < payments.length; i++) {
                item = payments[i];
                if(item != null)
                {
                    rubRus = item.getAmount() / 100;
                    copeyka = item.getAmount() % 100;
                    sb.append(String.format("\t Плательщик: %s , дата: %d.%d.%d сумма: %03d руб. %02d коп. ",
                            item.getFullName(), item.getDay(), item.getMonth(), item.getYear(), rubRus, copeyka));
                }
                if (i < payments.length - 1) {
                    sb.append(",/n");
                }
            }
        }
        sb.append("]]");
        return sb.toString();
    }

    //10
    public FinanceReport(FinanceReport other) {
        this.fullName = other.fullName;
        this.reportDay = other.reportDay;
        this.reportMonth = other.reportMonth;
        this.reportYear = other.reportYear;
        this.payments = new Payment[other.payments.length];
        for (int i = 0; i < other.payments.length; i++) {
            Payment original = other.payments[i];
            this.payments[i] = new Payment(
                    original.getFullName(),
                    original.getDay(),
                    original.getMonth(),
                    original.getYear(),
                    original.getAmount());
        }
    }

    public Payment[] getPayments() {
        return payments;
    }

}