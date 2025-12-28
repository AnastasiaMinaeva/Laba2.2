import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

public class TaxDeclarationTest {

    @Test
    public void testSingleIncomeStatement() {
        double[] incomes = {6000, 6000, 6000, 8000, 60000, 60000, 60000, 60000, 100000, 100000, 100000, 100000};
        IncomeStatement stmt = new IncomeStatement(2020, "Иванов И.И.", "ООО Рога и Копыта", incomes);

        TaxDeclaration decl = new TaxDeclaration(2020, "Иванов И.И.", stmt);

        double[] expectedMonthlyTax = {
                0.0,       // 6000 → cum=6000  ≤24000
                0.0,       // +6000 → cum=12000 ≤24000
                0.0,       // +6000 → cum=18000 ≤24000
                260.0,     // +8000 → cum=26000 → tax = 0.13 * (26000-24000) = 260
                7800.0,    // +60000 → cum=86000 → tax = 0.13*(86000-24000) = 8060; прирост = 8060-260 = 7800
                7800.0,    // +60000 → cum=146000 → tax = 0.13*(122000) = 15860; прирост = 15860-8060 = 7800
                7800.0,    // +60000 → cum=206000 → tax = 0.13*(182000) = 23660; прирост = 23660-15860 = 7800
                7120.0,    // +60000 → cum=266000 → tax = 0.13*216000 + 0.20*26000 = 28080+5200=33280; прирост = 33280-23660 = 9620?
                // Но из примера таблицы: в 8-м месяце — 34 тыс в пределах 240к (13%) + 26 тыс сверх (20%) → 13%*34000 + 20%*26000 = 4420+5200=9620
                20000.0,   // +100000 → cum=366000 → tax = 0.13*216000 + 0.20*126000 = 28080+25200=53280; прирост = 53280-33280 = 20000
                20000.0,
                20000.0,
                20000.0
        };

        // Проверим кумулятивный доход на 4-м месяце (индекс 3)
        assertEquals(26000.0, decl.getCumulativeIncome()[3], 0.0001);

        // Проверим налог за 4-й месяц (индекс 3)
        assertEquals(260.0, decl.getMonthlyTax()[3], 0.0001);

        // Проверим налог за 8-й месяц (индекс 7)
        assertEquals(9620.0, decl.getMonthlyTax()[7], 0.0001);

        // Проверим итоговый налог
        /* Пересчитаем вручную по данным из таблицы:
         Месяц 4: 260
         Месяц 5: 7800 -> итого 8060
         Месяц 6: 7800 -> итого 15860
         Месяц 7: 7800 -> итого 23660
         Месяц 8: 9620 -> итого 33280
         Месяцы 9-12: по 20000 -> +80000 -> итого (113280)*/
        assertEquals(113280.0, decl.getTotalTax(), 0.0001);
    }

    @Test
    public void testMultipleIncomeStatements() {
        // Источник 1  1000 каждый месяц
        double[] inc1 = new double[12];
        Arrays.fill(inc1, 1000.0);
        IncomeStatement s1 = new IncomeStatement(2023, "Петров П.П.", "Источник1", inc1);

        // Источник 2  1000 каждый месяц — итого 2000/мес
        double[] inc2 = new double[12];
        Arrays.fill(inc2, 1000.0);
        IncomeStatement s2 = new IncomeStatement(2023, "Петров П.П.", "Источник2", inc2);

        TaxDeclaration decl = new TaxDeclaration(2023, "Петров П.П.", s1, s2);

        // За год суммарно 24000 -> ровно на границе — налог 0
        assertEquals(0.0, decl.getTotalTax(), 0.01);
        assertEquals(24000.0, decl.getCumulativeIncome()[11], 0.01);

        // Проверим что 1-й месяц: 2000, кум = 2000
        assertEquals(2000.0, decl.getMonthlyIncome()[0], 0.01);
        assertEquals(2000.0, decl.getCumulativeIncome()[0], 0.01);
        assertEquals(0.0, decl.getMonthlyTax()[0], 0.01);
    }

    @Test
    public void testZeroIncome() {
        double[] zeros = new double[12];
        IncomeStatement stmt = new IncomeStatement(2024, "Сидоров С.С.", "Нет дохода", zeros);
        TaxDeclaration decl = new TaxDeclaration(2024, "Сидоров С.С.", stmt);

        for (int i = 0; i < 12; i++) {
            assertEquals(0.0, decl.getMonthlyIncome()[i], 0.01);
            assertEquals(0.0, decl.getCumulativeIncome()[i], 0.01);
            assertEquals(0.0, decl.getMonthlyTax()[i], 0.01);
        }
        assertEquals(0.0, decl.getTotalTax(), 0.01);
    }

    @Test
    public void testHighIncomeSingleMonth() {
        double[] incomes = new double[12];
        incomes[0] = 500_000; // всё за январь

        IncomeStatement stmt = new IncomeStatement(2025, "Зажравшийся З.З.", "Рент", incomes);
        TaxDeclaration decl = new TaxDeclaration(2025, "Зажравшийся З.З.", stmt);

        double taxJan = decl.getMonthlyTax()[0];
        /* Налог: 0.13 * (240000 - 24000) + 0.20 * (500000 - 240000)
         = 0.13 * 216000 + 0.20 * 260000 = 28080 + 52000 = 80080*/
        assertEquals(80080.0, taxJan, 0.01);
        assertEquals(80080.0, decl.getTotalTax(), 0.01);
        assertEquals(500000.0, decl.getCumulativeIncome()[0], 0.01);
    }
}