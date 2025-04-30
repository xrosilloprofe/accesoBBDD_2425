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

        datos.deleteCliente("123X");

        Cliente cliente1 =
                new Cliente("XAVIER","ROSILLO","123X", LocalDate.of(1993,2,14));

        datos.addCliente(cliente1);
        System.out.println(datos.getAllClientes());

        System.out.println(datos.getCliente("123X"));

        //datos.eliminarFactura(4);

        System.out.println(datos.articulosPrecio(100));
        System.out.println(datos.crearFactura(10));

    }
}
