package ironfist;

/**
 * DOCUMENT ME!
 * 
 * @author pmgremo
 */
public class Command {

  private CommandType type;
  private Object parameter;

  /**
   * Creates a new Command object.
   * 
   * @param type
   *          DOCUMENT ME!
   * @param parameter
   *          DOCUMENT ME!
   */
  public Command(CommandType type, Object parameter) {
    this.type = type;
    this.parameter = parameter;
  }

  /**
   * DOCUMENT ME!
   * 
   * @return DOCUMENT ME!
   */
  public Object getParameter() {
    return parameter;
  }

  /**
   * DOCUMENT ME!
   * 
   * @param value
   *          DOCUMENT ME!
   */
  public void setParameter(Object value) {
    parameter = value;
  }

  /**
   * Returns the type.
   * 
   * @return CommandType
   */
  public CommandType getType() {
    return type;
  }
}