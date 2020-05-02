## Compile:
    javac -cp PageRank\lib\*.jar -d out\production\PageRank PageRank\src\*.java
## Execute:
    java -cp PageRank\lib\*;out\production\PageRank PageRank\src\CrawlerTest.java
    OR 
    java -cp PageRank\lib\*;out\production\PageRank PageRank\src\CrawlerTest.java [NUMBER] [SUBREDDIT]
    
## Arguments:
    Prompt user for number of sites input
    Prompt user for root SubReddit to crawl
    OR
    Where [NUMBER] is number of sites to visit
    Where [SUBREDDIT] is root SubReddit to crawl 
    
## Output:
    Print the pages visited
    Number of pages unable to visit
    Outgoing links from Subreddit
    PageRank links from list
    PageRank ordered
