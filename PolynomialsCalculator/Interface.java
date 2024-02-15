import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class Interface extends JFrame {
    private final Font titleFont = new Font("Serif", Font.BOLD, 40);
    private final Font polynomialsFont = new Font("Arial", Font.PLAIN, 20);
    private final Font operatorsFont = new Font("Arial", Font.BOLD, 20);
    private JTextField polynomialFieldA;
    private JTextField polynomialFieldB;
    private JTextField polynomialFieldC;
    private JTextField polynomialFieldX;
    private JLabel scoreLabelA;
    private JLabel scoreLabelB;
    private JLabel scoreLabelC;

    Interface() {
        this.body();
    }

    private void body() {
        this.setTitle("POLYNOMIALS CALCULATOR");
        UIManager.put("OptionPane.background", Color.lightGray);
        UIManager.put("Panel.background", Color.lightGray);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setLayout(new BorderLayout());

        createNorthPanel();
        createSouthPanel();
        createCenterPanel();
        createWestPanel();
        createEastPanel();

        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    private void createNorthPanel(){
        JPanel northPanel = new JPanel();
        northPanel.setLayout(new BoxLayout(northPanel, BoxLayout.X_AXIS));
        JLabel title = new JLabel("POLYNOMIALS CALCULATOR");
        title.setForeground(Color.BLACK);
        title.setFont(this.titleFont);
        northPanel.add(Box.createHorizontalGlue());
        northPanel.add(title);
        northPanel.add(Box.createHorizontalGlue());

        this.add(northPanel, BorderLayout.NORTH);
    }

    private void createSouthPanel(){
        JPanel southPanel = new JPanel();
        southPanel.setLayout(new BoxLayout(southPanel, BoxLayout.X_AXIS));

        JButton add = new JButton("+");
        add.setBackground(Color.gray);
        add.setForeground(Color.BLACK);
        add.setFont(this.operatorsFont);
        add.addActionListener(e -> this.addPolynomials());

        JButton subtract = new JButton("-");
        subtract.setBackground(Color.gray);
        subtract.setFont(this.operatorsFont);
        subtract.setForeground(Color.BLACK);
        subtract.addActionListener(e -> this.subtractPolynomials());

        JButton multiply = new JButton("*");
        multiply.setForeground(Color.BLACK);
        multiply.setBackground(Color.gray);
        multiply.setFont(this.operatorsFont);
        multiply.addActionListener(e -> this.multiplyPolynomials());

        JButton valueForA = new JButton("A(x)");
        valueForA.setForeground(Color.BLACK);
        valueForA.setBackground(Color.gray);
        valueForA.setFont(this.operatorsFont);
        valueForA.addActionListener(e -> this.GetValurForX("A"));

        JButton valueForB = new JButton("B(x)");
        valueForB.setForeground(Color.BLACK);
        valueForB.setBackground(Color.gray);
        valueForB.setFont(this.operatorsFont);
        valueForB.addActionListener(e -> this.GetValurForX("B"));

        JButton valueForC = new JButton("C(x)");
        valueForC.setForeground(Color.BLACK);
        valueForC.setBackground(Color.gray);
        valueForC.setFont(this.operatorsFont);
        valueForC.addActionListener(e -> this.GetValurForX("C"));

        southPanel.add(Box.createHorizontalGlue());
        southPanel.add(Box.createHorizontalGlue());
        southPanel.add(add);
        southPanel.add(subtract);
        southPanel.add(multiply);
        southPanel.add(valueForA);
        southPanel.add(valueForB);
        southPanel.add(valueForC);
        southPanel.add(Box.createHorizontalGlue());

        this.add(southPanel, BorderLayout.SOUTH);
    }

    private void createCenterPanel(){
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));

        JPanel panelA = new JPanel();
        panelA.setLayout(new BoxLayout(panelA, BoxLayout.X_AXIS));
        JLabel panelATitle = new JLabel("A(X) = ");
        panelATitle.setForeground(Color.BLACK);
        panelATitle.setFont(this.operatorsFont);
        this.polynomialFieldA = new JTextField("");
        this.polynomialFieldA.setColumns(20);
        this.polynomialFieldA.setMaximumSize(new Dimension(400, this.polynomialFieldA.getPreferredSize().height));
        this.polynomialFieldA.setFont(this.polynomialsFont);
        panelA.add(Box.createHorizontalGlue());
        panelA.add(panelATitle);
        panelA.add(this.polynomialFieldA);
        panelA.add(Box.createHorizontalGlue());

        JPanel panelB = new JPanel();
        panelB.setLayout(new BoxLayout(panelB, BoxLayout.X_AXIS));
        JLabel panelBTitle = new JLabel("B(X) = ");
        panelBTitle.setForeground(Color.BLACK);
        panelBTitle.setFont(this.operatorsFont);
        this.polynomialFieldB = new JTextField("");
        this.polynomialFieldB.setColumns(20);
        this.polynomialFieldB.setMaximumSize(new Dimension(400, this.polynomialFieldB.getPreferredSize().height));
        this.polynomialFieldB.setFont(this.polynomialsFont);
        panelB.add(Box.createHorizontalGlue());
        panelB.add(panelBTitle);
        panelB.add(this.polynomialFieldB);
        panelB.add(Box.createHorizontalGlue());

        JPanel panelC = new JPanel();
        panelC.setLayout(new BoxLayout(panelC, BoxLayout.X_AXIS));
        JLabel panelCTitle = new JLabel("C(X) = ");
        panelCTitle.setForeground(Color.BLACK);
        panelCTitle.setFont(this.operatorsFont);
        this.polynomialFieldC = new JTextField("");
        this.polynomialFieldC.setColumns(20);
        this.polynomialFieldC.setMaximumSize(new Dimension(400, this.polynomialFieldC.getPreferredSize().height));
        this.polynomialFieldC.setFont(this.polynomialsFont);
        this.polynomialFieldC.setEditable(false);
        panelC.add(Box.createHorizontalGlue());
        panelC.add(panelCTitle);
        panelC.add(this.polynomialFieldC);
        panelC.add(Box.createHorizontalGlue());

        centerPanel.add(panelA);
        centerPanel.add(panelB);
        centerPanel.add(panelC);

        this.add(centerPanel, BorderLayout.CENTER);
    }
    private void createWestPanel(){
        JPanel westPanel = new JPanel();
        westPanel.setLayout(new BoxLayout(westPanel, BoxLayout.Y_AXIS));
        JButton CtoA = new JButton("C(X) to A(X)");
        CtoA.setFocusPainted(false);
        CtoA.setForeground(Color.BLACK);
        CtoA.setBackground(Color.gray);
        CtoA.setFont(this.operatorsFont);
        CtoA.addActionListener(e -> {
            if (!this.polynomialFieldC.getText().isEmpty()) {
                String text = this.polynomialFieldC.getText();
                this.polynomialFieldA.setText(text);
                this.polynomialFieldC.setText("");
            }

        });
        JButton CtoB = new JButton("C(X) to B(X)");
        CtoB.setForeground(Color.BLACK);
        CtoB.setBackground(Color.gray);
        CtoB.setFont(this.operatorsFont);
        CtoB.addActionListener(e -> {
            if (!this.polynomialFieldC.getText().isEmpty()) {
                String text = this.polynomialFieldC.getText();
                this.polynomialFieldB.setText(text);
                this.polynomialFieldC.setText("");
            }

        });
        JButton toggle = new JButton("swap A(X),B(X)");
        toggle.setForeground(Color.BLACK);
        toggle.setBackground(Color.gray);
        toggle.setFont(this.operatorsFont);
        toggle.addActionListener(e -> {
            String text1 = this.polynomialFieldA.getText();
            String text2 = this.polynomialFieldB.getText();
            this.polynomialFieldA.setText(text2);
            this.polynomialFieldB.setText(text1);
        });

        westPanel.add(Box.createVerticalGlue());
        westPanel.add(CtoA);
        westPanel.add(CtoB);
        westPanel.add(toggle);
        westPanel.add(Box.createVerticalGlue());
        westPanel.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 20));

        this.add(westPanel, BorderLayout.WEST);
    }

    private void createEastPanel(){
        JPanel eastPanel = new JPanel();
        eastPanel.setLayout(new BoxLayout(eastPanel, BoxLayout.Y_AXIS));

        JPanel PanelX = new JPanel();
        PanelX.setLayout(new BoxLayout(PanelX, BoxLayout.X_AXIS));

        JLabel labelX = new JLabel(" X = ");
        labelX.setForeground(Color.BLACK);
        labelX.setFont(this.operatorsFont);
        this.polynomialFieldX = new JTextField("");
        this.polynomialFieldX.setFont(this.polynomialsFont);
        this.polynomialFieldX.setColumns(3);
        this.polynomialFieldX.setMaximumSize(new Dimension(100, this.polynomialFieldX.getPreferredSize().height));
        PanelX.add(labelX);
        PanelX.add(this.polynomialFieldX);

        this.scoreLabelA = new JLabel("A(X) = ");
        scoreLabelA.setForeground(Color.BLACK);
        this.scoreLabelA.setFont(this.operatorsFont);
        this.scoreLabelB = new JLabel("B(X) = ");
        scoreLabelB.setForeground(Color.BLACK);
        this.scoreLabelB.setFont(this.operatorsFont);
        this.scoreLabelC = new JLabel("C(X) = ");
        scoreLabelC.setForeground(Color.BLACK);
        this.scoreLabelC.setFont(this.operatorsFont);

        JPanel panelAEquals = new JPanel();
        panelAEquals.setLayout(new BoxLayout(panelAEquals, BoxLayout.X_AXIS));

        JPanel panelBEquals = new JPanel();
        panelBEquals.setLayout(new BoxLayout(panelBEquals, BoxLayout.X_AXIS));

        JPanel panelCEquals = new JPanel();
        panelCEquals.setLayout(new BoxLayout(panelCEquals, BoxLayout.X_AXIS));

        eastPanel.add(Box.createVerticalGlue());
        eastPanel.add(PanelX);
        panelAEquals.add(this.scoreLabelA);
        panelBEquals.add(this.scoreLabelB);
        panelCEquals.add(this.scoreLabelC);
        eastPanel.add(panelAEquals);
        eastPanel.add(panelBEquals);
        eastPanel.add(panelCEquals);
        eastPanel.add(Box.createVerticalGlue());
        eastPanel.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 20));

        this.add(eastPanel, BorderLayout.EAST);
    }


    private void GetValurForX(String AorBorC) throws NumberFormatException {
        if (this.polynomialFieldX.getText().isEmpty()) {
            showErrorDialog("Error Acquired: X has no value");
        }
        char[] charTableValue = this.polynomialFieldX.getText().toCharArray();
        int i = 0;
        StringBuilder stringValue = new StringBuilder();
        while(i < charTableValue.length) {
            if (!Character.isDigit(charTableValue[i]) && charTableValue[i] != '.') {
                showErrorDialog("Error Acquired: X must be a number!");
                return;
            }
            stringValue.append(charTableValue[i]);
            i++;
        }

        double Xvalue = Double.parseDouble(stringValue.toString());
        String text;
        Polynomial polynomial;
        double valueForX;
        switch(AorBorC) {
            case "A":
                text = this.polynomialFieldA.getText();
                polynomial = new Polynomial(text);
                if (polynomial.isPolynomial()) {
                    valueForX = Math.round(100.0 * polynomial.valueForX(Xvalue))/100.0;
                    this.scoreLabelA.setText("A(" + Xvalue + ") = " + valueForX);
                } else {
                    showErrorDialog("A(X) must be Polynomial");
                }
                break;
            case "B":
                text = this.polynomialFieldB.getText();
                polynomial = new Polynomial(text);
                if (polynomial.isPolynomial()) {
                    valueForX = Math.round(100.0 * polynomial.valueForX(Xvalue))/100.0;
                    this.scoreLabelB.setText("B(" + Xvalue + ") = " + valueForX);
                } else {
                    showErrorDialog("B(X) must be Polynomial");
                }
                break;
            case "C":
                text = this.polynomialFieldC.getText();
                polynomial = new Polynomial(text);
                if (polynomial.isPolynomial()) {
                    valueForX = Math.round(100.0 * polynomial.valueForX(Xvalue))/100.0;
                    this.scoreLabelC.setText("C(" + Xvalue + ") = " + valueForX);
                } else {
                    showErrorDialog("C(X) must be Polynomial");
                }
                break;
            default:
                System.exit(-1);
                break;
        }

    }

    private static void showErrorDialog(String e) {
        JOptionPane.showMessageDialog(null, e, "Error", JOptionPane.ERROR_MESSAGE);
    }

    private void multiplyPolynomials() {
        Polynomial polynomial1 = new Polynomial(this.polynomialFieldA.getText());
        Polynomial polynomial2 = new Polynomial(this.polynomialFieldB.getText());
        if (polynomial1.isPolynomial() && polynomial2.isPolynomial()) {
            Polynomial newPolynomial = polynomial1.multiply(polynomial2);
            this.polynomialFieldC.setText(newPolynomial.toString());
        } else {
            showErrorDialog("A(X) and B(X) must be Polynomials");
        }

    }

    private void subtractPolynomials() {
        Polynomial polynomial1 = new Polynomial(this.polynomialFieldA.getText());
        Polynomial polynomial2 = new Polynomial(this.polynomialFieldB.getText());
        if (polynomial1.isPolynomial() && polynomial2.isPolynomial()) {
            Polynomial newPolynomial = polynomial1.subtract(polynomial2);
            this.polynomialFieldC.setText(newPolynomial.toString());
        } else {
            showErrorDialog("A(X) and B(X) must be Polynomials");
        }

    }

    private void addPolynomials() {
        Polynomial polynomial1 = new Polynomial(this.polynomialFieldA.getText());
        Polynomial polynomial2 = new Polynomial(this.polynomialFieldB.getText());
        if (polynomial1.isPolynomial() && polynomial2.isPolynomial()) {
            Polynomial newPolynomial = polynomial1.add(polynomial2);
            this.polynomialFieldC.setText(newPolynomial.toString());
        } else {
            showErrorDialog("A(X) and B(X) must be Polynomials");
        }

    }

    public static void main(String[] args) {
        InterfaceRunner runner = new InterfaceRunner();
        SwingUtilities.invokeLater(runner);
    }
}