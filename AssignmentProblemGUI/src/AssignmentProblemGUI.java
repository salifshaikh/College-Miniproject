import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Scanner;

public class AssignmentProblemGUI extends JFrame {

    private JTextField rowsField;
    private JTextField colsField;
    private JTextArea matrixArea;
    public static int colfreeze[] = new int[10];
    public static int rowfreeze[] = new int[10];
    public static int finalsum = 0;
    public static String finalcoordinates;
    public static int count = 0;
    public static int k = 0;

    public static int smallr(int r, int c, int mat[][]) {
        int small = mat[r][0];
        for (int j = 0; j < c; j++) {
            if (small > mat[r][j])
                small = mat[r][j];
        }
        return small;
    }

    public static int smallc(int r, int c, int mat[][]) {
        int small = mat[0][c];
        for (int i = 0; i < r; i++) {
            if (small > mat[i][c])
                small = mat[i][c];
        }
        return small;
    }

    public static int smallest(int length, int mat[]) {
        int small = mat[0];
        for (int i = 0; i < length; i++) {
            if (small > mat[i])
                small = mat[i];
        }
        return small;
    }

    public static int iscolfreeze(int colfreeze[], int j) {
        int a = 1000;
        for (int i = 0; i < colfreeze.length; i++) {
            if (colfreeze[i] == j)
                a = 1;
        }
        return a;
    }

    public static int isrowfreeze(int rowfreeze[], int j) {
        int a = 1111;
        for (int i = 0; i < rowfreeze.length; i++) {
            if (rowfreeze[i] == j)
                a = 1;
        }
        return a;
    }

    public static int noofzero(int r, int c, int mat[][]) {
        int count = 0;
        for (int j = 0; j < c; j++) {
            if (iscolfreeze(colfreeze, j) != 1) {
                if (mat[r][j] == 0)
                    count++;
            }
        }
        return count;
    }

    public static int noofzeroincol(int r, int c, int mat[][]) {
        int count = 0;
        for (int i = 0; i < r; i++) {
            if (isrowfreeze(rowfreeze, i) != 1) {
                if (mat[i][c] == 0)
                    count++;
            }
        }
        return count;
    }

    public static int colcoordinate(int r, int c, int mat[][]) {
        int col = 111;
        for (int j = 0; j < c; j++) {
            if (iscolfreeze(colfreeze, j) == 1)
                continue;
            else if (mat[r][j] == 0)
                col = j;
        }
        return col;
    }

    public static int rowcoordinate(int r, int c, int mat[][]) {
        int row = 111;
        for (int i = 0; i < r; i++) {
            if (isrowfreeze(rowfreeze, i) == 1)
                continue;
            if (mat[i][c] == 0)
                row = i;
        }
        return row;
    }

    public static void rowtraverseoptimize(int r, int c, int mat[][], int mat3[][]) {
        for (int i = 0; i < r; i++) {
            if (noofzero(i, c, mat3) == 1 && isrowfreeze(rowfreeze, i) != 1) {
                System.out.println("(" + i + "," + colcoordinate(i, c, mat3) + ")");
                finalcoordinates += "(" + i + "," + colcoordinate(i, c, mat3) + ")";
                finalsum += mat[i][colcoordinate(i, c, mat3)];
                colfreeze[k] = colcoordinate(i, c, mat3);
                k++;
                count++;
            }
        }
    }

    public static void coltraverseoptimize(int r, int c, int mat[][], int mat3[][]) {
        for (int j = 0; j < c; j++) {
            if (noofzeroincol(r, j, mat3) == 1 && iscolfreeze(colfreeze, j) != 1) {
                System.out.println("(" + rowcoordinate(r, j, mat3) + "," + j + ")");
                finalcoordinates += "(" + rowcoordinate(r, j, mat3) + "," + j + ")";
                finalsum += mat[rowcoordinate(r, j, mat3)][j];
                rowfreeze[k] = rowcoordinate(r, j, mat3);
                k++;
                count++;
            }
        }
    }

    public AssignmentProblemGUI() {
        super("Assignment Problem Solver");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);

        JPanel inputPanel = new JPanel(new FlowLayout());

        JLabel rowsLabel = new JLabel("Rows:");
        rowsField = new JTextField(5);
        JLabel colsLabel = new JLabel("Columns:");
        colsField = new JTextField(5);
        JPanel result = new JPanel(new FlowLayout());


        inputPanel.add(rowsLabel);
        inputPanel.add(rowsField);
        inputPanel.add(colsLabel);
        inputPanel.add(colsField);

