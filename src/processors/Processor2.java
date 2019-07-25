package processors;

import java.util.HashMap;

import blocks.BlockStatus;
import blocks.MemoryBlock;
import bus.BusOperations;

public class Processor2 {
	private static HashMap<Character,Integer> cache2 = new HashMap<Character, Integer>();
	private static String cacheStatus = ProcessorCacheStatus.INVALID; 
	
	public void setCacheStatus(String cacheStatus) {
		Processor2.cacheStatus = cacheStatus;
	}

	public String getCacheStatus() {
		return cacheStatus;
	}

	public HashMap<Character, Integer> getCache2() {
		return cache2;
	}
	
	private boolean snooper(){
		Processor1 p1 = new Processor1();
		Processor3 p3 = new Processor3();
		Processor4 p4 = new Processor4();
		
		if(p1.getCacheStatus() == "M" || p3.getCacheStatus() == "M" || p4.getCacheStatus() == "M")
			return true;
		else
			return false;
	}
	
	public void read(char variableName){
		System.out.println("Performing read operation by Processor2");
		MemoryBlock block = new MemoryBlock();
		if(cacheStatus.equals(ProcessorCacheStatus.INVALID)){
			if(this.snooper() || block.getBlockStatus() == BlockStatus.INVALID){
				BusOperations op = new BusOperations();
				cache2.clear();
				cache2.putAll(op.getModifiedData(BusOperations.BusRead, "Processor2"));
				Processor2.cacheStatus = ProcessorCacheStatus.SHARED;
				System.out.println("The value of variable "+variableName+" is:"+cache2.get(variableName));
			}
			else{
				cache2.clear();
				cache2.putAll(block.getBlock1());
				cacheStatus = ProcessorCacheStatus.SHARED;
				System.out.println("The value of variable "+variableName+" is:"+cache2.get(variableName));
			}			
		}
		else if(cacheStatus.equals(ProcessorCacheStatus.MODIFIED)){
			System.out.println("The value of variable "+variableName+" is:"+cache2.get(variableName));
		}
		else if(cacheStatus.equals(ProcessorCacheStatus.SHARED)){
			System.out.println("The value of variable "+variableName+" is:"+cache2.get(variableName));
		}
		
		System.out.println("Block in Processor2:"+cache2);
	}
	
	public void write(char variableName, int value){
		if(cacheStatus.equals(ProcessorCacheStatus.INVALID)){
			BusOperations op = new BusOperations();
			if(this.snooper()){
				cache2.clear();
				cache2.putAll(op.getModifiedData(BusOperations.BusWrite, "Processor2"));
				Processor2.cacheStatus = ProcessorCacheStatus.MODIFIED;
				cache2.remove(variableName);
				cache2.put(variableName, value);
				System.out.println("The new value of variable "+variableName+" is:"+cache2.get(variableName));
			}
			else{
				MemoryBlock block = new MemoryBlock();
				if(block.getBlockStatus() == BlockStatus.VALID){
					cache2.putAll(block.getBlock1());
					cacheStatus = ProcessorCacheStatus.MODIFIED;
					cache2.remove(variableName);
					cache2.put(variableName, value);
					block.setBlockStatus(BlockStatus.INVALID);
					System.out.println("The new value of variable "+variableName+" is:"+cache2.get(variableName));
				}
				else{
					cache2.clear();
					cache2.putAll(op.getModifiedData(BusOperations.BusRead, "Processor2"));
					Processor2.cacheStatus = ProcessorCacheStatus.MODIFIED;
					cache2.remove(variableName);
					cache2.put(variableName, value);
					block.setBlockStatus(BlockStatus.INVALID);
					op.invalidateCache("Processor2");
					System.out.println("The new value of variable "+variableName+" is:"+cache2.get(variableName));
				}
				
			}
			op.invalidateCache("Processor2");
		}
		else if(cacheStatus.equals(ProcessorCacheStatus.MODIFIED)){
			cache2.remove(variableName);
			cache2.put(variableName, value);
			System.out.println("The new value of variable "+variableName+" is:"+cache2.get(variableName));
		}
		else if(cacheStatus.equals(ProcessorCacheStatus.SHARED)){
			BusOperations op = new BusOperations();
			op.invalidateCache("Processor2");
			Processor2.cacheStatus = ProcessorCacheStatus.MODIFIED;
			cache2.remove(variableName);
			cache2.put(variableName, value);
			System.out.println("The new value of variable "+variableName+" is:"+cache2.get(variableName));
		}
		
		System.out.println("Block in Processor2:"+cache2);
	}
}
