package com.goiaba.model.enumeration;

public enum ThreadFileStatus {
  REGISTERED("Registered"),
  QUEUED("Queued"),
  DOWNLOADED("Downloaded"),
  NOT_FOUND("Not Found");
  
  private final String value;

  /**
   * @param valueType
   */
  private ThreadFileStatus(final String value) {
      this.value = value;
  }

  @Override
  public String toString() {
      return value;
  }
}
