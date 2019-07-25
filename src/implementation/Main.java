package implementation;

import blocks.BlockStatus;
import blocks.MemoryBlock;
import processors.*;

public class Main {
	public static void printProcessorCacheBlockStatus(){
		System.out.println("Printing processor cache block status");
		Processor1 p1 = new Processor1();
		Processor2 p2 = new Processor2();
		Processor3 p3 = new Processor3();
		Processor4 p4 = new Processor4();
		System.out.println("Processor1: "+p1.getCacheStatus());
		System.out.println("Processor2: "+p2.getCacheStatus());
		System.out.println("Processor3: "+p3.getCacheStatus());
		System.out.println("Processor4: "+p4.getCacheStatus());
	}
	
	public static void main(String[] args) {
		MemoryBlock block1 = new MemoryBlock();
		block1.addBlockValue('w', 50);
		block1.addBlockValue('x', 20);
		block1.addBlockValue('y', 30);
		block1.addBlockValue('z', 100);
		block1.setBlockStatus(BlockStatus.VALID);
		
		System.out.println("****************************************");
		System.out.println("Printing memory block:");
		System.out.println(block1.getBlock1());
		
		Processor1 p1 = new Processor1();
		Processor2 p2 = new Processor2();
		Processor3 p3 = new Processor3();
		
		System.out.println("****************************************");
		System.out.println("Reading variable x from Processor1:");
		p1.read('x');
		
		System.out.println("****************************************");
		System.out.println("Print cache statuses for all processors:");
		Main.printProcessorCacheBlockStatus();
		
		System.out.println("****************************************");
		System.out.println("Reading variable x from Processor2:");
		p2.read('x');
		
		System.out.println("****************************************");
		System.out.println("Print cache statuses for all processors:");
		Main.printProcessorCacheBlockStatus();
		
		System.out.println("****************************************");
		System.out.println("Write variable x = 150 from Processor3:");
		p3.write('x', 150);
		
		System.out.println("****************************************");
		System.out.println("Print cache statuses for all processors:");
		Main.printProcessorCacheBlockStatus();
		
		System.out.println("****************************************");
		System.out.println("Reading variable x from Processor2:");
		p2.read('x');
		
		System.out.println("****************************************");
		System.out.println("Print cache statuses for all processors:");
		Main.printProcessorCacheBlockStatus();
		
		System.out.println("****************************************");
		System.out.println("Write variable z = 500 from Processor1:");
		p1.write('z', 500);
		
		System.out.println("****************************************");
		System.out.println("Print cache statuses for all processors:");
		Main.printProcessorCacheBlockStatus();
		
		System.out.println("****************************************");
		System.out.println("Write variable y = 10 from Processor2:");
		p2.write('y', 10);
		
		System.out.println("****************************************");
		System.out.println("Print cache statuses for all processors:");
		Main.printProcessorCacheBlockStatus();
		
		System.out.println("****************************************");
		System.out.println("Reading variable w from Processor3:");
		p3.read('w');
		
		System.out.println("****************************************");
		System.out.println("Print cache statuses for all processors:");
		Main.printProcessorCacheBlockStatus();
		
		System.out.println("****************************************");
		System.out.println("Reading variable x from Processor2:");
		p2.read('x');
		
		System.out.println("****************************************");
		System.out.println("Print cache statuses for all processors:");
		Main.printProcessorCacheBlockStatus();
		
		System.out.println("****************************************");
		System.out.println("Reading variable w from Processor1:");
		p1.read('w');
		
		System.out.println("****************************************");
		System.out.println("Print cache statuses for all processors:");
		Main.printProcessorCacheBlockStatus();
	}
}
