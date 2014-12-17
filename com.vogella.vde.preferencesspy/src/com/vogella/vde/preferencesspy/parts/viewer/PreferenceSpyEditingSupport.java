package com.vogella.vde.preferencesspy.parts.viewer;

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ColumnViewer;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

import com.vogella.vde.preferencesspy.model.PreferenceEntry;
import com.vogella.vde.preferencesspy.model.PreferenceEntry.Fields;

public class PreferenceSpyEditingSupport extends EditingSupport {

	private Fields field;

	public PreferenceSpyEditingSupport(ColumnViewer viewer, Fields field) {
		super(viewer);
		this.field = field;
	}

	@Override
	protected CellEditor getCellEditor(Object element) {
		return new TextCellEditor((Composite) getViewer().getControl(),
				SWT.READ_ONLY);
	}

	@Override
	protected boolean canEdit(Object element) {
		return true;
	}

	@Override
	protected Object getValue(Object element) {
		String value = null;
		if (element instanceof PreferenceEntry) {
			PreferenceEntry preferenceEntry = (PreferenceEntry) element;
			switch (field) {
			case nodePath:
				value = preferenceEntry.getNodePath();
				break;
			case key:
				value = preferenceEntry.getKey();
				break;
			case oldValue:
				value = preferenceEntry.getOldValue();
				break;
			case newValue:
				value = preferenceEntry.getNewValue();
				break;
			default:
				break;
			}
		}

		return value;
	}

	@Override
	protected void setValue(Object element, Object value) {
	}

}
