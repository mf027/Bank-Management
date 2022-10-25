import com.mircea.main.model.account.Account;
import com.mircea.main.model.account.Chequing;
import com.mircea.main.model.account.Loan;
import com.mircea.main.model.account.Savings;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AccountTest {
     static Account []accounts;
    @BeforeAll
    public static void setup() {
        accounts = new Account[] {
                new Chequing("f84c43f4-a634-4c57-a644-7602f8840870", "Michael Scott", 1524.51),
                new Savings("ce07d7b3-9038-43db-83ae-77fd9c0450c9", "Saul Goodman", 2241.60),
                new Loan("4991bf71-ae8f-4df9-81c1-9c79cff280a5", "Phoebe Buffay", 2537.31)
        };
    }

    @Test
    public void withdrawTest() {
        accounts[0].setBalance(1524.51);
        accounts[0].withdraw(1440);
        assertEquals(84.51, accounts[0].getBalance());
    }

    @Test
    public void overDraft() {
        accounts[0].setBalance(1524.51);
        accounts[0].withdraw(1534.43);
        assertEquals(-15.42, accounts[0].getBalance());
    }

    @Test
    public void overDraftLimit() {
        accounts[0].setBalance(1524.51);
        accounts[0].withdraw(1726);
        assertEquals(1524.51, accounts[0].getBalance());
    }

    @Test
    public void withdrawalFee() {
        accounts[1].withdraw(100);
        assertEquals(2136.60, accounts[1].getBalance());
    }

    @Test
    public void withdrawInterest() {
        accounts[2].withdraw(2434.31);
        assertEquals(5020.31, accounts[2].getBalance());
    }

    @Test
    public void withdrawLimitTest() {
        accounts[2].setBalance(2537.31);
        accounts[2].withdraw(7463.69);
        assertEquals(2537.31, accounts[2].getBalance());
    }

    @Test
    public void deposit() {
        accounts[0].setBalance(1524.51);
        accounts[0].deposit(4000);
        assertEquals( 5374.51, accounts[0].getBalance());
    }

    @Test
    public void loanDeposit() {
        accounts[2].setBalance(2537.31);
        accounts[2].deposit(1000);
        assertEquals(1537.31, accounts[2].getBalance());
    }
}
