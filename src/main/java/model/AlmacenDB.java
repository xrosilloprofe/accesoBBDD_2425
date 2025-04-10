package model;

import java.util.List;

public interface AlmacenDB {
    List<Cliente> getAllClientes();
    int updateCliente(Cliente cliente);
    int addCliente(Cliente cliente);
    int deleteCliente(String dni);
    Cliente getCliente(String dni);
    void eliminarFactura(int numeroFactura);
    int articulosPrecio(double precio);
}
