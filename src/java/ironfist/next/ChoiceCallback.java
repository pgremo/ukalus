package ironfist.next;

/**
 * DOCUMENT ME!
 * 
 * @author pmgremo
 */
public class ChoiceCallback implements Callback {

  private String prompt;
  private Object[] options;
  private int[] selected;
  private boolean multipleAllowed;
  private int defaultOption;

  public ChoiceCallback(String prompt, Object[] options, int[] selected,
      boolean multipleAllowed, int defaultOption) {
    this.prompt = prompt;
    this.options = options;
    this.selected = selected;
    this.multipleAllowed = multipleAllowed;
    this.defaultOption = defaultOption;
  }

  /**
   * Returns the defaultOption.
   * 
   * @return int
   */
  public int getDefaultOption() {
    return defaultOption;
  }

  /**
   * Returns the multipleAllowed.
   * 
   * @return boolean
   */
  public boolean isMultipleAllowed() {
    return multipleAllowed;
  }

  /**
   * Returns the options.
   * 
   * @return Object[]
   */
  public Object[] getOptions() {
    return options;
  }

  /**
   * Returns the selected.
   * 
   * @return int[]
   */
  public int[] getSelected() {
    return selected;
  }

  /**
   * Sets the defaultOption.
   * 
   * @param defaultOption
   *          The defaultOption to set
   */
  public void setDefaultOption(int defaultOption) {
    this.defaultOption = defaultOption;
  }

  /**
   * Sets the multipleAllowed.
   * 
   * @param multipleAllowed
   *          The multipleAllowed to set
   */
  public void setMultipleAllowed(boolean multipleAllowed) {
    this.multipleAllowed = multipleAllowed;
  }

  /**
   * Sets the options.
   * 
   * @param options
   *          The options to set
   */
  public void setOptions(Object[] options) {
    this.options = options;
  }

  public void setSelected(int value) {
    selected = new int[1];
    selected[0] = value;
  }

  /**
   * Sets the selected.
   * 
   * @param selected
   *          The selected to set
   * 
   * @throws UnsupportedOperationException
   *           DOCUMENT ME!
   */
  public void setSelected(int[] selected) {
    if (multipleAllowed) {
      this.selected = selected;
    } else {
      throw new UnsupportedOperationException("multiples not allowed");
    }
  }

  /**
   * Returns the prompt.
   * 
   * @return String
   */
  public String getPrompt() {
    return prompt;
  }

}