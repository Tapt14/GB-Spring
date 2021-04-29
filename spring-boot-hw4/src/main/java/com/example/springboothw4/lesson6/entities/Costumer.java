package com.example.springboothw4.lesson6.entities;

import org.hibernate.annotations.Cascade;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.util.List;

@Component
@Entity
@Table(name = "costumers")
public class Costumer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    int id;

    @Column(name = "name")
    private String name;

    @Cascade(org.hibernate.annotations.CascadeType.DELETE)
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "costumers_products",
            joinColumns = @JoinColumn(name = "costumer_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    private List<Product> products;

    public Costumer() {
    }

    public int getId() {
        return id;
    }

    public Costumer setId(int id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    @Override
    public String toString() {
        return "Costumer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
