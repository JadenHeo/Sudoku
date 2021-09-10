import java.io.FileNotFoundException;
import java.awt.event.KeyEvent;
import java.io.Writer;
import java.io.FileWriter;
import java.io.File;
import java.awt.event.ActionEvent;
import java.awt.Container;
import java.awt.Color;
import java.awt.Font;
import java.awt.LayoutManager;
import java.awt.GridLayout;
import javax.swing.JLabel;
import java.awt.Component;
import javax.swing.JButton;
import javax.swing.border.Border;
import javax.swing.border.BevelBorder;
import java.awt.Dimension;
import javax.swing.JPanel;
import java.io.PrintWriter;
import java.util.Scanner;
import java.awt.TextArea;
import javax.swing.JTextField;
import java.awt.event.KeyListener;
import java.awt.event.ActionListener;
import javax.swing.JFrame;

public class SudokuDemo extends JFrame implements ActionListener, KeyListener
{
    JTextField fileName;
    TextArea contents;
    Scanner in;
    PrintWriter out;
    JTextField[][] InputField;
    int[][] input;
    Sudoku s;
    
    public SudokuDemo() {
        this.InputField = new JTextField[9][9];
        this.input = new int[9][9];
        this.s = new Sudoku();
        this.setSize(1100, 680);
        this.setResizable(false);
        final JPanel buttonPanel = new JPanel();
        buttonPanel.setPreferredSize(new Dimension(30, 40));
        buttonPanel.setBorder(new BevelBorder(1));
        final JButton openButton = new JButton("불러오기");
        openButton.addActionListener(this);
        buttonPanel.add(openButton);
        final JButton printButton = new JButton("출력");
        printButton.addActionListener(this);
        buttonPanel.add(printButton);
        final JButton saveButton = new JButton("txt로저장");
        saveButton.addActionListener(this);
        buttonPanel.add(saveButton);
        final JButton resetButton = new JButton("리셋");
        resetButton.addActionListener(this);
        buttonPanel.add(resetButton);
        final JButton exitButton = new JButton("종료");
        exitButton.addActionListener(this);
        buttonPanel.add(exitButton);
        final JPanel buttonPanel2 = new JPanel();
        buttonPanel2.setBorder(new BevelBorder(1));
        buttonPanel2.setPreferredSize(new Dimension(150, 40));
        final JButton solveButton = new JButton("Solve to End");
        solveButton.addActionListener(this);
        buttonPanel2.add(solveButton);
        final JButton BasicException = new JButton("Basic Exception");
        BasicException.addActionListener(this);
        buttonPanel2.add(BasicException);
        final JButton OneBoxException = new JButton("OneBox Exception");
        OneBoxException.addActionListener(this);
        buttonPanel2.add(OneBoxException);
        final JButton PowerExceptionButton = new JButton("Power Exception");
        PowerExceptionButton.addActionListener(this);
        buttonPanel2.add(PowerExceptionButton);
        final JButton XwingButton = new JButton("Xwing");
        XwingButton.addActionListener(this);
        buttonPanel2.add(XwingButton);
        final JLabel fileNameLabel = new JLabel("저장할 / 불러올 파일명");
        (this.fileName = new JTextField("", 20)).addKeyListener(this);
        final JPanel fileNamePanel = new JPanel();
        fileNamePanel.add(fileNameLabel, "North");
        fileNamePanel.add(this.fileName, "South");
        fileNamePanel.setBorder(new BevelBorder(1));
        final JPanel InputPanel = new JPanel(new GridLayout(9, 9));
        for (int i = 0; i < 9; ++i) {
            for (int j = 0; j < 9; ++j) {
                (this.InputField[i][j] = new JTextField("", 2)).setFont(new Font("신명조", 1, 35));
                final JTextField textField = this.InputField[i][j];
                final JTextField textField2 = this.InputField[i][j];
                textField.setHorizontalAlignment(0);
                this.InputField[i][j].addKeyListener(this);
                if (this.s.RectangleNumber(i, j) % 2 == 1) {
                    this.InputField[i][j].setBackground(Color.yellow);
                }
                else {
                    this.InputField[i][j].setBackground(Color.GREEN);
                }
                InputPanel.add(this.InputField[i][j], "South");
            }
        }
        InputPanel.setBorder(new BevelBorder(1));
        (this.contents = new TextArea(50, 1000)).setBackground(Color.WHITE);
        this.contents.setEditable(false);
        final Container cp = this.getContentPane();
        cp.add(fileNamePanel, "North");
        cp.add(InputPanel, "West");
        cp.add(this.contents, "Center");
        cp.add(buttonPanel, "South");
        cp.add(buttonPanel2, "East");
    }
    
    public void PRINT(final int a) {
        for (int i = 0; i < 9; ++i) {
            for (int j = 0; j < 9; ++j) {
                if (a == 0 && this.s.getCan(i, j).getCandidate().length() <= 1) {
                    this.InputField[i][j].setFont(new Font("신명조", 1, 35));
                    this.InputField[i][j].setText(this.s.getCan(i, j).getCandidate());
                    this.InputField[i][j].setForeground(Color.red);
                }
                if (this.s.getCan(i, j).getCandidate().length() <= 1) {
                    this.InputField[i][j].setFont(new Font("신명조", 1, 35));
                    this.InputField[i][j].setText(this.s.getCan(i, j).getCandidate());
                }
                else if (a == 1) {
                    this.InputField[i][j].setFont(new Font("신명조", 0, 12));
                    this.InputField[i][j].setText(this.s.getCan(i, j).getCandidate());
                }
                else if (a == 0) {
                    this.InputField[i][j].setFont(new Font("신명조", 1, 35));
                    this.InputField[i][j].setText("");
                }
            }
        }
        this.s.countTotal();
        this.contents.append(String.valueOf(Integer.toString(this.s.getTotalCount())) + "%n%n");
    }
    
