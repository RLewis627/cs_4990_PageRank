import java.io.*;
import java.util.*;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Authors: Rachel Lewis
 *          Samuel Belarmino
 *          Josh Woolbright
 *          Auraiporn Auksorn
 *          Daniel Santana
 */

public class Crawler {
    private final int maxPagesToVisit;
    private int pagesNotVisited;
    private List<String> pagesVisited = new LinkedList<>();
    private List<String> pagesToVisit = new LinkedList<>();
    private List<String> links = new LinkedList<>();
    private static final String WEB_BROWSER = "Chrome-Chrome OS";
    private ArrayList<String> listOfSubreddits;
    private static ArrayList<SubRedditNode> workingSetOfNodes;

    public Crawler(int max) {
        this.pagesNotVisited = 0;
        this.maxPagesToVisit = max;
    }

    public void search(String url) {
        listOfSubreddits = new ArrayList<>();
        workingSetOfNodes = new ArrayList<>();
        String currentUrl;
        int i = 1;
        boolean zeroSubs = false;
        while ((listOfSubreddits.size() < maxPagesToVisit) && (!zeroSubs)) {

            //Sleep the main thread for 1 second
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                System.out.println("The main thread was interrupted!");
                System.exit(1);
            }

            if (pagesToVisit.isEmpty()) {
                currentUrl = url;
            } else {
                currentUrl = this.nextUrl();
            }

            int index = currentUrl.indexOf("/", 25);
            String subredditName = currentUrl.substring(25, index);
            listOfSubreddits.add(subredditName);

            //visit & crawl the site
            crawl(i, currentUrl.substring(0, index + 1));
            this.pagesToVisit.addAll(getLinks());
            workingSetOfNodes.add(new SubRedditNode(subredditName, getLinks()));
            i++;
            if (pagesToVisit.get(0).equals("https://www.reddit.com/") && i > 2) { // Added
                zeroSubs = true;
            }
        }

        calculateInLinks(workingSetOfNodes);

        if (pagesNotVisited > 0) {
            System.out.println("\nThe crawler was unable to visit " + pagesNotVisited + " link(s) due to not being a valid subreddit.");
            System.out.println("The visited links are: " + Arrays.toString(listOfSubreddits.toArray()) + "\n");
        }

        for (SubRedditNode e : workingSetOfNodes) {
            e.printLinksToSubreddits();
            System.out.println("\n");
        }

        System.out.println("** Links FROM Subreddits **\n");

        for (SubRedditNode e : workingSetOfNodes) {
            e.printLinksFromSubreddits();
            System.out.println("\n");
        }
    }

    private void calculateInLinks(ArrayList<SubRedditNode> workingSetOfNodes2) {
        for (SubRedditNode node : workingSetOfNodes2) {
            node.filterLinksToSubreddits(listOfSubreddits);
            for (String s : node.getlinksToSubreddits()) {
                for (SubRedditNode node2 : workingSetOfNodes2) {
                    if (node2.getsubredditName().equals(s)) {
                        node2.setnumOfInLinks(node2.getnumOfInLinks() + 1);
                        node2.addInLinkSubreddit(node.getsubredditName());
                    }
                }
            }
        }
    }

    private String nextUrl() {
        String nextUrl;
        boolean canCrawl;
        do {
            canCrawl = true;
            nextUrl = this.pagesToVisit.remove(0);
            if (nextUrl.length() < 25) {
                canCrawl = false;
                pagesNotVisited++;
            } else if (!nextUrl.startsWith("https://www.reddit.com/r/")) {
                canCrawl = false;
                pagesNotVisited++;
            } else {
                int index = nextUrl.indexOf("/", 25);
                if (index == -1) {
                    canCrawl = false;
                    pagesNotVisited++;
                } else {
                    String subredditName = nextUrl.substring(25, index);
                    for (String subreddit : listOfSubreddits) {
                        if (subredditName.equals(subreddit)) {
                            canCrawl = false;
                            pagesNotVisited++;
                        }
                    }
                }
            }
        } while (!canCrawl && !pagesToVisit.isEmpty());
        this.pagesVisited.add(nextUrl);
        return nextUrl;
    }

    public void crawl(int siteNumber, String url) // Makes an HTTP request for a given url
    {
        try {
            Connection connection = Jsoup.connect(url).userAgent(WEB_BROWSER);
            Document htmlDocument = connection.get();
            if (connection.response().statusCode() == 200) {
                System.out.println("\n(" + siteNumber + ") Visiting " + url);
            }
            if (!connection.response().contentType().contains("text/html")) {
                System.out.println("Not a valid HTML file");
                pagesNotVisited++;
            }

            Elements linksOnPage = htmlDocument.select("a[href]");

            for (Element link : linksOnPage) {
                this.links.add(link.absUrl("href"));
            }
        } catch (IOException e) {
            System.out.println("Error in out HTTP request " + e);
        } catch (IllegalArgumentException e) {
            System.out.println("Error: Invalid URL");
        }
    }

    public void printPageRank() {
        PageRank rank = new PageRank(workingSetOfNodes);
        rank.printRanks(workingSetOfNodes);
    }

    public List<String> getLinks() // Returns a list of all the URLs on the page
    {
        return this.links;
    }
}
