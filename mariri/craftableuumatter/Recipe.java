package mariri.craftableuumatter;

public class Recipe {
	private int itemCode;
	private int subItemCode;
	private int productValue;
	private String[] structure;
	
	public Recipe(){
		structure = new String[3];
	}
	
	public int getItemCode(){
		return itemCode;
	}
	
	public void setItemCode(int value){
		itemCode = value;
	}
	
	public int getSubItemCode(){
		return subItemCode;
	}
	
	public void setSubItemCode(int value){
		subItemCode = value;
	}
	
	public int getProductValue(){
		return productValue;
	}
	
	public void setProductValue(int value){
		productValue = value;
	}
	
	public String getStructure(int index){
		return structure[index];
	}
	
	public void setStructure(int index, String value){
		structure[index] = value;
	}
}
