//package com.georgeciachir.az204cosmosdb;
//
//import com.google.gson.Gson;
//import com.microsoft.azure.documentdb.ConnectionPolicy;
//import com.microsoft.azure.documentdb.Document;
//import com.microsoft.azure.documentdb.DocumentClient;
//import com.microsoft.azure.documentdb.DocumentClientException;
//import com.microsoft.azure.documentdb.RequestOptions;
//
//import java.util.Arrays;
//
//import static com.microsoft.azure.documentdb.ConsistencyLevel.Session;
//
//public class AddEmployeeDocument {
//
//    private static final String DB_SERVICE_ENDPOINT = "https://developwithcosmos.documents.azure.com:443/";
//    private static final String KEY = "oFRZSXqiM6HkgwdD0o44dsHt0kmn4ipUTcDqRrDNDvpPhM8R1mkpltkqKfbM6L9enxdev6JNRonDjy1zrKJibQ==";
//
//    private static final String COLLECTION_NAME = "employees";
//    private static final String DB_NAME = "demoDB";
//
//    private static final String COLLECTION_LINK = String.format("dbs/%s/colls/%s", DB_NAME, COLLECTION_NAME);
//
//    public static void main(String[] args) throws DocumentClientException {
//        Employee employee = new Employee("35", "Gigi", "Furtuna", "35");
//
//        String employeeJson = new Gson().toJson(employee);
//        Document employeeDocument = new Document(employeeJson);
//
//
//        DocumentClient documentClient = new DocumentClient(DB_SERVICE_ENDPOINT, KEY, ConnectionPolicy.GetDefault(), Session);
//        RequestOptions requestOptions = new RequestOptions();
//        requestOptions.setPreTriggerInclude(Arrays.asList("trigger1"));
//
//        employeeDocument = documentClient.createDocument(COLLECTION_LINK, employeeDocument, requestOptions, false)
//                .getResource();
//
//        System.out.println(employeeDocument);
//
//        documentClient.close();
//    }
//}
