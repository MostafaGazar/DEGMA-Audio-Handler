/*
    Copyright (C) 2005 - 2007 Mikael Högdahl - dronten@gmail.com

    This library is free software; you can redistribute it and/or
    modify it under the terms of the GNU Lesser BaseSetupPanel Public
    License as published by the Free Software Foundation; either
    version 2.1 of the License, or (at your option) any later version.

    This library is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
    Lesser BaseSetupPanel Public License for more details.

    You should have received a copy of the GNU Lesser BaseSetupPanel Public
    License along with this library; if not, write to the Free Software
    Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 */

package RipConvertShared.UI;

import RipConvertShared.thread.BaseThread;
import RipConvertShared.util.Progress;
import RipConvertShared.util.StopWatch;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;

/**
 * A progress dialog object.
 */
public class ProgressDialog extends BaseDialog implements ActionListener {
	/**
	 * A TimerTask object that checks the thread if it has stopped running. And
	 * also update the dialoger progress component.
	 */
	final class ThreadTask extends TimerTask {
		private final Vector<BaseThread> aThreads;
		private StopWatch aWatch = new StopWatch();

		/**
		 * @param thread
		 *            Worker thread
		 */
		public ThreadTask(BaseThread thread) {
			aThreads = new Vector<BaseThread>();
			aThreads.add(thread);
			aThreads.get(0).start();
			aWatch.start();
		}

		/**
		 * @param threads
		 *            Worker threads
		 */
		public ThreadTask(Vector<BaseThread> threads) {
			aThreads = threads;
			aThreads.get(0).start();
		}

		/**
		 * @return Current work thread
		 */
		public BaseThread getThread() {
			return aThreads.get(0);
		}

		/**
		 * Timer callback method. If thread has stopped then close modal dialog
		 * window.
		 */
		public void run() {
			synchronized (aThreads) {
				if (aThreads.get(0).isAlive() == false) {
					if (aThreads.get(0).hasFailed()) {
						aHasFailed = true;
						cancel();
						cancelTask();
						aWatch.stop();
					} else if (aThreads.size() > 1) {
						aThreads.remove(0);
						Progress.get().nextWorkTask();
						aThreads.get(0).start();
					} else {
						cancel();
						cancelTask();
						aWatch.stop();
					}
				} else {
					if (aMajorProgress.isVisible()) {
						setMinorProgress(Progress.get().getMinorProgress(),
								Progress.get().getMinorMessage());
						setMajorProgress(Progress.get().getMajorProgress(),
								Progress.get().getMajorMessage());
					} else {
						setMinorProgress(Progress.get().getMajorProgress(),
								Progress.get().getMinorMessage());
						setMajorProgress(Progress.get().getMajorProgress(),
								Progress.get().getMajorMessage());
					}
					setTotalProgress(Progress.get().getMajorProgress());
				}
			}
		}

		/**
		 * Check if task and threads is working.
		 * 
		 * @return true if they are
		 */
		public boolean isWorking() {
			synchronized (aThreads) {
				return aThreads.get(0) != null && aThreads.get(0).isAlive();
			}
		}
	}

	private static final long serialVersionUID = 666L;
	private JProgressBar aMinorProgress = null;
	private JProgressBar aMajorProgress = null;
	private ThreadTask aTask = null;
	// private Application aApp = null;
	private String aTitle = "";
	private boolean aHasStopped = false;
	private boolean aHasFailed = false;

	/**
	 * Create log window.
	 * 
	 * @param owner
	 *            - Set to null to create a window that can hide below
	 *            application.
	 * @param title
	 * @param close_text
	 * @param modal
	 * @throws java.awt.HeadlessException
	 */
	public ProgressDialog(JFrame owner, String title, String close_text,
			boolean modal) {
		super(owner, title, modal);

		this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

		aTitle = title;
		JButton close = ComponentFactory.createButton(close_text, "", this, 0,
				0);
		aMinorProgress = new JProgressBar();
		aMajorProgress = new JProgressBar();
		JPanel progressPanel = new JPanel();
		JPanel mainPanel = new JPanel();

		aMinorProgress.setMaximum(100);
		aMinorProgress.setPreferredSize(new Dimension(600, 25));
		aMinorProgress.setIndeterminate(true);
		aMajorProgress.setMaximum(100);
		aMajorProgress.setPreferredSize(new Dimension(600, 25));
		mainPanel
				.setMaximumSize(new Dimension(Short.MAX_VALUE, Short.MAX_VALUE));

		mainPanel.setLayout(ComponentFactory.createBorderLayout(5, 5));
		mainPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		progressPanel.setLayout(ComponentFactory.createBorderLayout(5, 5));
		progressPanel.add(aMajorProgress, BorderLayout.NORTH);
		progressPanel.add(aMinorProgress, BorderLayout.SOUTH);
		mainPanel.add(progressPanel, BorderLayout.NORTH);
		mainPanel.add(close, BorderLayout.SOUTH);

		aMajorProgress.setVisible(false);
		setContentPane(mainPanel);
		pack();
	}

