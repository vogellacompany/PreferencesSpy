package com.vogella.vde.preferencesspy.parts.viewer;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.databinding.observable.masterdetail.IObservableFactory;
import org.eclipse.core.databinding.observable.set.IObservableSet;
import org.eclipse.jface.databinding.viewers.ObservableSetTreeContentProvider;
import org.eclipse.jface.databinding.viewers.TreeStructureAdvisor;

import com.vogella.vde.preferencesspy.model.PreferenceEntry;
import com.vogella.vde.preferencesspy.model.PreferenceNodeEntry;

public class PreferenceEntriesContentProvider extends ObservableSetTreeContentProvider {

	private boolean hierarchicalLayout;

	public PreferenceEntriesContentProvider(IObservableFactory setFactory, TreeStructureAdvisor structureAdvisor) {
		super(setFactory, structureAdvisor);
	}

	@Override
	public Object[] getElements(Object inputElement) {
		Object[] children = super.getElements(inputElement);
		if (isHierarchicalLayout()) {
			return children;
		}

		List<PreferenceEntry> childList = new ArrayList<PreferenceEntry>();

		for (Object object : children) {
			getChildren(object, childList);
		}

		return childList.toArray();
	}


	private void getChildren(Object element, List<PreferenceEntry> childList) {
		if (element instanceof PreferenceNodeEntry) {
			IObservableSet preferenceEntries = ((PreferenceNodeEntry) element).getPreferenceEntries();
			for (Object object : preferenceEntries) {
				getChildren(object, childList);
			}
		} else if (element instanceof PreferenceEntry) {
			childList.add((PreferenceEntry) element);
		}
	}

	public boolean isHierarchicalLayout() {
		return hierarchicalLayout;
	}

	public void setHierarchicalLayout(boolean hierarchicalLayout) {
		this.hierarchicalLayout = hierarchicalLayout;
	}
}
