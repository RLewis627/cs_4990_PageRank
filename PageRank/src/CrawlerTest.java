import java.util.Scanner;
public class CrawlerTest {

	public static void main(String[] args) {
        int maxPages;
        String subredditName;
        Scanner sc = new Scanner(System.in);
        if (args.length >= 1) {
            maxPages = Integer.parseInt(args[0].substring(1, 3));
            subredditName = args[1];
            System.out.println("number of sites: " + maxPages);
        } else {
            System.out.println("Enter the number of max sites you would like to crawl: ");
            maxPages = sc.nextInt();
            System.out.println("Enter the the name of the subreddit you would like to crawl: ");
            subredditName = sc.next();
        }
        sc.close();
        Crawler crawler = new Crawler(maxPages);
        
        String searchSite = "https://www.reddit.com/r/"+subredditName+"/";

        crawler.search(searchSite);
	crawler.printPageRank();
	}

}
