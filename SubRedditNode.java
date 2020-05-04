import java.util.ArrayList;
import java.util.List;

public class SubRedditNode {
	private List<String> linksToSubreddits = new ArrayList<>();
	private List<String> linksFromSubreddits = new ArrayList<>();
	private String subredditName;
	private int numOfOutlinks;
	private int numOfInLinks;
	
	SubRedditNode(String name, List<String> links){
		subredditName = name;
		LinksToOtherSubreddits(links);
	}
	
	public void setlinksFromSubreddits(List<String> internalList){
		linksFromSubreddits = internalList;
		numOfInLinks = internalList.size();
	}
	public void setnumOfInLinks(int i) {numOfInLinks = i;}
	
	public String getsubredditName() {return subredditName;}
	public List<String> getlinksToSubreddits(){return linksToSubreddits;}
	public int getnumOfInLinks() {return numOfInLinks;}
	
	public void addInLinkSubreddit(String s) {
		linksFromSubreddits.add(s);
	}

	private void LinksToOtherSubreddits(List<String> links) {
		String nextUrl = null;
		boolean isValidSubreddit;
		while(!links.isEmpty()) {
			do {
				isValidSubreddit = true;
	            if(!links.isEmpty()){nextUrl = links.remove(0);}else {break;}
	            if(nextUrl.length() < 25) {isValidSubreddit = false;}
	            else if(!nextUrl.substring(0, 25).equals("https://www.reddit.com/r/")){isValidSubreddit = false;}
	            else if(nextUrl.indexOf("/",25) == -1){isValidSubreddit = false;}
	            else if(subredditName.equals(nextUrl.substring(25,nextUrl.indexOf("/",25)))){isValidSubreddit = false;}
	            else {
	            	String Name = nextUrl.substring(25,nextUrl.indexOf("/",25));
	            	for(String subreddit : linksToSubreddits) {
			       		if(Name.equals(subreddit)) {isValidSubreddit = false;}
			       	}
	            }
	        } while (isValidSubreddit == false);
			if(!links.isEmpty()) {
				linksToSubreddits.add(nextUrl.substring(25, nextUrl.indexOf("/",25)));
				numOfOutlinks++;
			}
		}
	}
	
	public void printLinksToSubreddits() {
		System.out.println("There are "+numOfOutlinks+" subreddits that "+subredditName+" links to");
		if(numOfOutlinks != 0) {
			for(String subreddit : linksToSubreddits) {System.out.printf(subreddit+" ");}
		}
	}
	
	public void printLinksFromSubreddits() {
		System.out.println("There are "+numOfInLinks+" subreddits that link to "+subredditName);
		if(numOfInLinks != 0) {
			for(String subreddit : linksFromSubreddits) {System.out.printf(subreddit+" ");}
		}
	}
}
