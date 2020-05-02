
import java.util.Collections;
import java.util.List;

public class PageRank {
	
	public PageRank(List<SubRedditNode> list) {
		calculatePageRank(list);
	}
	
    public List<SubRedditNode> calculatePageRank(List<SubRedditNode> list) { 

    	float initialRank = 1 / list.size();
    	for(int i = 0; i < list.size(); i++) {
    		list.get(i).setPageRank(initialRank);
    	}
    	
    	int iterations = 2;
    	
    	while (iterations >= 0) {
    		
    		for (int i = 0; i < list.size(); i++) {
    			if (list.get(i).getnumOfInLinks() != 0) {
    				float tempRank = 0;
    				for (int j = 0; j < list.get(i).getnumOfInLinks(); j++) {
    					String name = list.get(i).getFromName(j);
    					tempRank += calculate(list, name);
    				}
    				list.get(i).setPageRank(tempRank);
    			}
    		}
    		iterations--;
    	}
    	
    	Collections.sort(list);
    	
    	return list;
    }
    
    
    private float calculate(List<SubRedditNode> list, String name) {
    	for (int i = 0; i < list.size(); i++) {
    		if (name.equals(list.get(i).getsubredditName())) {
    			int in = list.get(i).getnumOfInLinks();
    			int out = list.get(i).getNumOutLinks();
    			return (float) in / out;
    		}
    	}
    	return -1;
    }

    public void printRanks(List<SubRedditNode> list) {
    	for (int i = 0; i < list.size(); i++) {
    		System.out.println(list.size() - i + " " + list.get(i).getsubredditName());
    	}
    }
}