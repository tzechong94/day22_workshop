package com.example.day22_workshop.repo;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.PreparedStatementSetter;

import com.example.day22_workshop.model.Rsvp;

public class RsvpRepoImpl implements RsvpRepo {
    
    @Autowired
    JdbcTemplate jdbcTemplate;

    private final String countSQL = "select count(*) from rsvp_table";
    private final String insertSQL = "insert into rsvp_table (name, email, phone, confirmation_date, comments) values (?, ?, ?, ?, ?)";
    private final String selectAllSQL = "select * from rsvp_table";
    private final String selectByNameSQL = "select * from rsvp_table where name like ?";
    private final String updateSQL = "update rsvp_table set name = ?, email = ?, phone = ?, confirmation_date = ?, comments = ? where id = ?";
    private final String selectByEmailSQL = "select * from rsvp_table where email = ?";

    @Override
    public int count() {
        Integer result = 0;
        result = jdbcTemplate.queryForObject(countSQL, Integer.class);
        if (result == null) {
            return 0;
        } else {
            return result;
        }
    }


    // Create a new rsvp
    @Override
    public Boolean save(Rsvp rsvp) {
        Boolean saved = false;
        saved = jdbcTemplate.execute(insertSQL, new PreparedStatementCallback<Boolean>() {

            @Override
            public Boolean doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
                ps.setString(1, rsvp.getName());
                ps.setString(2, rsvp.getEmail());
                ps.setString(3, rsvp.getPhone());
                ps.setDate(4, rsvp.getConfirmationDate());
                ps.setString(5, rsvp.getComments());
                Boolean result = ps.execute();
                return result;
            }
        });
        return saved;
    }

    @Override
    public List<Rsvp> findAll() {
        return jdbcTemplate.query(selectAllSQL, BeanPropertyRowMapper.newInstance(Rsvp.class));
    }

    @Override
    public Rsvp findByName(String name) {
        Rsvp result = new Rsvp();
        result = jdbcTemplate.queryForObject(selectByNameSQL, BeanPropertyRowMapper.newInstance(Rsvp.class));
        return result;
    }

    @Override
    public Rsvp findByEmail(String email) {
        Rsvp result = new Rsvp();
        result = jdbcTemplate.queryForObject(selectByEmailSQL, BeanPropertyRowMapper.newInstance(Rsvp.class));
        return result;
    }


    // Execute method allows you to execute
    //  any arbitrary data access operation within one single statement.

    // Query method lets you send a query using a prepared statement.
    
    
    // Update an existing rsvp
    @Override
    public Boolean update(Rsvp rsvp) {
        int updated = 0;
        updated = jdbcTemplate.update(updateSQL, new PreparedStatementSetter() {

            @Override
            public void setValues(PreparedStatement ps) throws SQLException {
                ps.setString(1, rsvp.getName());
                ps.setString(2, rsvp.getEmail());
                ps.setString(3, rsvp.getPhone());
                ps.setDate(4, rsvp.getConfirmationDate());
                ps.setString(5, rsvp.getComments());
                ps.setInt(6, rsvp.getId());
            }
        });
        return updated > 0 ? true: false;
    }

    // Get number of RSVPs

    
}