	/**
	 * Cancel work thread and close dialog.
	 * 
	 * @param actionEvent
	 */
	public void actionPerformed(ActionEvent actionEvent) {
		close();
	}

	/**
	 * Cancel timer task.
	 */
	public void cancelTask() {
		if (aTask != null) {
			aTask.cancel();
			for (BaseThread thread : aTask.aThreads) {
				thread.close();
			}
		}

		dispose();
		setVisible(false);

		/*
		 * if (aApp != null) { aApp.setTitle(null); }
		 */
	}

	/**
	 * Cancel work thread and close dialog.
	 */
	public void close() {
		cancelTask();
		aHasStopped = true;
		if (isVisible()) {
			setVisible(false);
		}
	}

	/**
	 * Check if task and threads is working.
	 * 
	 * @return true if a thread is working
	 */
	public boolean isWorking() {
		return aTask != null && aTask.isWorking();
	}

	/**
	 * Check if user has pushed cancel/close button.
	 * 
	 * @return true if canceled
	 */
	public boolean hasBeenStopped() {
		return aHasStopped;
	}

	/**
	 * Check if last running htread has failed.
	 * 
	 * @return true if failed
	 */
	public boolean hasFailed() {
		return aHasFailed;
	}

	// /**
	// * Set application object for adding total % numer to the title.
	// *
	// * @param app
	// */
	// public void setApplication(Application app) {
	// aApp = app;
	// }

	/**
	 * Set progress value.
	 * 
	 * @param percent
	 *            - A number between 0 and 100
	 * @param text
	 *            - Message to paint in the background of progress bar
	 */
	private void setMinorProgress(int percent, String text) {
		aMinorProgress.setValue(percent);
		aMinorProgress.setString(text);
		aMinorProgress.setStringPainted(true);
	}

	/**
	 * Set progress value.
	 * 
	 * @param percent
	 *            - A number between 0 and 100
	 * @param text
	 *            - Message to paint in the background of progress bar
	 */
	private void setMajorProgress(final int percent, final String text) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				aMajorProgress.setValue(percent);
				aMajorProgress.setString(text);
				aMajorProgress.setStringPainted(true);
			}
		});
		// aMajorProgress.setValue(percent);
		// aMajorProgress.setString(text);
		// aMajorProgress.setStringPainted(true);
	}

	/**
	 * Set progress value.
	 * 
	 * @param percent
	 *            - A number between 0 and 100
	 */
	private void setTotalProgress(int percent) {
		if (percent > 0) {
			/*
			 * if (aApp != null) { aApp.setTitle(String.format(" - %d%%",
			 * percent)); }
			 */
			setTitle(String.format("%s - %d%%", aTitle, percent));
		} else {
			/*
			 * if (aApp != null) { aApp.setTitle(""); }
			 */
			setTitle(String.format("%s", aTitle));
		}
	}

	/**
	 * Show dialog and start timer task.
	 * 
	 * @param thread
	 */
	public void show(BaseThread thread) {
		Timer timer = new Timer();
		aTask = new ThreadTask(thread);

		timer.schedule(aTask, 200, 100);
		setVisible(true);
	}

	/**
	 * Show dialog and start timer task.
	 * 
	 * @param threads
	 */
	public void show(Vector<BaseThread> threads) {
		Timer timer = new Timer();
		aTask = new ThreadTask(threads);

		timer.schedule(aTask, 200, 100);
		setVisible(true);
	}

	/**
	 * Show second progress bar. Should run the total work progress while the
	 * progressbar 1 shows the smaller work load.
	 */
	public void showMajorProgress() {
		aMajorProgress.setVisible(true);
		pack();
	}
}