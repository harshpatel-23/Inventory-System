package npu.invManagement.gui;

import java.awt.Container;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class InvViewerFrame extends JFrame {
	private static final long serialVersionUID = 1L;

	public InvViewerFrame(JPanel topPanel) {
		Container topContainer = getContentPane();

		WindowListener exitListener = new FrameTerminator();
		addWindowListener(exitListener);
		setSize(500, 200);
		topContainer.add(topPanel);
	}

	class FrameTerminator extends WindowAdapter {
		public void windowClosing(WindowEvent event) {
			System.exit(0);
		}
	}

}