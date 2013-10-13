package org.mattpayne.util.cleaner;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.kohsuke.github.GHMyself;
import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GitHub;
import org.kohsuke.github.PagedIterable;

public class MainCleanup {

	public static void main(String[] arg) {
		try {
			new MainCleanup().run();
			System.out.println("Normal termination.");
		} catch (Exception bland) {
			bland.printStackTrace();
		}
	}

	private void run() throws IOException {
		@SuppressWarnings("unchecked")
		List<String> lstReposToDelete = FileUtils.readLines(new File(
				"repo_list.txt"), "UTF-8");
		deleteRepos(lstReposToDelete);
	}

	private void deleteRepos(List<String> lstReposToDelete) throws IOException {
		GitHub github = GitHub.connect();
		GHMyself me = github.getMyself();
		PagedIterable<GHRepository> lstRepos = me.listRepositories();
		int cntDeleted = 0;
		for (GHRepository repo : lstRepos) {
			String name = repo.getName();
			if (lstReposToDelete.contains(name)) {
				repo.delete();
				cntDeleted++;
				System.out.format("Deleted: %s\n", name);
			}
		}
		System.out.format("That's %d repositories deleted!\n",
				cntDeleted);
	}
}
