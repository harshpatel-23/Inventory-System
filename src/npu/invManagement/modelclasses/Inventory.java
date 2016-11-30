package npu.invManagement.modelclasses;

import java.util.ArrayList;

public class Inventory {
	private ArrayList<ProductCnt> inv;

	public Inventory() {
		/* instantiate an empty list */
		inv = new ArrayList<ProductCnt>();
	}

	/* Add a product whose inventory we need to keep track of */
	public void addProduct(ProductCnt prodCnt) {
		inv.add(prodCnt);
	}

	/* A product delivery means we will have more of this product */
	public ProductCnt findProdCnt(int prodId) {
		for (ProductCnt curProd : inv) {
			if (curProd.matchesId(prodId)) {
				return curProd;
			}
		}
		return null; /* didn't find */
	}

	public void deliverProduct(int prodId, int numItems) {
		ProductCnt prod;
		prod = findProdCnt(prodId);
		if (prod != null) {
			prod.addProducts(numItems);
		}
	}

	/* A product shipment means we will have less of this product */
	public void shipProduct(int prodId, int numItems) {
		for (int i = 0; i <= inv.size() - 1; i++) {
			if (inv.get(i).getProdId() == prodId) {
				inv.get(i).removeProducts(numItems);
			}
		}
		return;
	}

	public ArrayList<ProductCnt> currentOverstockedProducts() {
		/* create a new (empty) ArrayList */
		/*
		 * find which products are currently overstocked and add them to the
		 * ArrayList
		 */
		ArrayList<ProductCnt> overstockprod = new ArrayList<>();

		for (int i = 0; i <= inv.size() - 1; i++) {
			if (inv.get(i).isOverstocked()) {
				overstockprod.add(inv.get(i));
			}
		}
		return overstockprod;
	}

	public ArrayList<ProductCnt> currentUnderStockedProducts() {
		/* create a new (empty) ArrayList */
		/*
		 * find which products are currently understocked and add them to the
		 * ArrayList
		 */
		ArrayList<ProductCnt> understockprod = new ArrayList<>();

		for (int i = 0; i <= inv.size() - 1; i++) {
			if (inv.get(i).isUnderstocked()) {
				understockprod.add(inv.get(i));
			}
		}
		return understockprod;
	}

	public ArrayList<ProductCnt> allproducts() {
		/* create a new (empty) ArrayList */
		/*
		 * find all products
		 */
		ArrayList<ProductCnt> allprod = new ArrayList<>();

		for (int i = 0; i <= inv.size() - 1; i++) {
			allprod.add(inv.get(i));
		}
		return allprod;
	}

}
