package com.example.maya2;

import javafx.collections.ObservableList;

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

    public void StudentRegisterUpdate(String ID, String password, String email, String programme, int muet, String fullName){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/maya","root","testing");
            PreparedStatement statement = connection.prepareStatement("INSERT INTO maya.student VALUES(?,?,?,?,?,?)");
            statement.setString(1, ID);
            statement.setString(2, email);
            statement.setString(3, password);
            statement.setString(4, programme);
            statement.setInt(5, muet);
            statement.setString(6, fullName.toUpperCase());
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
            statement.setString(4, fullName.toUpperCase());
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
            PreparedStatement statement = connection.prepareStatement("select Module,Occurrence,Mode,Day,Start,End,Tutorial,Target,Actual from maya.fsktm where `Module` Like ? UNION select Module,Occurrence,Mode,Day,Start,End,Tutorial,Target,Actual from maya.fll where `Module` Like ? UNION select Module,Occurrence,Mode,Day,Start,End,Tutorial,Target,Actual from maya.uni where `Module` Like ?");
            statement.setString(1,"%" + search +"%");
            statement.setString(2,"%" + search +"%");
            statement.setString(3,"%" + search +"%");
            rs = statement.executeQuery();
        } catch(SQLException | ClassNotFoundException e){
            e.printStackTrace();
        }
        return rs;
    }

    public void EditQuery(ObservableList<String> list, String day, String start, String end, String Lecturer, int target){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/maya","root","testing");
            PreparedStatement statement = connection.prepareStatement("UPDATE maya.fsktm SET Day = ?, Start = ?, End = ?, Tutorial = ? WHERE Module = ? AND Occurrence = ? AND Mode = ?");
            PreparedStatement changeTargetStatement = connection.prepareStatement("UPDATE maya.fsktm SET Target = ? WHERE Module = ? AND Occurrence = ?");
            statement.setString(1, day);
            statement.setString(2, start);
            statement.setString(3, end);
            statement.setString(4, Lecturer.toUpperCase());
            statement.setString(5, list.get(0));
            statement.setString(6, list.get(1));
            statement.setString(7, list.get(2));
            changeTargetStatement.setInt(1, target);
            changeTargetStatement.setString(2, list.get(0));
            changeTargetStatement.setString(3, list.get(1));
            statement.executeUpdate();
            changeTargetStatement.executeUpdate();
        } catch(SQLException | ClassNotFoundException e){
            e.printStackTrace();
        }
    }

    public void InsertQuery(String faculty, String module, String occ, String mode, String day, String start, String end, String lecturer, int target){
        faculty = switch(faculty){
            case "Faculty of Computer Science and Information Technology" -> "fsktm";
            case "Faculty of Language and Linguistics" -> "fll";
            case "University" -> "uni";
            default -> throw new IllegalStateException("Unexpected value: " + faculty);
        };

        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/maya","root","testing");
            PreparedStatement statement = connection.prepareStatement("INSERT INTO maya." + faculty + " (Module, Occurrence, Mode, Day, Start, End, Tutorial, Target, Actual) VALUES (?,?,?,?,?,?,?,?,?)");
            statement.setString(1, module.toUpperCase());
            statement.setString(2, occ);
            statement.setString(3, mode);
            statement.setString(4, day);
            statement.setString(5, start);
            statement.setString(6, end);
            statement.setString(7, lecturer.toUpperCase());
            statement.setInt(8, target);
            statement.setInt(9, 0);
            statement.executeUpdate();
        } catch(SQLException | ClassNotFoundException e){
            e.printStackTrace();
        }
    }

    public void DeleteQuery(String module, String occ, String mode){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            String[] tables = {"fsktm", "fll", "uni"};
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/maya","root","testing");
            PreparedStatement statement = connection.prepareStatement("DELETE FROM maya.fsktm WHERE Module = ? AND Occurrence = ? AND Mode = ?");
            statement.setString(1, module);
            statement.setString(2, occ);
            statement.setString(3, mode);
            statement.executeUpdate();
        } catch(SQLException | ClassNotFoundException e){
            e.printStackTrace();
        }
    }
}
