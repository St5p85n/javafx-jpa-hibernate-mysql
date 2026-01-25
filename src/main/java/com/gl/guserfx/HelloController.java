package com.gl.guserfx;

import com.gl.guserfx.DAO.UtilisateurDao;
import com.gl.guserfx.Model.ManagerJPAEntity;
import com.gl.guserfx.Model.Produit;
import com.gl.guserfx.Model.Utilisateur;
import com.gl.guserfx.utilitaires.Navigation;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import javax.persistence.EntityManager;
import java.util.List;

public class HelloController {
    UtilisateurDao db = new UtilisateurDao();
    @FXML
    private Label welcomeText;
    @FXML
    private TextField nomT;
    @FXML
    private TextField emailT;
    @FXML
    private TextField passT;

    //Infos produit
    @FXML
    private TextField desT;
    @FXML
    private TextField prixT;
    @FXML
    private TextField qtT;
    @FXML
    private TextField idT;

    @FXML
    protected void addUser(){
        Utilisateur u = new Utilisateur();
        u.setEmail(emailT.getText());
        u.setNom(nomT.getText());
        u.setPassword(passT.getText());
        db.addUser(u);
        clearForm();
    }
    @FXML
    protected void toListeUser(ActionEvent event){
        Node node = (Node) event.getSource();
        Navigation.changerFenetre("listUser.fxml","Liste Utilisateur",node);
    }
    private void clearForm(){
        nomT.setText("");
        emailT.setText("");
        passT.setText("");
    }


    //Produit Start
    @FXML
    protected void toAddProduct(ActionEvent event){
        Node node = (Node) event.getSource();
        Navigation.changerFenetre("addProd.fxml","Ajout Produit",node);
    }

    @FXML
    protected void addProduit(){

        EntityManager em = ManagerJPAEntity.getEntityManager();
        try {
            em.getTransaction().begin();

            Produit p = new Produit();
            p.setDesignation(desT.getText());
            p.setPrix(Double.parseDouble(prixT.getText()));
            p.setQuantite(Integer.parseInt(qtT.getText()));
            em.persist(p);

            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    public List<Produit> getAllProduits() {
        EntityManager em = ManagerJPAEntity.getEntityManager();
        try {
            return em.createQuery("SELECT p FROM Produit p", Produit.class)
                    .getResultList();
        } finally {
            em.close();
        }
    }
    public Produit getProduitById(Long id) {
        EntityManager em = ManagerJPAEntity.getEntityManager();
        try {
            return em.find(Produit.class, id);
        } finally {
            em.close();
        }
    }

    @FXML
    protected void updateProduit() {

        EntityManager em = ManagerJPAEntity.getEntityManager();
        try {
            em.getTransaction().begin();

            Long id = Long.parseLong(idT.getText()); // champ ID
            Produit p = em.find(Produit.class, id);

            if (p != null) {
                p.setDesignation(desT.getText());
                p.setPrix(Double.parseDouble(prixT.getText()));
                p.setQuantite(Integer.parseInt(qtT.getText()));
            }

            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }
    @FXML
    protected void deleteProduit() {

        EntityManager em = ManagerJPAEntity.getEntityManager();
        try {
            em.getTransaction().begin();

            Long id = Long.parseLong(idT.getText());
            Produit p = em.find(Produit.class, id);

            if (p != null) {
                em.remove(p);
            }

            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }



    //Produit End
    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
}
