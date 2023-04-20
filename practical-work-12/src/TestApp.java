import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class TestApp extends JFrame {
    private int questionIndex = 0;
    private ArrayList<Integer> scores = new ArrayList<>();
    private JLabel questionLabel;
    private JRadioButton[] answerButtons;
    private JButton nextButton;
    private JPanel mainPanel;
    private JPanel answerPanel;
    private JPanel buttonPanel;

    private final String[][] questions = {
            {"Question 1: What is the capital of France?", "Paris", "London", "New York", "Berlin"},
            {"Question 2: 3 + 2 = ?", "5", "6", "32", "23"},
            {"Question 3: Who is the founder of Microsoft?", "Bill Gates", "Steve Jobs", "Mark Zuckerberg", "Elon Musk"}
    };

    public TestApp() {
        super("Test App");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(400, 200));
        setResizable(false);

        questionLabel = new JLabel(questions[questionIndex][0]);

        answerButtons = new JRadioButton[4];
        for (int i = 0; i < 4; i++) {
            answerButtons[i] = new JRadioButton(questions[questionIndex][i+1]);
        }

        ButtonGroup answerGroup = new ButtonGroup();
        for (JRadioButton button : answerButtons) {
            answerGroup.add(button);
        }

        nextButton = new JButton("Next");
        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (answerGroup.getSelection() == null) {
                    JOptionPane.showMessageDialog(TestApp.this, "Please select an answer", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                int selectedAnswer = -1;
                for (int i = 0; i < 4; i++) {
                    if (answerButtons[i].isSelected()) {
                        selectedAnswer = i;
                        break;
                    }
                }

                if (questions[questionIndex][selectedAnswer+1].equals(questions[questionIndex][1])) {
                    scores.add(5);
                } else {
                    scores.add(2);
                }

                questionIndex++;
                if (questionIndex == questions.length) {
                    double averageScore = scores.stream().mapToInt(Integer::intValue).average().orElse(0.0);
                    JOptionPane.showMessageDialog(TestApp.this, "Your average score is: " + averageScore);
                    System.exit(0);
                }

                questionLabel.setText(questions[questionIndex][0]);
                for (int i = 0; i < 4; i++) {
                    answerButtons[i].setText(questions[questionIndex][i+1]);
                    answerButtons[i].setSelected(false);
                }
            }
        });

        answerPanel = new JPanel(new GridLayout(4, 1));
        for (JRadioButton button : answerButtons) {
            answerPanel.add(button);
        }

        buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(nextButton);

        mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(questionLabel, BorderLayout.NORTH);
        mainPanel.add(answerPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        setContentPane(mainPanel);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(TestApp::new);
    }
}