package com.example.springboothw4.repositories;

import com.example.springboothw4.entities.Product;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProductDao {

    private final SessionFactory sessionFactory;
    Session session = null;


    public ProductDao() {
        this.sessionFactory = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(Product.class)
                .buildSessionFactory();
    }


    public void addProduct(Product product) {
        session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        session.save(product);
        session.getTransaction().commit();
        session.close();
    }

    public List<Product> findAllProduct() {
        session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        session.byId(Product.class);
        List<Product> listCatalog = session
        .createQuery("FROM Product ORDER BY id").getResultList();
        session.getTransaction().commit();
        session.close();
        return listCatalog;
    }

    public Product findById(long id) {
        session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        Product product = session.get(Product.class, id);
        session.getTransaction().commit();
        session.close();
        return product;
    }

    public void update(Product product) {
        session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        session.update(product);
        session.getTransaction().commit();
        session.close();
    }

    public void deleteProduct(Long id) {
        session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        Product product = session.get(Product.class, id);
        session.delete(product);
        session.getTransaction().commit();
        session.close();
    }


}
