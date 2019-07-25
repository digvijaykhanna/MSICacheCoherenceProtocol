package bus;

import java.util.HashMap;

import blocks.BlockStatus;
import blocks.MemoryBlock;
import processors.*;

public class BusOperations {
	private static HashMap<Character, Integer> block = new HashMap<Character, Integer>();
	public static String BusRead = "BusRd";
	public static String BusWrite = "BusWr";
	
	public HashMap<Character, Integer> getModifiedData(String operation, String processorName){
		Processor1 p1 = new Processor1();
		Processor2 p2 = new Processor2();
		Processor3 p3 = new Processor3();
		Processor4 p4 = new Processor4();
		
		if(operation.equals(BusOperations.BusRead)){
			switch(processorName){
				case "Processor1":					
					if(p2.getCacheStatus().equals("M")){
						block.putAll(p2.getCache2());
						p2.setCacheStatus(ProcessorCacheStatus.SHARED);
					}
					else if(p3.getCacheStatus().equals("M")){
						block.putAll(p3.getCache3());
						p3.setCacheStatus(ProcessorCacheStatus.SHARED);
					}
					else if(p4.getCacheStatus().equals("M")){
						block.putAll(p4.getCache4());
						p4.setCacheStatus(ProcessorCacheStatus.SHARED);
					}
					break;
				
				case "Processor2":
					if(p1.getCacheStatus().equals("M")){
						block.putAll(p1.getCache1());
						p1.setCacheStatus(ProcessorCacheStatus.SHARED);
					}
					else if(p3.getCacheStatus().equals("M")){
						block.putAll(p3.getCache3());
						p3.setCacheStatus(ProcessorCacheStatus.SHARED);
					}
					else if(p4.getCacheStatus().equals("M")){
						block.putAll(p4.getCache4());
						p4.setCacheStatus(ProcessorCacheStatus.SHARED);
					}
					break;
				
				case "Processor3":
					if(p1.getCacheStatus().equals("M")){
						block.putAll(p1.getCache1());
						p1.setCacheStatus(ProcessorCacheStatus.SHARED);
					}
					else if(p2.getCacheStatus().equals("M")){
						block.putAll(p2.getCache2());
						p2.setCacheStatus(ProcessorCacheStatus.SHARED);
					}
					else if(p4.getCacheStatus().equals("M")){
						block.putAll(p4.getCache4());
						p4.setCacheStatus(ProcessorCacheStatus.SHARED);
					}
					
					break;
					
				case "Processor4":
					if(p1.getCacheStatus().equals("M")){
						block.putAll(p1.getCache1());
						p1.setCacheStatus(ProcessorCacheStatus.SHARED);
					}
					else if(p2.getCacheStatus().equals("M")){
						block.putAll(p2.getCache2());
						p2.setCacheStatus(ProcessorCacheStatus.SHARED);
					}
					else if(p3.getCacheStatus().equals("M")){
						block.putAll(p3.getCache3());
						p3.setCacheStatus(ProcessorCacheStatus.SHARED);
					}
					break;
				
				default:
					MemoryBlock block = new MemoryBlock();
					for(Character varName: block.getBlock1().keySet()){
						block.removeBlockValue(varName.charValue());
					}
					block.setBlock1(BusOperations.block);
					block.setBlockStatus(BlockStatus.VALID);
			}
		}
		else if(operation.equals(BusOperations.BusWrite)){
			switch(processorName){
				case "Processor1":
					if(p2.getCacheStatus().equals("M")){
						block.putAll(p2.getCache2());
					}
					else if(p3.getCacheStatus().equals("M")){
						block.putAll(p3.getCache3());
					}
					else if(p4.getCacheStatus().equals("M")){
						block.putAll(p4.getCache4());						
					}
				
				case "Processor2":
					if(p1.getCacheStatus().equals("M")){
						block.putAll(p1.getCache1());
					}
					else if(p3.getCacheStatus().equals("M")){
						block.putAll(p3.getCache3());
					}
					else if(p4.getCacheStatus().equals("M")){
						block.putAll(p4.getCache4());						
					}
				
				case "Processor3":
					if(p1.getCacheStatus().equals("M")){
						block.putAll(p1.getCache1());
					}
					else if(p2.getCacheStatus().equals("M")){
						block.putAll(p2.getCache2());
					}
					else if(p4.getCacheStatus().equals("M")){
						block.putAll(p4.getCache4());						
					}
					
				case "Processor4":
					if(p1.getCacheStatus().equals("M")){
						block.putAll(p1.getCache1());
					}
					else if(p2.getCacheStatus().equals("M")){
						block.putAll(p2.getCache2());
					}
					else if(p3.getCacheStatus().equals("M")){
						block.putAll(p3.getCache3());
					}					
					
				default:
					invalidateCache(processorName);
					MemoryBlock m = new MemoryBlock();
					m.setBlockStatus(BlockStatus.INVALID);
			}
		}
		return block;
	}
	
	public void invalidateCache(String processorName){
		Processor1 p1 = new Processor1();
		Processor2 p2 = new Processor2();
		Processor3 p3 = new Processor3();
		Processor4 p4 = new Processor4();
		
		switch(processorName){
			case "Processor1":
				p2.setCacheStatus(ProcessorCacheStatus.INVALID);
				p3.setCacheStatus(ProcessorCacheStatus.INVALID);
				p4.setCacheStatus(ProcessorCacheStatus.INVALID);
				break;
			
			case "Processor2":
				p1.setCacheStatus(ProcessorCacheStatus.INVALID);
				p3.setCacheStatus(ProcessorCacheStatus.INVALID);
				p4.setCacheStatus(ProcessorCacheStatus.INVALID);
				break;
			
			case "Processor3":
				p2.setCacheStatus(ProcessorCacheStatus.INVALID);
				p1.setCacheStatus(ProcessorCacheStatus.INVALID);
				p4.setCacheStatus(ProcessorCacheStatus.INVALID);
				break;
				
			case "Processor4":
				p2.setCacheStatus(ProcessorCacheStatus.INVALID);
				p3.setCacheStatus(ProcessorCacheStatus.INVALID);
				p1.setCacheStatus(ProcessorCacheStatus.INVALID);
				break;
		}
	}
}
