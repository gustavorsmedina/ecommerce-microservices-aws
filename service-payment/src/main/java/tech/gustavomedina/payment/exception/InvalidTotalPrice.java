package tech.gustavomedina.payment.exception;

public class InvalidTotalPrice extends RuntimeException {

    public InvalidTotalPrice(String message) {
        super(message);
    }

}
