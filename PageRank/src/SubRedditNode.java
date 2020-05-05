import java.util.ArrayList;
import java.util.List;

public class SubRedditNode implements Comparable<SubRedditNode> {
    //public String Url;
    //public final String BASE_URL = "https://www.reddit.com/r/";
    private List<String> linksToSubreddits = new ArrayList<>();
    private List<String> linksToSubredditsFiltered = new ArrayList<>();
    private List<String> linksFromSubreddits = new ArrayList<>();
    private double pageRank;
    private String subredditName;
    private int numOfOutlinks;
    private int numOfInLinks;

    SubRedditNode(String name, List<String> links) {
        subredditName = name;
        LinksToOtherSubreddits(links);
    }

    SubRedditNode(String name, double pageRank, int numOfOutlinks) {
        this.pageRank = pageRank;
        subredditName = name;
        this.numOfOutlinks = numOfOutlinks;
    }

    public void setlinksFromSubreddits(List<String> internalList) {
        linksFromSubreddits = internalList;
        numOfInLinks = internalList.size();
    }

    public void setnumOfInLinks(int i) {
        numOfInLinks = i;
    }

    public void setPageRank(double num) {
        pageRank = num;
    }

    public double getPageRank() {
        return pageRank;
    }

    public String getsubredditName() {
        return subredditName;
    }

    public List<String> getlinksToSubreddits() {
        return linksToSubreddits;
    }

    public int getnumOfInLinks() {
        return numOfInLinks;
    }

    public int getNumOutLinks() {
        return numOfOutlinks;
    }

    public String getFromName(int num) {
        return linksFromSubreddits.get(num);
    }

    public void addInLinkSubreddit(String s) {
        linksFromSubreddits.add(s);
    }

    public void filterLinksToSubreddits(List<String> visitedSubreddits) {
        for (String subreddit : visitedSubreddits) {
            for (String linksToSubreddit : linksToSubreddits) {
                if (linksToSubreddit.equals(subreddit)) {
                    linksToSubredditsFiltered.add(subreddit);
                    numOfOutlinks++;
                }
            }
        }
    }

    private void LinksToOtherSubreddits(List<String> links) {
        String nextUrl = null;
        boolean isValidSubreddit;
        while (!links.isEmpty()) {
            do {
                isValidSubreddit = true;
                if (!links.isEmpty()) {
                    nextUrl = links.remove(0);
                } else {
                    break;
                }
                if (nextUrl.length() < 25) {
                    isValidSubreddit = false;
                } else if (!nextUrl.startsWith("https://www.reddit.com/r/")) {
                    isValidSubreddit = false;
                } else if (nextUrl.indexOf("/", 25) == -1) {
                    isValidSubreddit = false;
                } else if (subredditName.equals(nextUrl.substring(25, nextUrl.indexOf("/", 25)))) {
                    isValidSubreddit = false;
                } else {
                    String Name = nextUrl.substring(25, nextUrl.indexOf("/", 25));
                    for (String subreddit : linksToSubreddits) {
                        if (Name.equals(subreddit)) {
                            isValidSubreddit = false;
                        }
                    }
                }
            } while (!isValidSubreddit);
            if (!links.isEmpty()) {
                String subredditName = nextUrl.substring(25, nextUrl.indexOf("/", 25));
                linksToSubreddits.add(subredditName);
            }
        }
    }

    public void printLinksToSubreddits() {
        System.out.println("There are " + numOfOutlinks + " subreddits that " + subredditName + " links to");
        if (numOfOutlinks != 0) {
            for (String subreddit : linksToSubredditsFiltered) {
                System.out.print(subreddit + " ");
            }
        }
    }

    public void printLinksFromSubreddits() {
        System.out.println("There are " + numOfInLinks + " subreddits that link to " + subredditName);
        if (numOfInLinks != 0) {
            for (String subreddit : linksFromSubreddits) {
                System.out.print(subreddit + " ");
            }
        }
    }

    @Override
    public int compareTo(SubRedditNode node) {
        if (this.pageRank >= node.pageRank) {
            return -1;
        }
        return 1;
    }
}
