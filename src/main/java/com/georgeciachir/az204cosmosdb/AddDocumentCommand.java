package com.georgeciachir.az204cosmosdb;

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
