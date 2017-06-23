package org.eclipse.swt.tests.gtk.snippets;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Container;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import org.eclipse.swt.SWT;
import org.eclipse.swt.awt.SWT_AWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.browser.LocationAdapter;
import org.eclipse.swt.browser.LocationEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

/*
 * Title: Bug 262974 - [SWT_AWT] On Linux, Browser dialogs are not modal
 * How to run: launch snippet and observe that the popup window is not modal
 * Bug description: The popup is not modal
 * Expected results: The popup should act as a modal dialog
 * GTK Version(s): N/A
 */
public class Bug262974_ModalBrowserDialog extends JFrame {

  public Bug262974_ModalBrowserDialog() {
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    Canvas canvas = new Canvas() {
      @Override
      public void addNotify() {
        super.addNotify();
        final Canvas canvas_ = this;
        display.asyncExec(() -> {
            final Shell shell = SWT_AWT.new_Shell(display, canvas_);
            shell.setLayout(new FillLayout());
            final Browser browser = new Browser(shell, SWT.NONE);
            browser.setUrl("http://www.google.com");
            browser.addLocationListener(new LocationAdapter() {
              @Override
              public void changed(LocationEvent e) {
                display.asyncExec(() -> browser.execute("alert('some dialog');"));
              }
            });
            // The initial size is wrong on Windows so we have to force it
            shell.setSize(canvas_.getWidth(), canvas_.getHeight());
          });
      }
    };
    Container contentPane = getContentPane();
    contentPane.add(canvas, BorderLayout.CENTER);
    contentPane.add(new JButton("Some Swing Button"), BorderLayout.SOUTH);
    setSize(400, 300);
  }

  private static Display display;

  public static void main(String[] args) {
    System.setProperty("sun.awt.noerasebackground", "true");
    System.setProperty("sun.awt.xembedserver", "true");
    display = new Display();
    SwingUtilities.invokeLater(() -> new Bug262974_ModalBrowserDialog().setVisible(true));
    while(true) {
      if(!display.readAndDispatch()) {
        display.sleep();
      }
    }
  }

}