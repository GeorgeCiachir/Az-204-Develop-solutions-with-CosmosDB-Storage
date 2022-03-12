function trigger() {

    //JSON does not allow multi-line string
    var context = getContext();
    var request = context.getRequest();

    // item to be created in the current operation
    var itemToCreate = request.getBody();

    if (!("timestamp" in itemToCreate)) {
        var ts = new Date();
        itemToCreate["timestamp"] = ts.getTime();
    }

    itemToCreate["aNewField"] = "aNewField";

    request.setBody(itemToCreate);
}