package edu.aitu.oop3.repositories;

import edu.aitu.oop3.db.DatabaseConnection;
import edu.aitu.oop3.entities.Vehicle;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class VehicleRepository {
    public Vehicle findByPlate(String plateNumber){
        String sql = "select id, plate_number from vehicles where plate_number = ? ";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);) {
            preparedStatement.setString(1, plateNumber);

            try (ResultSet rs = preparedStatement.executeQuery()) {
                if (rs.next()) {
                    int id = rs.getInt("id");
                    String plate = rs.getString("plate_number");
                    return new Vehicle(id, plate);
                }
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public Vehicle createNewVehicle(String plateNumber){
        String sql = "insert into vehicles (plate_number) values (?) returning id";
        try(Connection connection = DatabaseConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);) {
            preparedStatement.setString(1, plateNumber);
            try(ResultSet rs = preparedStatement.executeQuery()){
                if(rs.next()){
                    int id = rs.getInt("id");
                    return new Vehicle(id, plateNumber);
                }
                throw new RuntimeException("Vehicle was not created");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    public Vehicle findOrCreate(String plateNumber){
        Vehicle vehicle = findByPlate(plateNumber);
        if(vehicle != null){
            return vehicle;
        }
        return createNewVehicle(plateNumber) ;

    }
}
