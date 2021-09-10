import java.io.FileNotFoundException;
import java.awt.TextArea;
import java.util.Scanner;
import java.io.File;

public class SudokuMain
{
    public static void main(final String[] args) throws FileNotFoundException {
        for (int ProblemNumber = 1; ProblemNumber < 26; ++ProblemNumber) {
            final Sudoku s = new Sudoku();
            Scanner in;
            if (ProblemNumber < 10) {
                in = new Scanner(new File("L0" + Integer.toString(ProblemNumber) + "0.txt"));
            }
            else if (ProblemNumber < 25) {
                in = new Scanner(new File("L" + Integer.toString(ProblemNumber) + "0.txt"));
            }
            else {
                in = new Scanner(new File("L242.txt"));
            }
            final Scanner keyboard = new Scanner(System.in);
            final TextArea contents = new TextArea();
            for (int i = 0; i < 9; ++i) {
                for (int j = 0; j < 9; ++j) {
                    s.setProblem(i, j, in.nextInt());
                    if (s.getProblem(i, j) != 0) {
                        s.getCan(i, j).setCandidate(Integer.toString(s.getProblem(i, j)));
                    }
                }
            }
            int n = 0;
            int trial = 0;
            s.countTotal();
            while (s.getTotalCount() != n) {
                n = s.getTotalCount();
                s.BasicException(contents);
                s.UniqueException(contents);
                s.OneBoxException(contents);
                s.OneRowColumnException(contents);
                contents.append("메소드명 : Power Exception%n%n");
                for (int k = 8; k > 1; --k) {
                    for (int l = 0; l < 9; ++l) {
                        s.PowerException(l, -1, k, contents);
                        s.PowerException(-1, l, k, contents);
                    }
                    s.PowerException_BOX(k, contents);
                }
                s.Xwing(contents);
                ++trial;
                s.countTotal();
            }
            if (ProblemNumber < 10) {
                System.out.println("L0" + Integer.toString(ProblemNumber) + "0 : " + s.getTotalCount());
            }
            else if (ProblemNumber < 25) {
                System.out.println("L" + Integer.toString(ProblemNumber) + "0 : " + s.getTotalCount());
            }
            else {
                System.out.println("L242 : " + s.getTotalCount());
            }
        }
    }
}