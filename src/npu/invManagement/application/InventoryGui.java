package npu.invManagement.application;

import npu.invManagement.gui.InvViewerFrame;
import npu.invManagement.gui.OverstockedViewerPanel;
import npu.invManagement.gui.ProdInputPanel;
import npu.invManagement.gui.UnderstockedViewerPanel;
import npu.invManagement.modelclasses.Inventory;
import npu.invManagement.modelclasses.InventoryDb;

public class InventoryGui {

	public static void main(String args[]) {
		Inventory inv;
		InvViewerFrame overstockedFrame, prodInputFrame, understockedFrame;

		// Instantiate our data structure
		// Normally we would read it in from a file or database
		inv = InventoryDb.readInventory();

		prodInputFrame = buildProdInputFrame(inv);
		overstockedFrame = buildOverstockedProdFrame(inv);
		understockedFrame = buildUnderstockedProdFrame(inv);

		overstockedFrame.setVisible(true);
		overstockedFrame.setLocation(100, 200);

		understockedFrame.setVisible(true);
		understockedFrame.setLocation(100, 400);

		prodInputFrame.setVisible(true);
		prodInputFrame.setLocation(100, 100);
	}

	public static InvViewerFrame buildProdInputFrame(Inventory inv) {
		InvViewerFrame prodInputFrame;
		ProdInputPanel prodInPanel;

		prodInPanel = new ProdInputPanel(inv);
		prodInputFrame = new InvViewerFrame(prodInPanel);
		prodInputFrame.setTitle("Input Shipment/Delivery info");
		prodInputFrame.pack();

		return prodInputFrame;
	}

	public static InvViewerFrame buildOverstockedProdFrame(Inventory inv) {
		InvViewerFrame overstockedFrame;
		OverstockedViewerPanel overstockedPanel;

		overstockedPanel = new OverstockedViewerPanel(inv);
		overstockedFrame = new InvViewerFrame(overstockedPanel);
		overstockedFrame.setTitle("Overstocked Products");
		overstockedFrame.pack();

		return overstockedFrame;
	}

	public static InvViewerFrame buildUnderstockedProdFrame(Inventory inv) {
		InvViewerFrame understockedFrame;
		UnderstockedViewerPanel understockedPanel;

		understockedPanel = new UnderstockedViewerPanel(inv);
		understockedFrame = new InvViewerFrame(understockedPanel);
		understockedFrame.setTitle("Understocked Products");
		understockedFrame.pack();

		return understockedFrame;
	}

}
