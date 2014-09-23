

import java.net.URISyntaxException;
import java.security.InvalidKeyException;

import com.microsoft.azure.storage.*;
import com.microsoft.azure.storage.table.*;
import com.microsoft.azure.storage.table.TableQuery.*;


public class AzureTable {
	
	
	// Define the connection-string with your values.
	public static final String storageConnectionString = 
	    "DefaultEndpointsProtocol=http;" + 
	    "AccountName=your_storage_account;" + 
	    "AccountKey=your_storage_account_key";
	
	private final String PARTITION_KEY = "PartitionKey";
    private final String ROW_KEY = "RowKey";
    private final String TIMESTAMP = "Timestamp";
    	
	private CloudStorageAccount storageAccount;
	private CloudTableClient tableClient;

	public AzureTable(){		
		 // Retrieve storage account from connection-string.	    
		try {
				this.storageAccount = CloudStorageAccount.parse(storageConnectionString);
			 // Create the table client.
			   	this.tableClient = this.storageAccount.createCloudTableClient();
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		}	  
	}
	
	
	public void createTable(String tableName){
		try
		{
		   // Create the table if it doesn't exist.		   
		   CloudTable cloudTable = new CloudTable(tableName,this.tableClient);
		   cloudTable.createIfNotExists();
		}
		catch (Exception e)
		{
		    // Output the stack trace.
			System.out.println(e.getMessage());
		}
	}
	
	
	public void listTables(){
		try
		{
		    // Loop through the collection of table names.
		    for (String table : this.tableClient.listTables())
		    {
		      // Output each table name.
		      System.out.println(table);
		   }
		}
		catch (Exception e)
		{
		    // Output the stack trace.
			System.out.println(e.getMessage());
		}
	}
	
	
	public void insertEntities(TableBatchOperation batchOperation, String cloudtable){
		try
		{
			CloudTable cloudTable = tableClient.getTableReference(cloudtable);
			//Execute the batch of operations on the "people" table.
			cloudTable.execute(batchOperation);
		}
		catch (Exception e)
		{
		    // Output the stack trace.
		   System.out.println(e.getMessage());
		}
	}
	
	
	public void retrieveEntities(String tablename, String partitionKey){
		
		int tablecount = 0;
		
		try {
			CloudTable cloudTable = tableClient.getTableReference(tablename);

			// Create a filter condition where the partition key is "Smith".
			String partitionFilter = TableQuery.generateFilterCondition(PARTITION_KEY, QueryComparisons.EQUAL, partitionKey);

		   // Specify a partition query, using "Smith" as the partition key filter.
		   TableQuery<CustomerEntity> partitionQuery = TableQuery.from(CustomerEntity.class).where(partitionFilter);		   
		   
		   // Loop through the results, displaying information about the entity.
		    for (CustomerEntity entity : cloudTable.execute(partitionQuery)) {
		        System.out.println(entity.getPartitionKey() +
		            " " + entity.getRowKey() + 
		            "\t" + entity.getEmail() +
		            "\t" + entity.getPhoneNumber());
		        tablecount++;
		   }
		    System.out.println("Table has "+tablecount+ " of  "+ partitionKey + " entries");
		   
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (StorageException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	//expression  = (PartitionKey eq 'Smith') and (RowKey lt 'E')	
	public void retrieveEntityRange(String tablename, String expression){
		
		try
		{
		   // Create a cloud table object for the table.
		   CloudTable cloudTable = tableClient.getTableReference(tablename);

		    // Specify a range query, using "Smith" as the partition key,
		    // with the row key being up to the letter "E".
		    TableQuery<CustomerEntity> rangeQuery = TableQuery.from(CustomerEntity.class).where(expression);

		    // Loop through the results, displaying information about the entity
		    for (CustomerEntity entity : cloudTable.execute(rangeQuery)) {
		        System.out.println(entity.getPartitionKey() +
		            " " + entity.getRowKey() +
		            "\t" + entity.getEmail() +
		            "\t" + entity.getPhoneNumber());
		    }
		}
		catch (Exception e)
		{
		    // Output the stack trace.
		    e.printStackTrace();
		}
	}
	

}
