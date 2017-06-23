package org.eclipse.swt.tests.gtk.snippets;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

/*
 * Title: Bug 500703 - [GTK3.20+] Combo with SWT.READ_ONLY is garbled upon re-size
 * How to run: launch snippet, select long box, start to shrink window
 * Bug description: Text is drawn over itself numerous times, causing garbled text
 * Expected results: Text should shrink and not be drawn over other widgets
 * GTK Version(s): 3.20+
 */
public class Bug500703_ComboGarbledResize {

	public static void main(String[] args) {
		Display display = new Display();
		Shell shell = new Shell(display);
		shell.setLayout(new GridLayout(2, false));

		Label label = new Label(shell, SWT.NONE);
		label.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false,false, 1, 1));
		label.setText("Testing Label");

		Combo text = new Combo(shell, SWT.READ_ONLY);
		text.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		text.add("This is a very long long long long long box");

		shell.pack();
		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		display.dispose();
	}
}