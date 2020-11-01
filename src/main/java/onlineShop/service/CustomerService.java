package onlineShop.service;

import onlineShop.entity.Cart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import onlineShop.dao.CustomerDao;
import onlineShop.entity.Customer;

/**
 * CustomerService is a Registration Service for a new customer.
 * CustomerService deals with the business-related logic for the controller.
 * "@Service" is a component to be used by Controller.
 */
@Service
public class CustomerService {

    @Autowired
    private CustomerDao customerDao;

    /**
     * Add/Save a customer's information into database through Hibernate (ORM).
     * @param customer
     */
    public void addCustomer(Customer customer) {
        // activate the account (By default it's not enabled)
        customer.getUser().setEnabled(true);

        Cart cart = new Cart();
        customer.setCart(cart);

        // add to data tier
        customerDao.addCustomer(customer);
    }

    /**
     * Return a customer given a userName (userName == email).
     * @param userName
     * @return a customer object
     */
    public Customer getCustomerByUserName(String userName) {
        return customerDao.getCustomerByUserName(userName);
    }
}
