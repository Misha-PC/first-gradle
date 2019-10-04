package com.example.demo.customer;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private int zone;
    protected Customer(){}

    public Customer(long id, int zone){
        this.id = id;
        this.zone = zone;
    }

    @Override

    public String toString(){
        return String.format(
          "Customer=[id=%d, zone='%d']", id, zone);
    }
}
