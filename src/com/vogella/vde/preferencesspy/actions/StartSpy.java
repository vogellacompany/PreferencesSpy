package com.vogella.vde.preferencesspy.actions;

import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.core.runtime.preferences.IEclipsePreferences.IPreferenceChangeListener;
import org.eclipse.core.runtime.preferences.IEclipsePreferences.PreferenceChangeEvent;
import org.eclipse.core.runtime.preferences.IPreferenceNodeVisitor;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
import org.osgi.service.prefs.BackingStoreException;

import com.vogella.vde.preferencesspy.Activator;

/**
 * Our sample action implements workbench action delegate. The action proxy will
 * be created by the workbench and shown in the UI. When the user tries to use
 * the action, this delegate will be created and execution will be delegated to
 * it.
 * 
 * @see IWorkbenchWindowActionDelegate
 */
public class StartSpy implements IWorkbenchWindowActionDelegate {
	private IWorkbenchWindow window;

	/**
	 * The constructor.
	 */
	public StartSpy() {
	}

	/**
	 * The action has been activated. The argument of the method represents the
	 * 'real' action sitting in the workbench UI.
	 * 
	 * @see IWorkbenchWindowActionDelegate#run
	 */
	public void run(IAction action) {
		MessageDialog.openInformation(window.getShell(), "Preferencesspy",
				"Hello, Eclipse world");

		IPreferenceStore preferenceStore2 = Activator.getDefault()
				.getPreferenceStore();
		preferenceStore2
				.addPropertyChangeListener(new IPropertyChangeListener() {

					@Override
					public void propertyChange(PropertyChangeEvent event) {
						// TODO Auto-generated method stub
						System.out.println(event);
					}
				});
		IEclipsePreferences node = InstanceScope.INSTANCE.getNode("");

		try {
			node.accept(new IPreferenceNodeVisitor() {

				@Override
				public boolean visit(IEclipsePreferences node)
						throws BackingStoreException {
					node.addPreferenceChangeListener(new IPreferenceChangeListener() {

						@Override
						public void preferenceChange(PreferenceChangeEvent event) {
							// TODO Auto-generated method stub
							System.out.println("Key:"
									+ event.getNode().absolutePath()
									+ event.getKey() + "Value:"
									+ event.getNewValue());
							
							MessageDialog.openInformation(window.getShell(), "Preferences Spy",
									"Key:"
											+ event.getNode().absolutePath()
											+ event.getKey() + "Value:"
											+ event.getNewValue());
						}
					});
					return true;
				}
			});
		} catch (BackingStoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Selection in the workbench has been changed. We can change the state of
	 * the 'real' action here if we want, but this can only happen after the
	 * delegate has been created.
	 * 
	 * @see IWorkbenchWindowActionDelegate#selectionChanged
	 */
	public void selectionChanged(IAction action, ISelection selection) {
	}

	/**
	 * We can use this method to dispose of any system resources we previously
	 * allocated.
	 * 
	 * @see IWorkbenchWindowActionDelegate#dispose
	 */
	public void dispose() {
	}

	/**
	 * We will cache window object in order to be able to provide parent shell
	 * for the message dialog.
	 * 
	 * @see IWorkbenchWindowActionDelegate#init
	 */
	public void init(IWorkbenchWindow window) {
		this.window = window;
	}
}