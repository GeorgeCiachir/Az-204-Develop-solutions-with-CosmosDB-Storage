package com.georgeciachir.az204cosmosdb;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.microsoft.azure.documentdb.ConnectionPolicy;
import com.microsoft.azure.documentdb.Document;
import com.microsoft.azure.documentdb.DocumentClient;
import com.microsoft.azure.documentdb.DocumentClientException;
import com.microsoft.azure.documentdb.RequestOptions;
import com.microsoft.azure.functions.ExecutionContext;
import com.microsoft.azure.functions.HttpRequestMessage;
import com.microsoft.azure.functions.HttpResponseMessage;
import com.microsoft.azure.functions.HttpStatus;
import com.microsoft.azure.functions.annotation.FunctionName;
import com.microsoft.azure.functions.annotation.HttpTrigger;
import org.apache.http.entity.ContentType;

import java.io.IOException;
import java.util.Optional;

import static com.microsoft.azure.documentdb.ConsistencyLevel.Session;
import static com.microsoft.azure.functions.HttpMethod.POST;
import static com.microsoft.azure.functions.annotation.AuthorizationLevel.ANONYMOUS;

public class AddDocumentHttpFunction {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    @FunctionName("create-document")
    public HttpResponseMessage createDocument(
            @HttpTrigger(name = "req",
                    methods = POST,
                    authLevel = ANONYMOUS) HttpRequestMessage<Optional<String>> request,
            ExecutionContext context) {
        try {
            return addDocument(request, context);
        } catch (Exception e) {
            return request.createResponseBuilder(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(e.getMessage())
                    .build();
        }
    }

    private HttpResponseMessage addDocument(HttpRequestMessage<Optional<String>> request, ExecutionContext context) throws IOException, DocumentClientException {
        context.getLogger().info("Received request to create document");
        String requestBody = request.getBody()
                .orElseThrow(() -> new RuntimeException("Request body must be provided"));

        AddDocumentCommand addCommand = MAPPER.readValue(requestBody, AddDocumentCommand.class);

        String employeeJson = MAPPER.writeValueAsString(addCommand.employee);

        Document employeeDocument = new Document(employeeJson);

        DocumentClient documentClient = new DocumentClient(addCommand.dbServiceEndpoint, addCommand.dbKey, ConnectionPolicy.GetDefault(), Session);
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.setPreTriggerInclude(addCommand.preTriggers);
        requestOptions.setPostTriggerInclude(addCommand.postTriggers);

        Document addedDoc = documentClient.createDocument(
                        String.format("dbs/%s/colls/%s", addCommand.dbName, addCommand.collectionName),
                        employeeDocument,
                        requestOptions,
                        false)
                .getResource();

        documentClient.close();

        return request.createResponseBuilder(HttpStatus.CREATED)
                .header("Content-Type", "application/json")
                .body(MAPPER.writeValueAsString(addedDoc.getHashMap()))
                .build();
    }
}
