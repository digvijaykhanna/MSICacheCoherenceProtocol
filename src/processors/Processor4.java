package processors;

import java.util.HashMap;

import blocks.BlockStatus;
import blocks.MemoryBlock;
import bus.BusOperations;

public class Processor4 {
	private static HashMap<Character,Integer> cache4 = new HashMap<Character, Integer>();
	private static String cacheStatus = ProcessorCacheStatus.INVALID; 
	
	public void setCacheStatus(String cacheStatus) {
		Processor4.cacheStatus = cacheStatus;
	}

	public String getCacheStatus() {
		return cacheStatus;
	}
	
	public HashMap<Character, Integer> getCache4() {
		return cache4;
	}
	
	private boolean snooper(){
		Processor1 p1 = new Processor1();
		Processor3 p3 = new Processor3();
		Processor2 p2 = new Processor2();
		
		if(p1.getCacheStatus() == "M" || p3.getCacheStatus() == "M" || p2.getCacheStatus() == "M")
			return true;
		else
			return false;
	}
	
	public void read(char variableName){
		System.out.println("Performing read operation by Processor4");
		MemoryBlock block = new MemoryBlock();
		if(cacheStatus.equals(ProcessorCacheStatus.INVALID)){
			if(this.snooper() || block.getBlockStatus() == BlockStatus.INVALID){
				BusOperations op = new BusOperations();
				cache4.clear();
				cache4.putAll(op.getModifiedData(BusOperations.BusRead, "Processor2"));
				Processor4.cacheStatus = ProcessorCacheStatus.SHARED;
				System.out.println("The value of variable "+variableName+" is:"+cache4.get(variableName));
			}
			else{
				cache4.clear();
				cache4.putAll(block.getBlock1());
				cacheStatus = ProcessorCacheStatus.SHARED;
				System.out.println("The value of variable "+variableName+" is:"+cache4.get(variableName));
			}
		}
		else if(cacheStatus.equals(ProcessorCacheStatus.MODIFIED)){
			System.out.println("The value of variable "+variableName+" is:"+cache4.get(variableName));
		}
		else if(cacheStatus.equals(ProcessorCacheStatus.SHARED)){
			System.out.println("The value of variable "+variableName+" is:"+cache4.get(variableName));
		}
		System.out.println("Block in Processor4:"+cache4);
	}
	
	public void write(char variableName, int value){
		if(cacheStatus.equals(ProcessorCacheStatus.INVALID)){
			BusOperations op = new BusOperations();
			if(this.snooper()){
				cache4.clear();
				cache4.putAll(op.getModifiedData(BusOperations.BusWrite, "Processor2"));
				Processor4.cacheStatus = ProcessorCacheStatus.MODIFIED;
				cache4.remove(variableName);
				cache4.put(variableName, value);
				System.out.println("The new value of variable "+variableName+" is:"+cache4.get(variableName));
			}
			else{
				MemoryBlock block = new MemoryBlock();
				if(block.getBlockStatus() == BlockStatus.VALID){
					cache4.putAll(block.getBlock1());
					cacheStatus = ProcessorCacheStatus.MODIFIED;
					cache4.remove(variableName);
					cache4.put(variableName, value);
					block.setBlockStatus(BlockStatus.INVALID);
					System.out.println("The new value of variable "+variableName+" is:"+cache4.get(variableName));
				}
				else{
					cache4.clear();
					cache4.putAll(op.getModifiedData(BusOperations.BusRead, "Processor4"));
					Processor4.cacheStatus = ProcessorCacheStatus.MODIFIED;
					cache4.remove(variableName);
					cache4.put(variableName, value);
					block.setBlockStatus(BlockStatus.INVALID);
					op.invalidateCache("Processor4");
					System.out.println("The new value of variable "+variableName+" is:"+cache4.get(variableName));
				}
			}
			op.invalidateCache("Processor4");
		}
		else if(cacheStatus.equals(ProcessorCacheStatus.MODIFIED)){
			cache4.remove(variableName);
			cache4.put(variableName, value);
			System.out.println("The new value of variable "+variableName+" is:"+cache4.get(variableName));
		}
		else if(cacheStatus.equals(ProcessorCacheStatus.SHARED)){
			BusOperations op = new BusOperations();
			op.invalidateCache("Processor4");
			Processor4.cacheStatus = ProcessorCacheStatus.MODIFIED;
			cache4.remove(variableName);
			cache4.put(variableName, value);
			System.out.println("The new value of variable "+variableName+" is:"+cache4.get(variableName));
		}	
		System.out.println("Block in Processor4:"+cache4);
	}

}
