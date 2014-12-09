package com.vogella.vde.preferencesspy.model;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.ui.dialogs.PatternFilter;

public class PreferenceEntryPatternFilter extends PatternFilter {

	public PreferenceEntryPatternFilter() {
		super();
	}

	@Override
	protected boolean isLeafMatch(Viewer viewer, Object element) {

		if (element instanceof PreferenceEntry) {
			PreferenceEntry preferenceEntry = (PreferenceEntry) element;
			if (wordMatches(preferenceEntry.getNodePath()) || wordMatches(preferenceEntry.getKey())
					|| wordMatches(preferenceEntry.getOldValue()) || wordMatches(preferenceEntry.getNewValue())) {
				return true;
			}
		}

		return super.isLeafMatch(viewer, element);
	}

}
