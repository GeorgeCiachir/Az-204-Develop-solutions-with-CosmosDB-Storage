package com.georgeciachir.az204cosmosdb;

import com.microsoft.azure.functions.ExecutionContext;
import com.microsoft.azure.functions.annotation.CosmosDBTrigger;
import com.microsoft.azure.functions.annotation.FunctionName;

import java.util.Arrays;
import java.util.logging.Logger;

public class ChangeFeedExample {

    @FunctionName("changefeed-example")
    public void cosmosDbTrigger(
            @CosmosDBTrigger(
                    name = "items",
                    databaseName = "demoDB",
                    collectionName = "employees",
                    leaseCollectionName = "leases",
                    connectionStringSetting = "AzureWebJobsStorage",
                    createLeaseCollectionIfNotExists = true) Employee[] employees,
            ExecutionContext context) {
        Logger logger = context.getLogger();

        logger.info("Trigger function executed.");
        logger.info("Documents count: " + employees.length);

        System.out.println("Employee ids: ");
        Arrays.stream(employees)
                .forEach(System.out::println);
    }

    public static class Employee {

        String id;
        String firstName;
        String lastName;
        String employeeId;

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
}
