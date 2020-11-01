package onlineShop.dao;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import onlineShop.entity.Authorities;
import onlineShop.entity.Customer;
import onlineShop.entity.User;

/**
 * CustomerDao deals with the data-related logic for the controller.
 * "@Repository" is a component to be used by CustomerService.
 */
@Repository
public class CustomerDao {
    /**
     * Use SessionFactory from Hibernate to create the ORM
     * The sessionFactory object is already created in ApplicationConfig
     * class, now only need to Autowired.
     *
     * "@Autowired" is one of the annotations-driven Dependency Injection features
     * of Spring, it allows Spring to resolve and inject collaborating beans into our bean.
     */
    @Autowired
    private SessionFactory sessionFactory;

    /**
     * Add/Save a customer's information into database through Hibernate (ORM).
     * @param customer
     */
    public void addCustomer(Customer customer) {
        Authorities authorities = new Authorities();
        authorities.setEmailId(customer.getUser().getEmailId());
        authorities.setAuthorities("ROLE_USER");
        Session session = null;

        // add/save the "authorities" and "customer" to database through Hibernate (ORM) using a session
        // Transaction is an object used by the application to specify atomic units of work/operation
        try {
            session = sessionFactory.openSession();
            session.beginTransaction();
            session.save(authorities);
            session.save(customer);
            session.getTransaction().commit(); // save/commit the intended operations
        } catch (Exception e) {
            e.printStackTrace();
            session.getTransaction().rollback(); // roll (i.e. undo operation) back to "session.beginTransaction()"
        } finally {
            if (session != null) {
                session.close(); // transaction also ends with session
            }
        }
    }

    /**
     * Return a customer given a userName (userName == email).
     * @param userName
     * @return a customer object
     */
    public Customer getCustomerByUserName(String userName) {
        User user = null;

        // java8 syntax of try(...) statement (where ... is some Autocloseable resource/object)
        // another similar syntax: the "try-with-resource" statement
        try (Session session = sessionFactory.openSession()) {
            // Criteria is equiv. to "SELECT ... WHERE..." sql query
            Criteria criteria = session.createCriteria(User.class);
            criteria.add(Restrictions.eq("emailId", userName));
            user = (User) criteria.uniqueResult();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (user != null) {
            return user.getCustomer();
        }
        return null;
    }
}