package com.stefanski.booksearch.bgjobs;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.stefanski.booksearch.Launcher;
import com.stefanski.booksearch.dao.WebLinksDao;
import com.stefanski.booksearch.entities.WebLink;
import com.stefanski.booksearch.util.HttpConnect;
import com.stefanski.booksearch.util.IOUtil;

/**
 * Multithread web page Downloader. With thread-per-task approach (Executor
 * freamowork).
 * 
 * @author Frederik Stefanski
 *
 */
public class WebpageDownloaderTask implements Runnable {

	private final static Logger LOGGER = Logger.getLogger(Launcher.class.getName());

	private static WebLinksDao webLinksDao = new WebLinksDao();
	private static final long TIME_FRAME = 3; // 3 second - time limit on downloaded process
	private static final int THREAD_POOL_SIZE = 10;
	private boolean downloadAll = false;

	// Executors -> thread-per-task
	private ExecutorService downloadExecutor = Executors.newFixedThreadPool(THREAD_POOL_SIZE);

	/**
	 * Static nested class. Generic implementation of Callable Downloader.
	 * 
	 * @author Frederik Stefanski
	 *
	 * @param <T>
	 *            -> Weblink - downloader dedicated to download web pages
	 */
	private class Downloader<T extends WebLink> implements Callable<T> {
		private T webLink;

		public Downloader(T weblink) {
			this.webLink = weblink;
		}

		@Override
		public T call() {
			try {
				// Download web page under url
				webLink.setDownloadStatus(WebLink.DownloadStatus.FAILED);
				String webPage = HttpConnect.download(webLink.getUrl());
				webLink.setWebPage(webPage);

				if (webPage != null) {

					IOUtil.write(webPage, webLink); // file name = bookmark_Id
					webLink.setDownloadStatus(WebLink.DownloadStatus.SUCCESS); // web page download success

					System.out.println("\t<> Download success, id: " + webLink.getId());
					System.out.println("\t\t--Task --> " + Thread.currentThread().getId());
					System.out.println("\t\t--Thread.activeCount(): " + Thread.activeCount());
				}
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (URISyntaxException e) {
				e.printStackTrace();
			}
			return webLink;
		}
	}

	/**
	 * Get all web links from data store via WebLinksDao if the downloadAll flag is
	 * true.
	 * 
	 * @param downloadAll
	 */
	public WebpageDownloaderTask(boolean downloadAll) {
		this.downloadAll = downloadAll;
	}

	/**
	 * Implements Runnable interface
	 */
	@Override
	public void run() {

		while (!Thread.currentThread().isInterrupted()) {

			System.out.println("\n>> Multithread web page download\t--START");
			LOGGER.log(Level.FINE, "Multithread web page download\t--START");

			// Get web links
			List<WebLink> webLinks = getWebLinks();
			System.out.println("\t<> Amount of links to download: " + webLinks.size());
			LOGGER.log(Level.FINER, "<> Amount of links to download: " + webLinks.size());

			// Download concurrently
			if (webLinks.size() > 0) {
				download(webLinks);
			} else {
				Thread.currentThread().interrupt();
			}

			// Waits for n seconds and continue an new set of web links, if added
			// try {
			// System.out.println("\t<> Waiting for new set of web links ...");
			// TimeUnit.SECONDS.sleep(3);
			// } catch (InterruptedException e) {
			// e.printStackTrace();
			// }
		}

		downloadExecutor.shutdown();
		while (!downloadExecutor.isTerminated()) {
		}
		System.out.println("\n\t<> No new Web Links to download!");
		System.out.println("\t<> downloadExecutor.shutdown()");
		System.out.println("\t<> Waiting for all Executors to terminate ...");
		System.out.println("\t\t--Thread.activeCount(): " + Thread.activeCount());
		System.out.println("\t\t--downloadExecutor.isShutdown(): " + downloadExecutor.isShutdown());
		System.out.println("\t\t--downloadExecutor.isTerminated(): " + downloadExecutor.isTerminated());
		System.out.println(">> Multithread web page download\t--END");

		LOGGER.log(Level.FINER, "No new Web Links to download!");
		LOGGER.log(Level.FINER, "downloadExecutor.shutdown()");
		LOGGER.log(Level.FINER, "Waiting for all Executors to terminate ...");
		LOGGER.log(Level.FINEST, "--Thread.activeCount(): " + Thread.activeCount());
		LOGGER.log(Level.FINEST, "--downloadExecutor.isShutdown(): " + downloadExecutor.isShutdown());
		LOGGER.log(Level.FINEST, "--downloadExecutor.isTerminated(): " + downloadExecutor.isTerminated());
		LOGGER.log(Level.FINE, "Multithread web page download\t--END");
	}

	/**
	 * Web page downloader with: - invoke all, - time executor,
	 * 
	 * @param webLinks
	 */
	private void download(List<WebLink> webLinks) {
		List<Downloader<WebLink>> tasks = getTask(webLinks);
		List<Future<WebLink>> futures = new ArrayList<>();

		try {
			futures = downloadExecutor.invokeAll(tasks, TIME_FRAME, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (RejectedExecutionException e) {
			e.printStackTrace();
		}

		for (Future<WebLink> future : futures) {
			try {
				if (!future.isCancelled()) {
					future.get(); // blocks until task completes
				} else {
					LOGGER.log(Level.FINER, "Task is cancelled");
					// System.out.println("\t<> Task is cancelled");
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Get a List of Downloader tasks.
	 * 
	 * @param WebLink
	 *            List
	 * @return Downloader List
	 */
	private List<Downloader<WebLink>> getTask(List<WebLink> webLinks) {
		List<Downloader<WebLink>> tasks = new ArrayList<>();

		for (WebLink webLink : webLinks) {
			tasks.add(new Downloader<WebLink>(webLink));
		}
		return tasks;
	}

	/**
	 * Get all web links from data store via WebLinksDao if the downloadAll flag is
	 * true. If any web links are added in the future download only the new one -
	 * downloadAll = false;
	 * 
	 * @return WebLink List
	 */
	private List<WebLink> getWebLinks() {
		List<WebLink> webLinks = null;

		if (downloadAll) {
			webLinks = webLinksDao.getAllWebLinks();
			downloadAll = false; // don't download again the same web links after first cycle of downloading is
									// done
		} else {
			// get only not attempted web links
			webLinks = webLinksDao.getWeblinks(WebLink.DownloadStatus.NOT_ATTEMPTED);
		}
		return webLinks;
	}

	public static WebLinksDao getWebLinksDao() {
		return webLinksDao;
	}

	public static void setWebLinksDao(WebLinksDao webLinksDao) {
		WebpageDownloaderTask.webLinksDao = webLinksDao;
	}

	public boolean isDownloadAll() {
		return downloadAll;
	}

	public void setDownloadAll(boolean downloadAll) {
		this.downloadAll = downloadAll;
	}

	public static long getTimeFrame() {
		return TIME_FRAME;
	}

	public static int getThreadPoolSize() {
		return THREAD_POOL_SIZE;
	}

	public ExecutorService getDownloadExecutor() {
		return downloadExecutor;
	}
}
