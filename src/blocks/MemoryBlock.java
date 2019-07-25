package blocks;

import java.util.HashMap;

public class MemoryBlock {
	private static HashMap<Character,Integer> block1 = new HashMap<Character, Integer>();
	public void setBlock1(HashMap<Character, Integer> block1) {
		MemoryBlock.block1 = block1;
	}
	
	public HashMap<Character, Integer> getBlock1(){
		return block1;
	}

	private static char blockStatus = BlockStatus.INVALID;
	
	public char getBlockStatus() {
		return blockStatus;
	}

	public void setBlockStatus(char blockStatus) {
		MemoryBlock.blockStatus = blockStatus;
	}

	public String addBlockValue(char variableName, int value){
		if(block1.size() < 4){
			block1.put(variableName, value);
			return "Value (" + variableName + "," + value +") added successfully to the block.";
		}
		else{
			return "Block is full.";
		}
	}
	
	public String removeBlockValue(char variableName){
		if(block1.size()==0){
			return "Block is empty.";
		}
		else if(block1.get(variableName) != null){
			block1.remove(variableName);
			return "Variable "+variableName + " is removed successfully from the block.";
		}
		else{
			return "Variable "+variableName + " does not exist in the block.";
		}
	}
}
