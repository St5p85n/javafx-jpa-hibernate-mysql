module com.gl.guserfx {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;
    requires java.sql;
    //module important debut
    requires java.persistence;
    requires org.hibernate.orm.core;
    opens com.gl.guserfx.Model to org.hibernate.orm.core, java.persistence;
    //module important fin

    opens com.gl.guserfx to javafx.fxml;
    exports com.gl.guserfx;
}