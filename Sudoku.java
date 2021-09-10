import java.awt.TextArea;

public class Sudoku extends Candidate
{
    private int[][] problem;
    private int[][] solution;
    private Candidate[][] can;
    private int totalCount;
    public String s;
    
    Sudoku() {
        this.problem = new int[9][9];
        this.solution = new int[9][9];
        this.can = new Candidate[9][9];
        this.s = "";
        for (int i = 0; i < 9; ++i) {
            for (int j = 0; j < 9; ++j) {
                this.problem[i][j] = 0;
                this.solution[i][j] = 0;
                this.can[i][j] = new Candidate();
            }
        }
        this.totalCount = 0;
    }
    
    public void setProblem(final int i, final int j, final int number) {
        this.problem[i][j] = number;
    }
    
    public Candidate getCan(final int i, final int j) {
        return this.can[i][j];
    }
    
    public int getProblem(final int i, final int j) {
        return this.problem[i][j];
    }
    
    public int getSolution(final int i, final int j) {
        return this.solution[i][j];
    }
    
    public int getTotalCount() {
        return this.totalCount;
    }
    
    public int RectangleNumber(final int a, final int b) {
        int index = 0;
        index = (int)(3.0 * Math.ceil(a / 3) + Math.ceil(b / 3)) + 1;
        return index;
    }
    
    public int RectangleAB(final int index) {
        final int A = 3 * (int)Math.ceil((index - 1) / 3);
        final int B = 3 * ((index - 1) % 3);
        return 10 * A + B;
    }
    
    public void countTotal() {
        this.totalCount = 0;
        for (int i = 0; i < 9; ++i) {
            for (int j = 0; j < 9; ++j) {
                this.totalCount += this.can[i][j].getCandidate().length();
            }
        }
    }
    
    public int Checking(final String numberString, final int i, final int j, int p, int q) {
        p = -1;
        q = -1;
        if (this.can[i][j].getCandidate().length() != 1) {
            for (int a = 0; a < 9; ++a) {
                if (a != j && numberString.equals(this.can[i][a].getCandidate())) {
                    p = i;
                    q = a;
                }
                if (a != i && numberString.equals(this.can[a][j].getCandidate())) {
                    p = a;
                    q = j;
                }
            }
            int A = 0;
            int B = 0;
            A = this.RectangleAB(this.RectangleNumber(i, j)) / 10;
            B = this.RectangleAB(this.RectangleNumber(i, j)) % 10;
            for (int a2 = A; a2 < A + 3; ++a2) {
                for (int b = B; b < B + 3; ++b) {
                    if ((a2 != i || b != j) && numberString.equals(this.can[a2][b].getCandidate())) {
                        p = a2;
                        q = b;
                    }
                }
            }
        }
        return 10 * p + q;
    }
    
    public int Checking(final int number, final int i, final int j, final int p, final int q) {
        final String numberString = Integer.toString(number);
        return this.Checking(numberString, i, j, p, q);
    }
    
    public void BasicException(final TextArea contents) {
        System.out.print("메소드명 : Basic Exception%n%n");
        int t = 0;
        for (int r = 0; r < 9; ++r) {
            for (int i = 0; i < 9; ++i) {
                if (this.can[i][r].getCandidate().length() == 1) {
                    final int T = t;
                    for (int j = 0; j < 9; ++j) {
                        if (this.can[i][j].getCandidate().length() > 1 && this.can[i][j].subCandidate(this.can[i][r].getCandidate())) {
                            System.out.print("(가로) (" + i + ", " + j + ") 에 있는 " + this.can[i][r].getCandidate() + "이(가) 제거됬습니다.%n");
                            ++t;
                        }
                        if (this.can[j][r].getCandidate().length() > 1 && this.can[j][r].subCandidate(this.can[i][r].getCandidate())) {
                            System.out.print("(세로) (" + j + ", " + r + ") 에 있는 " + this.can[i][r].getCandidate() + "이(가) 제거됬습니다.%n");
                            ++t;
                        }
                        final int A = this.RectangleAB(this.RectangleNumber(i, r)) / 10;
                        final int B = this.RectangleAB(this.RectangleNumber(i, r)) % 10;
                        for (int a = A; a < A + 3; ++a) {
                            for (int b = B; b < B + 3; ++b) {
                                if (this.can[a][b].getCandidate().length() > 1 && this.can[a][b].subCandidate(this.can[i][r].getCandidate())) {
                                    System.out.print("(박스) (" + a + ", " + b + ") 에 있는 " + this.can[i][r].getCandidate() + "이(가) 제거됬습니다.%n");
                                    ++t;
                                }
                            }
                        }
                    }
                    if (T != t) {
                        System.out.print("(" + i + ", " + r + ") 에 있는 " + this.can[i][r].getCandidate() + "에 의해****************%n%n");
                    }
                }
            }
        }
        System.out.print("%n%n");
    }
    
