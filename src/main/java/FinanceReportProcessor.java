import java.util.ArrayList;
import java.util.List;

public class FinanceReportProcessor {

    private final FinanceReport report;

    public FinanceReportProcessor(FinanceReport report) {
        this.report = report;
    }
    // 11.1
    public static FinanceReport getPaymentsBySurname(FinanceReport report, char firstChar) {
        int count = 0;
        for (int i = 0; i < report.getPaymentCount(); i++) {
            Payment p = report.getPaymentI(i);
            String surname = p.getFullName().split(" ")[0];
            if (!surname.isEmpty() &&
                    Character.toLowerCase(surname.charAt(0)) == Character.toLowerCase(firstChar)) {
                count++;
            }
        }

        Payment[] filteredPayments = new Payment[count];
        int index = 0;
        for (int i = 0; i < report.getPaymentCount(); i++) {
            Payment p = report.getPaymentI(i);
            String surname = p.getFullName().split(" ")[0];
            if (!surname.isEmpty() &&
                    Character.toLowerCase(surname.charAt(0)) == Character.toLowerCase(firstChar)) {
                filteredPayments[index] = new Payment(
                        p.getFullName(), p.getDay(), p.getMonth(), p.getYear(), p.getAmount()
                );
                index++;
            }
        }

        return new FinanceReport(filteredPayments, report.getFullName(),
                report.getReportDay(), report.getReportMonth(), report.getReportYear());
    }

    // 11.2
    public static FinanceReport getPaymentsBelowAmount(FinanceReport report, int maxAmount) {
        int count = 0;
        for (int i = 0; i < report.getPaymentCount(); i++) {
            if (report.getPaymentI(i).getAmount() < maxAmount) {
                count++;
            }
        }

        Payment[] filteredPayments = new Payment[count];
        int index = 0;
        for (int i = 0; i < report.getPaymentCount(); i++) {
            Payment p = report.getPaymentI(i);
            if (p.getAmount() < maxAmount) {
                filteredPayments[index] = new Payment(
                        p.getFullName(), p.getDay(), p.getMonth(), p.getYear(), p.getAmount()
                );
                index++;
            }
        }

        return new FinanceReport(filteredPayments, report.getFullName(),
                report.getReportDay(), report.getReportMonth(), report.getReportYear());
    }


    //12.1
    public double getPaymentsByData(String dataStr) {
        if(dataStr == null || dataStr.trim().isEmpty())
        {
            throw new IllegalArgumentException("dataStr is null or empty");
        }
        String[] data = dataStr.split("\\."); //
        if(data.length != 3)
        {
            throw new IllegalArgumentException("Неверный формат даты: '" + dataStr + "'. Ожидается dd.mm.yy");
        }
        int day = Integer.parseInt(data[0]);
        int month = Integer.parseInt(data[1]);
        int yy = Integer.parseInt(data[2]);
        int year = 2000 + yy;

        FinanceReport k = new FinanceReport(report);
        int total = 0;
        for (int i = 0; i < k.getPaymentCount(); i++) {
            Payment p = k.getPaymentI(i);
            if (p.getDay() == day && p.getMonth() == month && p.getYear() == year) {
                total += p.getAmount();
            }
        }
        return total;
    }


    //12.2
    public List<String> getMonthsWithoutPayments(int year) {
        List<String> result = new ArrayList<String>();
        String[] monthNames = {"", "январь", "февраль", "март", "апрель", "май", "июнь",
                "июль", "август", "сентябрь", "октябрь", "ноябрь", "декабрь"};

        for (int month = 1; month <= 12; month++) {
            boolean found = false;
            for (int i = 0; i < report.getPaymentCount(); i++) {
                Payment p = report.getPaymentI(i);

                if (p.getYear() == year && p.getMonth() == month) {
                    found = true;
                    break;
                }
            }

            if (!found) {
                result.add(monthNames[month]);
            }
        }

        return result;
    }
}