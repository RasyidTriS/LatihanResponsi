package controllers;

import connector.DatabaseConnection;
import models.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RecruitmentController {
    public void saveCandidate(Candidate candidate, String path) {
        double score = candidate.calculateScore();
        String status = candidate.getStatus();

        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "INSERT INTO candidates (name, path, writing, coding, interview, score, status) VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, candidate.getName());
            stmt.setString(2, path);
            stmt.setInt(3, candidate.getWriting());
            stmt.setInt(4, candidate.getCoding());
            stmt.setInt(5, candidate.getInterview());
            stmt.setDouble(6, score);
            stmt.setString(7, status);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteCandidate(String name) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "DELETE FROM candidates WHERE name = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, name);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateCandidate(Candidate candidate, String path) {
        double score = candidate.calculateScore();
        String status = candidate.getStatus();

        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "UPDATE candidates SET path=?, writing=?, coding=?, interview=?, score=?, status=? WHERE name=?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, path);
            stmt.setInt(2, candidate.getWriting());
            stmt.setInt(3, candidate.getCoding());
            stmt.setInt(4, candidate.getInterview());
            stmt.setDouble(5, score);
            stmt.setString(6, status);
            stmt.setString(7, candidate.getName());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<String[]> getAllCandidates() {
        List<String[]> data = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "SELECT * FROM candidates";
            ResultSet rs = conn.createStatement().executeQuery(query);
            while (rs.next()) {
                data.add(new String[]{
                    rs.getString("name"),
                    rs.getString("path"),
                    String.valueOf(rs.getInt("writing")),
                    String.valueOf(rs.getInt("coding")),
                    String.valueOf(rs.getInt("interview")),
                    String.format("%.2f", rs.getDouble("score")),
                    rs.getString("status")
                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return data;
    }
}