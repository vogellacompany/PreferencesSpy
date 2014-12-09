
package com.vogella.vde.preferencesspy.handler;

import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;

import com.vogella.vde.preferencesspy.parts.TreeViewerPart;

public class ExpandAllHandler {

	@Execute
	public void execute(MPart part) {
		Object partImpl = part.getObject();
		if (partImpl instanceof TreeViewerPart) {
			((TreeViewerPart) partImpl).getViewer().expandAll();
		}
	}

}