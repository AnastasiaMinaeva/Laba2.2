import org.junit.Assert;
import org.junit.Test;
import static org.junit.Assert.*;

public class PaymentTest {
    @Test
    public void TestNumber1GetAndSet()
    {
        Payment payment = new Payment("Анастасия Минаева", 27, 11, 2003, 45000);

        assertEquals("Анастасия Минаева", payment.getFullName());
        assertEquals(27, payment.getDay());
        assertEquals(11, payment.getMonth());
        assertEquals(2003, payment.getYear());
        assertEquals(45000, payment.getAmount());

        payment.setFullName("Анастасия Юрьевна");
        payment.setDay(25);
        payment.setMonth(13);
        payment.setYear(2016);
        payment.setAmount(3000);

        assertEquals("Анастасия Юрьевна", payment.getFullName());
        assertEquals(25, payment.getDay());
        assertEquals(13, payment.getMonth());
        assertEquals(2016, payment.getYear());
        assertEquals(3000, payment.getAmount());
    }

    @Test
    public void TestNumber2Equals()
    {
        Payment onePay = new Payment("Елизавета Андреевна", 25, 2, 2013, 6000);
        Payment twoPay = new Payment("Елизавета Андреевна", 25, 2, 2013, 6000);
        Payment threePay = new Payment("Анна Юрьевна", 25, 3, 2005, 95000);

        assertEquals(onePay, twoPay);
        assertNotEquals(onePay, threePay);
    }

    @Test
    public void testNumberThreeToString() {Payment payNew = new Payment("Елизавета Андреевна", 10, 11, 2010, 7700);
        String expectedResult = "Payment{fullName='Елизавета Андреевна', day=10, month=11, year=2010, amount=7700}";
        assertEquals(expectedResult, payNew.toString());
    }
}