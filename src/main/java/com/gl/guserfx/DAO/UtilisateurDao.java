package com.gl.guserfx.DAO;



import com.gl.guserfx.Model.Utilisateur;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UtilisateurDao {
    public String url = "jdbc:mysql://localhost:3306/introfx";
    public String user = "root";
    public String pass = "";
    public int addUser(Utilisateur u){
        try (
                Connection connection = DriverManager.getConnection(url,user,pass);
                PreparedStatement stmt = connection.prepareStatement("INSERT INTO utilisateur(nom,email,password) VALUES (?,?,?)")
        ){
            stmt.setString(1,u.getNom());
            stmt.setString(2,u.getEmail());
            stmt.setString(3,u.getPassword());
            stmt.executeUpdate();
            return 1;
        }catch (SQLException e){
            e.printStackTrace();
            return 0;
        }
    }

    public List<Utilisateur> printAllUsers(){
        List<Utilisateur> listeU = new ArrayList<>();
        try (
                Connection connection = DriverManager.getConnection(url,user,pass);
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery("SELECT * FROM utilisateur")
        ){
            while (resultSet.next()){
                Utilisateur u = new Utilisateur();
                u.setPassword(resultSet.getString("password"));
                u.setNom(resultSet.getString("nom"));
                u.setEmail(resultSet.getString("email"));
                u.setId(resultSet.getInt("id"));
                listeU.add(u);
            }
            return listeU;
        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }
}