        JButton solveButton = new JButton("Solve");
        solveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                solveAssignmentProblem();
            }
        });

        matrixArea = new JTextArea(10, 40);
        matrixArea.setLineWrap(true);


        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(solveButton);

        add(inputPanel, BorderLayout.NORTH);
        add(new JScrollPane(matrixArea), BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    public static void main(String[] args) {
        AssignmentProblemGUI gui = new AssignmentProblemGUI();
        gui.setVisible(true);
    }

    private void solveAssignmentProblem() {
        try {
            int r = Integer.parseInt(rowsField.getText());
            int c = Integer.parseInt(colsField.getText());
            int mat[][] = new int[r][c];

            Scanner scanner = new Scanner(matrixArea.getText());
            for (int i = 0; i < r; i++) {
                for (int j = 0; j < c; j++) {
                    if (scanner.hasNextInt()) {
                        mat[i][j] = scanner.nextInt();
                    } else {
                        JOptionPane.showMessageDialog(this, "Invalid matrix input.");
                        return;
                    }
                }
            }

            int mat2[][] = new int[10][10];
            System.out.println("After row reduction");
            for (int i = 0; i < r; i++) {
                for (int j = 0; j < c; j++) {
                    mat2[i][j] = mat[i][j] - smallr(i, c, mat);
                    System.out.print(mat2[i][j] + " ");
                }
                System.out.println();
            }
            int mat3[][] = new int[10][10];
            System.out.println("After col reduction");
            for (int i = 0; i < r; i++) {
                for (int j = 0; j < c; j++) {
                    mat3[i][j] = mat2[i][j] - smallc(r, j, mat2);
                    System.out.print(mat3[i][j] + " ");
                }
                System.out.println();
            }
            for (int i = 0; i < colfreeze.length; i++)
                colfreeze[i] = 99;
            for (int i = 0; i < rowfreeze.length; i++)
                rowfreeze[i] = 99;
            k = 0;
            rowtraverseoptimize(r, c, mat, mat3);
            k = 0;
            coltraverseoptimize(r, c, mat, mat3);
            k = 0;
            for (int i = 0; i < r; i++) {
                for (int j = 0; j < c; j++) {
                    if (isrowfreeze(rowfreeze, i) == 1 && iscolfreeze(colfreeze, j) == 1)
                        System.out.print("rc ");
                    else if (isrowfreeze(rowfreeze, i) == 1)
                        System.out.print("r  ");
                    else if (iscolfreeze(colfreeze, j) == 1)
                        System.out.print("c  ");
                    else {
                        System.out.print(" . ");
                        k++;
                    }
                }
                System.out.println();
            }
            int leftcount = 0;
            int leftover[] = new int[k];
            for (int i = 0; i < r; i++) {
                for (int j = 0; j < c; j++) {
                    if (isrowfreeze(rowfreeze, i) == 1 && iscolfreeze(colfreeze, j) == 1)
                        continue;
                    else if (isrowfreeze(rowfreeze, i) == 1)
                        continue;
                    else if (iscolfreeze(colfreeze, j) == 1)
                        continue;
                    else {
                        leftover[leftcount] = mat3[i][j];
                        leftcount++;
                    }
                }
            }
            System.out.println("Leftover numbers are ");
            for (int i = 0; i < leftover.length; i++)
                System.out.print(leftover[i] + " ");
            System.out.println();
            System.out.println("Smallest no from leftovers is " + smallest(leftover.length, leftover));
            finalsum = 0;
            finalcoordinates = "Optimized coordinates are ";
            if (smallest(leftover.length, leftover) == 0) {
                rowtraverseoptimize(r, c, mat, mat3);
                coltraverseoptimize(r, c, mat, mat3);
            }
            int sfl = smallest(leftover.length, leftover);
            for (int i = 0; i < r; i++) {
                for (int j = 0; j < c; j++) {
                    if (isrowfreeze(rowfreeze, i) == 1 && iscolfreeze(colfreeze, j) == 1) {
                        mat3[i][j] += sfl;
                        System.out.print(mat3[i][j] + " ");
                    } else if (isrowfreeze(rowfreeze, i) == 1)
                        System.out.print(mat3[i][j] + " ");
                    else if (iscolfreeze(colfreeze, j) == 1)
                        System.out.print(mat3[i][j] + " ");
                    else {
                        mat3[i][j] -= sfl;
                        System.out.print(mat3[i][j] + " ");
                    }
                }
                System.out.println();
            }


            for (int i = 0; i < colfreeze.length; i++)
                colfreeze[i] = 99;
            for (int i = 0; i < rowfreeze.length; i++)
                rowfreeze[i] = 99;

            k = 0;
            rowtraverseoptimize(r, c, mat, mat3);
            k = 0;
            coltraverseoptimize(r, c, mat, mat3);


            System.out.println("Optimised cost is " + finalsum);
            System.out.println(finalcoordinates);
            JOptionPane.showMessageDialog(this, finalcoordinates + "\nOptimized cost is " + finalsum);


        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter valid integers for rows and columns.");
        }
    }
}