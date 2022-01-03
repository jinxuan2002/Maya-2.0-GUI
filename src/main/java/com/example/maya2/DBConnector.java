package com.example.maya2;

import java.sql.*;

public class DBConnector {
    private Connection connection;
    
    public ResultSet StudentLoginQuery(String ID, String password){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/maya","root","testing");
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM maya.student where studentid = ? AND password = ?");
            statement.setString(1, ID);
            statement.setString(2, password);
            return statement.executeQuery();
        } catch (SQLException | ClassNotFoundException e){
            e.printStackTrace();
        }
        return null;
    }

    public ResultSet StaffLoginQuery(String ID, String password){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/maya","root","testing");
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM maya.staff where username = ? AND password = ?");
            statement.setString(1, ID);
            statement.setString(2, password);
            return statement.executeQuery();
        } catch (SQLException | ClassNotFoundException e){
            e.printStackTrace();
        }
        return null;
    }

    public void StudentRegisterUpdate(String ID, String password, String email, String programme, int muet){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/maya","root","testing");
            PreparedStatement statement = connection.prepareStatement("INSERT INTO maya.student VALUES(?,?,?,?,?)");
            statement.setString(1, ID);
            statement.setString(2, email);
            statement.setString(3, password);
            statement.setString(4, programme);
            statement.setInt(5, muet);
            statement.executeUpdate();
        } catch(SQLException | ClassNotFoundException e){
            e.printStackTrace();
        }
    }

    public void StaffRegisterUpdate(String username, String mail, String password, String fullName){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/maya","root","testing");
            PreparedStatement statement = connection.prepareStatement("INSERT INTO maya.staff VALUES(?,?,?,?)");
            statement.setString(1, username);
            statement.setString(2, mail);
            statement.setString(3, password);
            statement.setString(4, fullName);
            statement.executeUpdate();
        } catch(SQLException | ClassNotFoundException e){
            e.printStackTrace();
        }
    }

    public ResultSet SearchQuery(String search){
        ResultSet rs = null;
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/maya","root","testing");
            PreparedStatement statement = connection.prepareStatement("select * from maya.fsktm where `Module` Like ?");
            statement.setString(1,"%" + search +"%");
            rs = statement.executeQuery();
        } catch(SQLException | ClassNotFoundException e){
            e.printStackTrace();
        }
        return rs;
    }
}
