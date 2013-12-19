package com.vogella.vde.preferencesspy.preferencepage;

import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import com.vogella.vde.preferencesspy.Activator;

public class TracePreferencePage extends FieldEditorPreferencePage implements
		IWorkbenchPreferencePage {

	public TracePreferencePage() {
		super(GRID);
		setPreferenceStore(Activator.getDefault().getPreferenceStore());
		setDescription("Settings for the preference spy");
	}

	/**
	 * Creates the field editors
	 */
	public void createFieldEditors() {
		addField(new BooleanFieldEditor(PreferenceConstants.P_BOOLEAN,
				"&Trace preference values ", getFieldEditorParent()));
	}

	public void init(IWorkbench workbench) {
	}

}