


This Project is a compilation of the code found at 
http://azure.microsoft.com/en-us/documentation/articles/storage-java-how-to-use-table-storage/#CreateApplication


Setting up maven project(the easy way):

Step 1:	Create a java project

Step 2:	After project is completed 
		Right click on project and hover over the configuration option, in the drop down click convert to Maven project

Step 3:	Once project is successfully converted
		Right click, hover over Maven option, click add Dependency 
			Fill out the following:
			Group Id: 		com.microsoft.azure
		    Artifact Id: 	azure-storage
		    Version:		1.2.0
		Click ok		


Now your project is ready...


Note: 	Make sure that your execution environment is set to JDK and no JRE
		Add your storage key
		Add your storage account key