package org.example.payment;

public class UPIPayment implements PaymentMethod{
    private String upiId;

    public UPIPayment(String upiId){
        this.upiId=upiId;
    }

    @Override
    public void pay(double amount) {
        System.out.println(amount+"$ paid by this "+upiId+" upi id ");
    }
}
