package app;

import javax.swing.SwingUtilities;

import controller.PrincipalController;

public class Main {

	public static void main(String args[]) {
		SwingUtilities.invokeLater(new Runnable() {
		
			@Override
			public void run() {
				new PrincipalController();
			}
		});
	}
}
