import org.junit.Test;
import static org.junit.Assert.*;

public class IncomeStatementTest {

    @Test
    public void testConstructor_validInput() {
        double[] incomes = {0, 0, 24000.0, 25000.0, 0, 0, 0, 0, 0, 0, 0, 0};

        IncomeStatement stmt = new IncomeStatement(2020, "Иванов И.И.", "ООО Рога и Копыта", incomes);

        assertEquals(2020, stmt.getYear());
        assertEquals("Иванов И.И.", stmt.getFullName());
        assertEquals("ООО Рога и Копыта", stmt.getOrganization());
        assertArrayEquals(incomes, stmt.getMonthlyIncomes(), 0.0001);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructor_nullIncomes() {
        new IncomeStatement(2020, "Иванов И.И.", "ООО Рога и Копыта", null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructor_wrongLength() {
        new IncomeStatement(2020, "Иванов И.И.", "ООО Рога и Копыта", new double[11]);
    }

    @Test
    public void testImmutability() {
        double[] incomes = {1200, 2350, 24000, 25000, 0, 0, 0, 0, 0, 0, 0, 0};
        IncomeStatement stmt = new IncomeStatement(2020, "Иванов И.И.", "ООО Рога и Копыта", incomes);

        double[] returned = stmt.getMonthlyIncomes();
        returned[0] = 999999;
        assertArrayEquals(incomes, stmt.getMonthlyIncomes(), 0.0001);
    }
}