    public void UniqueException(final TextArea contents) {
        for (int number = 1; number < 10; ++number) {
            for (int i = 0; i < 9; ++i) {
                int count1 = 0;
                int count2 = 0;
                for (int j = 0; j < 9; ++j) {
                    if (this.can[i][j].getCandidate().indexOf(Integer.toString(number)) != -1) {
                        ++count1;
                    }
                    if (this.can[j][i].getCandidate().indexOf(Integer.toString(number)) != -1) {
                        ++count2;
                    }
                }
                if (count1 == 1) {
                    for (int j = 0; j < 9; ++j) {
                        if (this.can[i][j].getCandidate().indexOf(Integer.toString(number)) != -1) {
                            this.can[i][j].setCandidate(Integer.toString(number));
                        }
                    }
                }
                if (count2 == 1) {
                    for (int j = 0; j < 9; ++j) {
                        if (this.can[j][i].getCandidate().indexOf(Integer.toString(number)) != -1) {
                            this.can[j][i].setCandidate(Integer.toString(number));
                        }
                    }
                }
            }
            for (int i = 0; i < 9; ++i) {
                int count3 = 0;
                final int A = this.RectangleAB(i) / 10;
                final int B = this.RectangleAB(i) % 10;
                for (int a = A; a < A + 3; ++a) {
                    for (int b = B; b < B; ++b) {
                        if (this.can[a][b].getCandidate().indexOf(Integer.toString(number)) != -1) {
                            ++count3;
                        }
                    }
                }
                if (count3 == 1) {
                    for (int a = A; a < A + 3; ++a) {
                        for (int b = B; b < B; ++b) {
                            if (this.can[a][b].getCandidate().indexOf(Integer.toString(number)) != -1) {
                                this.can[a][b].setCandidate(Integer.toString(number));
                            }
                        }
                    }
                }
            }
        }
    }
    
    public void PowerException(final int row, final int column, final int power, final TextArea contents) {
        final Integer[] c = new Integer[power];
        if (power > 0) {
            for (int c2 = 0; c2 < 10 - power; ++c2) {
                c[0] = c2;
                if (power > 1) {
                    for (int c3 = c2 + 1; c3 < 11 - power; ++c3) {
                        c[1] = c3;
                        if (power > 2) {
                            for (int c4 = c3 + 1; c4 < 12 - power; ++c4) {
                                c[2] = c4;
                                if (power > 3) {
                                    for (int c5 = c4 + 1; c5 < 13 - power; ++c5) {
                                        c[3] = c5;
                                        if (power > 4) {
                                            for (int c6 = c5 + 1; c6 < 14 - power; ++c6) {
                                                c[4] = c6;
                                                if (power > 5) {
                                                    for (int c7 = c6 + 1; c7 < 15 - power; ++c7) {
                                                        c[5] = c7;
                                                        if (power > 6) {
                                                            for (int c8 = c7 + 1; c8 < 16 - power; ++c8) {
                                                                c[6] = c8;
                                                                if (power > 7) {
                                                                    for (int c9 = c8 + 1; c9 < 17 - power; ++c9) {
                                                                        c[7] = c9;
                                                                        this.PowerException(row, column, power, c, contents);
                                                                    }
                                                                }
                                                                else {
                                                                    this.PowerException(row, column, power, c, contents);
                                                                }
                                                            }
                                                        }
                                                        else {
                                                            this.PowerException(row, column, power, c, contents);
                                                        }
                                                    }
                                                }
                                                else {
                                                    this.PowerException(row, column, power, c, contents);
                                                }
                                            }
                                        }
                                        else {
                                            this.PowerException(row, column, power, c, contents);
                                        }
                                    }
                                }
                                else {
                                    this.PowerException(row, column, power, c, contents);
                                }
                            }
                        }
                        else {
                            this.PowerException(row, column, power, c, contents);
                        }
                    }
                }
            }
        }
    }
    
