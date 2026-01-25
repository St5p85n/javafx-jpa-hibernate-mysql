package com.gl.guserfx.utilitaires;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Navigation {
    public static void changerFenetre(String page,String titre, Node node){


        try {
            FXMLLoader loader = new FXMLLoader(
                    Navigation.class.getResource("/com/gl/guserfx/views/"+page)
            );
            Parent parent = loader.load();
            Stage stage = (Stage) node.getScene().getWindow();
            stage.setScene(new Scene(parent));
            stage.setTitle(titre);
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
