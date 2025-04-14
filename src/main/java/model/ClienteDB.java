package model;

import connection.MyDataSource;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClienteDB implements AlmacenDB{
    @Override
    public List<Cliente> getAllClientes() {
        List<Cliente> clientes = new ArrayList<>();
        DataSource dataSource = MyDataSource.getMySQLDataSource();

        try(Connection connection= dataSource.getConnection();
            Statement statement=connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from Cliente")){

            while(resultSet.next()){
                clientes.add(new Cliente(
                        resultSet.getInt("id"),
                        resultSet.getString("nombre"),
                        resultSet.getString("apellidos"),
                        resultSet.getString("dni"),
                        resultSet.getDate("fecha_nacimiento").toLocalDate()
                ));
            }

        }catch (Exception e){
            e.printStackTrace();
        }

        return clientes;
    }

//    @Override
//    public int updateCliente(Cliente cliente) {
//
//        DataSource dataSource = MyDataSource.getMySQLDataSource();
//        int result=-1;
//        try(Connection connection = dataSource.getConnection();
//            Statement st = connection.createStatement()){
//
//            String query = "update Cliente set" +
//                    " nombre = '" + cliente.getNombre() + "'," +
//                    " apellidos = '" + cliente.getApellidos() + "'," +
//                    " fecha_nacimiento = '" + cliente.getFecha_nacimiento() + "'" +
//                    " where dni = '" + cliente.getDni() + "'";
//
//            result = st.executeUpdate(query);
//
//        } catch (SQLException e){
//            e.printStackTrace();
//        }
//        return result;
//    }

//    @Override
//    public int addCliente(Cliente cliente) {
//        DataSource dataSource = MyDataSource.getMySQLDataSource();
//        int result=-1;
//        try(Connection connection = dataSource.getConnection();
//            Statement st = connection.createStatement()){
//
//            String query = "insert into Cliente" +
//                    "(nombre,apellidos,dni,fecha_nacimiento)" +
//                    " values (" +
//                    " '" + cliente.getNombre() + "'," +
//                    " '" + cliente.getApellidos() + "'," +
//                    " '" + cliente.getDni() + "'," +
//                    " '" + cliente.getFecha_nacimiento() + "')";
//            result = st.executeUpdate(query);
//
//        } catch (SQLException e){
//            e.printStackTrace();
//        }
//        return result;
//    }

//    @Override
//    public int deleteCliente(String dni) {
//        DataSource dataSource = MyDataSource.getMySQLDataSource();
//        int result=-1;
//        try(Connection connection = dataSource.getConnection();
//            Statement st = connection.createStatement()){
//
//            String query = "delete from Cliente " +
//                    " where dni = '" + dni + "'";
//            result = st.executeUpdate(query);
//
//        } catch (SQLException e){
//            e.printStackTrace();
//        }
//        return result;
//    }

//    @Override
//    public Cliente getCliente(String dni) {
//        DataSource dataSource = MyDataSource.getMySQLDataSource();
//        Cliente cliente = null;
//        try(Connection connection = dataSource.getConnection();
//            Statement statement = connection.createStatement();
//            ResultSet resultSet =
//                    statement.executeQuery("SELECT * FROM Cliente WHERE" +
//                            " dni = '" + dni + "'");){
//            resultSet.next();
//            cliente = new Cliente(resultSet.getInt(1),resultSet.getString(2),
//                    resultSet.getString(3), resultSet.getString(4),
//                    resultSet.getDate(5).toLocalDate());
//
//        } catch (SQLException e){
//            e.printStackTrace();
//        }
//
//        return cliente;
//    }

    @Override
    public Cliente getCliente(String dni) {
        DataSource dataSource = MyDataSource.getMySQLDataSource();
        Cliente cliente = null;
        String query = "select * from Cliente where dni = ? ";
        try(Connection connection=dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query)
        ){
            preparedStatement.setString(1,dni);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            cliente = new Cliente(resultSet.getInt(1),resultSet.getString(2),
                    resultSet.getString(3), resultSet.getString(4),
                    resultSet.getDate(5).toLocalDate());
        } catch (SQLException e){
            e.printStackTrace();
        }

        return cliente;
    }

    @Override
    public int deleteCliente(String dni) {
        DataSource dataSource = MyDataSource.getMySQLDataSource();
        int result=-1;
        String query = "delete from Cliente where dni = ? ";
        try(Connection connection = dataSource.getConnection();
            PreparedStatement st = connection.prepareStatement(query)){
            st.setString(1,dni);
            result = st.executeUpdate();
        } catch (SQLException e){
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public int addCliente(Cliente cliente) {
        DataSource dataSource = MyDataSource.getMySQLDataSource();
        int result=-1;
        String query = "insert into Cliente" +
                "(nombre,apellidos,dni,fecha_nacimiento)" +
                " values (?,?,?,?)";

        try(Connection connection = dataSource.getConnection();
            PreparedStatement st = connection.prepareStatement(query)){

            st.setString(1,cliente.getNombre());
            st.setString(2,cliente.getApellidos());
            st.setString(3,cliente.getDni());
            st.setDate(4,Date.valueOf(cliente.getFecha_nacimiento()));
            result = st.executeUpdate();

        } catch (SQLException e){
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public int updateCliente(Cliente cliente) {

        String query = "update Cliente set" +
                " nombre = ?, apellidos = ?, fecha_nacimiento = ? " +
                " where dni = ? ";

        DataSource dataSource = MyDataSource.getMySQLDataSource();
        int result=-1;
        try(Connection connection = dataSource.getConnection();
            PreparedStatement st = connection.prepareStatement(query)){
            st.setString(1,cliente.getNombre());
            st.setString(2,cliente.getApellidos());
            st.setString(4,cliente.getDni());
            st.setDate(3,Date.valueOf(cliente.getFecha_nacimiento()));

            result = st.executeUpdate();

        } catch (SQLException e){
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public void eliminarFactura(int numeroFactura) {
        DataSource dataSource = MyDataSource.getMySQLDataSource();
        String query = "call eliminar_factura(?)";

        try(Connection connection= dataSource.getConnection();
            CallableStatement callableStatement = connection.prepareCall(query)){
            callableStatement.setInt(1,numeroFactura);
            callableStatement.executeUpdate();

        } catch (SQLException e){
            e.printStackTrace();
        }

    }

    @Override
    public int articulosPrecio(int precio) {
        DataSource dataSource = MyDataSource.getMySQLDataSource();
        String query = "{ ? = call articulos_precio(?) }";
        int resultado=-1;

        try(Connection connection= dataSource.getConnection();
            CallableStatement callableStatement = connection.prepareCall(query)){
            callableStatement.setInt(2,precio);

            ResultSet resultSet = callableStatement.executeQuery();
            resultSet.next();
            resultado = resultSet.getInt(1);


        } catch (SQLException e){
            e.printStackTrace();
        }

        return resultado;
    }


}
