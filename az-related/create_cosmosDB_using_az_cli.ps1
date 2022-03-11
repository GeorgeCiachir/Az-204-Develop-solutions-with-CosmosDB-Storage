# Define resource group name
$resourceGroup="developWithCosmosDB"
$location = "westeurope"

# Login to Azure
az login
az account set --subscription "TestAccount"

# List the current resource groups table format
az group list --output table

# Create a resource group if needed
az group create `
    --name $resourceGroup `
    --location $location

# Create a Cosmos DB account
$cosmosDBAccount = "developwithcosmos"
az cosmosdb create `
    --name $cosmosDBAccount `
    --resource-group $resourceGroup

# Create a SQL database
$databaseName="demoDB"
az cosmosdb sql database create `
    --resource-group $resourceGroup `
    --account-name $cosmosDBAccount `
    --name $databaseName

# Create a SQL database container
$containerName="to-do-items"
$partitionKey="/category"
az cosmosdb sql container create `
    --resource-group $resourceGroup `
    --account-name $cosmosDBAccount `
    --database-name $databaseName `
    --name $containerName `
    --partition-key-path $partitionKey
