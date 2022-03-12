package com.georgeciachir.az204cosmosdb;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.microsoft.azure.documentdb.ConnectionPolicy;
import com.microsoft.azure.documentdb.DocumentClient;
import com.microsoft.azure.documentdb.DocumentClientException;
import com.microsoft.azure.documentdb.RequestOptions;
import com.microsoft.azure.documentdb.Trigger;
import com.microsoft.azure.functions.ExecutionContext;
import com.microsoft.azure.functions.HttpRequestMessage;
import com.microsoft.azure.functions.HttpResponseMessage;
import com.microsoft.azure.functions.HttpStatus;
import com.microsoft.azure.functions.annotation.FunctionName;
import com.microsoft.azure.functions.annotation.HttpTrigger;

import java.io.IOException;
import java.util.Optional;

import static com.microsoft.azure.documentdb.ConsistencyLevel.Session;
import static com.microsoft.azure.functions.HttpMethod.POST;
import static com.microsoft.azure.functions.annotation.AuthorizationLevel.ANONYMOUS;

public class TriggerRegistrationHttpFunction {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    @FunctionName("register-trigger")
    public HttpResponseMessage registerTrigger(
            @HttpTrigger(name = "req",
                    methods = POST,
                    authLevel = ANONYMOUS) HttpRequestMessage<Optional<String>> request,
            ExecutionContext context) {
        try {
            return createTrigger(request, context);
        } catch (Exception e) {
            return request.createResponseBuilder(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(e.getMessage())
                    .build();
        }
    }

    private HttpResponseMessage createTrigger(HttpRequestMessage<Optional<String>> request, ExecutionContext context) throws IOException, DocumentClientException {
        context.getLogger().info("Received request to register trigger");
        String requestBody = request.getBody()
                .orElseThrow(() -> new RuntimeException("Request body must be provided"));

        TriggerRegistrationCommand registrationCommand = MAPPER.readValue(requestBody, TriggerRegistrationCommand.class);

        String containerLink = String.format("dbs/%s/colls/%s", registrationCommand.dbName, registrationCommand.collectionName);
        Trigger trigger = createTrigger(registrationCommand);
        DocumentClient documentClient = new DocumentClient(registrationCommand.dbServiceEndpoint, registrationCommand.dbKey, ConnectionPolicy.GetDefault(), Session);
        Trigger createdTrigger = documentClient.createTrigger(containerLink, trigger, new RequestOptions())
                .getResource();
        documentClient.close();

        return request.createResponseBuilder(HttpStatus.CREATED)
                .header("Content-Type", "application/json")
                .body(MAPPER.writeValueAsString(createdTrigger.getHashMap()))
                .build();
    }

    private Trigger createTrigger(TriggerRegistrationCommand registrationCommand) {
        Trigger trigger = new Trigger();
        trigger.setId(registrationCommand.triggerName);
        trigger.setBody(registrationCommand.triggerBody);
        trigger.setTriggerOperation(registrationCommand.operation);
        trigger.setTriggerType(registrationCommand.type);
        return trigger;
    }
}
