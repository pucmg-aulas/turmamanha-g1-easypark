package test;

import Models.Cliente;

import org.junit.Before;
import org.junit.Test;


import java.time.LocalTime;

import static org.junit.Assert.*;
public class ClienteTest
{

    @Test
    public void testCadastroCliente()
    {
        Cliente cliente = new Cliente("João", "123456789");
        assertEquals("João", cliente.getNome());
    }
}
