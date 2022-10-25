import com.mircea.main.model.Transaction;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TransactionsTest {
   static Transaction transaction;

    @BeforeAll
    public static void setup() {
        transaction = new Transaction(Transaction.Type.WITHDRAW , 1546905600, "6b8dd258-aba3-4b19-b238-45d15edd4b48", 624.99);
    }

    @Test
    public void correctDateTest() {
        assertEquals("08-01-2019", transaction.returnDate(transaction.getTimeStamp()));
    }
}