    public void PowerException(final int row, final int column, final int power, final Integer[] c, final TextArea contents) {
        final String[] s = new String[power];
        int t = 0;
        if (row == -1) {
            for (int i = 0; i < power; ++i) {
                s[i] = this.can[c[i]][column].getCandidate();
            }
        }
        else if (column == -1) {
            for (int i = 0; i < power; ++i) {
                s[i] = this.can[row][c[i]].getCandidate();
            }
        }
        boolean excute1 = true;
        for (int l = 0; l < power; ++l) {
            if (s[l].length() <= 1) {
                excute1 = false;
            }
        }
        if (excute1 && this.sumCandidate(s, power).length() == power) {
            final int T = t;
            for (int j = 0; j < 9; ++j) {
                boolean excute2 = true;
                for (int k = 0; k < power; ++k) {
                    if (j == c[k]) {
                        excute2 = false;
                    }
                }
                if (excute2) {
                    if (row == -1) {
                        for (int m = 0; m < this.sumCandidate(s, power).length(); ++m) {
                            if (this.can[j][column].subCandidate(this.sumCandidate(s, power).substring(m, m + 1))) {
                                System.out.print("(세로) (" + j + ", " + column + ") 에 있는 " + this.sumCandidate(s, power).substring(m, m + 1) + "이(가) 제거됬습니다.%n");
                                ++t;
                            }
                        }
                    }
                    else if (column == -1) {
                        for (int m = 0; m < this.sumCandidate(s, power).length(); ++m) {
                            if (this.can[row][j].subCandidate(this.sumCandidate(s, power).substring(m, m + 1))) {
                                System.out.print("(가로) (" + row + ", " + j + ") 에 있는 " + this.sumCandidate(s, power).substring(m, m + 1) + "이(가) 제거됬습니다.%n");
                                ++t;
                            }
                        }
                    }
                }
            }
            if (row == -1 && T != t) {
                System.out.print(String.valueOf(column + 1) + "째 열에 ");
                for (int j = 0; j < power; ++j) {
                    System.out.print(Integer.toString(c[j]));
                }
                System.out.print("  총" + power + "개의 유니온 => [" + this.sumCandidate(s, power) + "] 에 의해**********%n%n");
            }
            else if (column == -1 && T != t) {
                System.out.print(String.valueOf(row + 1) + "째 행에 ");
                for (int j = 0; j < power; ++j) {
                    System.out.print(Integer.toString(c[j]));
                }
                System.out.print("  총" + power + "개의 유니온 => [" + this.sumCandidate(s, power) + "] 에 의해**********%n%n");
            }
        }
    }
    
    public void PowerException_BOX(final int power, final TextArea contents) {
        final Integer[] c = new Integer[power];
        for (int index = 1; index < 10; ++index) {
            c[0] = 0;
            while (c[0] < 10 - power) {
                if (power > 1) {
                    c[1] = c[0] + 1;
                    while (c[1] < 11 - power) {
                        if (power > 2) {
                            c[2] = c[1] + 1;
                            while (c[2] < 12 - power) {
                                if (power > 3) {
                                    c[3] = c[2] + 1;
                                    while (c[3] < 13 - power) {
                                        if (power > 4) {
                                            c[4] = c[3] + 1;
                                            while (c[4] < 14 - power) {
                                                if (power > 5) {
                                                    c[5] = c[4] + 1;
                                                    while (c[5] < 15 - power) {
                                                        if (power > 6) {
                                                            c[6] = c[5] + 1;
                                                            while (c[6] < 16 - power) {
                                                                if (power > 7) {
                                                                    c[7] = c[6] + 1;
                                                                    while (c[7] < 17 - power) {
                                                                        this.PowerException_BOX(index, power, c, contents);
                                                                        final Integer[] array = c;
                                                                        final int n = 7;
                                                                        ++array[n];
                                                                    }
                                                                }
                                                                else {
                                                                    this.PowerException_BOX(index, power, c, contents);
                                                                }
                                                                final Integer[] array2 = c;
                                                                final int n2 = 6;
                                                                ++array2[n2];
                                                            }
                                                        }
                                                        else {
                                                            this.PowerException_BOX(index, power, c, contents);
                                                        }
                                                        final Integer[] array3 = c;
                                                        final int n3 = 5;
                                                        ++array3[n3];
                                                    }
                                                }
                                                else {
                                                    this.PowerException_BOX(index, power, c, contents);
                                                }
                                                final Integer[] array4 = c;
                                                final int n4 = 4;
                                                ++array4[n4];
                                            }
                                        }
                                        else {
                                            this.PowerException_BOX(index, power, c, contents);
                                        }
                                        final Integer[] array5 = c;
                                        final int n5 = 3;
                                        ++array5[n5];
                                    }
                                }
                                else {
                                    this.PowerException_BOX(index, power, c, contents);
                                }
                                final Integer[] array6 = c;
                                final int n6 = 2;
                                ++array6[n6];
                            }
                        }
                        else {
                            this.PowerException_BOX(index, power, c, contents);
                        }
                        final Integer[] array7 = c;
                        final int n7 = 1;
                        ++array7[n7];
                    }
                }
                final Integer[] array8 = c;
                final int n8 = 0;
                ++array8[n8];
            }
        }
    }
    
