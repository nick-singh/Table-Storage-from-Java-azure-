import java.util.Random;

import com.microsoft.azure.storage.CloudStorageAccount;
import com.microsoft.azure.storage.table.CloudTable;
import com.microsoft.azure.storage.table.CloudTableClient;
import com.microsoft.azure.storage.table.TableBatchOperation;
import com.microsoft.azure.storage.table.TableQuery;
import com.microsoft.azure.storage.table.TableQuery.QueryComparisons;


public class Main {
	
	static final String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	static Random rnd = new Random();
	static int ID = 100;

	public static String randomString( int len ) 
	{
	   StringBuilder sb = new StringBuilder( len );
	   for( int i = 0; i < len; i++ ) 
	      sb.append( AB.charAt( rnd.nextInt(AB.length()) ) );
	   return sb.toString();
	}

	public static int idGen(){
		return ID++;
	}
    public static void main(String[] args) {
    	
    	AzureTable azure = new AzureTable();
    	CustomerEntity customer;
        // Define a batch operation.
        TableBatchOperation batchOperation = new TableBatchOperation();
    	azure.createTable("people");
    	
    	azure.listTables();
    	
    	for(int i = 0; i<100; i++){
			customer = new CustomerEntity("Kerry", Main.randomString(5));
			customer.setEmail(Main.randomString(5)+".com");
			customer.setPhoneNumber("425-555-0104");
			batchOperation.insertOrReplace(customer);
			
    	}   
    	azure.insertEntities(batchOperation, "people");
    	
    	
    	azure.retrieveEntityRange("people", "(PartitionKey eq 'Smith') and (RowKey lt 'E')");
    }
}
