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
                    connectionStringSetting = "developwithcosmos_DOCUMENTDB",
                    createLeaseCollectionIfNotExists = true) Employee[] employees,
            ExecutionContext context) {
        Logger logger = context.getLogger();

        logger.info("Employees count: " + employees.length);
        logger.info("Employees: " + Arrays.asList(employees));
    }
}
