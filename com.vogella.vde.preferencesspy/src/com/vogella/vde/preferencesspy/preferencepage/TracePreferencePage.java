package com.vogella.vde.preferencesspy.preferencepage;

import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import com.vogella.vde.preferencesspy.Activator;
import com.vogella.vde.preferencesspy.constants.PreferenceConstants;

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
	@Override
	public void createFieldEditors() {
		addField(new BooleanFieldEditor(PreferenceConstants.TRACE_PREFERENCES,
				"&Trace preference values ", getFieldEditorParent()));
	}

	@Override
	public void init(IWorkbench workbench) {
	}
}
