package cs3500.model;

/**
 * Models the difficulty of a question with EASY or HARD
 */
public enum Difficulty {
  EASY("***EASY"), HARD("***HARD");


  Difficulty(String diff) {
    this.difficulty = diff;
  }

  private final String difficulty;

  public String getDifficulty() {
    return difficulty;
  }
}

