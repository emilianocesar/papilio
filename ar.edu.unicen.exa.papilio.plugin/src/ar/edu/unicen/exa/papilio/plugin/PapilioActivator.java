package ar.edu.unicen.exa.papilio.plugin;

import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle
 */
public class PapilioActivator extends AbstractUIPlugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "ar.edu.unicen.exa.papilio.plugin"; //$NON-NLS-1$

	// The shared instance
	private static PapilioActivator plugin;

	/**
	 * The constructor
	 */
	public PapilioActivator() {
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
		plugin = null;
		super.stop(context);
	}

	/**
	 * Returns the shared instance
	 *
	 * @return the shared instance
	 */
	public static PapilioActivator getDefault() {
		return plugin;
	}
}
