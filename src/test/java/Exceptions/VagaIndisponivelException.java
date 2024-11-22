package Exceptions;


public class VagaIndisponivelException extends Exception {
    
    public VagaIndisponivelException(){
        super("A Vaga está indisponível para uso");
    }
    
}
