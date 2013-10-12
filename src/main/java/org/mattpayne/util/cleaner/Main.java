package org.mattpayne.util.cleaner;

import java.io.IOException;
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
		int counterSinceThereSeemsToBeNoSizeMethodOnPagedIterable=0;
		for (GHRepository repo: lstRepos) {
			System.out.println(repo.getName());
			counterSinceThereSeemsToBeNoSizeMethodOnPagedIterable++;
		}

		System.out.format("That's %d repositories!\n", counterSinceThereSeemsToBeNoSizeMethodOnPagedIterable);
	}
}
