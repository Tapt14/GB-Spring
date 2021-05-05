package com.example.springboothw4.lesson6;

import com.example.springboothw4.lesson6.entities.Costumer;
import com.example.springboothw4.lesson6.entities.Product;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class Factory {

    Product product;
    Costumer costumer;
    Session session;

    @Autowired
    public Factory setProduct(Product product) {
        this.product = product;
        return this;
    }

    @Autowired
    public Factory setCostumer(Costumer costumer) {
        this.costumer = costumer;
        return this;
    }

    public void prepareData() {

        try (SessionFactory factory = new Configuration()
                .configure(("hibernate.cfg.xml"))
                .buildSessionFactory()) {
            String sql = Files.lines(Paths.get("src/main/java/com/example/springboothw4/lesson6/createDB.sql")).collect(Collectors.joining(" "));
            session = factory.getCurrentSession();
            session.beginTransaction();

            session.createNativeQuery(sql).executeUpdate();

            buyProduct(1L, new Long[]{2L});
            buyProduct(2L, new Long[]{1L, 2L, 3L});
            buyProduct(3L, new Long[]{1L, 2L, 3L, 4L});

            session.getTransaction().commit();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    private void buyProduct(Long idCostumer, Long[] idProducts) {
        costumer = session.get(Costumer.class, idCostumer);
        if (costumer.getProducts() != null) {
            for (Long l : idProducts) {
                product = session.get(Product.class, l);
                costumer.getProducts().add(product);
            }
        } else {
            Product[] products = new Product[idProducts.length];
            for (int i = 0; i < idProducts.length; i++) {
                products[i] = session.get(Product.class, idProducts[i]);
            }
            costumer.setProducts(List.of(products));
        }
    }

    public void getProductByIdCostumer(Long id) {

        try (SessionFactory factory = new Configuration()
                .configure(("hibernate.cfg.xml"))
                .buildSessionFactory();
            ) {
            session = factory.getCurrentSession();
            session.beginTransaction();
            Costumer costumer = session.get(Costumer.class, id);
            List<String> list = new ArrayList<>();
            for (Product p : costumer.getProducts()) {
                list.add(p.getTitle());

            }
            System.out.println(costumer + " Product: " + list);
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if(session != null) {
                session.close();
            }
        }
    }

    public void getCostumerByIdProduct(Long id) {
        try (SessionFactory factory = new Configuration()
                .configure(("hibernate.cfg.xml"))
                .buildSessionFactory();
        ) {
            session = factory.getCurrentSession();
            session.beginTransaction();
            Product product = session.get(Product.class, id);
            List<String> list = new ArrayList<>();
            for (Costumer c : product.getCostumers()) {
                list.add(c.getName());

            }
            System.out.println(product + " Costumer: " + list);
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if(session != null) {
                session.close();
            }
        }
    }

}
