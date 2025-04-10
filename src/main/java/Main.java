import connection.MyDataSource;
import model.AlmacenDB;
import model.Cliente;
import model.ClienteDB;

import java.time.LocalDate;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        MyDataSource.conectarMySQL();

        AlmacenDB datos = new ClienteDB();
        List<Cliente> clientes = datos.getAllClientes();
        System.out.println(clientes);

        Cliente cliente =
                new Cliente("JOAQUIN","ALONSO SAIZ","53052298S", LocalDate.now());
        datos.updateCliente(cliente);
    }
}
