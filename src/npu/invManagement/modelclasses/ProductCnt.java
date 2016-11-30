package npu.invManagement.modelclasses;

import java.awt.Desktop.Action;
import java.util.Observable;
import npu.invManagement.modelclasses.ProductCntAction;

public class ProductCnt extends Observable {
	private int prodId;
	private String name;
	private int numItems;
	private int overStockedLimit;
	private int underStockedLimit;

	public ProductCnt(int prodId, String name, int numItems) {
		this.prodId = prodId;
		this.name = name;
		this.numItems = numItems;
	}

	public String getName() {
		return name;
	}

	public int getNumItems() {
		return numItems;
	}

	public int getOverStockedLimit() {
		return overStockedLimit;
	}

	public void setOverStockedLimit(int overStockedLimit) {
		this.overStockedLimit = overStockedLimit;
	}

	public int getUnderStockedLimit() {
		return underStockedLimit;
	}

	public void setUnderStockedLimit(int underStockedLimit) {
		this.underStockedLimit = underStockedLimit;
	}

	public int getProdId() {
		return prodId;
	}

	public boolean isOverstocked() {
		if (numItems >= overStockedLimit) {
			return true;
		}

		return false;
	}

	public boolean isUnderstocked() {
		if (numItems <= underStockedLimit) {
			return true;
		}

		return false;
	}

	public void addProducts(int numProds) {
		ProductCntAction action;
		numItems = numItems + numProds;
		action = new ProductCntAction(ProductCntAction.Delivery, numProds);
		setChanged();
		notifyObservers(action);

	}

	public void removeProducts(int numProds) {
		ProductCntAction action;
		numItems = numItems - numProds;
		action = new ProductCntAction(ProductCntAction.Shipment, numProds);
		setChanged();
		notifyObservers(action);
	}

	public boolean matchesId(int testprodId) {
		return testprodId == prodId;
	}

	@Override
	public boolean equals(Object OtherObj) {
		if (this == OtherObj) {
			return true;
		}

		if (OtherObj == null || getClass() != OtherObj.getClass()) {
			return false;
		}

		ProductCnt otherprod = (ProductCnt) OtherObj;
		if (prodId != otherprod.prodId) {
			return false;
		}
		return true;
	}

}