    public void PowerException_BOX(final int index, final int power, final Integer[] c, final TextArea contents) {
        final String[] s = new String[power];
        final String[] temp = new String[9];
        final int A = this.RectangleAB(index) / 10;
        final int B = this.RectangleAB(index) % 10;
        int t = 0;
        for (int column1 = A; column1 < A + 3; ++column1) {
            for (int row1 = B; row1 < B + 3; ++row1) {
                temp[t] = this.can[column1][row1].getCandidate();
                ++t;
            }
        }
        for (int i = 0; i < power; ++i) {
            s[i] = temp[c[i]];
        }
        boolean excute1 = true;
        for (int l = 0; l < power; ++l) {
            if (s[l].length() <= 1) {
                excute1 = false;
            }
        }
        if (excute1 && this.sumCandidate(s, power).length() == power) {
            final int T = t;
            for (int j = 0; j < 9; ++j) {
                boolean excute2 = true;
                for (int k = 0; k < power; ++k) {
                    if (j == c[k]) {
                        excute2 = false;
                    }
                }
                if (excute2) {
                    final int row2 = B + j % 3;
                    final int column2 = A + j / 3;
                    for (int m = 0; m < this.sumCandidate(s, power).length(); ++m) {
                        if (this.can[column2][row2].subCandidate(this.sumCandidate(s, power).substring(m, m + 1))) {
                            System.out.print("(박스) (" + column2 + ", " + row2 + ") 에 있는 " + this.sumCandidate(s, power).substring(m, m + 1) + "이(가) 제거됬습니다.%n");
                            ++t;
                        }
                    }
                }
            }
            if (T != t) {
                System.out.print(String.valueOf(index) + "번 박스에 ");
                for (int j = 0; j < power; ++j) {
                    System.out.print(Integer.toString(c[j]));
                }
                System.out.print("  총" + power + "개의 유니온 => [" + this.sumCandidate(s, power) + "] 에 의해**********%n%n");
            }
        }
    }
    
