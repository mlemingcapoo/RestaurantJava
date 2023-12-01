

package helper;

/**
 *
 * @author capoo
 */
class ProcRunner implements Runnable {

  private String procName;
  private Object[] args;

  public ProcRunner(String procName, Object[] args) {
    this.procName = procName;
    this.args = args;
  }

  @Override 
  public void run() {
    // existing executeProc logic
  }

}
