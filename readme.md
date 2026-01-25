## La bonne pratique

````


public class GenericDAO<T, ID> {
	
	protected Class<T> entityClass;
	
	public GenericDAO(Class<T> entityClass) {
		this.entityClass = entityClass;
	}
	
	public void save(EntityManager em, T entity) {
		em.persist(entity);
	}
	
	public T findById(EntityManager em, ID id) {
		return em.find(entityClass, id);
	}
	
	public List<T> findAll(EntityManager em) {
		String jpql = "SELECT e FROM " + entityClass.getSimpleName() + " e";
		return em.createQuery(jpql, entityClass).getResultList();
	}
	
	public T update(EntityManager em, T entity) {
		return em.merge(entity);
	}
	
	public void delete(EntityManager em, T entity) {
		em.remove(em.contains(entity) ? entity : em.merge(entity));
	}
}

public class GenericService<T, ID> {
	
	protected GenericDAO<T, ID> dao;
	
	public GenericService(GenericDAO<T, ID> dao) {
		this.dao = dao;
	}
	
	public void save(T entity) {
		EntityManager em = ManagerJPAEntity.getEntityManager();
		try {
			em.getTransaction().begin();
			dao.save(em, entity);
			em.getTransaction().commit();
		} catch (Exception e) {
			em.getTransaction().rollback();
			throw e;
		} finally {
			em.close();
		}
	}
	
	public T findById(ID id) {
		EntityManager em = ManagerJPAEntity.getEntityManager();
		try {
			return dao.findById(em, id);
		} finally {
			em.close();
		}
	}
	
	public List<T> findAll() {
		EntityManager em = ManagerJPAEntity.getEntityManager();
		try {
			return dao.findAll(em);
		} finally {
			em.close();
		}
	}
	
	public T update(T entity) {
		EntityManager em = ManagerJPAEntity.getEntityManager();
		try {
			em.getTransaction().begin();
			T updated = dao.update(em, entity);
			em.getTransaction().commit();
			return updated;
		} catch (Exception e) {
			em.getTransaction().rollback();
			throw e;
		} finally {
			em.close();
		}
	}
	
	public void delete(T entity) {
		EntityManager em = ManagerJPAEntity.getEntityManager();
		try {
			em.getTransaction().begin();
			dao.delete(em, entity);
			em.getTransaction().commit();
		} catch (Exception e) {
			em.getTransaction().rollback();
			throw e;
		} finally {
			em.close();
		}
	}
}


public class ProduitService extends GenericService<Produit, Long> {
	
	public ProduitService() {
		super(new GenericDAO<>(Produit.class));
	}
	
	// ajouter des règles métier
	public void augmenterPrix(Long id, double pourcentage) {
		Produit p = findById(id);
		p.setPrix(p.getPrix() * (1 + pourcentage));
		update(p);
	}
}

public class ProduitController {
	
	private final ProduitService produitService = new ProduitService();
	
	@FXML private TextField desT;
	@FXML private TextField prixT;
	@FXML private TextField qtT;
	
	@FXML
	protected void addProduit() {
		
		Produit p = new Produit();
		p.setDesignation(desT.getText());
		p.setPrix(Double.parseDouble(prixT.getText()));
		p.setQuantite(Integer.parseInt(qtT.getText()));
		
		produitService.save(p);
	}
}

````