    public void OneBoxException(final TextArea contents) {
        System.out.print("메소드명 : OneBox Exception%n%n");
        for (int i = 0; i < 9; ++i) {
            for (int number = 1; number < 10; ++number) {
                int count1 = 0;
                int count2 = 0;
                boolean excute1 = true;
                boolean excute2 = true;
                for (int j = 0; j < 9; ++j) {
                    if (this.can[i][j].getCandidate().indexOf(Integer.toString(number)) != -1) {
                        ++count1;
                    }
                }
                for (int j = 0; j < 9; ++j) {
                    if (this.can[j][i].getCandidate().indexOf(Integer.toString(number)) != -1) {
                        ++count2;
                    }
                }
                final int[] c1 = new int[count1];
                final int[] c2 = new int[count2];
                int t1 = 0;
                int t2 = 0;
                if (1 < count1 && count1 < 4) {
                    for (int k = 0; k < 9; ++k) {
                        if (this.can[i][k].getCandidate().indexOf(Integer.toString(number)) != -1) {
                            c1[t1] = k;
                            ++t1;
                        }
                    }
                    for (int k = 0; k < t1 - 1; ++k) {
                        if (c1[k] / 3 != c1[k + 1] / 3) {
                            excute1 = false;
                        }
                    }
                    if (excute1) {
                        int A = 0;
                        int B = 0;
                        final int S;
                        int s = S = 0;
                        A = this.RectangleAB(this.RectangleNumber(i, c1[0])) / 10;
                        B = this.RectangleAB(this.RectangleNumber(i, c1[0])) % 10;
                        for (int a = A; a < A + 3; ++a) {
                            for (int b = B; b < B + 3; ++b) {
                                boolean excute3 = true;
                                for (int l = 0; l < t1; ++l) {
                                    if (a == i && b == c1[l]) {
                                        excute3 = false;
                                    }
                                }
                                if (excute3 && this.can[a][b].subCandidate(Integer.toString(number))) {
                                    System.out.print("(가로) (" + a + ", " + b + ") 에 있는 " + number + "이(가) 제거됬습니다.%n");
                                    ++s;
                                }
                            }
                        }
                        if (s != S) {
                            System.out.print(String.valueOf(i + 1) + "번째 행에 " + number + "이(가) Box" + this.RectangleNumber(i, c1[0]) + "에 모두 속해있으므로******%n%n");
                        }
                    }
                }
                if (1 < count2 && count2 < 4) {
                    for (int k = 0; k < 9; ++k) {
                        if (this.can[k][i].getCandidate().indexOf(Integer.toString(number)) != -1) {
                            c2[t2] = k;
                            ++t2;
                        }
                    }
                    for (int k = 0; k < t2 - 1; ++k) {
                        if (c2[k] / 3 != c2[k + 1] / 3) {
                            excute2 = false;
                        }
                    }
                    if (excute2) {
                        int A = 0;
                        int B = 0;
                        final int S;
                        int s = S = 0;
                        A = this.RectangleAB(this.RectangleNumber(c2[0], i)) / 10;
                        B = this.RectangleAB(this.RectangleNumber(c2[0], i)) % 10;
                        for (int a = A; a < A + 3; ++a) {
                            for (int b = B; b < B + 3; ++b) {
                                boolean excute3 = true;
                                for (int l = 0; l < t2; ++l) {
                                    if (b == i && a == c2[l]) {
                                        excute3 = false;
                                    }
                                }
                                if (excute3 && this.can[a][b].subCandidate(Integer.toString(number))) {
                                    System.out.print("(세로) (" + a + ", " + b + ") 에 있는 " + number + "이(가) 제거됬습니다.%n");
                                    ++s;
                                }
                            }
                        }
                        if (s != S) {
                            System.out.print(String.valueOf(i + 1) + "번째 열에 " + number + "이(가) Box" + this.RectangleNumber(i, c2[0]) + "에 모두 속해있으므로******%n%n");
                        }
                    }
                }
            }
        }
        System.out.print("%n%n");
    }
    
