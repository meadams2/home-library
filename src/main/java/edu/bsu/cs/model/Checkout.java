package edu.bsu.cs.model;

import java.time.LocalDate;

public class Checkout {

    public static final class Builder {
        private String name;
        LocalDate checkoutDate;
        LocalDate dueDate;

        public Builder setName(String name){
            this.name = name;
            return this;
        }

        public Builder setCheckoutDate(LocalDate checkoutDate) {
            this.checkoutDate = checkoutDate;
            return this;
        }
        public Builder setDueDate(LocalDate dueDate) {
            this.dueDate = dueDate;
            return this;
        }

        public Checkout build(){
            return new Checkout(this);
        }
    }
    private final String name;
    private final LocalDate checkoutDate;
    private final LocalDate dueDate;

    public Checkout(Builder builder){
        this.name = builder.name;
        this.checkoutDate = builder.checkoutDate;
        this.dueDate = builder.dueDate;
    }

    public String getName(){return name;}
    public LocalDate getDueDate() {return dueDate;}

    @Override
    public String toString() {
        return "Checkout{" +
                "name='" + name + '\'' +
                ", checkoutDate=" + checkoutDate +
                ", dueDate=" + dueDate +
                '}';
    }
}
