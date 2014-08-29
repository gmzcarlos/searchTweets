package search;

import twitter4j.*;

import java.util.List;
import java.util.regex.*;
import java.util.ArrayList;

public class GetTweets {

	public static void main(String[] args) {
	//public GetTweets(String[] args) {
		// TODO Auto-generated constructor stub
		if (args.length < 1) {
			System.out.println("Missing Parameter.");
            System.out.println("Usage: java search.GetTweets [query]");
            System.exit(-1);
        }
        Twitter twitter = new TwitterFactory().getInstance();
        try {
        	int cnt = 0;
            Query query = new Query(args[0]);
            QueryResult result;
            do {
                result = twitter.search(query);
                List<Status> tweets = result.getTweets();
                for (Status tweet : tweets) {
                    //System.out.println(cnt + ": @" + tweet.getUser().getScreenName() + " - " + tweet.getText());
                	
                	// 
                	// TODO: Apply Filter
                	// 1: Tweets must mention 2-4 other users
                	// 2: should not be a retweet
                	// 3: check for keywords (challenge, call on, etc ..)
                	// 4: 
                    if ( tweet.getText().matches("((?s).*(@S+).*{2,4})") )
            		{
                    	//System.out.println(cnt + " FOUND MATCH !!");
                    	//System.out.println(cnt + ": @" + tweet.getUser().getScreenName() + " - " + tweet.getText());
                    	
                    	System.out.print(cnt + ": @" + tweet.getUser().getScreenName() + "=");
                    	printUsersInTweet(tweet.getText());
                    	
                    	cnt++;
            		}
                    
                }
            } while ((query = result.nextQuery()) != null && cnt < 20);
            System.exit(0);
        } catch (TwitterException te) {
            te.printStackTrace();
            System.out.println("Failed to search tweets: " + te.getMessage());
            System.exit(-1);
        }
	}
	
	private static List<String> GetUsersFromTweet( String tweet )
	{
		Pattern pattern = Pattern.compile("(@\\S+)\\b");
        Matcher matcher = pattern.matcher(tweet);

        List<String> listMatches = new ArrayList<String>();

        while(matcher.find())
        {
            listMatches.add(matcher.group(1));
        }
        
        return listMatches;
	}
	
	private static void printUsersInTweet(String tweet)
	{
		List<String> listUsers = GetUsersFromTweet( tweet );
		
		for(String s : listUsers)
        {
            System.out.print("," + s);
        }
		System.out.println("");
	}

}
