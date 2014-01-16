package com.vogella.vde.preferencesspy;

import org.eclipse.jface.dialogs.PopupDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.layout.GridData;

public class PreferencePopupDialog extends PopupDialog {
	private final static int SHELL_STYLE = PopupDialog.INFOPOPUP_SHELLSTYLE;
	private Text text;
	private Color foregroundColor = new Color(Display.getCurrent(), new RGB(0,
			0, 0));
	private Color backgroundColor = new Color(Display.getCurrent(), new RGB(
			245, 245, 181));
	private String value;

	public PreferencePopupDialog(Shell parent, String titleText, String infoText) {
		super(parent, SHELL_STYLE, true, false, false, false, false, titleText,
				infoText);
	}

	@Override
	protected void adjustBounds() {
		super.adjustBounds();
		Display d = Display.getCurrent();
		if (d == null) {
			d = Display.getDefault();
		}
		Point point = d.getCursorLocation();
		getShell().setLocation(point.x + 9, point.y + 14);
	}

	@Override
	protected Control createDialogArea(Composite parent) {
		Control composite = super.createDialogArea(parent);

		Composite table = new Composite((Composite) composite, SWT.NONE);
		table.setLayout(new GridLayout(1, false));
		text = new Text(table, SWT.MULTI | SWT.BORDER);
		text.setText(value);
		return composite;
	}

	@Override
	protected Color getBackground() {
		return backgroundColor;
	}
	
	@Override
	protected Color getForeground() {
		return foregroundColor;
	}
	

	@Override
	protected Control createContents(Composite parent) {
		Control contents = super.createContents(parent);
		text.setForeground(foregroundColor);
		return contents;
	}
	

	public void setText(String value) {
		this.value = value;
	}

}