package org.example.payment;

public class PaymentManager {
    public static void processPayment(PaymentMethod method,double amount){
        method.pay(amount);
    }

    public static void main(String[] args) {
        PaymentMethod upi=new UPIPayment("deb@axis");
        PaymentMethod card=new CardPayment("081203");
        processPayment(upi,10000);
        processPayment(card,20000);
    }
}
