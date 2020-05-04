import java.util.Scanner;

/**
 * Authors:
 * Rachel Lewis
 * Samuel Belarmino
 * Josh Woolbright
 * Daniel Santana
 * Auraiporn Auksorn
 * <p>
 * Description:
 * The project crawls a number of specified SubReddits
 * given a root SubReddit & calculates the PageRank algorithm
 * then prints the ordered list of the PageRank.
 */

public class CrawlerTest {

    public static void main(String[] args) {
        int maxPages;
        final String SubRedditName;
        Scanner sc = new Scanner(System.in);
        if (args.length >= 1) {
            maxPages = Integer.parseInt(args[0]);
            SubRedditName = args[1];
            System.out.printf("Number of sites to crawl: %d %n", maxPages);
            System.out.printf("Root reddit crawling: %s %n", SubRedditName);
        } else {
            System.out.println("Enter the number of max sites you would like to crawl: ");
            maxPages = sc.nextInt();
            System.out.println("Enter the the name of the SubReddit you would like to crawl: ");
            SubRedditName = sc.next();
        }
        sc.close();
        Crawler crawler = new Crawler(maxPages);

        String searchSite = "https://www.reddit.com/r/" + SubRedditName + "/";

        crawler.search(searchSite);
        crawler.printPageRank();
    }

}
