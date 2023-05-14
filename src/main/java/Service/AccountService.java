package Service;

import DAO.AccountDAO;
import Model.Account;

import java.util.List;

public class AccountService {
    private final AccountDAO accountDAO;

    public AccountService() {
        accountDAO = new AccountDAO();
    }

    public AccountService(AccountDAO accountDAO) {
        this.accountDAO = accountDAO;
    }

    public Account findAccount(String username) {
        return accountDAO.selectAccount(username);
    }

    public Account findAccountById(int accountId) {
        return accountDAO.selectAccountById(accountId);
    }

    public Account addAccount(Account account) {
        String username = account.getUsername();
        String password = account.getPassword();
        boolean userExisted = findAccount(username) != null;
        if(username.length() > 0 && password.length() >= 4 && !userExisted) {
            return accountDAO.insertAccount(account);
        }
        return null;
    }
}
