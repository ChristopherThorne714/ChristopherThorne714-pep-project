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

        try {
            String sql = "INSERT INTO account (username, password) VALUES (?, ?);";
            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            ps.setString(1, account.getUsername());
            ps.setString(2, account.getPassword());

            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                int generated_account_key = (int) rs.getLong(1);
                return new Account(generated_account_key, rs.getString("username"), rs.getString("password"));
            }
        } catch(SQLException e) {
            System.err.println(e.getMessage());
        }
        return null;
    }
    
}
