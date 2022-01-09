package com.example.maya2;

import javafx.collections.ObservableList;

import java.sql.*;

public class DBConnector {
    private Connection connection;

    public DBConnector(){
        try{
            this.connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/maya","root","testing");
        } catch (SQLException e){
            e.printStackTrace();
        }
    }
    
    public ResultSet StudentLoginQuery(String ID, String password){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM maya.student where studentid = ? AND password = ?");
            statement.setString(1, ID);
            statement.setString(2, password);
            return statement.executeQuery();
        } catch (SQLException | ClassNotFoundException e){
            e.printStackTrace();
        }
        return null;
    }

    public ResultSet FindStudent(String ID){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM maya.student where studentid = ?");
            statement.setString(1, ID);
            return statement.executeQuery();
        } catch (SQLException | ClassNotFoundException e){
            e.printStackTrace();
        }
        return null;
    }

    public ResultSet FindStaff(String ID){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM maya.staff where username = ?");
            statement.setString(1, ID);
            return statement.executeQuery();
        } catch (SQLException | ClassNotFoundException e){
            e.printStackTrace();
        }
        return null;
    }

    public ResultSet StaffLoginQuery(String ID, String password){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM maya.staff where username = ? AND password = ?");
            statement.setString(1, ID);
            statement.setString(2, password);
            return statement.executeQuery();
        } catch (SQLException | ClassNotFoundException e){
            e.printStackTrace();
        }
        return null;
    }

    public ResultSet StaffIDQuery(String ID){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM maya.staff where username = ?");
            statement.setString(1, ID);
            return statement.executeQuery();
        } catch (SQLException | ClassNotFoundException e){
            e.printStackTrace();
        }
        return null;
    }

    public void StudentRegisterUpdate(String ID, String password, String email, String programme, int muet, String fullName){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
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
            PreparedStatement statement = connection.prepareStatement("select * from maya.moduledb where `Module` Like ?");
            statement.setString(1,"%" + search +"%");
            rs = statement.executeQuery();
        } catch(SQLException | ClassNotFoundException e){
            e.printStackTrace();
        }
        return rs;
    }

    public ResultSet SearchQuery(String search, String occ){
        ResultSet rs = null;
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            PreparedStatement statement = connection.prepareStatement("select * from maya.moduledb where `Module` Like ? AND `Occurrence` = ?", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            statement.setString(1,"%" + search +"%");
            statement.setString(2, occ);
            rs = statement.executeQuery();
        } catch(SQLException | ClassNotFoundException e){
            e.printStackTrace();
        }
        return rs;
    }

    public ResultSet SearchLecturer(String lecture){
        ResultSet rs = null;
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            PreparedStatement statement = connection.prepareStatement("select * from maya.moduledb where `Tutorial` Like ?", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            statement.setString(1,"%" + lecture +"%");
            rs = statement.executeQuery();
        } catch(SQLException | ClassNotFoundException e){
            e.printStackTrace();
        }
        return rs;
    }

    public ResultSet SearchDistinctModuleOcc(String search){
        ResultSet rs = null;
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            PreparedStatement statement = connection.prepareStatement("select distinct Module,Occurrence from maya.moduledb where `Module` Like ?");
            statement.setString(1,"%" + search +"%");
            rs = statement.executeQuery();
        } catch(SQLException | ClassNotFoundException e){
            e.printStackTrace();
        }
        return rs;
    }

    public ResultSet SearchDistinctModule(String search){
        ResultSet rs = null;
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            PreparedStatement statement = connection.prepareStatement("select distinct Module from maya.moduledb where `Module` Like ?");
            statement.setString(1,"%" + search +"%");
            rs = statement.executeQuery();
        } catch(SQLException | ClassNotFoundException e){
            e.printStackTrace();
        }
        return rs;
    }

    public void EditQuery(ObservableList<String> list, String day, String start, String end, String Lecturer, int target){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            PreparedStatement statement = connection.prepareStatement("UPDATE maya.moduledb SET Day = ?, Start = ?, End = ?, Tutorial = ? WHERE Module = ? AND Occurrence = ? AND Mode = ?");
            PreparedStatement changeTargetStatement = connection.prepareStatement("UPDATE maya.moduledb SET Target = ? WHERE Module = ? AND Occurrence = ?");
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

    public void InsertQuery(String module, String occ, String mode, String day, String start, String end, String lecturer, int target){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            PreparedStatement statement = connection.prepareStatement("INSERT INTO maya.moduledb (Module, Occurrence, Mode, Day, Start, End, Tutorial, Target, Actual) VALUES (?,?,?,?,?,?,?,?,?)");
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
            PreparedStatement statement = connection.prepareStatement("DELETE FROM maya.moduledb WHERE Module = ? AND Occurrence = ? AND Mode = ?");
            statement.setString(1, module);
            statement.setString(2, occ);
            statement.setString(3, mode);
            statement.executeUpdate();
        } catch(SQLException | ClassNotFoundException e){
            e.printStackTrace();
        }
    }

    public void DeleteModuelForID(String ID){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            PreparedStatement delete = connection.prepareStatement("DELETE FROM maya.registered WHERE studentid = ?");
            delete.setString(1, ID);
            delete.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void AddModuleForID(String ID, String module, String occ){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            PreparedStatement add = connection.prepareStatement("INSERT INTO maya.registered VALUES (?,?,?)");
            add.setString(1, ID);
            add.setString(2, module);
            add.setString(3, occ);
            add.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public ResultSet SearchRegistered(String ID){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            PreparedStatement search = connection.prepareStatement("SELECT * FROM maya.registered WHERE studentid = ?", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            search.setString(1, ID);
            return search.executeQuery();
        } catch (SQLException | ClassNotFoundException e){
            e.printStackTrace();
        }
        return null;
    }

    public ResultSet SearchRegistered(String module, String occ){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            PreparedStatement search = connection.prepareStatement("SELECT * FROM maya.registered WHERE Module = ? AND Occurrence = ?", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            search.setString(1, module);
            search.setString(2, occ);
            return search.executeQuery();
        } catch (SQLException | ClassNotFoundException e){
            e.printStackTrace();
        }
        return null;
    }

    public void AddToActual(String module, String occ){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            PreparedStatement update = connection.prepareStatement("UPDATE maya.moduledb SET Actual = Actual + 1 WHERE Module = ? AND Occurrence = ?");
            update.setString(1, module);
            update.setString(2, occ);
            update.executeUpdate();
        } catch (SQLException | ClassNotFoundException e){
            e.printStackTrace();
        }
    }

    public void RemoveFromActual(String module, String occ){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            PreparedStatement update = connection.prepareStatement("UPDATE maya.moduledb SET Actual = Actual - 1 WHERE Module = ? AND Occurrence = ?");
            update.setString(1, module);
            update.setString(2, occ);
            update.executeUpdate();
        } catch (SQLException | ClassNotFoundException e){
            e.printStackTrace();
        }
    }
}
