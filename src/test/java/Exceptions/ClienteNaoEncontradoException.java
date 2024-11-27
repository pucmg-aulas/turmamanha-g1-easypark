package Exceptions;

public class ClienteNaoEncontradoException extends Exception {

    public ClienteNaoEncontradoException() {
        super("Cliente não encontrado.");
    }

    public ClienteNaoEncontradoException(String message) {
        super(message);
    }
}
