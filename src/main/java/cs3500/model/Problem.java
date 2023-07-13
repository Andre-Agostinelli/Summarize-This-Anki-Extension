package cs3500.model;

/**
 * Represents a single problem in the .sr file
 */
public class Problem {
  String question;

  String answer;

  Difficulty difficulty;

  /**
   * Instantiates a Problem: question, answer and associated difficulty.
   *
   * @param question the String representing the question
   * @param answer the String answer to corresponding question
   * @param difficulty the corresponding Difficulty enum
   */
  public Problem(String question, String answer, Difficulty difficulty) {
    this.question = question;
    this.answer = answer;
    this.difficulty = difficulty;
  }

  /**
   * Convenience constructor if no difficulty is stated
   *
   * @param question the String representing the question
   * @param answer the String answer to corresponding question
   */
  public Problem(String question, String answer) {
    this.question = question;
    this.answer = answer;
    this.difficulty = Difficulty.HARD;
  }

  /**
   * Takes a problem and turns it into a String
   *
   * @return returns the string equivalent of a Problem
   */
  public String problemToString() {
    String str;
    str = ("[[" + this.question + ":::" + this.answer + "]]" + this.difficulty.getDifficulty()
        + "\n");
    return str;
  }


  /**
   * Turns the question to string and outputs.
   *
   * @return returns the question part of problem as a string
   */
  public String getQuestion() {
    return this.question;
  }

  /**
   * Returns problem difficultly
   *
   * @return the difficulty of the given problem
   */
  public String getDifficulty() {
    return this.difficulty.toString();
  }

  /**
   * Changes the difficulty according to the given parameter
   *
   * @param difficulty the difficulty to change to
   */
  public void changeDiff(Difficulty difficulty) {
    this.difficulty = difficulty;
  }

  /**
   * Returns the answer to a question.
   *
   * @return the String answer
   */
  public String getAnswer() {
    return this.answer;
  }
}
