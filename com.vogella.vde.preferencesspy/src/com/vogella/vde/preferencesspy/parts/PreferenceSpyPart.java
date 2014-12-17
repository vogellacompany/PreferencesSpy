package com.vogella.vde.preferencesspy.parts;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.eclipse.core.databinding.beans.BeanProperties;
import org.eclipse.core.databinding.observable.Realm;
import org.eclipse.core.databinding.property.Properties;
import org.eclipse.core.runtime.preferences.IEclipsePreferences.PreferenceChangeEvent;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.ui.di.UIEventTopic;
import org.eclipse.e4.ui.model.application.ui.basic.MWindow;
import org.eclipse.e4.ui.workbench.modeling.EModelService;
import org.eclipse.e4.ui.workbench.modeling.ESelectionService;
import org.eclipse.jface.databinding.swt.DisplayRealm;
import org.eclipse.jface.databinding.viewers.ObservableSetTreeContentProvider;
import org.eclipse.jface.resource.FontDescriptor;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.TreeViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.ui.dialogs.FilteredTree;

import com.vogella.vde.preferencesspy.constants.PreferenceSpyEventTopics;
import com.vogella.vde.preferencesspy.model.PreferenceEntry;
import com.vogella.vde.preferencesspy.model.PreferenceEntry.Fields;
import com.vogella.vde.preferencesspy.model.PreferenceEntryPatternFilter;
import com.vogella.vde.preferencesspy.model.PreferenceNodeEntry;
import com.vogella.vde.preferencesspy.parts.viewer.PreferenceEntryViewerComparator;
import com.vogella.vde.preferencesspy.parts.viewer.PreferenceMapLabelProvider;
import com.vogella.vde.preferencesspy.parts.viewer.PreferenceSpyEditingSupport;

public class PreferenceSpyPart implements TreeViewerPart {

	private PreferenceNodeEntry input = new PreferenceNodeEntry();
	private FilteredTree filteredTree;

	@PostConstruct
	public void postConstruct(Composite parent, final ESelectionService selectionService, EModelService modelService,
			MWindow window) {

		PreferenceEntryPatternFilter patternFilter = new PreferenceEntryPatternFilter();
		patternFilter.setIncludeLeadingWildcard(true);
		filteredTree = new FilteredTree(parent, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER, patternFilter,
				true);

		Tree table = filteredTree.getViewer().getTree();
		table.setHeaderVisible(true);
		table.setLinesVisible(true);

		filteredTree.getViewer().addSelectionChangedListener(new ISelectionChangedListener() {

			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				ISelection selection = event.getSelection();
				if (selection instanceof IStructuredSelection) {
					@SuppressWarnings("unchecked")
					ArrayList<PreferenceEntry> preferenceEntries = new ArrayList<PreferenceEntry>(
							((IStructuredSelection) selection).toList());
					selectionService.setSelection(preferenceEntries);
				}
			}
		});

		createColumn(Fields.nodePath, "Nodepath", 300);
		createColumn(Fields.key, "Key", 300);
		createColumn(Fields.oldValue, "Old Value", 150);
		createColumn(Fields.newValue, "New Value", 150);

		filteredTree.getViewer().setComparator(new PreferenceEntryViewerComparator());

		FontDescriptor fontDescriptor = getBoldFontDescriptor();

		Realm realm = DisplayRealm.getRealm(parent.getDisplay());
		ObservableSetTreeContentProvider contentProvider = new ObservableSetTreeContentProvider(
				BeanProperties.set("preferenceEntries",
						PreferenceNodeEntry.class).setFactory(realm), null);
		filteredTree.getViewer().setContentProvider(contentProvider);
		filteredTree.getViewer().setLabelProvider(
				new PreferenceMapLabelProvider(fontDescriptor, Properties.observeEach(
						contentProvider.getKnownElements(), BeanProperties.values(PreferenceEntry.class, new String[] { "nodePath",
							"key", "oldValue", "newValue" }))));
		filteredTree.getViewer().setInput(input);
	}

	private FontDescriptor getBoldFontDescriptor() {
		Font origFont = filteredTree.getViewer().getControl().getFont();
		FontData[] fontData = origFont.getFontData();
		for (FontData fontDataItem : fontData) {
			fontDataItem.style = fontDataItem.style | SWT.BOLD;
		}

		FontDescriptor fontDescriptor = FontDescriptor.createFrom(fontData);
		return fontDescriptor;
	}

	private void createColumn(Fields field, String columnName, int width) {
		TreeViewerColumn viewerColumn = new TreeViewerColumn(filteredTree.getViewer(), SWT.NONE);
		viewerColumn.getColumn().setWidth(width);
		viewerColumn.getColumn().setText(columnName);

		viewerColumn.setLabelProvider(new ColumnLabelProvider());
		viewerColumn.setEditingSupport(new PreferenceSpyEditingSupport(filteredTree.getViewer(), field));
	}

	@Inject
	@Optional
	public void preferenceChanged(
			@UIEventTopic(PreferenceSpyEventTopics.PREFERENCESPY_PREFERENCE_CHANGED) PreferenceChangeEvent event) {

		PreferenceNodeEntry preferenceNodeEntry = new PreferenceNodeEntry(event.getNode().absolutePath());
		PreferenceEntry preferenceEntry = new PreferenceEntry(event.getNode().absolutePath(), event.getKey(),
				String.valueOf(event.getOldValue()), String.valueOf(event.getNewValue()));
		preferenceEntry.setRecentlyChanged(true);
		preferenceNodeEntry.addChildren(preferenceEntry);
		preferenceNodeEntry.setRecentlyChanged(true);
		input.addChildren(preferenceNodeEntry);
		filteredTree.getViewer().refresh();
	}

	@Inject
	@Optional
	public void preferenceChanged(
			@UIEventTopic(PreferenceSpyEventTopics.PREFERENCESPY_PREFERENCE_SHOW) Collection<PreferenceEntry> preferenceEntries) {
		input.addChildren(preferenceEntries);
		filteredTree.getViewer().refresh();
	}

	@Inject
	@Optional
	public void DeletePreferenceEntries(
			@UIEventTopic(PreferenceSpyEventTopics.PREFERENCESPY_PREFERENCE_ENTRIES_DELETE) List<PreferenceEntry> preferenceEntries) {
		if (preferenceEntries != null && !preferenceEntries.isEmpty()) {
			input.removeChildren(preferenceEntries);
			filteredTree.getViewer().refresh();
		}
	}

	@Inject
	@Optional
	public void DeleteAllPreferenceEntries(
			@UIEventTopic(PreferenceSpyEventTopics.PREFERENCESPY_PREFERENCE_ENTRIES_DELETE_ALL) List<PreferenceEntry> preferenceEntries) {
		if (input != null) {
			input.getPreferenceEntries().clear();
			filteredTree.getViewer().refresh();
		}
	}

	@Override
	public TreeViewer getViewer() {
		return filteredTree.getViewer();
	}

}