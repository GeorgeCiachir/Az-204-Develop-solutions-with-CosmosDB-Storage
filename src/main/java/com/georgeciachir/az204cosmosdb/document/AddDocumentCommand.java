package com.georgeciachir.az204cosmosdb.document;

import com.georgeciachir.az204cosmosdb.domain.Employee;

import java.util.List;

public class AddDocumentCommand {

    public Employee employee;
    public String dbServiceEndpoint;
    public String dbKey;
    public String dbName;
    public String collectionName;
    public List<String> preTriggers;
    public List<String> postTriggers;
}