    public void Xwing(final TextArea contents) {
        System.out.print("메소드명 : Xwing%n%n");
        for (int number = 1; number < 10; ++number) {
            final String[] ifTwo_ROW = { "", "", "", "", "", "", "", "", "" };
            final String[] ifTwo_COLUMN = { "", "", "", "", "", "", "", "", "" };
            for (int i = 0; i < 9; ++i) {
                for (int j = 0; j < 9; ++j) {
                    if (this.can[i][j].getCandidate().indexOf(Integer.toString(number)) != -1) {
                        final String[] array = ifTwo_ROW;
                        final int n = i;
                        array[n] = String.valueOf(array[n]) + Integer.toString(j);
                    }
                    if (this.can[j][i].getCandidate().indexOf(Integer.toString(number)) != -1) {
                        final String[] array2 = ifTwo_COLUMN;
                        final int n2 = i;
                        array2[n2] = String.valueOf(array2[n2]) + Integer.toString(j);
                    }
                }
            }
            for (int i = 0; i < 8; ++i) {
                for (int j = i + 1; j < 9; ++j) {
                    if (ifTwo_ROW[i].length() == 2 && ifTwo_ROW[j].length() == 2 && ifTwo_ROW[i].equals(ifTwo_ROW[j])) {
                        final int S;
                        int s = S = 0;
                        final int a1 = Integer.parseInt(ifTwo_ROW[i].substring(0, 1));
                        final int a2 = Integer.parseInt(ifTwo_ROW[i].substring(1, 2));
                        for (int k = 0; k < 9; ++k) {
                            if (k != i && k != j) {
                                if (this.can[k][a1].subCandidate(Integer.toString(number))) {
                                    System.out.print("(가로) (" + k + ", " + ifTwo_ROW[i].substring(0, 1) + ") 에 있는 " + number + "이(가) 제거됬습니다.%n");
                                    ++s;
                                }
                                if (this.can[k][a2].subCandidate(Integer.toString(number))) {
                                    System.out.print("(가로) (" + k + ", " + ifTwo_ROW[i].substring(1, 2) + ") 에 있는 " + number + "이(가) 제거됬습니다.%n");
                                    ++s;
                                }
                            }
                        }
                        if (S != s) {
                            System.out.print(String.valueOf(i) + ", " + j + "번째 행 " + a1 + a2 + "칸에 " + number + "이(가) 2개씩이므로*********%n");
                        }
                    }
                    if (ifTwo_COLUMN[i].length() == 2 && ifTwo_COLUMN[j].length() == 2 && ifTwo_COLUMN[i].equals(ifTwo_COLUMN[j])) {
                        final int S;
                        int s = S = 0;
                        final int a1 = Integer.parseInt(ifTwo_COLUMN[i].substring(0, 1));
                        final int a2 = Integer.parseInt(ifTwo_COLUMN[i].substring(1, 2));
                        for (int k = 0; k < 9; ++k) {
                            if (k != i && k != j) {
                                if (this.can[a1][k].subCandidate(Integer.toString(number))) {
                                    System.out.print("(세로) (" + k + ", " + ifTwo_COLUMN[i].substring(0, 1) + ") 에 있는 " + number + "이(가) 제거됬습니다.%n");
                                    ++s;
                                }
                                if (this.can[a2][k].subCandidate(Integer.toString(number))) {
                                    System.out.print("(세로) (" + k + ", " + ifTwo_COLUMN[i].substring(1, 2) + ") 에 있는 " + number + "이(가) 제거됬습니다.%n");
                                    ++s;
                                }
                            }
                        }
                        if (S != s) {
                            System.out.print(String.valueOf(i) + ", " + j + "번째 열 " + a1 + a2 + "칸에" + number + "이(가) 2개씩이므로*********%n");
                        }
                    }
                }
            }
        }
        System.out.print("%n%n%n");
    }
    
    public void OneRowColumnException(final TextArea contents) {
        for (int index = 1; index < 10; ++index) {
            final int A = this.RectangleAB(index) / 10;
            final int B = this.RectangleAB(index) % 10;
            for (int number = 1; number < 10; ++number) {
                int count = 0;
                for (int a = A; a < A + 3; ++a) {
                    for (int b = B; b < B + 3; ++b) {
                        if (this.can[a][b].getCandidate().indexOf(Integer.toString(number)) != -1) {
                            ++count;
                        }
                    }
                }
                final int[][] c = new int[count][2];
                int t = 0;
                for (int a2 = A; a2 < A + 3; ++a2) {
                    for (int b2 = B; b2 < B + 3; ++b2) {
                        if (this.can[a2][b2].getCandidate().indexOf(Integer.toString(number)) != -1) {
                            c[t][0] = a2;
                            c[t][1] = b2;
                            ++t;
                        }
                    }
                }
                if (1 < count && count < 4) {
                    boolean excute1 = true;
                    boolean excute2 = true;
                    for (int i = 0; i < count - 1; ++i) {
                        if (c[i][0] != c[i + 1][0]) {
                            excute1 = false;
                        }
                    }
                    for (int i = 0; i < count - 1; ++i) {
                        if (c[i][1] != c[i + 1][1]) {
                            excute2 = false;
                        }
                    }
                    if (excute1) {
                        for (int i = 0; i < 9; ++i) {
                            boolean excute3 = true;
                            for (int j = 0; j < count; ++j) {
                                if (i == c[j][1]) {
                                    excute3 = false;
                                }
                            }
                            if (excute3) {
                                this.can[c[0][0]][i].subCandidate(Integer.toString(number));
                            }
                        }
                    }
                    if (excute2) {
                        for (int i = 0; i < 9; ++i) {
                            boolean excute3 = true;
                            for (int j = 0; j < count; ++j) {
                                if (i == c[j][0]) {
                                    excute3 = false;
                                }
                            }
                            if (excute3) {
                                this.can[i][c[0][1]].subCandidate(Integer.toString(number));
                            }
                        }
                    }
                }
            }
        }
    }
}