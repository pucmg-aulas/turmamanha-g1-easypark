package dao;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractDAO {

    public String gravar(String local, List lista) {
        try {
            FileOutputStream fo = new FileOutputStream(local);
            ObjectOutputStream oo = new ObjectOutputStream(fo);
            oo.writeObject(lista);
            oo.close();
            return "Dados gravados com sucesso";
        } catch (Exception e) {
            return "Erro ao serializar " + e.getMessage();
        }
    }

    public List ler(String local) {
        List retorno = new ArrayList();
        try {
            File arq = new File(local);
            FileInputStream fi = new FileInputStream(arq.getAbsolutePath());
            ObjectInputStream oi = new ObjectInputStream(fi);
            retorno = (List) oi.readObject();
            oi.close();
            return retorno;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return retorno;
        }
    }
}
