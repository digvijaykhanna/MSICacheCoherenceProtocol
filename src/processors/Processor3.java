package processors;

import java.util.HashMap;

import blocks.BlockStatus;
import blocks.MemoryBlock;
import bus.BusOperations;

public class Processor3 {
	private static HashMap<Character,Integer> cache3 = new HashMap<Character, Integer>();
	private static String cacheStatus = ProcessorCacheStatus.INVALID; 
	
	public void setCacheStatus(String cacheStatus) {
		Processor3.cacheStatus = cacheStatus;
	}

	public String getCacheStatus() {
		return cacheStatus;
	}

	public HashMap<Character, Integer> getCache3() {
		return cache3;
	}
	
	private boolean snooper(){
		Processor1 p1 = new Processor1();
		Processor2 p2 = new Processor2();
		Processor4 p4 = new Processor4();
		
		if(p1.getCacheStatus() == "M" || p2.getCacheStatus() == "M" || p4.getCacheStatus() == "M")
			return true;
		else
			return false;
	}
	
	public void read(char variableName){
		System.out.println("Performing read operation by Processor3");
		MemoryBlock block = new MemoryBlock();
		if(cacheStatus.equals(ProcessorCacheStatus.INVALID)){
			if(this.snooper() || block.getBlockStatus() == BlockStatus.INVALID){
				BusOperations op = new BusOperations();
				cache3.clear();
				cache3.putAll(op.getModifiedData(BusOperations.BusRead, "Processor3"));
				Processor3.cacheStatus = ProcessorCacheStatus.SHARED;
				System.out.println("The value of variable "+variableName+" is:"+cache3.get(variableName));
			}
			else{
				cache3.clear();
				cache3.putAll(block.getBlock1());
				cacheStatus = ProcessorCacheStatus.SHARED;
				System.out.println("The value of variable "+variableName+" is:"+cache3.get(variableName));
			}
		}
		else if(cacheStatus.equals(ProcessorCacheStatus.MODIFIED)){
			System.out.println("The value of variable "+variableName+" is:"+cache3.get(variableName));
		}
		else if(cacheStatus.equals(ProcessorCacheStatus.SHARED)){
			System.out.println("The value of variable "+variableName+" is:"+cache3.get(variableName));
		}
		
		System.out.println("Block in Processor3:"+cache3);
	}
	
	public void write(char variableName, int value){
		if(cacheStatus.equals(ProcessorCacheStatus.INVALID)){
			BusOperations op = new BusOperations();
			if(this.snooper()){				
				cache3.clear();
				cache3.putAll(op.getModifiedData(BusOperations.BusWrite, "Processor3"));
				Processor3.cacheStatus = ProcessorCacheStatus.MODIFIED;
				cache3.remove(variableName);
				cache3.put(variableName, value);
				System.out.println("The new value of variable "+variableName+" is:"+cache3.get(variableName));
			}
			else{
				MemoryBlock block = new MemoryBlock();
				if(block.getBlockStatus() == BlockStatus.VALID){
					cache3.putAll(block.getBlock1());
					cacheStatus = ProcessorCacheStatus.MODIFIED;
					cache3.remove(variableName);
					cache3.put(variableName, value);
					block.setBlockStatus(BlockStatus.INVALID);
					System.out.println("The new value of variable "+variableName+" is:"+cache3.get(variableName));
				}
				else{
					cache3.clear();
					cache3.putAll(op.getModifiedData(BusOperations.BusRead, "Processor3"));
					Processor3.cacheStatus = ProcessorCacheStatus.MODIFIED;
					cache3.remove(variableName);
					cache3.put(variableName, value);
					block.setBlockStatus(BlockStatus.INVALID);
					op.invalidateCache("Processor3");
					System.out.println("The new value of variable "+variableName+" is:"+cache3.get(variableName));
				}
			}
			op.invalidateCache("Processor3");
		}
		else if(cacheStatus.equals(ProcessorCacheStatus.MODIFIED)){
			cache3.remove(variableName);
			cache3.put(variableName, value);
			System.out.println("The new value of variable "+variableName+" is:"+cache3.get(variableName));
		}		
		else if(cacheStatus.equals(ProcessorCacheStatus.SHARED)){
			BusOperations op = new BusOperations();
			op.invalidateCache("Processor3");
			Processor3.cacheStatus = ProcessorCacheStatus.MODIFIED;
			cache3.remove(variableName);
			cache3.put(variableName, value);
			System.out.println("The new value of variable "+variableName+" is:"+cache3.get(variableName));
		}
		
		System.out.println("Block in Processor3:"+cache3);
	}
}
