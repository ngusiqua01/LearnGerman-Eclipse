package com.ufo.learngerman.utils;

public class Native
{
  static
  {
    System.loadLibrary("test");
  }
  
  public native String getName();
}
