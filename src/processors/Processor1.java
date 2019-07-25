package processors;

import java.util.HashMap;

import blocks.BlockStatus;
import blocks.MemoryBlock;
import bus.BusOperations;

public class Processor1 {
	private static HashMap<Character,Integer> cache1 = new HashMap<Character, Integer>();
	private static String cacheStatus = ProcessorCacheStatus.INVALID; 
	
	public void setCacheStatus(String cacheStatus) {
		Processor1.cacheStatus = cacheStatus;
	}

	public String getCacheStatus() {
		return cacheStatus;
	}

	public HashMap<Character, Integer> getCache1() {
		return cache1;
	}
	
	private boolean snooper(){
		Processor2 p2 = new Processor2();
		Processor3 p3 = new Processor3();
		Processor4 p4 = new Processor4();
		
		if(p2.getCacheStatus() == "M" || p3.getCacheStatus() == "M" || p4.getCacheStatus() == "M")
			return true;
		else
			return false;
	}
	
	public void read(char variableName){
		System.out.println("Performing read operation by Processor1");
		MemoryBlock block = new MemoryBlock();
		if(cacheStatus.equals(ProcessorCacheStatus.INVALID)){
			if(this.snooper() || block.getBlockStatus() == BlockStatus.INVALID){
				BusOperations op = new BusOperations();
				cache1.clear();
				cache1.putAll(op.getModifiedData(BusOperations.BusRead, "Processor1"));
				Processor1.cacheStatus = ProcessorCacheStatus.SHARED;
				System.out.println("The value of variable "+variableName+" is:"+cache1.get(variableName));
			}
			else{				
				cache1.clear();
				cache1.putAll(block.getBlock1());
				cacheStatus = ProcessorCacheStatus.SHARED;
				System.out.println("The value of variable "+variableName+" is:"+cache1.get(variableName));
			}			
		}
		else if(cacheStatus.equals(ProcessorCacheStatus.MODIFIED)){
			System.out.println("The value of variable "+variableName+" is:"+cache1.get(variableName));
		}
		else if(cacheStatus.equals(ProcessorCacheStatus.SHARED)){
			System.out.println("The value of variable "+variableName+" is:"+cache1.get(variableName));
		}
		
		System.out.println("Block in Processor1:"+cache1);
	}
	
	public void write(char variableName, int value){
		if(cacheStatus.equals(ProcessorCacheStatus.INVALID)){
			BusOperations op = new BusOperations();
			if(this.snooper()){
				cache1.clear();
				cache1.putAll(op.getModifiedData(BusOperations.BusWrite, "Processor1"));
				Processor1.cacheStatus = ProcessorCacheStatus.MODIFIED;
				cache1.remove(variableName);
				cache1.put(variableName, value);
				System.out.println("The new value of variable "+variableName+" is:"+cache1.get(variableName));
			}
			else{
				MemoryBlock block = new MemoryBlock();
				if(block.getBlockStatus() == BlockStatus.VALID){
					cache1.putAll(block.getBlock1());
					cacheStatus = ProcessorCacheStatus.MODIFIED;
					cache1.remove(variableName);
					cache1.put(variableName, value);
					block.setBlockStatus(BlockStatus.INVALID);
					System.out.println("The new value of variable "+variableName+" is:"+cache1.get(variableName));
				}
				else{
					cache1.clear();
					cache1.putAll(op.getModifiedData(BusOperations.BusRead, "Processor1"));
					Processor1.cacheStatus = ProcessorCacheStatus.MODIFIED;
					cache1.remove(variableName);
					cache1.put(variableName, value);
					block.setBlockStatus(BlockStatus.INVALID);
					op.invalidateCache("Processor1");
					System.out.println("The new value of variable "+variableName+" is:"+cache1.get(variableName));
				}
			}
			op.invalidateCache("Processor1");		
		}
		else if(cacheStatus.equals(ProcessorCacheStatus.MODIFIED)){
			cache1.remove(variableName);
			cache1.put(variableName, value);
			System.out.println("The new value of variable "+variableName+" is:"+cache1.get(variableName));
		}
		else if(cacheStatus.equals(ProcessorCacheStatus.SHARED)){
			BusOperations op = new BusOperations();
			op.invalidateCache("Processor1");
			Processor1.cacheStatus = ProcessorCacheStatus.MODIFIED;
			cache1.remove(variableName);
			cache1.put(variableName, value);
			System.out.println("The new value of variable "+variableName+" is:"+cache1.get(variableName));
		}
		
		System.out.println("Block in Processor1:"+cache1);
	}
}