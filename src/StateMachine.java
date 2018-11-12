public class StateMachine {

  public static boolean binMachine(String input) {
    int i = 0;
    while (i < input.length()) {
      char c = input.charAt(i);
      if (c != '0' && c != '1') {
        return false;
      }
      i = i + 1;
    }
    return true;
  }

  public static int binConvMachine(String input) {
    int i = 0;
    int acc = 0;
    if (!binMachine(input)) {
      return -1;
    }
    while (i < input.length()) {
      char c = input.charAt(input.length() - i - 1);
      if (c == '1') {
        acc += Math.pow(2, i);
      }
      i = i + 1;
    }
    return acc;
  }

  public static boolean numMachine(String input) {
    
    return false;
  }

  public static boolean decMachine(String input) {
    // TODO
    return false;
  }

  public static boolean signMachine(String input) {
    // TODO
    return false;
  }

  public static boolean numberMachine(String input) {
    // TODO
    return false;
  }

  public static boolean wordMachine(String input) {
    // TODO
    return false;
  }

  public static boolean wordyMachine(String input) {
    // TODO
    return false;
  }

  public static boolean sentenceMachine(String input) {
    // TODO
    return false;
  }

  public static boolean grammarMachine(String input) {
    // TODO
    return false;
  }

  public static double calcMachine(String input) {
    // TODO
    return Double.NaN;
  }
}
