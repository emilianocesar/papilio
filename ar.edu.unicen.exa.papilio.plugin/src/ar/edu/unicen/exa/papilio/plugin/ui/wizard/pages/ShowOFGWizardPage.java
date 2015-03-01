package ar.edu.unicen.exa.papilio.plugin.ui.wizard.pages;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.layout.TreeColumnLayout;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Tree;

import ar.edu.unicen.exa.papilio.core.main.PapilioMain;
import ar.edu.unicen.exa.papilio.core.ofg.OFGGraph;
import ar.edu.unicen.exa.papilio.core.ofg.element.OFGEdge;
import ar.edu.unicen.exa.papilio.core.ofg.element.OFGNode;
import ar.edu.unicen.exa.papilio.plugin.ui.provider.ofg.OFGContentProvider;
import ar.edu.unicen.exa.papilio.plugin.ui.provider.ofg.OFGSimpleContentProvider;

public class ShowOFGWizardPage extends WizardPage {
	private TreeViewer ofgTreeViewer;
	private TreeViewer outNodesTreeViewer;
	private TreeViewer inNodesTreeViewer;
	private PapilioMain papilioMain;

	public ShowOFGWizardPage(String string, PapilioMain papilioMain) {
		super(string);
		setTitle("OFG Graph");
		setPageComplete(false);
		this.papilioMain = papilioMain;
	}

	@Override
	public void createControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NULL);
		setControl(container);

		container.setLayout(new FillLayout(SWT.HORIZONTAL));

		Composite ofgComposite = new Composite(container, SWT.NONE);
		ofgComposite.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		Composite composite = new Composite(ofgComposite, SWT.NONE);
		TreeColumnLayout tcl_composite = new TreeColumnLayout();
		composite.setLayout(tcl_composite);
		
		ofgTreeViewer = new TreeViewer(composite, SWT.BORDER);
		Tree nodesTree = ofgTreeViewer.getTree();
		nodesTree.setToolTipText("OFG Nodes");
		nodesTree.setHeaderVisible(true);
		nodesTree.setLinesVisible(true);
		ofgTreeViewer.addSelectionChangedListener(new ISelectionChangedListener() {
			
			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				if (event.getSelection() instanceof IStructuredSelection) {
					IStructuredSelection selection = (IStructuredSelection) event.getSelection();
					Object firstElement = selection.getFirstElement();
					if (!(firstElement instanceof OFGNode)) {
						return;
					}
					OFGNode node = (OFGNode)firstElement;
					List<OFGNode> out = new ArrayList<OFGNode>();
					for (OFGEdge edges : node.getEdges()) {
						out.add(edges.getTargetNode());
					}
					outNodesTreeViewer.setInput(out);
					outNodesTreeViewer.refresh();
					List<OFGNode> in = new ArrayList<OFGNode>();
					for (OFGEdge edges : node.getPredecesors()) {
						in.add(edges.getSourceNode());
					}
					inNodesTreeViewer.setInput(in);
					inNodesTreeViewer.refresh();
				}
			}
		});
		
		Composite nodeEdgesComposite = new Composite(ofgComposite, SWT.NONE);
		nodeEdgesComposite.setLayout(new FillLayout(SWT.VERTICAL));
		
		Composite outNodesComposite = new Composite(nodeEdgesComposite, SWT.NONE);
		outNodesComposite.setLayout(new TreeColumnLayout());
		
		outNodesTreeViewer = new TreeViewer(outNodesComposite, SWT.BORDER);
		outNodesTreeViewer.setExpandPreCheckFilters(true);
		Tree outNodesTree = outNodesTreeViewer.getTree();
		outNodesTree.setLinesVisible(true);
		outNodesTree.setHeaderVisible(true);
		
		Composite inNodesComposite = new Composite(nodeEdgesComposite, SWT.NONE);
		inNodesComposite.setLayout(new TreeColumnLayout());
		
		inNodesTreeViewer = new TreeViewer(inNodesComposite, SWT.BORDER);
		Tree inNodesTree = inNodesTreeViewer.getTree();
		inNodesTree.setHeaderVisible(true);
		inNodesTree.setLinesVisible(true);
		
		ofgTreeViewer.setContentProvider(new OFGContentProvider());
		ofgTreeViewer.addDoubleClickListener(new IDoubleClickListener() {
			
			@Override
			public void doubleClick(DoubleClickEvent event) {
				final ISelection selection = event.getSelection();
				Display.getDefault().asyncExec(new Runnable() {

					@Override
					public void run() {
						ofgTreeViewer.setSelection(selection, true);
					}
				});	
			}
		});
		inNodesTreeViewer.setContentProvider(new OFGSimpleContentProvider());
		outNodesTreeViewer.setContentProvider(new OFGSimpleContentProvider());

	}

	@Override
	public void setVisible(boolean visible) {
		super.setVisible(visible);
		if (visible)
			onEnterPage();
	}

	private void onEnterPage() {
		Job job = new Job("Generating Abstract Syntax") {

			@Override
			protected IStatus run(IProgressMonitor monitor) {
				ShowOFGWizardPage.this.papilioMain.generateOFG(monitor, new PapilioMain.GenerateOFGGraphListener() {
					
					@Override
					public void onOFGGenerated(final OFGGraph ofg) {
						Display.getDefault().asyncExec(new Runnable() {

							@Override
							public void run() {
								ofgTreeViewer.setInput(ofg);
							}
						});						
					}
				});
				return Status.OK_STATUS;
			}

		};
		job.schedule();
		setPageComplete(true);
		getContainer().updateButtons();
	}
}
