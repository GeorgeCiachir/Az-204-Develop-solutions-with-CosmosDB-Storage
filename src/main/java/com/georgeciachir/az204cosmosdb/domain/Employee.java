package com.georgeciachir.az204cosmosdb.domain;

public class Employee {

    public String id;
    public String firstName;
    public String lastName;
    public String employeeId;

    //Required for deserialization
    public Employee() {

    }

    public Employee(String id, String firstName, String lastName, String employeeId) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.employeeId = employeeId;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id='" + id + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", employeeId='" + employeeId + '\'' +
                '}';
    }
}