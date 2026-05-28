package com.squarestruct.infrastructure.persistence.mysql;

import com.squarestruct.domain.enums.TipoProducto;
import com.squarestruct.domain.model.Producto;
import com.squarestruct.domain.model.Proveedor;
import com.squarestruct.domain.repository.ProductoRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/*
 * Implementación JDBC de ProductoRepository.
 * Es el primer repositorio MySQL real; el resto de agregados se completa desde MySqlRepositoryFactory.
 */
public class MySqlProductoRepository implements ProductoRepository {

    private final MySqlConnectionFactory connectionFactory;

    public MySqlProductoRepository(MySqlConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    @Override
    public Producto create(Producto producto) {
        String sql = """
                INSERT INTO productos (nombre, descripcion, precio, tipo, material, alto, ancho, largo, idProveedor)
                VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)
                """;

        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, producto.getNombre());
            statement.setString(2, producto.getDescripcion());
            statement.setDouble(3, producto.getPrecio());
            statement.setString(4, producto.getTipo().name());
            statement.setString(5, producto.getMaterial());
            statement.setDouble(6, producto.getAlto());
            statement.setDouble(7, producto.getAncho());
            statement.setDouble(8, producto.getLargo());
            statement.setLong(9, producto.getProveedor().getId());

            statement.executeUpdate();

            try (ResultSet keys = statement.getGeneratedKeys()) {
                if (keys.next()) {
                    producto.setId(keys.getLong(1));
                }
            }

            return producto;
        } catch (SQLException e) {
            throw new IllegalStateException("Error al crear producto en MySQL", e);
        }
    }

    @Override
    public Optional<Producto> findById(Long id) {
        String sql = "SELECT * FROM productos WHERE idProducto = ?";

        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.of(mapToProducto(resultSet));
                }
            }

            return Optional.empty();
        } catch (SQLException e) {
            throw new IllegalStateException("Error al buscar producto por ID", e);
        }
    }

    @Override
    public List<Producto> findAll() {
        String sql = "SELECT * FROM productos";
        List<Producto> productos = new ArrayList<>();

        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                productos.add(mapToProducto(resultSet));
            }

            return productos;
        } catch (SQLException e) {
            throw new IllegalStateException("Error al listar productos", e);
        }
    }

    @Override
    public Producto update(Producto producto) {
        String sql = """
                UPDATE productos
                SET nombre = ?, descripcion = ?, precio = ?, tipo = ?, material = ?, alto = ?, ancho = ?, largo = ?, idProveedor = ?
                WHERE idProducto = ?
                """;

        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, producto.getNombre());
            statement.setString(2, producto.getDescripcion());
            statement.setDouble(3, producto.getPrecio());
            statement.setString(4, producto.getTipo().name());
            statement.setString(5, producto.getMaterial());
            statement.setDouble(6, producto.getAlto());
            statement.setDouble(7, producto.getAncho());
            statement.setDouble(8, producto.getLargo());
            statement.setLong(9, producto.getProveedor().getId());
            statement.setLong(10, producto.getId());

            statement.executeUpdate();
            return producto;
        } catch (SQLException e) {
            throw new IllegalStateException("Error al actualizar producto", e);
        }
    }

    @Override
    public void deleteById(Long id) {
        String sql = "DELETE FROM productos WHERE idProducto = ?";

        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalStateException("Error al eliminar producto", e);
        }
    }

    @Override
    public boolean existsById(Long id) {
        return findById(id).isPresent();
    }

    @Override
    public List<Producto> findByNombreContaining(String nombre) {
        return findByCampoTexto("nombre", nombre);
    }

    @Override
    public List<Producto> findByTipo(TipoProducto tipo) {
        String sql = "SELECT * FROM productos WHERE tipo = ?";
        return findByParameter(sql, tipo.name());
    }

    @Override
    public List<Producto> findByMaterial(String material) {
        return findByCampoTexto("material", material);
    }

    @Override
    public List<Producto> findByProveedorId(Long proveedorId) {
        String sql = "SELECT * FROM productos WHERE idProveedor = ?";
        List<Producto> productos = new ArrayList<>();

        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, proveedorId);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    productos.add(mapToProducto(resultSet));
                }
            }

            return productos;
        } catch (SQLException e) {
            throw new IllegalStateException("Error al buscar productos por proveedor", e);
        }
    }

    private List<Producto> findByCampoTexto(String campo, String valor) {
        /*
         * El nombre de columna no viene de entrada de usuario: solo se invoca con campos internos
         * controlados por el repositorio.
         */
        String sql = "SELECT * FROM productos WHERE " + campo + " LIKE ?";
        return findByParameter(sql, "%" + valor + "%");
    }

    private List<Producto> findByParameter(String sql, String value) {
        List<Producto> productos = new ArrayList<>();

        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, value);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    productos.add(mapToProducto(resultSet));
                }
            }

            return productos;
        } catch (SQLException e) {
            throw new IllegalStateException("Error al buscar productos", e);
        }
    }

    private Producto mapToProducto(ResultSet resultSet) throws SQLException {
        /*
         * El esquema solo guarda la clave foránea del proveedor en productos. Se hidrata un Proveedor mínimo
         * con id para mantener la relación sin hacer un JOIN innecesario en estas consultas.
         */
        Proveedor proveedor = new Proveedor();
        proveedor.setId(resultSet.getLong("idProveedor"));

        return new Producto(
                resultSet.getLong("idProducto"),
                resultSet.getString("nombre"),
                resultSet.getString("descripcion"),
                resultSet.getDouble("precio"),
                TipoProducto.valueOf(resultSet.getString("tipo").toUpperCase()),
                resultSet.getString("material"),
                resultSet.getDouble("alto"),
                resultSet.getDouble("ancho"),
                resultSet.getDouble("largo"),
                proveedor
        );
    }
}
