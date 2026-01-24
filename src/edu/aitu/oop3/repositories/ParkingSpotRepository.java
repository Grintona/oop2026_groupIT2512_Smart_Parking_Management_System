package edu.aitu.oop3.repositories;

import edu.aitu.oop3.db.DatabaseConnection;
import edu.aitu.oop3.entities.ParkingSpot;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ParkingSpotRepository {
    public List<ParkingSpot> findFreeSpots(){
        List<ParkingSpot> spots = new ArrayList<>();
        String sql = "select id, spot_number, is_free from parking_spots where is_free = true order by id ";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet rs = preparedStatement.executeQuery()) {
            while (rs.next()){
                int id = rs.getInt("id");
                String spotNumber = rs.getString("spot_number");
                boolean isFree = rs.getBoolean("is_free");
                spots.add(new ParkingSpot(id, spotNumber, isFree));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return spots ;
    }


    public ParkingSpot findFirstFreeSpot() {
        String sql = "select id, spot_number, is_free from parking_spots where is_free = true order by id limit 1";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet rs = preparedStatement.executeQuery()) {
            if (rs.next()) {
                int id = rs.getInt("id");
                String spotNumber = rs.getString("spot_number");
                boolean isFree = rs.getBoolean("is_free");
                return new ParkingSpot(id, spotNumber, isFree);
            }
            return null;

        } catch (SQLException e) {
            throw new RuntimeException("Error in findFirstFreeSpot()", e);
        }
    }

    public boolean updateFreeStatus(int id , boolean isFree){
        String sql = "update parking_spots set is_free = ? where id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setBoolean(1,isFree);
            preparedStatement.setInt(2,id);
            int rows = preparedStatement.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}