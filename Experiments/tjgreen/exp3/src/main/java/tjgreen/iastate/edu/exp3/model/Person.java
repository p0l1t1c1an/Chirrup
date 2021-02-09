package tjgreen.iastate.edu.exp3.model;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Person {
    private final UUID id;
    private final String firstName;
    private final String lastName;

    // public Person() {
    //     this.id = UUID.randomUUID();
    //     this.firstName = "";
    //     this.lastName = "";
    // }

    public Person(@JsonProperty("firstName") String firstName, @JsonProperty("lastName") String lastName) {
        this.id = UUID.randomUUID();
        this.firstName = firstName;
        this.lastName = lastName;
    }
    
    // public Person(@JsonProperty("id") UUID id, @JsonProperty("firstName") String firstName, @JsonProperty("lastName") String lastName) {
    //     this.id = id;
    //     this.firstName = firstName;
    //     this.lastName = lastName;
    // }

    public UUID getId() {
        return id;
    }
    
    public String getFirstName() {
        return this.firstName;
    }

    public String getLastName() {
        return this.lastName;
    }
}
