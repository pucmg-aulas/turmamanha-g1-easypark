package Exceptions;


public class VeiculoNaoEncontradoException extends Exception {
    
    public VeiculoNaoEncontradoException() {
        super("Veículo não encontrado.");
    }

    public VeiculoNaoEncontradoException(String message) {
        super(message);
    }
}
