package org.example.payment;

public class CardPayment implements PaymentMethod{
    private String cardNo;

    public CardPayment(String cardNo){
        this.cardNo=cardNo;
    }

    @Override
    public void pay(double amount) {
        System.out.println(amount+"$ paid by this "+cardNo+" card no");
    }
}
