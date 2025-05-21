package model;

import connector.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CandidateDAOImpl implements CandidateDAO {
    
    @Override
    public void insert(Candidate candidate) throws Exception {
        String sql = "INSERT INTO candidates (name, path, writing_score, coding_score, interview_score) VALUES (?, ?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            stmt.setString(1, candidate.getName());
            
            // Determine path (Android or Web)
            String path;
            if (candidate instanceof AndroidDeveloper) {
                path = "Android Developer";
            } else if (candidate instanceof WebDeveloper) {
                path = "Web Developer";
            } else {
                throw new IllegalArgumentException("Unknown candidate type");
            }
            
            stmt.setString(2, path);
            stmt.setDouble(3, candidate.getWritingScore());
            stmt.setDouble(4, candidate.getCodingScore());
            stmt.setDouble(5, candidate.getInterviewScore());
            
            int affectedRows = stmt.executeUpdate();
            
            if (affectedRows == 0) {
                throw new SQLException("Creating candidate failed, no rows affected.");
            }
            
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    candidate.setId(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("Creating candidate failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            throw new Exception("Error inserting candidate: " + e.getMessage(), e);
        }
    }
    
    @Override
    public void update(Candidate candidate) throws Exception {
        String sql = "UPDATE candidates SET name=?, path=?, writing_score=?, coding_score=?, interview_score=? WHERE id=?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, candidate.getName());
            
            // Determine path (Android or Web)
            String path;
            if (candidate instanceof AndroidDeveloper) {
                path = "Android Developer";
            } else if (candidate instanceof WebDeveloper) {
                path = "Web Developer";
            } else {
                throw new IllegalArgumentException("Unknown candidate type");
            }
            
            stmt.setString(2, path);
            stmt.setDouble(3, candidate.getWritingScore());
            stmt.setDouble(4, candidate.getCodingScore());
            stmt.setDouble(5, candidate.getInterviewScore());
            stmt.setInt(6, candidate.getId());
            
            int affectedRows = stmt.executeUpdate();
            
            if (affectedRows == 0) {
                throw new SQLException("Updating candidate failed, no rows affected.");
            }
        } catch (SQLException e) {
            throw new Exception("Error updating candidate: " + e.getMessage(), e);
        }
    }
    
    @Override
    public void delete(int id) throws Exception {
        String sql = "DELETE FROM candidates WHERE id=?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            
            int affectedRows = stmt.executeUpdate();
            
            if (affectedRows == 0) {
                throw new SQLException("Deleting candidate failed, no rows affected.");
            }
        } catch (SQLException e) {
            throw new Exception("Error deleting candidate: " + e.getMessage(), e);
        }
    }
    
    @Override
    public List<Candidate> getAll() throws Exception {
        String sql = "SELECT * FROM candidates";
        List<Candidate> candidates = new ArrayList<>();
        
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String path = rs.getString("path");
                double writingScore = rs.getDouble("writing_score");
                double codingScore = rs.getDouble("coding_score");
                double interviewScore = rs.getDouble("interview_score");
                
                Candidate candidate;
                if (path.equals("Android Developer")) {
                    candidate = new AndroidDeveloper(id, name, writingScore, codingScore, interviewScore);
                } else {
                    candidate = new WebDeveloper(id, name, writingScore, codingScore, interviewScore);
                }
                
                candidates.add(candidate);
            }
            return candidates;
        } catch (SQLException e) {
            throw new Exception("Error getting all candidates: " + e.getMessage(), e);
        }
    }
    
    @Override
    public Candidate getById(int id) throws Exception {
        String sql = "SELECT * FROM candidates WHERE id=?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String name = rs.getString("name");
                    String path = rs.getString("path");
                    double writingScore = rs.getDouble("writing_score");
                    double codingScore = rs.getDouble("coding_score");
                    double interviewScore = rs.getDouble("interview_score");
                    
                    if (path.equals("Android Developer")) {
                        return new AndroidDeveloper(id, name, writingScore, codingScore, interviewScore);
                    } else {
                        return new WebDeveloper(id, name, writingScore, codingScore, interviewScore);
                    }
                } else {
                    return null;
                }
            }
        } catch (SQLException e) {
            throw new Exception("Error getting candidate by ID: " + e.getMessage(), e);
        }
    }
}