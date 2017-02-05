package com.goiaba;

import java.util.concurrent.Callable;

public class ThreadTest implements Callable<String> {
  
  public int property;
 
  @Override
  public String call() throws Exception {
    System.out.println("property: " + property);
    Thread.sleep(3000);
    return "p" + property;
  }

}
