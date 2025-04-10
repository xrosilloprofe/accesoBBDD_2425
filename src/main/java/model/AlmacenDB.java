package model;

import java.util.List;

public interface AlmacenDB {
    List<Cliente> getAllClientes();
    int updateCliente(Cliente cliente);
    int addCliente(Cliente cliente);
    int deleteCliente(String DNI);
}
