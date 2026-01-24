package edu.aitu.oop3.repositories;

import edu.aitu.oop3.db.DatabaseConnection;
import edu.aitu.oop3.entities.Tariff;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TariffRepository {

    public Tariff findById(int id) {
        String sql = "select id, name, price_per_hour from tariffs where id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new Tariff(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getInt("price_per_hour")
                );
            }
            return null;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void save(Tariff tariff) {
        String sql = "insert into tariffs(name, pricePerHour) values (?, ?)";
        try (Connection connection = DatabaseConnection.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, tariff.getName());
            ps.setInt(2, tariff.getPricePerHour());
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
