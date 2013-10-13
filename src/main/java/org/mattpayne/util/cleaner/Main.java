package org.mattpayne.util.cleaner;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Map;

import org.kohsuke.github.GHMyself;
import org.kohsuke.github.GHOrganization;
import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GitHub;
import org.kohsuke.github.PagedIterable;

public class Main {

	public static void main(String[] arg) {
		try {
			new Main().run();
			System.out.println("Normal termination.");
		} catch (Exception bland) {
			bland.printStackTrace();
		}
	}

	private void run() throws IOException {
		GitHub github = GitHub.connect();
		Map<String, GHOrganization> orgs = github.getMyOrganizations();
		System.out.println(orgs);
		GHMyself me = github.getMyself();
		PagedIterable<GHRepository> lstRepos = me.listRepositories();
		// TODO:mgp: Figure out what I am missing here.
		int counterSinceThereSeemsToBeNoSizeMethodOnPagedIterable = 0;
		GHRepository repoToDelete = null;
		PrintWriter outCloneList = new PrintWriter("clone_these.sh");
		PrintWriter outRepoList = new PrintWriter("repo_list.txt");
		for (GHRepository repo : lstRepos) {
			String name = repo.getName();
			Date pushedAt = repo.getPushedAt();
			String readOnlyUrl = repo.getGitTransportUrl();
			outRepoList.format("%s\n", name);
			outCloneList.println(readOnlyUrl);
			counterSinceThereSeemsToBeNoSizeMethodOnPagedIterable++;
			if (name.equals("c-style")) {
				repoToDelete = repo;
				System.out.println("Well?");
			}
		}
		outCloneList.close();
		outRepoList.close();
		System.out.format("That's %d repositories!\n",
				counterSinceThereSeemsToBeNoSizeMethodOnPagedIterable);
		if (null != repoToDelete) {
			repoToDelete.delete();
		}
	}
}
