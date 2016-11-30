package npu.invManagement.modelclasses;

public class ProductCntAction {

	final static public char Shipment = 's';
	final static public char Delivery = 'd';
	private char action;
	private int numItems;

	ProductCntAction(char action, int numItems) {
		this.action = action;
		this.numItems = numItems;
	}

	public boolean isShipment() {
		return action == Shipment;
	}

	public boolean isDelivery() {
		return action == Delivery;
	}

	public int getNumItems() {
		return numItems;
	}

}