package edu.aitu.oop3.repositories;

import edu.aitu.oop3.db.DatabaseConnection;
import edu.aitu.oop3.entities.Reservation;

import java.sql.*;

public class ReservationRepository {
    public int createReservation(int vehicleId, int spotId, int tariffId, Timestamp startTime) {
        String sql = "insert into reservations (vehicle_id, spot_id, tariff_id, start_time, end_time) values (?, ?, ?, ?, null) returning id";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);) {
            preparedStatement.setInt(1, vehicleId);
            preparedStatement.setInt(2, spotId);
            preparedStatement.setInt(3, tariffId);
            preparedStatement.setTimestamp(4, startTime);
            try (ResultSet rs = preparedStatement.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("id");
                }
            }
            throw new RuntimeException("Reservation was not created");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Reservation findActiveByVehicleId(int vehicleId) {
        String sql = "select id, vehicle_id, spot_id, tariff_id, start_time from reservations where vehicle_id = ? and end_time is null";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);) {
            preparedStatement.setInt(1, vehicleId);
            try (ResultSet rs = preparedStatement.executeQuery()) {
                if (rs.next()) {
                    int id = rs.getInt("id");
                    int spotId = rs.getInt("spot_id");
                    int tariffId = rs.getInt("tariff_id");
                    Timestamp startTime = rs.getTimestamp("start_time");
                    return new Reservation(id, vehicleId, spotId, tariffId, startTime);
                }
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Reservation findById(int id) {
        String sql = "select id, vehicle_id, spot_id, tariff_id, start_time, end_time from reservations where id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);) {
            preparedStatement.setInt(1, id);
            try (ResultSet rs = preparedStatement.executeQuery()) {
                if (rs.next()) {
                    int vehicleId = rs.getInt("vehicle_id");
                    int spotId = rs.getInt("spot_id");
                    int tariffId = rs.getInt("tariff_id");
                    Timestamp startTime = rs.getTimestamp("start_time");
                    Timestamp endTime = rs.getTimestamp("end_time"); // может быть null

                    if (endTime == null) {
                        return new Reservation(id, vehicleId, spotId, tariffId, startTime);
                    } else {
                        return new Reservation(id, vehicleId, spotId, tariffId, startTime, endTime);
                    }
                }
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean finishReservation(int reservationId, Timestamp endTime){
        String sql = "update reservations set end_time = ? where id = ? and end_time is null";
        try(Connection connection = DatabaseConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);) {
            preparedStatement.setTimestamp(1, endTime);
            preparedStatement.setInt(2, reservationId);
            int rows = preparedStatement.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }


}