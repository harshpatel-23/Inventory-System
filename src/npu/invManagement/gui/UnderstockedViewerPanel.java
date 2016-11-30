package npu.invManagement.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListModel;

import npu.invManagement.modelclasses.Inventory;
import npu.invManagement.modelclasses.ProductCnt;
import npu.invManagement.modelclasses.ProductCntAction;

public class UnderstockedViewerPanel extends JPanel implements Observer {
	private static final long serialVersionUID = 1L;
	private ArrayList<ProductCnt> understockedList = new ArrayList<ProductCnt>();
	private JLabel titleLbl = new JLabel("Understocked Products");
	private JList<String> understockedListDisplay;
	private JScrollPane underStockedScrollPane;
	private DefaultListModel<String> listModel = new DefaultListModel<String>();
	private Inventory inv;
	private ProductCnt prodCnt;
	private ProductCnt rmvprod;

	public UnderstockedViewerPanel(Inventory inv) {
		this.inv = inv;
		/*
		 * Find which products are currently understocked. This will be our
		 * initial display.
		 */
		ArrayList<ProductCnt> understockedProdList;

		understockedProdList = inv.currentUnderStockedProducts();
		if (understockedProdList == null)
			return; /* don't crash */

		for (ProductCnt curProd : understockedProdList) {
			prodCnt = curProd;
			prodCnt.addObserver(this);
		}
		addUnderstockedProdsToListViewer();

		/*
		 * Our JList will display all the Strings in listModel so assign the
		 * listModel to our JList here.
		 */
		understockedListDisplay = new JList<String>(listModel);
		underStockedScrollPane = new JScrollPane(understockedListDisplay);
		underStockedScrollPane.setPreferredSize(new Dimension(250, 150));
		setLayout(new BorderLayout());
		add(titleLbl, "North");
		add(underStockedScrollPane, "Center");
	}

	public void addNewUnderstockedProd(ProductCnt prod) {
		String displayStr = buildProdDisplayStr(prod);
		String str = displayStr.substring(0, 4);
		boolean b = true;

		for (int i = 0; i < listModel.size(); i++) {
			String substr = listModel.get(i).substring(0, 4).toString();
			if (str.matches(substr)) {
				b = false;
				listModel.remove(i);
				listModel.add(i, displayStr);
			}
		}
		if (b == true) {
			listModel.addElement(displayStr);
		}
		understockedList.add(prod);
	}

	public void removeProdFromUnderstockedList(int prodIdx) {
		if (prodIdx >= 0) {
			listModel.removeElementAt(prodIdx);
			understockedList.remove(prodIdx);
		}
	}

	/*
	 * Could use a ListCellRenderer instead of storing Strings in the List. But
	 * for our purposes, I'm using a simpler to understand approach. The index
	 * of the ProductCnt object in overstockedProdList will be the same index in
	 * listModel. This is the idea of "parallel arrays".
	 */
	private String buildProdDisplayStr(ProductCnt prod) {
		String displayStr;
		displayStr = prod.getName() + " " + prod.getNumItems() + " items, increase to: "
				+ (prod.getUnderStockedLimit() + 1);
		return displayStr;
	}

	/*
	 * This method will initialize the overstockedProdList and its parallel
	 * array listModel.
	 */
	public void addUnderstockedProdsToListViewer() {
		String displayStr;
		ArrayList<ProductCnt> understockedProdList;

		understockedProdList = inv.currentUnderStockedProducts();
		if (understockedProdList == null)
			return; /* don't crash */

		for (ProductCnt curProd : understockedProdList) {
			displayStr = buildProdDisplayStr(curProd);
			listModel.addElement(displayStr);
			understockedList.add(curProd);
		}
	}

	public void update(Observable obs, Object actionObj) {
		ArrayList<ProductCnt> understockedProdList;

		understockedProdList = inv.currentUnderStockedProducts();
		ProductCntAction action;
		for (ProductCnt curProd : understockedProdList) {
			if (obs.equals(curProd)) {
				prodCnt = curProd;
				prodCnt.addObserver(this);

				if (prodCnt.getUnderStockedLimit() > prodCnt.getNumItems()) {
					addNewUnderstockedProd(prodCnt);
					action = (ProductCntAction) actionObj;
				} else if (prodCnt.getUnderStockedLimit() < prodCnt.getNumItems()) {
				} else {

				}
			}
		}

		rmvprod = (ProductCnt) obs;

		if (rmvprod == obs) {
			if (rmvprod.getOverStockedLimit() > rmvprod.getNumItems()
					&& rmvprod.getUnderStockedLimit() < rmvprod.getNumItems()) {

				String str = rmvprod.getName().substring(0, 4);
				for (int i = 0; i < listModel.size(); i++) {
					String s = listModel.get(i).substring(0, 4).toString();
					if (str.matches(s)) {
						removeProdFromUnderstockedList(i);
					}
				}
			}
			if (rmvprod.getOverStockedLimit() < rmvprod.getNumItems()) {
				String str = rmvprod.getName().substring(0, 4);
				for (int i = 0; i < listModel.size(); i++) {
					String s = listModel.get(i).substring(0, 4).toString();
					if (str.matches(s)) {
						removeProdFromUnderstockedList(i);
					}
				}
				OverstockedViewerPanel osvpanel = new OverstockedViewerPanel(inv);
				osvpanel.update(obs, actionObj);
			}
		}
	}

}
