package com.vogella.vde.preferencesspy;

import org.eclipse.jface.dialogs.PopupDialog;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;

public class SelectedPreferenceDialog extends PopupDialog {

	public SelectedPreferenceDialog(Shell parent, int shellStyle,
			boolean takeFocusOnOpen, boolean persistSize,
			boolean persistLocation, boolean showDialogMenu,
			boolean showPersistActions, String titleText, String infoText) {
		super(parent, shellStyle, takeFocusOnOpen, persistSize,
				persistLocation, showDialogMenu, showPersistActions, titleText,
				infoText);
	}

	@Override
	protected Point getInitialLocation(final Point initialSize) {
		Point cursorLocation = PlatformUI.getWorkbench().getDisplay()
				.getCursorLocation();
		cursorLocation.x += 40;
		cursorLocation.y = Math.max(cursorLocation.y - initialSize.y / 2, 0);
		return cursorLocation;
	}

}
