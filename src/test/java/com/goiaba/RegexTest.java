package com.goiaba;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeoutException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;


public class RegexTest {
  
  private final ExecutorService downloaderThreadPool;

  @Autowired
  public RegexTest() {
    downloaderThreadPool = Executors.newFixedThreadPool(2);
  }
  
  private Future<String> initializeThread(int property) throws InterruptedException, ExecutionException, TimeoutException {
    ThreadTest t = new ThreadTest();
    t.property = property;
    return downloaderThreadPool.submit(t);    
  }
  
  @Test
  public void test2() throws InterruptedException, ExecutionException, TimeoutException {
    List<Future<String>> listTasks = new ArrayList<Future<String>>();

    listTasks.add(initializeThread(1));
    listTasks.add(initializeThread(2));
    listTasks.add(initializeThread(3));
    listTasks.add(initializeThread(4));
    listTasks.add(initializeThread(5));
    listTasks.add(initializeThread(6));
    
    for (Future<String> task : listTasks) {
      while (!task.isDone()) {
        Thread.sleep(1000);
      }
      System.out.println(task.get());
    }
    
    Assert.assertNotNull(null);
  }
  
  @Ignore
	@Test
	public void test() {
		String text = "<div class=\"fileText\" id=\"fT9796598\">File: <a title=\"2016-12-20 21_36_01-LL _ Gingersnaps Dildo Fucking and Pillow Humping 531 MB _ HqCollect.me.gif\" href=\"//is.4chan.org/gif/1482298610952.gif\" target=\"_blank\">2016-12-20 21_36_01-LL _ (....)gif</a> (321 KB, 1920x911)";
		
		final Pattern patternLink = Pattern.compile("href=\"(.+?)\" target=\"_blank\">(.+?)</a>", Pattern.DOTALL);
		
		final Matcher matcherLink = patternLink.matcher(text);
		matcherLink.find();
		
		String fileLink = matcherLink.group(1);
        String fileName = matcherLink.group(2);
		
		System.out.println(String.format("http://boards.4chan.org/%s/catalog", "bla"));
		
	}
	
  @Ignore
  @Test
  public void contextLoads() throws IOException {
    
    final Pattern patternJson = Pattern.compile("var catalog =(.+?)};");

    FileReader fileR = new FileReader("src/main/resources/source_test/catalog_test.html");
    BufferedReader br = new BufferedReader(fileR);
    
    String sCurrentLine;
    while ((sCurrentLine = br.readLine()) != null) {
      break;
    }
    final Matcher matcher = patternJson.matcher(sCurrentLine);
    
    String jsonString = "";
    while (matcher.find()) {
      jsonString = matcher.group(1) + "}};"; // Prints String I want to extract
    }
    
    JSONObject jsonObj = new JSONObject(jsonString);
    JSONObject jsonThreads = ((JSONObject)jsonObj.get("threads"));
    
    Iterator<?> keys = jsonThreads.keys();

    while( keys.hasNext() ) {
        String key = (String)keys.next();
        JSONObject jsonThread = (JSONObject) jsonThreads.get(key);
        
        System.out.println("---");
        System.out.println("---");
        System.out.println(key);
        System.out.println("---");
        System.out.println(jsonThread.get("sub"));
        System.out.println("---");
        System.out.println(jsonThread.get("teaser"));
        System.out.println("---");
        System.out.println("---");
    }
    
    br.close();
    
  }
}