    @Override
    public void actionPerformed(final ActionEvent e) {
        for (int i = 0; i < 9; ++i) {
            for (int j = 0; j < 9; ++j) {
                this.s.setProblem(i, j, 0);
            }
        }
        try {
            if (e.getActionCommand().equals("불러오기")) {
                this.s = new Sudoku();
                this.in = new Scanner(new File(String.valueOf(this.fileName.getText()) + ".txt"));
                for (int i = 0; i < 9; ++i) {
                    for (int j = 0; j < 9; ++j) {
                        this.s.setProblem(i, j, this.in.nextInt());
                        if (this.s.getProblem(i, j) != 0) {
                            this.s.getCan(i, j).setCandidate(Integer.toString(this.s.getProblem(i, j)));
                        }
                    }
                }
                this.PRINT(0);
            }
            else if (e.getActionCommand().equals("출력")) {
                this.PRINT(1);
            }
            else if (e.getActionCommand().equals("리셋")) {
                for (int i = 0; i < 9; ++i) {
                    for (int j = 0; j < 9; ++j) {
                        this.s.getCan(i, j).initCandidate();
                        this.InputField[i][j].setText("");
                        this.InputField[i][j].setFont(new Font("신명조", 1, 35));
                        this.InputField[i][j].setForeground(Color.BLACK);
                    }
                }
                this.contents.setText("");
            }
            else if (e.getActionCommand().equals("txt로저장")) {
                for (int i = 0; i < 9; ++i) {
                    for (int j = 0; j < 9; ++j) {
                        if (!this.InputField[i][j].getText().equals("")) {
                            this.s.setProblem(i, j, Integer.parseInt(this.InputField[i][j].getText()));
                        }
                    }
                }
                this.out = new PrintWriter(new FileWriter(String.valueOf(this.fileName.getText()) + ".txt"));
                for (int i = 0; i < 9; ++i) {
                    for (int j = 0; j < 9; ++j) {
                        this.out.print(String.valueOf(this.s.getProblem(i, j)) + " ");
                    }
                    this.out.println();
                }
                this.out.close();
            }
            else if (e.getActionCommand().equals("종료")) {
                System.exit(0);
            }
            else if (e.getActionCommand().equals("Solve to End")) {
                int n = 0;
                int trial = 0;
                while (this.s.getTotalCount() != n) {
                    n = this.s.getTotalCount();
                    this.s.BasicException(this.contents);
                    this.s.UniqueException(this.contents);
                    this.s.OneBoxException(this.contents);
                    this.s.OneRowColumnException(this.contents);
                    this.contents.append("메소드명 : Power Exception%n%n");
                    for (int k = 7; k > 1; --k) {
                        for (int l = 0; l < 9; ++l) {
                            this.s.PowerException(l, -1, k, this.contents);
                            this.s.PowerException(-1, l, k, this.contents);
                        }
                        this.s.PowerException_BOX(k, this.contents);
                    }
                    this.contents.append("%n");
                    this.s.Xwing(this.contents);
                    ++trial;
                    this.s.countTotal();
                }
                this.PRINT(1);
            }
            else if (e.getActionCommand().equals("Basic Exception")) {
                this.s.BasicException(this.contents);
                this.PRINT(1);
            }
            else if (e.getActionCommand().equals("OneBox Exception")) {
                this.s.OneRowColumnException(this.contents);
                this.PRINT(1);
            }
            else if (e.getActionCommand().equals("Power Exception")) {
                this.contents.append("메소드명 : Power Exception%n%n");
                for (int m = 8; m > 1; --m) {
                    for (int i2 = 0; i2 < 9; ++i2) {
                        this.s.PowerException(i2, -1, m, this.contents);
                        this.s.PowerException(-1, i2, m, this.contents);
                    }
                    this.s.PowerException_BOX(m, this.contents);
                }
                this.contents.append("%n%n%n");
                this.PRINT(1);
            }
            else if (e.getActionCommand().equals("Xwing")) {
                this.s.Xwing(this.contents);
                this.PRINT(1);
            }
            else {
                this.fileName.setText("Error in Button Interface.");
            }
        }
        catch (Exception e2) {
            this.contents.setText("File Not Found");
        }
    }
    
    @Override
    public void keyReleased(final KeyEvent e) {
    }
    
    @Override
    public void keyTyped(final KeyEvent e) {
    }
    
    @Override
    public void keyPressed(final KeyEvent e) {
        if (e.getKeyCode() == 10) {
            this.s = new Sudoku();
            try {
                this.in = new Scanner(new File(String.valueOf(this.fileName.getText()) + ".txt"));
            }
            catch (FileNotFoundException e2) {
                e2.printStackTrace();
            }
            for (int i = 0; i < 9; ++i) {
                for (int j = 0; j < 9; ++j) {
                    this.s.setProblem(i, j, this.in.nextInt());
                    if (this.s.getProblem(i, j) != 0) {
                        this.s.getCan(i, j).setCandidate(Integer.toString(this.s.getProblem(i, j)));
                    }
                }
            }
            this.PRINT(0);
        }
    }
    
    public static void main(final String[] args) {
        final SudokuDemo f = new SudokuDemo();
        f.setDefaultCloseOperation(3);
        f.setVisible(true);
    }
}