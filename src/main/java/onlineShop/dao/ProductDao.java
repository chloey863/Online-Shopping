package onlineShop.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import onlineShop.entity.Product;

@Repository
public class ProductDao {

    @Autowired
    private SessionFactory sessionFactory;

    /**
     * Add/Save a product's information into database through Hibernate (ORM).
     * @param product
     */
    public void addProduct(Product product) {
        Session session = null;
        try {
            session = sessionFactory.openSession();
            session.beginTransaction();
            session.save(product);
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    /**
     * Delete/Remove a product's information from database through Hibernate (ORM).
     * @param productId
     */
    public void deleteProduct(int productId) {
        Session session = null;
        try {
            session = sessionFactory.openSession();
            session.beginTransaction();
            Product product = session.get(Product.class, productId);
            session.delete(product);
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        } finally {
            if (session != null) {
                session.close();
            }
        }

    }

    /**
     * Update a product's information from database through Hibernate (ORM).
     * @param product
     */
    public void updateProduct(Product product) {
        Session session = null;
        try {
            session = sessionFactory.openSession();
            session.beginTransaction();
            session.saveOrUpdate(product);
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    /**
     * Get a product's information based on given productId.
     * @param productId
     */
    public Product getProductById(int productId) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(Product.class, productId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Product> getAllProducts() {
        List<Product> products = new ArrayList<Product>();
        try (Session session = sessionFactory.openSession()) {
            products = session.createCriteria(Product.class).list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return products;
    }
}
