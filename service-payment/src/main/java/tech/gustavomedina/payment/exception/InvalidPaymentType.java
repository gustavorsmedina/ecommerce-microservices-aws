package tech.gustavomedina.payment.exception;

public class InvalidPaymentType extends RuntimeException {

    public InvalidPaymentType(String message) {
        super(message);
    }

}
