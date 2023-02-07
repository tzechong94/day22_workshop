package com.example.day22_workshop.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.day22_workshop.model.Rsvp;
import com.example.day22_workshop.repo.RsvpRepo;

@RequestMapping("/api")
@RestController
public class RsvpController {
    
    @Autowired
    RsvpRepo rsvpRepo;

    @GetMapping(path="/rsvps", produces = "application/json")
    public ResponseEntity<List<Rsvp>> retrieveAllRsvps() {
        List<Rsvp> rsvps = new ArrayList<Rsvp>();
        rsvps = rsvpRepo.findAll();

        if (rsvps.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(rsvps, HttpStatus.OK);
    }

    @GetMapping(path="/rsvp")
    public ResponseEntity<Rsvp> retrieveByName(@RequestParam String name) {
        Rsvp result = new Rsvp();
        result = rsvpRepo.findByName(name);
        if (result == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping(path="/rsvp")
    public ResponseEntity<Boolean> create(@RequestBody Rsvp rsvp) {
        Rsvp r = rsvp;
        Boolean result = rsvpRepo.save(r);

        if (result) {
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PutMapping(path="/rsvp/{email}")
    public ResponseEntity<String> updateRsvp(@PathVariable String email, @RequestBody Rsvp rsvp) {
        Rsvp r = rsvpRepo.findByEmail(email);
        Boolean result = false;

        if (r != null) {
            result = rsvpRepo.update(rsvp);
        } 
        if (result) {
            return new ResponseEntity<>("RSVP record updated successfully.", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("RSVP record failed to update.", HttpStatus.NOT_ACCEPTABLE);        }
    }

    @GetMapping("/rsvps/count")
    public ResponseEntity<Integer> getRsvpCount() {
        Integer result = rsvpRepo.count();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }


}
