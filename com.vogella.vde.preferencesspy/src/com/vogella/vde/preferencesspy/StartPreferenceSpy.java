package com.vogella.vde.preferencesspy;

import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.core.runtime.preferences.IEclipsePreferences.IPreferenceChangeListener;
import org.eclipse.core.runtime.preferences.IEclipsePreferences.PreferenceChangeEvent;
import org.eclipse.core.runtime.preferences.IPreferenceNodeVisitor;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.ui.IStartup;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.osgi.service.prefs.BackingStoreException;

import com.vogella.vde.preferencesspy.preferencepage.PreferenceConstants;

/**
 * StartPreferenceSpy is registered via the org.eclipse.ui.startup extension
 * point and registers
 * 
 */
public class StartPreferenceSpy implements IStartup {

	private final class ChangedPreferenceListener implements
			IPreferenceChangeListener {
		
		@Override
		public void preferenceChange(PreferenceChangeEvent event) {
			System.out.println("Key:" + event.getNode().absolutePath()
					+ event.getKey() + "Value:" + event.getNewValue());
			IWorkbenchWindow window = PlatformUI.getWorkbench()
					.getActiveWorkbenchWindow();
			String preferenceKey = "Key:" + event.getNode().absolutePath() + event.getKey();
			String preferenceValue = "Value:" + event.getNewValue();
			PreferencePopupDialog dialog = new PreferencePopupDialog(window.getShell(), "Preference Spy", "" );
			dialog.setText(preferenceKey + preferenceValue);
			dialog.open();
		}
	}

	private IEclipsePreferences node;
	private ChangedPreferenceListener preferenceChangedListener = new ChangedPreferenceListener();

	@Override
	public void earlyStartup() {
		node = InstanceScope.INSTANCE.getNode("");
		IPreferenceStore preferenceStore = Activator.getDefault()
				.getPreferenceStore();

		preferenceStore
				.addPropertyChangeListener(new IPropertyChangeListener() {
					@Override
					public void propertyChange(PropertyChangeEvent event) {
						if (event.getProperty() == PreferenceConstants.P_BOOLEAN) {
							Boolean value = (Boolean) event.getNewValue();
							if (value) {
								registerVisitor();
							} else {
								deregisterVisitor();
							}
						}
					}

				});

	}

	private void deregisterVisitor() {
		try {
			node.accept(new IPreferenceNodeVisitor() {
				@Override
				public boolean visit(IEclipsePreferences node)
						throws BackingStoreException {
					node.removePreferenceChangeListener(preferenceChangedListener);
					return true;
				}
			});
		} catch (BackingStoreException e) {
			e.printStackTrace();
		}

	}

	private void registerVisitor() {
		try {
			node.accept(new IPreferenceNodeVisitor() {
				@Override
				public boolean visit(IEclipsePreferences node)
						throws BackingStoreException {
					node.addPreferenceChangeListener(preferenceChangedListener);
					return true;
				}
			});
		} catch (BackingStoreException e) {
			e.printStackTrace();
		}

	}
}