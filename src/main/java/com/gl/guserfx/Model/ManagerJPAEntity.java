package com.gl.guserfx.Model;

import javax.persistence.*;

public class ManagerJPAEntity {
    private static final EntityManagerFactory emf =
            Persistence.createEntityManagerFactory("javafxGL3");

    public static EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
}
