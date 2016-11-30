package npu.invManagement.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import npu.invManagement.modelclasses.Inventory;
import npu.invManagement.modelclasses.InventoryDb;
import npu.invManagement.modelclasses.ProductCnt;

//Data Gatherer on Product shipments and deliveries 
public class ProdInputPanel extends JPanel implements ActionListener {
	private static final long serialVersionUID = 1L;
	private JLabel prodIdLbl = new JLabel("Product Id: ");
	private JTextField prodIdTxt = new JTextField(5);
	private JLabel deliveryLbl = new JLabel("Delivery: ");
	private JLabel shipmentLbl = new JLabel("Shipment: ");
	private JTextField deliveryTxt = new JTextField(5);
	private JTextField shipmentTxt = new JTextField(5);
	private Inventory inv;
	private InventoryDb invdb;
	public OverstockedViewerPanel ovp;
	public UnderstockedViewerPanel uvp;
	private ArrayList<ProductCnt> invs;

	public ProdInputPanel(Inventory inv) {
		this.inv = inv;
		inv = invdb.readInventory();

		add(prodIdLbl);
		add(prodIdTxt);
		add(deliveryLbl);
		add(deliveryTxt);
		deliveryTxt.addActionListener(this);

		add(shipmentLbl);
		add(shipmentTxt);
		shipmentTxt.addActionListener(this);
	}

	private int getProdId() {

		String prodIdStr;
		int prodId = -1;

		prodIdStr = prodIdTxt.getText();
		try {
			prodId = Integer.parseInt(prodIdStr);
			if (inv.findProdCnt(prodId) != null) {
				return prodId;
			} else {
				JFrame frame = new JFrame("JOptionPane");
				JOptionPane.showMessageDialog(frame, "Bad Value For Product ID...: '" + prodId + "'.", "Message",
						JOptionPane.INFORMATION_MESSAGE);
			}
		} catch (NumberFormatException e) {
			JFrame frame = new JFrame("JOptionPane");
			JOptionPane.showMessageDialog(frame, "Please Enter A Valid Value...", "Message",
					JOptionPane.INFORMATION_MESSAGE);
		}
		return prodId;
	}

	/*
	 * private ProductCnt findProdCnt(int prodId) { for (ProductCnt curProd :
	 * invrd) { if (curProd.matchesId(prodId)) { return curProd; } } return
	 * null; }
	 */
	private int getShipmentAmt() {

		String numItemsStr;
		int numItems = 0;

		numItemsStr = shipmentTxt.getText();
		try {
			numItems = Integer.parseInt(numItemsStr);
		} catch (NumberFormatException e) {
			JFrame frame = new JFrame("JOptionPane");
			JOptionPane.showMessageDialog(frame, "Bad Value For Shipment...: '" + numItemsStr + "'.", "Message",
					JOptionPane.INFORMATION_MESSAGE);
		}
		return numItems;
	}

	private int getDeliveryAmt() {
		String numItemsStr;
		int numItems = 0;

		numItemsStr = deliveryTxt.getText();
		try {
			numItems = Integer.parseInt(numItemsStr);
		} catch (NumberFormatException e) {
			JFrame frame = new JFrame("JOptionPane");
			JOptionPane.showMessageDialog(frame, "Bad Value For Delivery...: '" + numItemsStr + "'.", "Message",
					JOptionPane.INFORMATION_MESSAGE);
		}
		return numItems;
	}

	private void shipProduct() {
		int prodId, numItems;
		prodId = getProdId();
		numItems = getShipmentAmt();
		inv.shipProduct(prodId, numItems);
	}

	private void deliverProduct() {
		int prodId, numItems;
		prodId = getProdId();
		numItems = getDeliveryAmt();
		inv.deliverProduct(prodId, numItems);
	}

	public void actionPerformed(ActionEvent event) {
		if (event.getSource() == deliveryTxt) {
			deliverProduct();
			inv.currentOverstockedProducts();
			ovp = new OverstockedViewerPanel(inv);
			for (ProductCnt curProd : inv.currentOverstockedProducts()) {
				ovp.addNewOverstockedProd(curProd);
			}

			inv.currentUnderStockedProducts();
			ovp = new OverstockedViewerPanel(inv);
			uvp = new UnderstockedViewerPanel(inv);
			for (ProductCnt curProd : inv.currentUnderStockedProducts()) {
				uvp.addNewUnderstockedProd(curProd);
			}
			deliveryTxt.setText("");
		} else if (event.getSource() == shipmentTxt) {
			shipProduct();
			inv.currentOverstockedProducts();
			ovp = new OverstockedViewerPanel(inv);
			for (ProductCnt curProd : inv.currentOverstockedProducts()) {
				ovp.addNewOverstockedProd(curProd);
			}

			inv.currentUnderStockedProducts();
			ovp = new OverstockedViewerPanel(inv);
			uvp = new UnderstockedViewerPanel(inv);
			for (ProductCnt curProd : inv.currentUnderStockedProducts()) {
				uvp.addNewUnderstockedProd(curProd);
			}
			shipmentTxt.setText("");
		}
	}
}
