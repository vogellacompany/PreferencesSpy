package com.vogella.vde.preferencesspy.parts.viewer;

import java.util.Comparator;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerComparator;

import com.vogella.vde.preferencesspy.model.PreferenceEntry;

public class PreferenceEntryViewerComparator extends ViewerComparator {

	public PreferenceEntryViewerComparator() {
	}

	public PreferenceEntryViewerComparator(Comparator<?> comparator) {
		super(comparator);
	}

	@Override
	public int compare(Viewer viewer, Object e1, Object e2) {
		if(e1 instanceof PreferenceEntry && e2 instanceof PreferenceEntry){
			PreferenceEntry entry1 = (PreferenceEntry) e1;
			PreferenceEntry entry2 = (PreferenceEntry) e2;

			long time = entry1.getTime();
			long time2 = entry2.getTime();

			if (time != 0 && time2 != 0) {
				return (int) (time2 - time);
			}
		}

		return super.compare(viewer, e1, e2);
	}

	@Override
	public int category(Object element) {
		if (element instanceof PreferenceEntry) {
			return ((PreferenceEntry) element).isRecentlyChanged() ? 0 : 1;
		}
		return 2;
	}

}
