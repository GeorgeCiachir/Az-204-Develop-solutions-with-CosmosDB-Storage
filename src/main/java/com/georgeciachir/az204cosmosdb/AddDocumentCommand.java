package com.georgeciachir.az204cosmosdb;

import java.util.List;

public class AddDocumentCommand {

    private Employee employee;
    private String dbServiceEndpoint;
    private String dbKey;
    private String dbName;
    private String collectionName;
    private List<String> preTriggers;
    private List<String> postTriggers;

    public Employee getEmployee() {
        return employee;
    }

    public String getDbServiceEndpoint() {
        return dbServiceEndpoint;
    }

    public String getDbKey() {
        return dbKey;
    }

    public String getDbName() {
        return dbName;
    }

    public String getCollectionName() {
        return collectionName;
    }

    public List<String> getPreTriggers() {
        return preTriggers;
    }

    public List<String> getPostTriggers() {
        return postTriggers;
    }
}
