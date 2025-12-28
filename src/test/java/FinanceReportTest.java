import org.junit.Assert;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Rule;
import org.junit.rules.ExpectedException;


public class FinanceReportTest {
    @Test
    public void TestOne() {
        Payment[] payments = {new Payment("Лиза", 2, 2, 2007, 20)}; //ТОТ КТО ВЗЯЛ ПЛАТЕЖ
        FinanceReport report = new FinanceReport(payments, "Анна", 7, 8, 2006); //  СОСТАВИТЕЛЬ ПЛАТЕЖА

        assertEquals("Анна", report.getFullName());
        assertEquals(7, report.getReportDay());
        assertEquals(8, report.getReportMonth());
        assertEquals(2006, report.getReportYear());
        assertEquals(1, report.getPaymentCount());
    }

    @Test
    public void TestTwoLengthPayments() {
        Payment[] payments = {new Payment("Лиза", 2, 2, 2007, 20)};
        assertEquals(1, payments.length);
    }

    @Test
    public void Test3PaymentI()
    {
        Payment[] payments = {new Payment("Лиза", 2, 2, 2007, 20),
                new Payment("Соня", 13, 11, 2005, 25)};



    }
    @Test
    public void Test4ToString() {
        Payment payment = new Payment("Анна", 15, 10, 2007, 24004);
        FinanceReport report = new FinanceReport(new Payment[]{payment}, "Дмитрий", 18, 12, 2022);

        String expectedResult = "[Автор: Дмитрий, Дата: 18.12.2022 Платежи: [\n\t Плательщик: Анна , дата: 15.10.2007 сумма: 240 руб. 04 коп. ]]";
        assertEquals(expectedResult, report.toString());
    }

    @Test
    public void Test5CopyPayments()
    {
        Payment[] payments = {new Payment("Alice", 22, 2, 2002, 20020)};
        FinanceReport original = new FinanceReport(payments, "Анна", 11, 11, 2011);
        FinanceReport copied = new FinanceReport(original);

        assertEquals("Анна", copied.getFullName());
        assertEquals(11, copied.getReportDay());
        assertEquals(11, copied.getReportMonth());
        assertEquals(2011, copied.getReportYear());
        assertEquals(1, copied.getPaymentCount());

        assertNotSame(original.getPayments(), copied.getPayments());

    }
    @Test
    public void Test6ToGetPaymentI() {
        Payment payment = new Payment("Alice", 22, 2, 2002, 20020);
        FinanceReport report = new FinanceReport(new Payment[]{payment}, "Анна", 11, 11, 2011);

        assertEquals("Alice", report.getPaymentI(0).getFullName());
    }

    @Rule
    public ExpectedException thrown = ExpectedException.none();
    @Test
    public void Test6_1IndexOfGetPaymentI()
    {
        FinanceReport report = new FinanceReport(new Payment[]{}, "Диденко", 1, 3, 2006);
        thrown.expect(IndexOutOfBoundsException.class);
        thrown.expectMessage("Некорректный размер индекса: -2" );

        report.getPaymentI(-2);
    }
    @Test
    public void Test6_2IndexOf_Get_PaymentIWithIndexEqualToLength()
    {
        FinanceReport report = new FinanceReport(new Payment[1], "Анна", 5, 11, 2007);
        thrown.expect(IndexOutOfBoundsException.class);
        thrown.expectMessage("Некорректный размер индекса: 4" );

        report.getPaymentI(4);
    }

    @Test
    public void Test7SetPayment() {
        FinanceReport report = new FinanceReport(new Payment[1], "Анна", 5, 11, 2007);
        Payment newPayment = new Payment("Аня", 1, 12, 2005, 400000);

        report.setPayment(0, newPayment);

        assertEquals("Аня", report.getPaymentI(0).getFullName());
    }

    @Test
    public void Test7_1IndexOfSetPaymentI()
    {
        Payment payment = new Payment("Минаева", 2, 3, 2006, 20000);
        FinanceReport report = new FinanceReport(new Payment[1], "Анна", 5, 11, 2007);
        thrown.expect(IndexOutOfBoundsException.class);
        thrown.expectMessage("Некорректный размер индекса: -1" );

        report.setPayment(-1, payment);
    }
    @Test
    public void Test7_2IndexOf_Set_PaymentIWithIndexEqualToLength()
    {
        Payment payment = new Payment("Минаева", 2, 3, 2006, 20000);
        FinanceReport report = new FinanceReport(new Payment[1], "Анна", 5, 11, 2007);
        thrown.expect(IndexOutOfBoundsException.class);
        thrown.expectMessage("Некорректный размер индекса: 4" );

        report.setPayment(4, payment);
    }
}
