
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author user
 */
public abstract class Product {
    protected String productId;
    protected String name;
    protected double price;
    protected int quantity;

    public Product(String productId, String name, double price, int quantity) {
        this.productId = productId;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    public abstract String getCategory();

    public String getProductId() { 
        return productId; }
    public String getName() {
         return name; }
    public double getPrice() {
         return price; }
    public int getQuantity() {
         return quantity; }

    public void setQuantity(int quantity) {
         this.quantity = quantity; }

    @Override
    public String toString() {
        return String.format("[%s] %s - RM%.2f (%d in stock)", productId, name, price, quantity);
    }

    public String toDataString() {
        return getCategory() + "," + productId + "," + name + "," + price + "," + quantity;
    }
    
  
}
