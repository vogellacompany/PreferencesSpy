package com.vogella.vde.preferencesspy.parts.viewer;

import java.util.Comparator;

import org.eclipse.jface.viewers.ViewerComparator;

import com.vogella.vde.preferencesspy.model.PreferenceEntry;

public class PreferenceEntryViewerComparator extends ViewerComparator {

	public PreferenceEntryViewerComparator() {
	}

	public PreferenceEntryViewerComparator(Comparator<?> comparator) {
		super(comparator);
	}

	@Override
	public int category(Object element) {
		if (element instanceof PreferenceEntry) {
			return ((PreferenceEntry) element).isRecentlyChanged() ? 0 : 1;
		}
		return 2;
	}

}
