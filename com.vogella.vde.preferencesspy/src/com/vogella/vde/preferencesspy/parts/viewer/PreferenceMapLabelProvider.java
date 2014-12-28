package com.vogella.vde.preferencesspy.parts.viewer;

import org.eclipse.core.databinding.observable.map.IObservableMap;
import org.eclipse.core.runtime.Assert;
import org.eclipse.jface.databinding.viewers.ObservableMapLabelProvider;
import org.eclipse.jface.resource.FontDescriptor;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.resource.LocalResourceManager;
import org.eclipse.jface.resource.ResourceManager;
import org.eclipse.jface.viewers.IFontProvider;
import org.eclipse.swt.graphics.Font;

import com.vogella.vde.preferencesspy.model.PreferenceEntry;

public class PreferenceMapLabelProvider extends ObservableMapLabelProvider implements IFontProvider {

	private LocalResourceManager resourceManager;
	private FontDescriptor fontDescriptor;

	public PreferenceMapLabelProvider(FontDescriptor fontDescriptor, IObservableMap attributeMap) {
		this(fontDescriptor, new IObservableMap[] { attributeMap });
	}

	public PreferenceMapLabelProvider(FontDescriptor fontDescriptor, IObservableMap[] attributeMaps) {
		super(attributeMaps);
		Assert.isNotNull(fontDescriptor, "<fontDescriptor> must not be null");
		this.fontDescriptor = fontDescriptor;
	}

	@Override
	public String getColumnText(Object element, int columnIndex) {
		String columnText = super.getColumnText(element, columnIndex);
		if ("".equals(columnText) && element instanceof PreferenceEntry) {
			PreferenceEntry entry = (PreferenceEntry) element;
			switch (columnIndex) {
			case 1:
				columnText = entry.getKey();
				break;
			case 2:
				columnText = entry.getOldValue();
				break;
			case 3:
				columnText = entry.getNewValue();
				break;
			default:
				columnText = entry.getNodePath();
				break;
			}
		}
		return columnText;
	}

	@Override
	public Font getFont(Object element) {
		if (element instanceof PreferenceEntry && ((PreferenceEntry) element).isRecentlyChanged()) {
			return getResourceManager().createFont(fontDescriptor);
		}
		return null;
	}

	@Override
	public void dispose() {
		super.dispose();
		if (resourceManager != null) {
			resourceManager.dispose();
		}
	}

	protected ResourceManager getResourceManager() {
		if (null == resourceManager) {
			resourceManager = new LocalResourceManager(JFaceResources.getResources());
		}
		return resourceManager;
	}
}
