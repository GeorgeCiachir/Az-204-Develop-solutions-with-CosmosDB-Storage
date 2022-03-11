package com.georgeciachir.az204cosmosdb;

import com.microsoft.azure.functions.ExecutionContext;
import com.microsoft.azure.functions.HttpRequestMessage;
import com.microsoft.azure.functions.HttpResponseMessage;
import com.microsoft.azure.functions.HttpStatus;
import com.microsoft.azure.functions.OutputBinding;
import com.microsoft.azure.functions.annotation.CosmosDBInput;
import com.microsoft.azure.functions.annotation.FunctionName;
import com.microsoft.azure.functions.annotation.HttpTrigger;
import com.microsoft.azure.functions.annotation.QueueOutput;

import java.util.Optional;

import static com.microsoft.azure.functions.HttpMethod.GET;
import static com.microsoft.azure.functions.annotation.AuthorizationLevel.ANONYMOUS;

public class Function {

    @FunctionName("binding-examples")
    public HttpResponseMessage httpTriggerWithBindings(
            @HttpTrigger(
                    name = "req",
                    methods = GET,
                    authLevel = ANONYMOUS) HttpRequestMessage<Optional<String>> request,
            @CosmosDBInput(
                    name = "cosmosDBInputBinding",
                    databaseName = "demoDB",
                    collectionName = "to-do-items",
                    connectionStringSetting = "CosmosDBConnection",
                    id = "{Query.id}",
                    partitionKey = "{Query.partition}") Optional<String> item,
            @QueueOutput(
                    name = "queueOutputBinding",
                    queueName = "queue-items",
                    connection = "AzureWebJobsStorage") OutputBinding<String> message,
            final ExecutionContext context) {

        String name = request.getQueryParameters().get("name");
        context.getLogger().info("Greeting [" + name + "]");

        // Item list
        context.getLogger().info("Parameters are: " + request.getQueryParameters());
        context.getLogger().info("String from the database is " + item.orElse(null));

        sendMessage(context, request, item, message);

        return item.map(i -> createItemResponse(request, i))
                .orElseGet(() -> notFound(request));
    }

    private void sendMessage(ExecutionContext context, HttpRequestMessage<Optional<String>> request, Optional<String> item, OutputBinding<String> message) {
        String itemId = request.getQueryParameters().get("id");
        context.getLogger().info("Sending queue message for item id " + itemId);

        String queueMessage = item.map(i -> String.format("Requested item with id [%s] found", itemId))
                .orElseGet(() -> String.format("Requested item with id [%s] not found", itemId));
        message.setValue(queueMessage);
    }

    private HttpResponseMessage createItemResponse(HttpRequestMessage<Optional<String>> request, String item) {
        return request.createResponseBuilder(HttpStatus.OK)
                .header("Content-Type", "application/json")
                .body(item)
                .build();
    }

    private HttpResponseMessage notFound(HttpRequestMessage<Optional<String>> request) {
        String name = request.getQueryParameters().get("name");
        return request.createResponseBuilder(HttpStatus.BAD_REQUEST)
                .body("Hello, " + name + "! Document not found.")
                .build();
    }
}
