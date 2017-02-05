package com.goiaba.model.enumeration;

public enum JobMode {
  ALL("All"),
  FILTER("Filter");
  
  private final String value;

  /**
   * @param valueType
   */
  private JobMode(final String value) {
      this.value = value;
  }

  @Override
  public String toString() {
      return value;
  }
}
