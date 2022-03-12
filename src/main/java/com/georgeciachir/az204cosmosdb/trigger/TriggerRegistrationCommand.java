package com.georgeciachir.az204cosmosdb.trigger;

import com.microsoft.azure.documentdb.TriggerOperation;
import com.microsoft.azure.documentdb.TriggerType;

public class TriggerRegistrationCommand {

    public String dbServiceEndpoint;
    public String dbKey;
    public String dbName;
    public String collectionName;
    public String triggerName;
    public String triggerBody;
    public TriggerOperation operation;
    public TriggerType type;
}
