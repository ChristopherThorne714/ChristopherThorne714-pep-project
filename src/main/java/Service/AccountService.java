package Service;

import DAO.AccountDAO;
import Model.Account;

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
        if (account.getUsername().isBlank() != true && account.getPassword().length() >= 4) {
            return accountDAO.insertAccount(account);
        } else {
            return null;
        }
    }

    /**
     * should pass on account to the DAO for validation and return the validated account or null if unsuccessful
     * @param account to be validated
     * @return validated account or null
     */
    public Account login(Account account) {
        return accountDAO.validateAccount(account);
    }


}
