import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;


public class FinanceReportProcessorTest {
    @Test
    public void testGetPaymentsBySurname() {
        Payment pers1 = new Payment("Минаева", 12, 1, 2025, 40000);
        Payment pers2 = new Payment("Пушкин", 22, 11, 2025, 15000);
        Payment pers3 = new Payment("Михайленко", 30, 12, 2025, 25000);

        FinanceReport original = new FinanceReport(new Payment[]{pers1, pers2, pers3}, "Анна", 1, 1, 2025);

        FinanceReport filtered1 = FinanceReportProcessor.getPaymentsBySurname(original, 'М');

        assertEquals(2, filtered1.getPaymentCount());
        assertEquals("Минаева", filtered1.getPaymentI(0).getFullName());
        assertEquals("Михайленко", filtered1.getPaymentI(1).getFullName());


        FinanceReport filtered2 = FinanceReportProcessor.getPaymentsBySurname(original, 'М');
        assertEquals(2, filtered2.getPaymentCount());
    }

    @Test
    public void testGetPaymentsWithStartingChar_WithNullAndEmptyNames() {
        Payment p1 = new Payment("Назарова", 1, 1, 2025, 10000);
        Payment p2 = new Payment("", 2, 2, 2025, 15000);

        FinanceReport original = new FinanceReport(new Payment[]{p1, p2}, "Аня", 1, 1, 2025);

        FinanceReport filtered = FinanceReportProcessor.getPaymentsBySurname(original, 'Н');

        assertEquals(1, filtered.getPaymentCount());
        assertEquals("Назарова", filtered.getPaymentI(0).getFullName());
    }

    @Test
    public void testGetPaymentsBelowAmountFiltersCorrectly() {
        Payment p1 = new Payment("Назарова", 11, 11, 2025, 10100);
        Payment p2 = new Payment("Минаева", 12, 2, 2025, 15050);
        Payment p3 = new Payment("Ахмадеев", 13, 3, 2025, 20000);

        FinanceReport original = new FinanceReport(new Payment[]{p1, p2, p3}, "Анна", 1, 1, 2025);

        FinanceReport filtered = FinanceReportProcessor.getPaymentsBelowAmount(original, 16000);

        assertEquals(2, filtered.getPaymentCount());
        assertEquals("Назарова", filtered.getPaymentI(0).getFullName());
        assertEquals("Минаева", filtered.getPaymentI(1).getFullName());
    }

    @Test
    public void testGetPaymentsWithAmountLessThan_NoMatches() {
        Payment p1 = new Payment("Минаева", 1, 1, 2025, 40000);
        Payment p2 = new Payment("Назарова", 2, 2, 2025, 57000);

        FinanceReport original = new FinanceReport(new Payment[]{p1, p2}, "Анна", 1, 1, 2025);

        FinanceReport filtered = FinanceReportProcessor.getPaymentsBelowAmount(original, 10000);

        assertEquals(0, filtered.getPaymentCount());
    }



    //ДОП 12
        private FinanceReport makeReport(Payment... payments) {
            return new FinanceReport(payments, "Тест", 1, 1, 2024);
        }

        // 12.1

        @Test
        public void test1() {
            Payment p1 = new Payment("Иванов", 5, 3, 2024, 1000);
            FinanceReport report = makeReport(p1);
            FinanceReportProcessor proc = new FinanceReportProcessor(report);

            assertEquals(1000, proc.getPaymentsByData("05.03.24"), 0.0001);
        }

        @Test
        public void test2() {
            Payment p1 = new Payment("Иванов", 5, 3, 2024, 1000);
            Payment p2 = new Payment("Петров", 5, 3, 2024, 2500);
            Payment p3 = new Payment("Сидоров", 6, 3, 2024, 500);
            FinanceReport report = makeReport(p1, p2, p3);
            FinanceReportProcessor proc = new FinanceReportProcessor(report);

            assertEquals(3500, proc.getPaymentsByData("05.03.24"), 0.0001);
            assertEquals(500, proc.getPaymentsByData("06.03.24"), 0.0001);
        }

        @Test
        public void test3() {
            FinanceReport report = makeReport();
            FinanceReportProcessor proc = new FinanceReportProcessor(report);

            assertEquals(0, proc.getPaymentsByData("01.01.24"), 0.0001);
        }

        @Test
        public void testGetTotalPaymentByDate_sameDayDifferentYear() {
            Payment p = new Payment("Иванов", 5, 3, 2023, 1000);
            FinanceReport report = makeReport(p);
            FinanceReportProcessor proc = new FinanceReportProcessor(report);

            assertEquals(0, proc.getPaymentsByData("05.03.24"), 0.0001);
            assertEquals(1000, proc.getPaymentsByData("05.03.23"), 0.0001);
        }


        //  12.2 getMonthsWithoutPayments

        @Test
        public void testGetMonthsWithoutPayments_noPaymentsAtAll() {
            FinanceReport report = makeReport();
            FinanceReportProcessor proc = new FinanceReportProcessor(report);

            List<String> result = proc.getMonthsWithoutPayments(2024);
            assertEquals(12, result.size());
            assertTrue(result.contains("январь"));
            assertTrue(result.contains("декабрь"));
            assertEquals(
                    Arrays.asList("январь", "февраль", "март", "апрель", "май", "июнь",
                            "июль", "август", "сентябрь", "октябрь", "ноябрь", "декабрь"),
                    result
            );
        }

        @Test
        public void testGetMonthsWithoutPayments_onePaymentInMarch() {
            Payment p = new Payment("Иванов", 15, 3, 2024, 1000);
            FinanceReport report = makeReport(p);
            FinanceReportProcessor proc = new FinanceReportProcessor(report);

            List<String> result = proc.getMonthsWithoutPayments(2024);
            assertEquals(11, result.size());
            assertFalse(result.contains("март"));
            assertTrue(result.contains("январь"));
            assertTrue(result.contains("декабрь"));
        }

        @Test
        public void testGetMonthsWithoutPayments_paymentsInJanFebDec() {
            Payment p1 = new Payment("A", 1, 1, 2024, 100);
            Payment p2 = new Payment("B", 1, 2, 2024, 200);
            Payment p3 = new Payment("C", 1, 12, 2024, 300);
            FinanceReport report = makeReport(p1, p2, p3);
            FinanceReportProcessor proc = new FinanceReportProcessor(report);

            List<String> result = proc.getMonthsWithoutPayments(2024);
            assertEquals(9, result.size());
            assertFalse(result.contains("январь"));
            assertFalse(result.contains("февраль"));
            assertFalse(result.contains("декабрь"));
            assertTrue(result.contains("март"));
            assertTrue(result.contains("ноябрь"));
        }

        @Test
        public void testGetMonthsWithoutPayments_paymentsInOtherYearIgnored() {
            Payment p = new Payment("Иванов", 5, 3, 2023, 1000);
            FinanceReport report = makeReport(p);
            FinanceReportProcessor proc = new FinanceReportProcessor(report);

            List<String> result2024 = proc.getMonthsWithoutPayments(2024);
            assertEquals(12, result2024.size());

            List<String> result2023 = proc.getMonthsWithoutPayments(2023);
            assertEquals(11, result2023.size());
            assertFalse(result2023.contains("март"));
        }

    }