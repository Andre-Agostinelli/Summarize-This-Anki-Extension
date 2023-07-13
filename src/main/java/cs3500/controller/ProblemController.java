package cs3500.controller;


import cs3500.model.Difficulty;
import cs3500.model.TestBank;
import cs3500.view.Reader;
import cs3500.view.ReaderImpl;
import cs3500.view.Writer;
import cs3500.view.WriterImpl;
import java.util.Objects;

/**
 * Represents the Controller Class
 */
public class ProblemController implements Controller {

  private final Readable input;

  private final Appendable output;

  private final TestBank testBank;

  /**
   * Instantiates a Controller object
   *
   * @param input the readable type to be input and processed
   * @param output the appendable type to output to view
   * @param testBank the testBank model associated
   */
  public ProblemController(Readable input, Appendable output, TestBank testBank) {
    this.input = Objects.requireNonNull(input);
    this.output = Objects.requireNonNull(output);
    this.testBank = Objects.requireNonNull(testBank);
  }

  /**
   * Runs the controller which determines functionality
   */
  @Override
  public void run() {
    Reader reader = new ReaderImpl(this.input, "***EXIT");
    Writer writer = new WriterImpl(this.output);
    String userIn = reader.read();

    //integer trackers
    int totalCorrect = 0;
    int totalChangeToHard = 0;
    int totalChangeToEasy = 0;
    int finalHard;
    int finalEasy;

    for (int i = 0; i < testBank.problemCollection.size(); i++) {
      writer.write(testBank.problemCollection.get(i).getQuestion());

      writer.write("If you would like to change the difficulty enter "
          + "'HARD' for HARD or 'EASY' for easy:");
      userIn = reader.read();

      if (userIn.equals("HARD") && (Objects.equals(
          testBank.problemCollection.get(i).getDifficulty(), Difficulty.EASY.toString()))) {
        totalChangeToHard++;
        testBank.problemCollection.get(i).changeDiff(Difficulty.HARD);

      } else if (userIn.equals("EASY") && (Objects.equals(
          testBank.problemCollection.get(i).getDifficulty(), Difficulty.HARD.toString()))) {
        totalChangeToEasy++;
        testBank.problemCollection.get(i).changeDiff(Difficulty.EASY);
      }

      writer.write("Please type your answer: ");
      userIn = reader.read();
      if (userIn.equals(testBank.problemCollection.get(i).getAnswer())) {
        totalCorrect++;
        writer.write("The answer was correct! Well done!");
      } else {
        writer.write("The answer was incorrect!");
      }

      writer.write("Please type 'seeAnswer' to see the Answer: ");
      userIn = reader.read();
      if (userIn.equals("seeAnswer")) {
        writer.write(testBank.problemCollection.get(i).getAnswer());
      }
    }
    finalHard = testBank.findHardTotal();
    //makes logical sense
    finalEasy = testBank.problemCollection.size() - finalHard;

    writer.write(printFinalScreen(totalCorrect, totalChangeToEasy,
        totalChangeToHard, finalHard, finalEasy));

  }

  /**
   * Prints the end game screen with all the game details
   *
   * @param totalCorrect the total number of questions correct
   * @param totalChangeToEasy the total number changed from hard to easy
   * @param totalChangeToHard the total number changed from easy to hard
   * @param finalHard the final total of hard questions after a session
   * @param finalEasy the final total of easy questions after a session
   * @return String to display
   */
  private String printFinalScreen(int totalCorrect, int totalChangeToEasy,
                                  int totalChangeToHard, int finalHard, int finalEasy) {

    StringBuilder str = new StringBuilder();
    str.append("WELL DONE!!!!!!!!!!!!!!!!!!!\n");
    str.append("The total number correct was: ").append(totalCorrect).append("\n");
    str.append("The total number changed from hard to easy was: ").append(totalChangeToEasy)
        .append("\n");
    str.append("The total number changed from easy to hard was: ").append(totalChangeToHard)
        .append("\n");
    str.append("The resulting final number of hard problems was: ").append(finalHard).append("\n");
    str.append("The resulting final number of easy problems was: ").append(finalEasy).append("\n");
    return str.toString();
  }

}
