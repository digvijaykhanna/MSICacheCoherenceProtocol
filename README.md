Created By: Digvijay Khanna
KSU ID: 810906879
Date: 7 November, 2017

Project Description: Implementing MSI Protocol for Cache Coherence using Write-back

Inside implementation package, there is 1 Main.java which is the starting point of the project. It performs different operations using different processors and prints the output.

I have created 4 Processor classes inside processors package: Processor1.java, Processor2.java, Processor3.java and Processor4.java that contains local cache, maintains cache status, implements read and write functions for cache and performs snooping by calling BusOperations.java.

There is one ProcessorCacheStatus.java which defines cache statutes: Modified (M), Shared (S) and Invalid(I)

Inside blocks package, there are 2 classes: 
	1) BlockStatus.java which defines memory block status as Invalid (I) or Valid (V) 
	2) MemoryBlock.java which defines memory block hashmap and block status and implements getters and setters for that. It also adds and removes value from memory block.

Inside bus package, there is 1 BusOperations.java which BusRead and BusWrite operations if there is any Modified block in other processors cache and it also performs write-back function for MemoryBlock.java.