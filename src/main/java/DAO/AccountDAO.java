package DAO;

import Model.Account;
import Util.ConnectionUtil;

import java.sql.*;

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

        try {
            String sql = "INSERT INTO account (username, password) SELECT ?, ? WHERE NOT EXISTS (SELECT 1 FROM account WHERE username = ?);";
            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            ps.setString(1, username);
            ps.setString(2, password);
            ps.setString(3, username);

            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                int generated_account_key = (int) rs.getLong(1);
                return new Account(generated_account_key, account.getUsername(), account.getPassword());
            }
        } catch(SQLException e) {
            System.err.println(e.getMessage());
        }
        return null;
    }

    /**
     * should query the database for an account object and validate against it then return the validated account object or null if unsuccessful
     * @param account for querying
     * @return validated Account object or null
     */
    public Account validateAccount(Account account) {
        Connection con = ConnectionUtil.getConnection();

        try {
            String sql = "SELECT * FROM account WHERE username = ? AND password = ?;";
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1,account.getUsername());
            ps.setString(2, account.getPassword());

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Account(rs.getInt("account_id"), rs.getString("username"), rs.getString("password"));
            }
        } catch(SQLException e) {
            System.err.println(e.getMessage());
        }
        return null;
    }
    
}
