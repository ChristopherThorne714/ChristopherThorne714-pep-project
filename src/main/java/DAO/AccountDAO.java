package DAO;

import Model.Account;
import Util.ConnectionUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AccountDAO {

    /**
     * should insert the given account into db and return the newly created object
     * @param account to be inserted
     * @return newly created account
     */
    public Account insertAccount(Account account) {
        Connection con = ConnectionUtil.getConnection();
        String username = account.getUsername();
        String password = account.getPassword();

        if (username.isBlank() != true && password.length() >= 4) {
            try {
                String sql = "INSERT INTO account (username, password) VALUES (?, ?);";
                PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

                ps.setString(1, username);
                ps.setString(2, password);

                ps.executeUpdate();
                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    int generated_account_key = (int) rs.getLong(1);
                    return new Account(generated_account_key, account.getUsername(), account.getPassword());
                }
            } catch(SQLException e) {
                System.err.println(e.getMessage());
            }
        }
        return null;
    }
    
}
