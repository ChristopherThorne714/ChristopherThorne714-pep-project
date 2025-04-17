package Service;

import DAO.AccountDAO;
import DAO.MessageDAO;
import Model.Account;
import java.util.List;

public class AccountService {
    private AccountDAO accountDAO;
    
    public AccountService() {
        accountDAO = new AccountDAO();
    }

    /**
     * for testing purposes
     * @param accountDAO
     */
    public AccountService(AccountDAO accountDAO) {
        this.accountDAO = accountDAO;
    }

    /**
     * calls AccountDAO to insert the given account to db
     * @param account to be inserted
     * @return the inserted account
     */
    public Account addAccount(Account account) {
        return accountDAO.insertAccount(account);
    }


}
