package ironfist.next;


/**
 * @author pmgremo
 */
public interface Controller {
  void determineAction();
  void handleCallback(Callback callback);
}
