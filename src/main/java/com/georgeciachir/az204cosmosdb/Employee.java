package com.georgeciachir.az204cosmosdb;

public class Employee {

    String id;
    String firstName;
    String lastName;
    String employeeId;

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