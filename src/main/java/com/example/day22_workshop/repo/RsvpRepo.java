package com.example.day22_workshop.repo;

import java.util.List;

import com.example.day22_workshop.model.Rsvp;

public interface RsvpRepo {
    
    // Get all
    List<Rsvp> findAll();

    // Search for 1
    Rsvp findByName(String name);


    // Create a new rsvp
    Boolean save(Rsvp rsvp);

    // Update an existing rsvp
    Boolean update(Rsvp rsvp);

    // Get number of RSVPs
    int count();

    Rsvp findByEmail(String email);



}
