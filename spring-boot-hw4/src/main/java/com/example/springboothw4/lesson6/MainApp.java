package com.example.springboothw4.lesson6;

import com.example.springboothw4.lesson6.entities.Costumer;
import com.example.springboothw4.lesson6.entities.Product;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Collectors;

public class MainApp {


    public static void main(String[] args) {

        prepareData();
        getProductByIdCostumer(3);

    }

    private static void prepareData() {
        SessionFactory factory = new Configuration()
                .configure(("hibernate.cfg.xml"))
                .buildSessionFactory();


        Session session = null;

        try {
            String sql = Files.lines(Paths.get("src/main/java/com/example/springboothw4/lesson6/createDB.sql")).collect(Collectors.joining(" "));
            session = factory.getCurrentSession();
            session.beginTransaction();
            session.createNativeQuery(sql).executeUpdate();
            session.getTransaction().commit();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            factory.close();
            if (session != null) {
                session.close();
            }
        }
    }

    private static void getProductByIdCostumer(int id) {

        try (SessionFactory factory = new Configuration()
                .configure("hibernate.cfg.xml")
                .buildSessionFactory();
             Session session = factory.getCurrentSession()) {
            session.beginTransaction();
            Costumer costumer = session.get(Costumer.class, id);
            for (Product p: costumer.getProducts()) {
                System.out.println(p.getTitle());
            }
            System.out.println(costumer);
                    session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
